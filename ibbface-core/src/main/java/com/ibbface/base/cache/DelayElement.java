/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) DelayElement.java 2013-08-10 23:49
 */

package com.ibbface.base.cache;

import com.google.common.base.Objects;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Fuchun
 * @since 1.0
 */
class DelayElement<T> implements Delayed {

    private static final long NANO_ORIGIN = System.nanoTime();

    static long now() {
        return System.nanoTime() - NANO_ORIGIN;
    }

    private static final AtomicLong SEQUENCE = new AtomicLong(0);

    /**
     * 构建一个指定元素和过期时间的 {@code DelayElement} 对象。
     *
     * @param <T>
     * @param element 元素。
     * @param timeout 过期时间，单位：纳秒。
     * @return 新创建的延迟元素对象。
     */
    public static <T> DelayElement<T> create(T element, long timeout) {
        return new DelayElement<T>(element, timeout);
    }

    private final long id;
    private final long time;
    private final T element;

    DelayElement(T element, long timeout) {
        this.element = element;
        this.time = now() + timeout;
        id = SEQUENCE.getAndIncrement();
    }

    /**
     * 返回缓存的元素对象。
     */
    public T getElement() {
        return element;
    }

    /** 返回元素对象保存的时间。 */
    public long getTime() {
        return time;
    }

    /** 返回延迟对象的序列号（唯一Id）。 */
    public long getId() {
        return id;
    }

    @Override
    public long getDelay(TimeUnit timeUnit) {
        return timeUnit.convert(time - now(), TimeUnit.NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        if (o == this) {
            return  0;
        }
        if (o instanceof DelayElement) {
            DelayElement<?> that = (DelayElement<?>) o;
            long diff = time - that.getTime();
            if (diff < 0) {
                return  -1;
            } else if (diff > 0) {
                return  1;
            } else if (id < that.getId()) {
                return -1;
            } else {
                return 1;
            }
        }
        long d = getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
        return (d == 0) ? 0 : (d < 0 ? -1 : 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DelayElement)) return false;

        DelayElement<?> that = (DelayElement<?>) o;

        if (id != that.id) return false;
        if (time != that.time) return false;
        if (!element.equals(that.element)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (time ^ (time >>> 32));
        result = 31 * result + element.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(getClass()).add("id", getId())
                .add("time", getTime()).add("element", getElement())
                .toString();
    }
}
