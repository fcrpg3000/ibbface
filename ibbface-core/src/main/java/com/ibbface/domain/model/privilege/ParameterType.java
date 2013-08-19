/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) ParameterType.java 2013-08-18 15:52
 */

package com.ibbface.domain.model.privilege;

import com.ibbface.domain.shared.ValueObject;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author Fuchun
 * @since 1.0
 */
public enum ParameterType implements ValueObject<ParameterType> {

    UINT8("uint8", Byte.class),

    UINT16("uint16", Short.class),

    UINT32("uint32", Integer.class),

    UINT64("uint64", Long.class),

    INT8("int8", Byte.class),

    INT16("int16", Short.class),

    INT32("int32", Integer.class),

    INT64("int64", Long.class),

    FLOAT32("float32", Float.class),

    FLOAT64("float64", Double.class),

    DECIMAL("decimal", BigDecimal.class),

    BIGINT("bigint", BigInteger.class),

    STRING("string", String.class),

    CHAR("char", char.class),

    BOOLEAN("bool", Boolean.class);

    final String name;
    final Class<?> type;

    private ParameterType(String name, Class<?> type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Class<?> getType() {
        return type;
    }

    /**
     * 值对象类对象是否相等，需要比较对象内的属性值，它没有唯一标识。
     *
     * @param other 其他的值对象。
     * @return 如果比较的值对象和当前对象相等，则返回 {@code true}，否则 {@code false}。
     */
    @Override
    public boolean sameValueAs(ParameterType other) {
        return other != null && other == this;
    }

    public static ParameterType of(String name, ParameterType defVal) {
        if (name == null || name.length() == 0) {
            return defVal;
        }
        for (ParameterType type : values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        return defVal;
    }
}
