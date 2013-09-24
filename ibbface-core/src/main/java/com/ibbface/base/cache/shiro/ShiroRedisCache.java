/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) ShiroCache.java 2013-09-15 13:03
 */

package com.ibbface.base.cache.shiro;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * This class is the implementation of {@code Cache} in the shiro framework using {@code redis}.
 *
 * @author Fuchun
 * @since 1.0
 */
public class ShiroRedisCache<K, V> implements Cache<K, V> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShiroRedisCache.class);
    private static final byte[][] EMPTY_BYTES_ARRAY = new byte[0][0];
    private static final long WAIT_FOR_LOCK = 300;
    private static final int PAGE_SIZE = 128;

    /* this cache name. */
    private final String name;
    private final RedisTemplate<K, V> template;
    /* redis set key name. */
    private final byte[] setName;
    /* this cache lock name. */
    private final byte[] cacheLockName;
    /* this cache expires time (second) */
    private final long expiration;

    public ShiroRedisCache(String name, RedisTemplate<K, V> template, long expiration) {
        Assert.hasText(name, "non-empty cache name is required");
        this.name = name;
        this.template = template;
        this.expiration = expiration;

        StringRedisSerializer stringSerializer = new StringRedisSerializer();

        this.setName = stringSerializer.serialize(name + "~key");
        this.cacheLockName = stringSerializer.serialize(name + "~lock");
    }

    /**
     * Returns the Cached value stored under the specified {@code key} or
     * {@code null} if there is no Cache entry for that {@code key}.
     *
     * @param key the key that the value was previous added with
     * @return the cached object or {@code null} if there is no entry for the specified {@code key}
     * @throws org.apache.shiro.cache.CacheException
     *          if there is a problem accessing the underlying cache system
     */
    @Override
    @SuppressWarnings("unchecked")
    public V get(final K key) throws CacheException {
        try {
            return (V) template.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    waitForLock(connection);
                    byte[] bs = connection.get(computeKey(key));
                    return deserialize(bs);
                }
            }, true);
        } catch (Exception ex) {
            throw new CacheException(ex);
        }
    }

    /**
     * Adds a Cache entry.
     *
     * @param key   the key used to identify the object being stored.
     * @param value the value to be stored in the cache.
     * @return the previous value associated with the given {@code key} or {@code null} if there was previous value
     * @throws org.apache.shiro.cache.CacheException
     *          if there is a problem accessing the underlying cache system
     */
    @Override
    public V put(K key, final V value) throws CacheException {
        final byte[] k = computeKey(key);
        try {
            return template.execute(new RedisCallback<V>() {
                @Override
                public V doInRedis(RedisConnection connection) throws DataAccessException {
                    waitForLock(connection);
                    byte[] bs = connection.get(k);
                    connection.multi();
                    connection.set(k, serialize(value));
                    connection.zAdd(setName, 0, k);

                    if (expiration > 0) {
                        connection.expire(k, expiration);
                        // update the expiration of the set of keys as well
                        connection.expire(setName, expiration);
                    }
                    connection.exec();
                    return deserialize(bs);
                }
            }, true);
        } catch (Exception ex) {
            throw new CacheException(ex);
        }
    }

    /**
     * Remove the cache entry corresponding to the specified key.
     *
     * @param key the key of the entry to be removed.
     * @return the previous value associated with the given {@code key} or {@code null} if there was previous value
     * @throws org.apache.shiro.cache.CacheException
     *          if there is a problem accessing the underlying cache system
     */
    @Override
    public V remove(K key) throws CacheException {
        final byte[] k = computeKey(key);
        try {
            return template.execute(new RedisCallback<V>() {
                @Override
                public V doInRedis(RedisConnection connection) throws DataAccessException {
                    waitForLock(connection);
                    byte[] bs = connection.get(k);

                    connection.del(k);
                    // remove key from set
                    connection.zRem(setName, k);
                    return deserialize(bs);
                }
            }, true);
        } catch (Exception ex) {
            throw new CacheException(ex);
        }
    }

    /**
     * Clear all entries from the cache.
     *
     * @throws org.apache.shiro.cache.CacheException
     *          if there is a problem accessing the underlying cache system
     */
    @Override
    public void clear() throws CacheException {
        try {
            // need to del each key individually
            template.execute(new RedisCallback<Object>() {
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    // another clear is on-going
                    if (connection.exists(cacheLockName)) {
                        return null;
                    }

                    try {
                        connection.set(cacheLockName, cacheLockName);

                        int offset = 0;
                        boolean finished;

                        do {
                            // need to paginate the keys
                            Set<byte[]> keys = connection.zRange(setName, (offset) * PAGE_SIZE, (offset + 1) * PAGE_SIZE
                                    - 1);
                            finished = keys.size() < PAGE_SIZE;
                            offset++;
                            if (!keys.isEmpty()) {
                                connection.del(keys.toArray(EMPTY_BYTES_ARRAY));
                            }
                        } while (!finished);

                        connection.del(setName);
                        return null;

                    } finally {
                        connection.del(cacheLockName);
                    }
                }
            }, true);
        } catch (Exception ex) {
            throw new CacheException(ex);
        }
    }

    /**
     * Returns the number of entries in the cache.
     *
     * @return the number of entries in the cache.
     */
    @Override
    public int size() {
        try {
            return template.execute(new RedisCallback<Integer>() {
                @Override
                public Integer doInRedis(RedisConnection connection) throws DataAccessException {
                    Long count = connection.zCount(setName, 0, 0);
                    return count == null ? 0 : count.intValue();
                }
            }, true);
        } catch (Exception ex) {
            return 0;
        }
    }

    /**
     * Returns a view of all the keys for entries contained in this cache.
     *
     * @return a view of all the keys for entries contained in this cache.
     */
    @Override
    public Set<K> keys() {
        try {
            return template.execute(new RedisCallback<Set<K>>() {
                @Override
                public Set<K> doInRedis(RedisConnection connection) throws DataAccessException {
                    // another clear is on-going
                    if (connection.exists(cacheLockName)) {
                        return null;
                    }

                    Set<K> result = Sets.newLinkedHashSet();
                    try {
                        connection.set(cacheLockName, cacheLockName);

                        int offset = 0;
                        boolean finished;

                        do {
                            // need to paginate the keys
                            Set<byte[]> keys = connection.zRange(setName, (offset) * PAGE_SIZE, (offset + 1) * PAGE_SIZE
                                    - 1);
                            finished = keys.size() < PAGE_SIZE;
                            offset++;
                            if (!keys.isEmpty()) {
                                for (byte[] bytes : keys) {
                                    result.add(deserializeKey(bytes));
                                }
                            }
                        } while (!finished);
                        return result;

                    } finally {
                        connection.del(cacheLockName);
                    }
                }
            }, true);
        } catch (Exception ex) {
            LOGGER.error("ShiroRedisCache#keys() is error, will be return empty Set.", ex);
        }
        return ImmutableSet.of();
    }

    /**
     * Returns a view of all of the values contained in this cache.
     *
     * @return a view of all of the values contained in this cache.
     */
    @Override
    public Collection<V> values() {
        try {
            return template.execute(new RedisCallback<Set<V>>() {
                @Override
                public Set<V> doInRedis(RedisConnection connection) throws DataAccessException {
                    // another clear is on-going
                    if (connection.exists(cacheLockName)) {
                        return null;
                    }

                    Set<V> result = Sets.newLinkedHashSet();
                    try {
                        connection.set(cacheLockName, cacheLockName);

                        int offset = 0;
                        boolean finished;

                        do {
                            // need to paginate the keys
                            Set<byte[]> keys = connection.zRange(setName, (offset) * PAGE_SIZE, (offset + 1) * PAGE_SIZE
                                    - 1);
                            finished = keys.size() < PAGE_SIZE;
                            offset++;
                            if (!keys.isEmpty()) {
                                List<byte[]> values = connection.mGet(keys.toArray(new byte[0][0]));
                                if (values == null || values.isEmpty()) {
                                    continue;
                                }
                                for (byte[] bytes : values) {
                                    result.add(deserialize(bytes));
                                }
                            }
                        } while (!finished);
                        return result;

                    } finally {
                        connection.del(cacheLockName);
                    }
                }
            }, true);
        } catch (Exception ex) {
            LOGGER.error("ShiroRedisCache#keys() is error, will be return empty Set.", ex);
        }
        return ImmutableSet.of();
    }

    public String getName() {
        return name;
    }

    public RedisTemplate<K, V> getTemplate() {
        return template;
    }

    @SuppressWarnings("unchecked")
    private V deserialize(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        return (V) template.getValueSerializer().deserialize(bytes);
    }

    @SuppressWarnings("unchecked")
    private byte[] serialize(V value) {
        if (value == null) {
            return null;
        }
        return ((RedisSerializer) template.getValueSerializer()).serialize(value);
    }

    @SuppressWarnings("unchecked")
    private K deserializeKey(byte[] key) {
        if (key == null || key.length == 0) {
            return null;
        }
        return (K) template.getKeySerializer().deserialize(key);
    }

    @SuppressWarnings("unchecked")
    private byte[] computeKey(K key) {
        if (key == null) {
            return null;
        }
        return ((RedisSerializer) template.getKeySerializer()).serialize(key);
    }

    private boolean waitForLock(RedisConnection connection) {
        boolean retry;
        boolean foundLock = false;
        do {
            retry = false;
            if (connection.exists(cacheLockName)) {
                foundLock = true;
                try {
                    Thread.sleep(WAIT_FOR_LOCK);
                } catch (InterruptedException ex) {
                    // ignore
                }
                retry = true;
            }
        } while (retry);
        return foundLock;
    }
}
