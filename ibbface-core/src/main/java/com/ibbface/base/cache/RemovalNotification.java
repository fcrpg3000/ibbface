/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) RemovalNotification.java 2013-08-10 23:49
 */

package com.ibbface.base.cache;

import com.google.common.base.Objects;

import java.io.Serializable;
import java.util.Map;

/**
 * 缓存条目被移除时的通知。包含了缓存的键、值和被移除的原因({@link RemovalCause})。
 *
 * @author Fuchun
 * @since 1.0
 */
public class RemovalNotification<K, V> implements Map.Entry<K, V>, Serializable {
    private static final long serialVersionUID = 1L;

    private final K key;
    private final V value;
    private final RemovalCause cause;

    protected RemovalNotification(K key, V value, RemovalCause cause) {
        this.key = key;
        this.value = value;
        this.cause = cause;
    }

    public RemovalCause getCause() {
        return cause;
    }

    /**
     * @see java.util.Map.Entry#getKey()
     */
    @Override
    public K getKey() {
        return key;
    }

    /**
     * @see java.util.Map.Entry#getValue()
     */
    @Override
    public V getValue() {
        return value;
    }

    /**
     * @see java.util.Map.Entry#setValue(Object)
     */
    @Override
    public V setValue(V value) {
        throw new UnsupportedOperationException();
    }

    /**
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        K k = getKey();
        V v = getValue();
        return ((k == null) ? 0 : k.hashCode()) ^ ((v == null) ? 0 : v.hashCode());
    }

    /**
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object object) {
        if (object instanceof Map.Entry) {
            Map.Entry<?, ?> that = (Map.Entry<?, ?>) object;
            return Objects.equal(this.getKey(), that.getKey())
                    && Objects.equal(this.getValue(), that.getValue());
        }
        return false;
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return getKey() + "=" + getValue();
    }
}
