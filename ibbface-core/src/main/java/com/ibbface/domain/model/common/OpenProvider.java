/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) OpenProvider.java 2013-08-07 22:49
 */

package com.ibbface.domain.model.common;

import com.ibbface.domain.shared.ValueObject;

/**
 * 开放平台提供者枚举。
 *
 * @author Fuchun
 * @since 1.0
 */
public enum OpenProvider implements ValueObject<OpenProvider> {
    WEIBO(1, "新浪微博"),

    QQ(2, "腾讯QQ"),

    ALIPAY(3, "支付宝"),

    DOUBAN(4, "豆瓣");

    private final short code;
    private final String name;

    private OpenProvider(int code, String name) {
        this.code = (short) code;
        this.name = name;
    }

    public short getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    /**
     * 值对象类对象是否相等，需要比较对象内的属性值，它没有唯一标识。
     *
     * @param other 其他的值对象。
     * @return 如果比较的值对象和当前对象相等，则返回 {@code true}，否则 {@code false}。
     */
    @Override
    public boolean sameValueAs(OpenProvider other) {
        return other != null && other == this;
    }

    public static <T extends Number> OpenProvider of(T code) {
        if (code == null) {
            return null;
        }
        for (OpenProvider op : values()) {
            if (op.code == code.shortValue()) {
                return op;
            }
        }
        return null;
    }
}
