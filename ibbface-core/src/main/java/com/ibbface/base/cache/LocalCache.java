/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) LocalCache.java 2013-08-10 23:49
 */

package com.ibbface.base.cache;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Monitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author Fuchun
 * @since 1.0
 */
public class LocalCache<K, V> implements Cache<K, V> {

    /**
     * 日志记录器。
     */
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * 本地缓存可存入的对象的最大数量的默认值。
     */
    static final int MAXIMUM_CAPACITY = 1 << 30;
    /**
     * 本地缓存自对象写入缓存后的默认的过期时间（秒）。
     */
    public static final int DEFAULT_EXPIRE_AFTER_WRITE = 300;
    /**
     * 本地缓存自对象被访问后的默认的过期时间（秒）。
     */
    public static final int DEFAULT_EXPIRE_AFTER_ACCESS = -1;

    public static final int DEFAULT_MAXIMUM_SIZE = 1000;

    private final List<RemovalListener<K, V>> removalListeners = Lists.newArrayList();
    private final Monitor cacheMonitor = new Monitor();
    private DelayQueue<DelayElement<Entry<K, V>>> queue = new DelayQueue<DelayElement<Entry<K, V>>>();

    private ConcurrentMap<K, V> cacheMap;

    /**
     * 本地缓存可容纳对象的最大数量。
     */
    private int maximumSize;
//    /**
//     * 本地缓存自写入后的过期时间（秒）。
//     */
//    private int expireAfterWrite = DEFAULT_EXPIRE_AFTER_WRITE;
//    /**
//     * 本地缓存自访问后的过期时间（秒）。
//     */
//    private int expireAfterAccess = DEFAULT_EXPIRE_AFTER_ACCESS;
    /**
     * 本地缓存的初始容量。
     */
    private int initialCapacity;

    private long expireAfterAccessNanos;
    private long expireAfterWriteNanos;

    private final String cacheName;
    private String threadName;
    private boolean isRunning = false;

    public LocalCache(String cacheName) {
        this.cacheName = cacheName;
    }

    public LocalCache(CacheBuilder<? super K, ? super V> builder) {
        cacheName = builder.getCacheName();
        maximumSize = builder.getMaximumSize();
        if (maximumSize <= 0) {
            maximumSize = DEFAULT_MAXIMUM_SIZE;
        }
        initialCapacity = Math.min(builder.getInitialCapacity(), MAXIMUM_CAPACITY);
        expireAfterAccessNanos = builder.getExpireAfterAccessNanos();
        expireAfterWriteNanos = builder.getExpireAfterWriteNanos();

        if (builder.getRemovalListeners() != null) {
            for (RemovalListener<K, V> listener : builder.getRemovalListeners()) {
                removalListeners.add(listener);
            }
        }
    }

    /**
     * 启动本地缓存。
     */
    public void start() {
        if (isRunning) {
            return;
        }

        isRunning = true;
        cacheMap = new ConcurrentHashMap<K, V>(initialCapacity);
        DaemonRunnable daemonRunnable = new DaemonRunnable();
        Thread cacheThread = new Thread(daemonRunnable);
        threadName = cacheName == null ? "LocalCache" : String.format("%s_LocalCache", cacheName);
        cacheThread.setName(threadName);
        cacheThread.setDaemon(true);
        cacheThread.start();
        LOGGER.info("{} started.", threadName);

        Runtime.getRuntime().addShutdownHook(new CleanLocalCacheHook(this));
    }

    /**
     * 停止本地缓存。
     */
    public void stop() {
        if (!isRunning) {
            return;
        }
        queue.clear();
        cacheMap.clear();
        isRunning = false;
        LOGGER.info("{} stopped.", threadName);
    }

    protected void checkRunning() {
        if (!isRunning) {
            throw new IllegalStateException(String.format("LocalCache not start yet."));
        }
    }

    /**
     * @see com.ibbface.base.cache.Cache#getIfPresent(Object)
     */
    @Override
    public V getIfPresent(K key) {
        checkRunning();
        V value = cacheMap.get(key);
        if (value != null && expireAfterAccessNanos > 0) {
            put(key, value, expireAfterAccessNanos, NANOSECONDS);
        }
        return value;
    }


    /**
     * @see com.ibbface.base.cache.Cache#get(Object, java.util.concurrent.Callable)
     */
    @Override
    public V get(K key, Callable<? extends V> valueLoader) throws ExecutionException {
        // use default expireAfterWriteNanos
        return get(key, 0, valueLoader);
    }

    /**
     * @see com.ibbface.base.cache.Cache#get(Object, int,
     *      java.util.concurrent.Callable)
     */
    @Override
    public V get(K key, int seconds, Callable<? extends V> valueLoader) throws ExecutionException {
        checkRunning();
        TimeUnit unit;
        long time;
        if (seconds <= 0) {
            time = expireAfterWriteNanos;
            unit = NANOSECONDS;
        } else {
            time = seconds;
            unit = SECONDS;
        }
        cacheMonitor.enter();
        V value = null;
        try {
            value = cacheMap.get(key);
            if (value != null) {
                if (expireAfterAccessNanos > 0) {
                    putInternal(key, value, expireAfterAccessNanos, NANOSECONDS);
                }
                return value;
            }
            if ((value = valueLoader.call()) == null) {
                throw new IllegalArgumentException("The valueLoader returned value must not be null.");
            }
            removeElementsIfMaximum();
            putInternal(key, value, time, unit);
        } catch (Exception ex) {
            throw new ExecutionException(ex);
        } finally {
            cacheMonitor.leave();
        }
        return value;
    }

    /**
     * @see com.ibbface.base.cache.Cache#put(Object, Object)
     */
    @Override
    public void put(K key, V value) {
//        put(key, value, getExpireAfterWrite());
        put(key, value, expireAfterWriteNanos, NANOSECONDS);

    }

    /**
     * @see com.ibbface.base.cache.Cache#put(Object, Object, int)
     */
    @Override
    public void put(K key, V value, int seconds) {
        put(key, value, seconds, SECONDS);
    }

    void put(K key, V value, long time, TimeUnit unit) {
        checkRunning();
        cacheMonitor.enter();
        try {
            removeElementsIfMaximum();
            putInternal(key, value, time, unit);
        } finally {
            cacheMonitor.leave();
        }
    }

    /**
     * @see com.ibbface.base.cache.Cache#putIfAbsent(Object, Object)
     */
    @Override
    public V putIfAbsent(K key, V value) {
        checkRunning();
        cacheMonitor.enter();
        try {
            V oldVal = cacheMap.get(key);
            if (oldVal != null) {
                return oldVal;
            }
            removeElementsIfMaximum();
            putInternal(key, value, expireAfterWriteNanos, NANOSECONDS);
        } finally {
            cacheMonitor.leave();
        }
        return null;
    }

    /**
     * @see com.ibbface.base.cache.Cache#asMap()
     */
    @Override
    public ConcurrentMap<K, V> asMap() {
        throw new UnsupportedOperationException();
    }

    /**
     * @see com.ibbface.base.cache.Cache#remove(Object)
     */
    @Override
    public void remove(K key) {
        checkRunning();
        cacheMonitor.enter();
        try {
            V oldValue = cacheMap.remove(key);
            if (oldValue != null) {
                removeQueueElement(key, RemovalCause.EXPLICIT);
            }
        } finally {
            cacheMonitor.leave();
        }
    }

    /**
     * @see com.ibbface.base.cache.Cache#removeAll(K[])
     */
    @Override
    public void removeAll(K... keys) {
        if (keys == null || keys.length == 0) {
            return;
        }
        cacheMonitor.enter();
        try {
            removeQueueElements(keys, RemovalCause.EXPLICIT);

            for (K key : keys) {
                cacheMap.remove(key);
            }
        } finally {
            cacheMonitor.leave();
        }
    }

    /**
     * @see com.ibbface.base.cache.Cache#clear()
     */
    @Override
    public void clear() {
        checkRunning();
        cacheMonitor.enter();
        try {
            cacheMap.clear();
            queue.clear();
        } finally {
            cacheMonitor.leave();
        }
    }

    /**
     * 返回本地缓存的名称。
     */
    public String getCacheName() {
        return cacheName;
    }

    /**
     * @see com.ibbface.base.cache.Cache#size()
     */
    @Override
    public int size() {
        return queue.size();
    }

    /**
     * @see com.ibbface.base.cache.Cache#shutdown()
     */
    @Override
    public void shutdown() {
        stop();
    }

    protected void removeQueueElement(final K key, final RemovalCause cause) {
        for (DelayElement<Entry<K, V>> de : queue) {
            if (Objects.equal(de.getElement().getKey(), key)) {
                if (queue.remove(de)) {
                    fireRemovalEvent(de.getElement(), cause);
                }
                break;
            }
        }
    }

    protected void removeQueueElements(final K[] keys, final RemovalCause cause) {
        List<DelayElement<Entry<K, V>>> deList = Lists.newArrayListWithCapacity(keys.length);
        for (DelayElement<Entry<K, V>> de : queue) {
            for (K key : keys) {
                if (Objects.equal(de.getElement().getKey(), key)) {
                    deList.add(de);
                    break;
                }
            }
        }
        if (queue.removeAll(deList)) {
            for (DelayElement<Entry<K, V>> de : deList) {
                fireRemovalEvent(de.getElement(), cause);
            }
        }
    }

    /**
     * 由于缓存达到最大上限而自动移除即将过期的缓存元素（默认移除5个）。
     */
    protected void removeElementsIfMaximum() {
        if (queue.size() < getMaximumSize()) {
            return;
        }
        int factor = 5;
        List<Entry<K, V>> removedEntries = Lists.newArrayList();
        for (int i = 0; i < factor; i++) {
            DelayElement<Entry<K, V>> de = queue.peek();
            if (de != null) {
                removedEntries.add(de.getElement());
                queue.remove(de);
            }
        }
        if (removedEntries.size() > 0) {
            for (Entry<K, V> entry : removedEntries) {
                cacheMap.remove(entry.getKey());
                fireRemovalEvent(entry, RemovalCause.SIZE);
            }
        }
    }

    protected void putWithSeconds(K key, V value, int seconds) {
        putInternal(key, value, seconds, SECONDS);
    }

    void putInternal(K key, V value, long time, TimeUnit unit) {
        V oldVal = cacheMap.put(key, value);
        if (oldVal != null) {
            removeQueueElement(key, RemovalCause.REPLACED);
        }

        long nanoTimes;
        if (unit == NANOSECONDS) {
            nanoTimes = time;
        } else {
            nanoTimes = NANOSECONDS.convert(time, unit);
        }
        Entry<K, V> entry = new LocalCacheEntry<K, V>(key, value);
        queue.put(DelayElement.create(entry, nanoTimes));
    }

    protected void fireRemovalEvent(final Entry<K, V> entry, final RemovalCause cause) {
        if (entry == null || cause == null || removalListeners.isEmpty()) {
            return;
        }
        List<RemovalListener<K, V>> listeners;
        synchronized (removalListeners) {
            listeners = Lists.newArrayList(removalListeners);
        }
        RemovalNotification<K, V> notification = new RemovalNotification<K, V>(entry.getKey(),
                entry.getValue(), cause);
        for (RemovalListener<K, V> listener : listeners) {
            listener.onRemoval(notification);
        }
    }

    /**
     * 返回本地缓存可容纳对象的最大数量。
     */
    public int getMaximumSize() {
        return maximumSize;
    }

    /**
     * 设置本地缓存可容纳对象的最大数量。
     */
    public void setMaximumSize(int maximumSize) {
        this.maximumSize = maximumSize;
    }

//    /**
//     * 返回本地缓存自定入缓存后的过期时间（秒）。
//     */
//    public int getExpireAfterWrite() {
//        return expireAfterWrite;
//    }
//
//    /**
//     * 设置本地缓存自定入缓存后的过期时间（秒）。
//     */
//    public void setExpireAfterWrite(int expireAfterWrite) {
//        this.expireAfterWrite = expireAfterWrite;
//    }
//
//    /**
//     * 返回本地缓存自访问后的过期时间（秒）。
//     */
//    public int getExpireAfterAccess() {
//        return expireAfterAccess;
//    }
//
//    /**
//     * 设置本地缓存自访问后的过期时间（秒）。
//     */
//    public void setExpireAfterAccess(int expireAfterAccess) {
//        this.expireAfterAccess = expireAfterAccess;
//    }

    /**
     * 返回本地缓存初始容量。
     */
    public int getInitialCapacity() {
        return initialCapacity;
    }

    /**
     * 设置本地缓存初始容量。
     */
    public void setInitialCapacity(int initialCapacity) {
        this.initialCapacity = initialCapacity;
    }

    /**
     * 添加本地缓存移除事件监听。
     */
    public void addCacheRemovalListener(RemovalListener<K, V> listener) {
        if (listener != null) {
            removalListeners.add(listener);
        }
    }

    /**
     * 删除指定的本地缓存的移除事件监听。
     */
    public void removeCacheRemovalListener(RemovalListener<K, V> listener) {
        if (listener != null) {
            removalListeners.remove(listener);
        }
    }

    /**
     * 返回缓存 map 的副本。
     */
    public Map<K, V> asMapInternal() {
        return ImmutableMap.copyOf(cacheMap);
    }

    static class LocalCacheEntry<K, V> implements Entry<K, V>, Serializable {

        private static final long serialVersionUID = 1L;

        final K key;
        final V value;

        LocalCacheEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int hashCode() {
            K k = getKey();
            V v = getValue();
            return ((k == null) ? 0 : k.hashCode()) ^ ((v == null) ? 0 : v.hashCode());
        }

        @Override
        public boolean equals(Object object) {
            if (object instanceof Entry) {
                Entry<?, ?> that = (Entry<?, ?>) object;
                return Objects.equal(this.getKey(), that.getKey())
                        && Objects.equal(this.getValue(), that.getValue());
            }
            return false;
        }

        @Override
        public String toString() {
            return String.format("%s=%s", getKey(), getValue());
        }
    }

    private class DaemonRunnable implements Runnable {

        @Override
        public void run() {
            while (isRunning) {
                try {
                    DelayElement<Entry<K, V>> element = queue.take();
                    if (element != null) {
                        Entry<K, V> entry = element.getElement();
                        cacheMap.remove(entry.getKey(), entry.getValue());
                        fireRemovalEvent(entry, RemovalCause.EXPIRED);
                    }
                } catch (InterruptedException ex) {
                    LOGGER.error("The daemon thread was interrupted in local cache: ", ex);
                }
            }
        }
    }

    private class CleanLocalCacheHook extends Thread {

        private final LocalCache localCache;

        private CleanLocalCacheHook(LocalCache localCache) {
            this.localCache = localCache;
        }

        @Override
        public void run() {
            if (localCache != null)
                localCache.shutdown();
        }
    }
}
