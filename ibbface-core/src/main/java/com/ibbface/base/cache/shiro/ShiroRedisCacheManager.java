/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) ShiroRedisCacheManager.java 2013-09-15 14:09
 */

package com.ibbface.base.cache.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Fuchun
 * @since 1.0
 */
public class ShiroRedisCacheManager<K, V> implements CacheManager {

    // fast lookup by name map
    private final ConcurrentMap<String, Cache<K, V>> caches = new ConcurrentHashMap<String, Cache<K, V>>();
    private final Collection<String> names = Collections.unmodifiableSet(caches.keySet());
    private final RedisTemplate<K, V> template;

    // 0 - never expire
    private long defaultExpiration = 0;
    private Map<String, Long> expires = null;

    public ShiroRedisCacheManager(RedisTemplate<K, V> template) {
        this.template = template;
    }

    /**
     * Acquires the cache with the specified <code>name</code>.  If a cache does not yet exist
     * with that name, a new one will be created with that name and returned.
     *
     * @param name the name of the cache to acquire.
     * @return the Cache with the given name
     * @throws org.apache.shiro.cache.CacheException
     *          if there is an error acquiring the Cache instance.
     */
    @Override
    @SuppressWarnings("unchecked")
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        Cache c = caches.get(name);
        if (c == null) {
            long expiration = computeExpiration(name);
            c = new ShiroRedisCache(name, template, expiration);
            caches.put(name, c);
        }
        return c;
    }

    private long computeExpiration(String name) {
        Long expiration = null;
        if (expires != null) {
            expiration = expires.get(name);
        }
        return (expiration != null ? expiration : defaultExpiration);
    }

    public Collection<String> getCacheNames() {
        return names;
    }

    /**
     * Sets the default expire time (in seconds).
     *
     * @param defaultExpireTime time in seconds.
     */
    public void setDefaultExpiration(long defaultExpireTime) {
        this.defaultExpiration = defaultExpireTime;
    }

    /**
     * Sets the expire time (in seconds) for cache regions (by key).
     *
     * @param expires time in seconds
     */
    public void setExpires(Map<String, Long> expires) {
        this.expires = (expires != null ? new ConcurrentHashMap<String, Long>(expires) : null);
    }
}
