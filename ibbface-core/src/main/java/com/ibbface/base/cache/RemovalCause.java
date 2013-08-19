/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) RemovalCause.java 2013-08-10 23:49
 */

package com.ibbface.base.cache;

/**
 * 定义缓存被移除的原因枚举。
 *
 * @author Fuchun
 * @since 1.0
 */
public enum RemovalCause {

    /**
     * 表示缓存中的实体是被用户手动调用以下方法移除的： {@link Cache#remove(Object)},
     * {@link Cache#removeAll(Object...)}, {@link Cache#clear()}。
     */
    EXPLICIT {
        @Override
        public boolean wasEvicted() {
            return false;
        }
    },

    /**
     * 表示缓存中的实体是被用户手动调用以下方法移除的： {@link Cache#put(Object, Object)}、
     * {@link Cache#put(Object, Object, int)} 或 {@link Cache#putIfAbsent(Object, Object)}。
     */
    REPLACED {
        @Override
        public boolean wasEvicted() {
            return false;
        }
    },

    /**
     * The entry's expiration timestamp has passed. This can occur when using
     * {@link CacheBuilder#expireAfterWrite} or {@link CacheBuilder#expireAfterAccess}.
     */
    EXPIRED {
        @Override
        public boolean wasEvicted() {
            return true;
        }
    },

    /**
     * The entry was evicted due to size constraints. This can occur when using
     * {@link CacheBuilder#maximumSize} or {@link CacheBuilder#getMaximumSize()}.
     */
    SIZE {
        @Override
        public boolean wasEvicted() {
            return true;
        }
    };

    /**
     * Returns {@code true} if there was an automatic removal due to eviction (the cause
     * is neither {@link #EXPLICIT} nor {@link #REPLACED}).
     */
    public abstract boolean wasEvicted();
}
