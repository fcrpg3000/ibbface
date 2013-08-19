/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) AbstractValueObject.java 2013-07-28 00:24
 */

package com.ibbface.domain.shared;

import java.io.Serializable;

/**
 * 一个抽象的值对象接口实现，实际的值对象类只需继承此抽象类即可。
 *
 * @author Fuchun
 * @since 1.0
 */
public abstract class AbstractValueObject<T extends Serializable>
        implements ValueObject<T>, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 值对象类对象是否相等，需要比较对象内的属性值，它没有唯一标识。
     *
     * @param other 其他的值对象。
     * @return 如果比较的值对象和当前对象相等，则返回 {@code true}，否则 {@code false}。
     */
    @Override
    public boolean sameValueAs(T other) {
        return other != null && equals(other);
    }

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object other);
}
