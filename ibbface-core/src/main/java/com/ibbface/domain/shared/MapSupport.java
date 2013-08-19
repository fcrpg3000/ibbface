/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) MapSupport.java 2013-07-28 00:34
 */

package com.ibbface.domain.shared;

import java.util.Map;

/**
 * 实现此接口以支持以 {@link java.util.Map} 的形式返回实现此接口的类对象信息。
 *
 * @author Fuchun
 * @since 1.0
 */
public interface MapSupport {

    /**
     * 类型信息的键。
     */
    String CLASS_TYPE_KEY = "@type";

    /**
     * 以 {@link java.util.Map} 的形式返回实现此接口的类对象信息。
     */
    public Map<String, Object> asMap();
}