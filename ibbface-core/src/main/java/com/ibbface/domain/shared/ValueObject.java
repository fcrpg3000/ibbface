/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) ValueObject.java 2013-07-28 00:14
 */

package com.ibbface.domain.shared;

import java.io.Serializable;

/**
 * 实现此接口的类，表示一个值对象类。同样的，值对象类也要求实现序列化接口。
 *
 * @author Fuchun
 * @since 1.0
 */
public interface ValueObject<T extends Serializable> {

    /**
     * 值对象类对象是否相等，需要比较对象内的属性值，它没有唯一标识。
     *
     * @param other 其他的值对象。
     * @return 如果比较的值对象和当前对象相等，则返回 {@code true}，否则 {@code false}。
     */
    boolean sameValueAs(T other);
}


