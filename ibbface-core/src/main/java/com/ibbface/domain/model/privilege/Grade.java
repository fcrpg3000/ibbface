/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) Grade.java 2013-08-18 19:14
 */

package com.ibbface.domain.model.privilege;

import com.ibbface.domain.shared.ValueObject;

/**
 * The api grade enumeration.
 *
 * @author Fuchun
 * @since 1.0
 */
public enum Grade implements ValueObject<Grade> {

    /**
     * 必须是IP验证的接口。
     */
    PRIVATE(0, "私有接口"),

    /**
     * 普通接口。
     */
    NORMAL(1, "普通接口"),

    /**
     * 高级接口。
     */
    ADVANCED(2, "高级接口"),

    /**
     * 管理接口。
     */
    ADMIN(3, "管理接口");

    final short code;
    final String description;

    private Grade(int code, String description) {
        this.code = (short) code;
        this.description = description;
    }

    public short getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    /**
     * 值对象类对象是否相等，需要比较对象内的属性值，它没有唯一标识。
     *
     * @param other 其他的值对象。
     * @return 如果比较的值对象和当前对象相等，则返回 {@code true}，否则 {@code false}。
     */
    @Override
    public boolean sameValueAs(Grade other) {
        return other != null && other == this;
    }

    public static <T extends Number> Grade of(T code, Grade defVal) {
        if (code == null) {
            return defVal;
        }
        for (Grade g : values()) {
            if (g.code == code.shortValue()) {
                return g;
            }
        }
        return defVal;
    }
}
