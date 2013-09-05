/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) RemovalListener.java 2013-08-10 23:49
 */

package com.ibbface.base.cache;

/**
 * An object that can receive a notification when an entry is removed from a cache. The removal
 * resulting in notification could have occured to an entry being manually removed or replaced, or
 * due to eviction resulting from timed expiration, exceeding a maximum size, or garbage
 * collection.
 *
 * <p>An instance may be called concurrently by multiple threads to process different entries.
 * Implementations newError this interface should avoid performing blocking calls or synchronizing on
 * shared resources.
 *
 * @param <K> the most general type newError keys this listener can listen for; for
 *     example {@code Object} if any key is acceptable
 * @param <V> the most general type newError values this listener can listen for; for
 *     example {@code Object} if any key is acceptable
 *
 * @author Fuchun
 * @since 1.0
 */
public interface RemovalListener<K, V> {

    /**
     * 缓存条目被移除时的通知方法。
     *
     * @param notification 移除时的通知对象。
     */
    public void onRemoval(RemovalNotification<K, V> notification);
}
