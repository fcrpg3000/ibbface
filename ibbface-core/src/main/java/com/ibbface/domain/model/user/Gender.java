/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) Gender.java 2013-07-28 15:38
 */

package com.ibbface.domain.model.user;

import com.google.common.base.Objects;
import com.ibbface.domain.shared.ValueObject;

/**
 * 性别枚举。
 *
 * @author Fuchun
 * @since 1.0
 */
public enum Gender implements ValueObject<Gender> {
    /**
     * 男 (1)。
     */
    MALE(1, "男", "Male", "M"),

    /**
     * 女 (2)。
     */
    FEMALE(2, "女", "Female", "F"),

    /**
     * 男孩（1）。
     */
    BOY(1, "男孩", "Boy", "B"),

    /**
     * 女孩（2）。
     */
    GIRL(2, "女孩", "Girl", "G"),

    /**
     * 保密 (3)。
     */
    UNKNOWN(3, "保密", "Unknown", "N");

    final short code;
    final String name;
    final String ename;
    final String alias;

    private Gender(int code, String name, String ename, String alias) {
        this.code = (short) code;
        this.name = name;
        this.ename = ename;
        this.alias = alias;
    }

    public short getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getEname() {
        return ename;
    }

    public String getAlias() {
        return alias;
    }

    /**
     * 值对象类对象是否相等，需要比较对象内的属性值，它没有唯一标识。
     *
     * @param other 其他的值对象。
     * @return 如果比较的值对象和当前对象相等，则返回 {@code true}，否则 {@code false}。
     */
    @Override
    public boolean sameValueAs(Gender other) {
        return other != null && this.code == other.code;
    }

    /**
     * 返回指定标识的 {@link Gender} 枚举对象，如果标识为 {@code null}，
     * 或者不存在，则返回 {@link #UNKNOWN}。
     *
     * @param code 标识。
     * @param <N>  标识的类型。
     * @return 返回指定标识的 {@link Gender} 枚举对象。
     */
    public static <N extends Number> Gender of(N code) {
        if (code == null) {
            return UNKNOWN;
        }
        for (Gender g : values()) {
            if (g.code == code.shortValue()) {
                return g;
            }
        }
        return UNKNOWN;
    }

    /**
     * 返回指定字符串标识（名称、英文名称或者别名）的枚举对象。
     * 如果 {@code sign == null || sign.length() == 0}，返回 {@link #UNKNOWN}。
     *
     * @param sign 字符串标识（名称、英文名称或者别名）。
     * @return 返回指定字符串标识（名称、英文名称或者别名）的枚举对象。
     */
    public static Gender of(String sign) {
        if (sign == null || sign.length() == 0) {
            return UNKNOWN;
        }
        for (Gender g : values()) {
            if (sign.equals(g.alias) || sign.equals(g.name) ||
                    sign.equals(g.ename)) {
                return g;
            }
        }
        return UNKNOWN;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(getClass())
                .add("code", getCode())
                .add("name", getName())
                .add("ename", getEname())
                .add("alias", getAlias()).toString();
    }
}
