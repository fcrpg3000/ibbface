/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) ServletUtils.java 2013-08-24 13:54
 */
package com.ibbface.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import static org.springframework.util.NumberUtils.parseNumber;
import static org.springframework.util.StringUtils.hasText;


/**
 * 包含 {@code Servlet} 常用方法的便捷工具类。
 *
 * @author Yichuan
 * @version $Id$
 * @since 1.0
 */
public class ServletUtils {

    private static final String UNKNOWN_IP = "unknown";

    private static final Logger LOGGER = LoggerFactory.getLogger(ServletUtils.class);

    /**
     * 从 {@code javax.servlet.http.HttpServletRequest} 的请求中获取给定参数名称的参数的值。
     * 若该参数没被传递或没被赋值时返回空字符串（&quot;&quot;）。
     *
     * @param request 当前Web应用的 {@code HttpServletRequest} 对象。
     * @param name    获取的参数的名称。
     * @return 返回给定参数名称的参数的值。
     * @see #getParameter(javax.servlet.http.HttpServletRequest, String, String)
     */
    public static String getParameter(HttpServletRequest request, String name) {
        return getParameter(request, name, "");
    }

    /**
     * 从 {@code javax.servlet.http.HttpServletRequest} 的请求中获取给定参数名称的参数的值。
     *
     * @param request      当前Web应用的 {@code HttpServletRequest} 对象。
     * @param name         获取的参数的名称。
     * @param defaultValue 该参数没被传递或没被赋值时返回的默认值。
     * @return 返回给定参数名称的参数的值。
     */
    public static String getParameter(HttpServletRequest request, String name, String defaultValue) {
        if (!hasText(name))
            return defaultValue;
        String value = request.getParameter(name);
        return value == null ? defaultValue : value.trim();
    }

    /**
     * 从 {@code javax.servlet.http.HttpServletRequest} 的请求中获取给定参数名称的参数的值。
     * 该参数所获得的值是一个数组，相当于接收的是 {@code CheckBox} 或者 {@code ComboBox} 的值。
     *
     * @param request 当前Web应用的 {@code HttpServletRequest} 请求对象。
     * @param name    获取的参数的名称。
     * @return 返回给定参数名称的参数的值。
     */
    public static String[] getParameters(HttpServletRequest request, String name) {
        return getParameters(request, name, new String[] { });
    }

    /**
     * 从 {@code javax.servlet.http.HttpServletRequest} 的请求中获取给定参数名称的参数的值。
     * 该参数所获得的值是一个数组，相当于接收的是 {@code CheckBox} 或者 {@code ComboBox} 的值。
     *
     * @param request  当前Web应用的 {@code HttpServletRequest} 请求对象。
     * @param name     获取的参数的名称。
     * @param defaults 该参数没被传递或没被赋值时返回的默认值。
     * @return 返回给定参数名称的参数的值。
     */
    public static String[] getParameters(HttpServletRequest request, String name, String[] defaults) {
        if (!hasText(name))
            return defaults;
        String[] values = request.getParameterValues(name);
        return values == null || values.length == 0 ? defaults : values;
    }

    /**
     * 从 {@code javax.servlet.http.HttpServletRequest} 的请求中获取给定参数名称的参数的值。
     * 该参数所获得的值是一组 {@code java.util.List&lt;String&gt;} 封装的值，相当于接收的是 {@code CheckBox} 或者
     * {@code ComboBox} 的值。若该参数没被传递或没被赋值时返回 0 长度的 {@code java.util.List&lt;String&gt;}。
     *
     * @param request 当前Web应用的 {@code HttpServletRequest} 请求对象。
     * @param name    获取的参数的名称。
     * @return 返回给定参数名称的参数的值。
     */
    public static List<String> getParametersByList(HttpServletRequest request, String name) {
        return getParametersByList(request, name, new LinkedList<String>());
    }

    /**
     * 从 {@code javax.servlet.http.HttpServletRequest} 的请求中获取给定参数名称的参数的值。
     * 该参数所获得的值是一组 {@code java.util.List&lt;String&gt;} 封装的值，相当于接收的是 {@code CheckBox} 或者
     * {@code ComboBox} 的值。
     *
     * @param request     当前Web应用的 {@code HttpServletRequest} 请求对象。
     * @param name        获取的参数的名称。
     * @param defaultList 该参数没被传递或没被赋值时返回的默认值。
     * @return 返回给定参数名称的参数的值。
     */
    public static List<String> getParametersByList(HttpServletRequest request, String name,
                                                   List<String> defaultList) {
        if (!hasText(name))
            return defaultList;
        String[] values = request.getParameterValues(name);
        if (values == null || values.length == 0)
            return defaultList;
        else {
            List<String> list = new LinkedList<String>();
            for (String val : values) {
                list.add(val);
            }
            return list;
        }
    }

    /**
     * 从 {@code javax.servlet.http.HttpServletRequest} 的请求中获取给定参数名称的
     * {@code java.math.BigDecimal} 类型的参数值。 若该参数没被传递或没被赋值时返回 {@code java.math.BigDecimal}
     * 类型的 0。
     *
     * @param request 当前Web应用的 {@code HttpServletRequest} 对象。
     * @param name    获取的参数的名称。
     * @return 返回给定参数名称的 {@code java.math.BigDecimal} 类型的参数值
     * @see #getBigDecimalParameter(javax.servlet.http.HttpServletRequest, String, java.math.BigDecimal)
     */
    public static BigDecimal getBigDecimalParameter(HttpServletRequest request, String name) {
        return getBigDecimalParameter(request, name, BigDecimal.ZERO);
    }

    /**
     * 从 {@code javax.servlet.http.HttpServletRequest} 的请求中获取给定参数名称的
     * {@code java.math.BigDecimal} 类型的参数值。
     *
     * @param request      当前Web应用的 {@code HttpServletRequest} 对象。
     * @param name         获取的参数的名称。
     * @param defaultValue 该参数没被传递或没被赋值时返回的默认值。
     * @return 返回给定参数名称的 {@code java.math.BigDecimal} 类型的参数值
     */
    public static BigDecimal getBigDecimalParameter(HttpServletRequest request, String name,
                                                    BigDecimal defaultValue) {
        try {
            return parseNumber(getParameter(request, name), BigDecimal.class);
        } catch (IllegalArgumentException ex) {
            return defaultValue;
        }
    }

    /**
     * 从 {@code javax.servlet.http.HttpServletRequest} 的请求中获取给定参数名称的布尔值。
     * 若该参数没被传递或没被赋值时默认返回 {@code false}。
     *
     * @param request 当前Web应用的 {@code HttpServletRequest} 对象。
     * @param name    获取的参数的名称。
     * @return 返回给定参数名称的布尔值。
     * @see #getBoolParameter(javax.servlet.http.HttpServletRequest, String, boolean)
     */
    public static boolean getBoolParameter(HttpServletRequest request, String name) {
        return getBoolParameter(request, name, Boolean.FALSE);
    }

    /**
     * 从 {@code javax.servlet.http.HttpServletRequest} 的请求中获取给定参数名称的布尔值。
     *
     * @param request      当前Web应用的 {@code HttpServletRequest} 对象。
     * @param name         获取的参数的名称。
     * @param defaultValue 该参数没被传递或没被赋值时返回的默认值。
     * @return 返回给定参数名称的布尔值。
     */
    public static boolean getBoolParameter(HttpServletRequest request, String name, boolean defaultValue) {
        String value = getParameter(request, name).trim();
        if (!hasText(value))
            return defaultValue;
        if (Boolean.TRUE.toString().equals(value) || "on".equals(value)
                || "1".equals(value))
            return Boolean.TRUE;
        else if (Boolean.FALSE.toString().equals(value) || "off".equals(value)
                || "0".equals(value))
            return Boolean.FALSE;
        return defaultValue;
    }

    /**
     * 从 {@code javax.servlet.http.HttpServletRequest} 的请求中获取给定参数名称的 {@code byte}
     * 类型的参数值。若该参数没被传递或没被赋值时返回 {@code byte} 类型的 0。
     *
     * @param request 当前Web应用的 {@code HttpServletRequest} 对象。
     * @param name    获取的参数的名称。
     * @return 返回给定参数名称的 {@code byte} 类型的参数值
     * @see #getByteParameter(javax.servlet.http.HttpServletRequest, String, byte)
     */
    public static byte getByteParameter(HttpServletRequest request, String name) {
        return getByteParameter(request, name, (byte) 0);
    }

    /**
     * 从 {@code javax.servlet.http.HttpServletRequest} 的请求中获取给定参数名称的 {@code byte} 类型的参数值。
     *
     * @param request      当前Web应用的 {@code HttpServletRequest} 对象。
     * @param name         获取的参数的名称。
     * @param defaultValue 该参数没被传递或没被赋值时返回的默认值。
     * @return 返回给定参数名称的 {@code byte} 类型的参数值
     */
    public static byte getByteParameter(HttpServletRequest request, String name, byte defaultValue) {
        try {
            return parseNumber(getParameter(request, name), Byte.class);
        } catch (IllegalArgumentException ex) {
            return defaultValue;
        }
    }

    /**
     * 从 {@code javax.servlet.http.HttpServletRequest} 的请求中获取给定参数名称的 {@code double}
     * 类型的参数值。若该参数没被传递或没被赋值时返回 {@code double} 类型的 0.0d。
     *
     * @param request 当前Web应用的 {@code HttpServletRequest} 对象。
     * @param name    获取的参数的名称。
     * @return 返回给定参数名称的 {@code double} 类型的参数值
     * @see #getDoubleParameter(javax.servlet.http.HttpServletRequest, String, double)
     */
    public static double getDoubleParameter(HttpServletRequest request, String name) {
        return getDoubleParameter(request, name, 0.0d);
    }

    /**
     * 从 {@code javax.servlet.http.HttpServletRequest} 的请求中获取给定参数名称的 {@code double}
     * 类型的参数值。
     *
     * @param request      当前Web应用的 {@code HttpServletRequest} 对象。
     * @param name         获取的参数的名称。
     * @param defaultValue 该参数没被传递或没被赋值时返回的默认值。
     * @return 返回给定参数名称的 {@code double} 类型的参数值
     */
    public static double getDoubleParameter(HttpServletRequest request, String name, double defaultValue) {
        try {
            return parseNumber(getParameter(request, name), Double.class);
        } catch (IllegalArgumentException ex) {
            return defaultValue;
        }
    }

    /**
     * 从 {@code javax.servlet.http.HttpServletRequest} 的请求中获取给定参数名称的 {@code float}
     * 类型的参数值。若该参数没被传递或没被赋值时返回 {@code float} 类型的 0.0f。
     *
     * @param request 当前Web应用的 {@code HttpServletRequest} 对象。
     * @param name    获取的参数的名称。
     * @return 返回给定参数名称的 {@code float} 类型的参数值
     * @see #getFloatParameter(javax.servlet.http.HttpServletRequest, String, float)
     */
    public static float getFloatParameter(HttpServletRequest request, String name) {
        return getFloatParameter(request, name, 0.0f);
    }

    /**
     * 从 {@code javax.servlet.http.HttpServletRequest} 的请求中获取给定参数名称的 {@code float} 类型的参数值。
     *
     * @param request      当前Web应用的 {@code HttpServletRequest} 对象。
     * @param name         获取的参数的名称。
     * @param defaultValue 该参数没被传递或没被赋值时返回的默认值。
     * @return 返回给定参数名称的 {@code float} 类型的参数值
     */
    public static float getFloatParameter(HttpServletRequest request, String name, float defaultValue) {
        try {
            return parseNumber(getParameter(request, name), Float.class);
        } catch (IllegalArgumentException ex) {
            return defaultValue;
        }
    }

    /**
     * 从 {@code javax.servlet.http.HttpServletRequest} 的请求中获取给定参数名称的 {@code int}
     * 类型的参数值。若该参数没被传递或没被赋值时返回 {@code int} 类型的 0。
     *
     * @param request 当前Web应用的 {@code HttpServletRequest} 对象。
     * @param name    获取的参数的名称。
     * @return 返回给定参数名称的 {@code int} 类型的参数值
     * @see #getIntParameter(javax.servlet.http.HttpServletRequest, String, int)
     */
    public static int getIntParameter(HttpServletRequest request, String name) {
        return getIntParameter(request, name, 0);
    }

    /**
     * 从 {@code javax.servlet.http.HttpServletRequest} 的请求中获取给定参数名称的 {@code int} 类型的参数值。
     *
     * @param request      当前Web应用的 {@code HttpServletRequest} 对象。
     * @param name         获取的参数的名称。
     * @param defaultValue 该参数没被传递或没被赋值时返回的默认值。
     * @return 返回给定参数名称的 {@code int} 类型的参数值
     */
    public static int getIntParameter(HttpServletRequest request, String name, int defaultValue) {
        try {
            return parseNumber(getParameter(request, name), Integer.class);
        } catch (IllegalArgumentException ex) {
            return defaultValue;
        }
    }

    /**
     * 从 {@code javax.servlet.http.HttpServletRequest} 的请求中获取给定参数名称的 {@code long}
     * 类型的参数值。若该参数没被传递或没被赋值时返回 {@code long} 类型的 0L。
     *
     * @param request 当前Web应用的 {@code HttpServletRequest} 对象。
     * @param name    获取的参数的名称。
     * @return 返回给定参数名称的 {@code long} 类型的参数值
     * @see #getLongParameter(javax.servlet.http.HttpServletRequest, String, long)
     */
    public static long getLongParameter(HttpServletRequest request, String name) {
        return getLongParameter(request, name, 0L);
    }

    /**
     * 从 {@code javax.servlet.http.HttpServletRequest} 的请求中获取给定参数名称的 {@code long} 类型的参数值。
     *
     * @param request      当前Web应用的 {@code HttpServletRequest} 对象。
     * @param name         获取的参数的名称。
     * @param defaultValue 该参数没被传递或没被赋值时返回的默认值。
     * @return 返回给定参数名称的 {@code long} 类型的参数值
     */
    public static long getLongParameter(HttpServletRequest request, String name, long defaultValue) {
        try {
            return parseNumber(getParameter(request, name), Long.class);
        } catch (IllegalArgumentException ex) {
            return defaultValue;
        }
    }

    /**
     * 从 {@code javax.servlet.http.HttpServletRequest} 的请求中获取给定参数名称的 {@code short}
     * 类型的参数值。若该参数没被传递或没被赋值时返回 {@code short} 类型的 0。
     *
     * @param request 当前Web应用的 {@code HttpServletRequest} 对象。
     * @param name    获取的参数的名称。
     * @return 返回给定参数名称的 {@code short} 类型的参数值
     * @see #getShortParameter(javax.servlet.http.HttpServletRequest, String, short)
     */
    public static short getShortParameter(HttpServletRequest request, String name) {
        return getShortParameter(request, name, (short) 0);
    }

    /**
     * 从 {@code javax.servlet.http.HttpServletRequest} 的请求中获取给定参数名称的 {@code short} 类型的参数值。
     *
     * @param request      当前Web应用的 {@code HttpServletRequest} 对象。
     * @param name         获取的参数的名称。
     * @param defaultValue 该参数没被传递或没被赋值时返回的默认值。
     * @return 返回给定参数名称的 {@code short} 类型的参数值
     */
    public static short getShortParameter(HttpServletRequest request, String name, short defaultValue) {
        try {
            return parseNumber(getParameter(request, name), Short.class);
        } catch (IllegalArgumentException ex) {
            return defaultValue;
        }
    }

    /**
     * 获取当前 {@code javax.servlet.http.HttpServletRequest} 的请求中或者 在
     * {@code javax.servlet.http.HttpSession} 中找到给定参数名称的属性值。若属性值不存在
     * 则返回空字符串（&quot;&quot;）。
     *
     * @param request 当前Web应用的 {@code HttpServletRequest} 请求对象。
     * @param name    获取的参数的名称。
     * @return 返回给定参数名称的属性值。查找顺序 request、session。
     */
    public static String getAttribute(HttpServletRequest request, String name) {
        return getAttribute(request, name, "");
    }

    /**
     * 获取当前 {@code javax.servlet.http.HttpServletRequest} 的请求中或者 在
     * {@code javax.servlet.http.HttpSession} 中找到给定参数名称的属性值。若属性值不存在 则返回给定的默认值。
     *
     * @param request      当前Web应用的 {@code HttpServletRequest} 请求对象。
     * @param name         获取的参数的名称。
     * @param defaultValue 该属性不存在或为 {@code NULL} 时返回的默认值。
     * @return 返回给定参数名称的属性值。查找顺序 request、session。
     */
    public static String getAttribute(HttpServletRequest request, String name, String defaultValue) {
        if (!hasText(name))
            return defaultValue;
        try {
            String value = (String) request.getAttribute(name);
            if (value == null) {
                value = (String) request.getSession().getAttribute(name);
            }
            return value;
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * 获取当前 {@code javax.servlet.http.HttpServletRequest} 的请求中或者 在
     * {@code javax.servlet.http.HttpSession} 中找到给定参数名称的属性值。若属性值不存在 则返回 0。
     *
     * @param request 当前Web应用的 {@code HttpServletRequest} 请求对象。
     * @param name    获取的参数的名称。
     * @return 返回给定参数名称的属性值。查找顺序 request、session。
     */
    public static int getIntAttribute(HttpServletRequest request, String name) {
        return getIntAttribute(request, name, 0);
    }

    /**
     * 获取当前 {@code javax.servlet.http.HttpServletRequest} 的请求中或者 在
     * {@code javax.servlet.http.HttpSession} 中找到给定参数名称的属性值。若属性值不存在 则返回给定的默认值。
     *
     * @param request      当前Web应用的 {@code HttpServletRequest} 请求对象。
     * @param name         获取的参数的名称。
     * @param defaultValue 该属性不存在或为 {@code NULL} 时返回的默认值。
     * @return 返回给定参数名称的属性值。查找顺序 request、session。
     */
    public static int getIntAttribute(HttpServletRequest request, String name, int defaultValue) {
        if (!hasText(name))
            return defaultValue;

        try {
            return parseNumber(getAttribute(request, name), Integer.class);
        } catch (IllegalArgumentException ex) {
            return defaultValue;
        }
    }

    /**
     * 获取当前 {@code javax.servlet.http.HttpServletRequest} 的请求中或者 在
     * {@code javax.servlet.http.HttpSession} 中找到给定参数名称的属性值。若属性值不存在 则返回 0。
     *
     * @param request 当前Web应用的 {@code HttpServletRequest} 请求对象。
     * @param name    获取的参数的名称。
     * @return 返回给定参数名称的属性值。查找顺序 request、session。
     */
    public static long getLongAttribute(HttpServletRequest request, String name) {
        return getLongAttribute(request, name, 0L);
    }

    /**
     * 获取当前 {@code javax.servlet.http.HttpServletRequest} 的请求中或者 在
     * {@code javax.servlet.http.HttpSession} 中找到给定参数名称的属性值。若属性值不存在 则返回给定的默认值。
     *
     * @param request      当前Web应用的 {@code HttpServletRequest} 请求对象。
     * @param name         获取的参数的名称。
     * @param defaultValue 该属性不存在或为 {@code NULL} 时返回的默认值。
     * @return 返回给定参数名称的属性值。查找顺序 request、session。
     */
    public static long getLongAttribute(HttpServletRequest request, String name, long defaultValue) {
        if (!hasText(name))
            return defaultValue;
        try {
            return parseNumber(getAttribute(request, name), Long.class);
        } catch (IllegalArgumentException ex) {
            return defaultValue;
        }
    }

    /**
     * 获取当前 {@code javax.servlet.http.HttpServletRequest} 的请求中或者 在
     * {@code javax.servlet.http.HttpSession} 中找到给定参数名称的属性值。若属性值不存在 则返回 0。
     *
     * @param request 当前Web应用的 {@code HttpServletRequest} 请求对象。
     * @param name    获取的参数的名称。
     * @return 返回给定参数名称的属性值。查找顺序 request、session。
     */
    public static short getShortAttribute(HttpServletRequest request, String name) {
        return getShortAttribute(request, name, (short) 0);
    }

    /**
     * 获取当前 {@code javax.servlet.http.HttpServletRequest} 的请求中或者 在
     * {@code javax.servlet.http.HttpSession} 中找到给定参数名称的属性值。若属性值不存在 则返回给定的默认值。
     *
     * @param request      当前Web应用的 {@code HttpServletRequest} 请求对象。
     * @param name         获取的参数的名称。
     * @param defaultValue 该属性不存在或为 {@code NULL} 时返回的默认值。
     * @return 返回给定参数名称的属性值。查找顺序 request、session。
     */
    public static short getShortAttribute(HttpServletRequest request, String name, short defaultValue) {
        if (!hasText(name))
            return defaultValue;
        try {
            return parseNumber(getAttribute(request, name), Short.class);
        } catch (IllegalArgumentException ex) {
            return defaultValue;
        }
    }

    /**
     * 重新获得在 {@code HttpServletRequest} 或 {@code Cookie} 中保存的值。
     *
     * @param request  当前Web应用的 {@code HttpServletRequest} 请求对象。
     * @param response 当前Web应用的 {@code HttpServletResponse} 服务器响应对象。
     * @param key      给定的键。
     * @return 在 {@code HttpServletRequest} 或 {@code Cookie} 中保存的值。
     */
    public static String retrieve(HttpServletRequest request, HttpServletResponse response, String key) {
        String value = (String) request.getAttribute(key);
        if (!hasText(value) || "null".equalsIgnoreCase(value)) {
            value = getCookieValue(request, key);
        }
        return value;
    }

    /**
     * 在 {@code HttpServletRequest} 或 {@code Cookie} 中保存给定的键值对。
     *
     * @param request  当前Web应用的 {@code HttpServletRequest} 请求对象。
     * @param response 当前Web应用的 {@code HttpServletResponse} 服务器响应对象。
     * @param name     保存的值的唯一名称。
     * @param value    保存的对应于名称的值。
     * @param domains  保存 {@code Cookie} 时指定的一个或一组域。
     */
    public static void store(HttpServletRequest request, HttpServletResponse response, String name,
                             String value, String... domains) {
        store(request, response, name, value, 0, domains);
    }

    /**
     * 在 {@code HttpServletRequest} 中保存给定的键值对，若给定的保存周期 {@code storeTime} 大于0，
     * 则同时保存到客户端 {@code Cookie} 中。
     *
     * @param request  当前Web应用的 {@code HttpServletRequest} 请求对象。
     * @param response 当前Web应用的 {@code HttpServletResponse} 服务器响应对象。
     * @param name     保存的值的唯一名称。
     * @param value    保存的对应于名称的值。
     * @param saveTime 存入 {@code Cookie} 的周期，单位：秒(s)。
     * @param domains  保存 {@code Cookie} 时指定的一个或一组域。
     */
    public static void store(HttpServletRequest request, HttpServletResponse response, String name,
                             String value, int saveTime, String... domains) {
        if (!hasText(name)) {
            return;
        }
        request.setAttribute(name, value);
        if (saveTime > 0) { // 需要保存到Cookie中
            if (domains != null && domains.length > 0 && hasText(domains[0])) {
                for (String domain : domains) {
                    saveCookie(response, name, value, saveTime, domain, "/");
                }
            }
        }
    }

    /**
     * 删除存储的值，该值可能保存于 {@code HttpServletRequest} 或 {@code Cookie} 中。 若要删除的给定键 {@code name}
     * 为空，则不作任何处理。
     *
     * @param request  当前Web应用的 {@code HttpServletRequest} 请求对象。
     * @param response 当前Web应用的 {@code HttpServletResponse} 服务器响应对象。
     * @param name     要删除的值的给定键。
     * @param domains  {@code Cookie} 值保存的域，该域可能存在多个。
     */
    public static void delete(HttpServletRequest request, HttpServletResponse response, String name,
                              String... domains) {
        if (!hasText(name))
            return;
        request.removeAttribute(name);
        // 删除所有有关域下的给定键的值。
        if (domains != null && domains.length > 0) {
            for (String domain : domains) {
                if (hasText(domain))
                    deleteCookie(response, name, domain);
            }
        }
    }

    /**
     * 删除给定键的 {@code Cookie} 值。{@code name} 为 {@code null} 或者空字符串，则不作任何操作。
     *
     * @param response 当前Web应用的 {@code HttpServletResponse} 服务器响应对象。
     * @param name     要删除的 {@code Cookie} 键名。
     * @param domain   要删除的{@code Cookie} 所在域。
     * @param paths    要删除的{@code Cookie} 路径。
     */
    public static void deleteCookie(HttpServletResponse response, String name, String domain, String... paths) {
        Cookie cookie = new Cookie(name, "");
        // 当用户关闭浏览器时删除Cookie的值
        if (hasText(domain))
            cookie.setDomain(domain);
        cookie.setMaxAge(-1);
        if (paths == null || paths.length == 0 || !hasText(paths[0])) {
            cookie.setPath("/");
        } else {
            cookie.setPath(paths[0]);
        }
        response.addCookie(cookie);
    }

    /**
     * 在客户端 {@code Cookie} 中保存给定的键值对，保存的值的周期由 {@code saveTime} 指定，单位：秒(s)。
     * 默认的保存周期为一小时。
     *
     * @param response 当前Web应用的 {@code HttpServletResponse} 服务器响应对象。
     * @param name     保存的 {@code Cookie} 的值的唯一名称。
     * @param value    存的 {@code Cookie} 的对应于名称的值。
     * @param domain   {@code Cookie} 保存的域。
     * @param path     {@code Cookie} 保存的路径。
     */
    public static void saveCookie(HttpServletResponse response, String name, String value, String domain,
                                  String path) {
        saveCookie(response, name, value, 60 * 60, domain, path);
    }

    /**
     * 在客户端 {@code Cookie} 中保存给定的键值对，保存的值的周期由 {@code saveTime} 指定，单位：秒(s)。
     *
     * @param response 当前Web应用的 {@code HttpServletResponse} 服务器响应对象。
     * @param name     保存的 {@code Cookie} 的值的唯一名称。
     * @param value    保存的 {@code Cookie} 的对应于名称的值。
     * @param saveTime {@code Cookie} 保存的周期(单位：秒) -1 - 关闭浏览器即失效。
     * @param domain   {@code Cookie} 保存的域。
     * @param path     {@code Cookie} 保存的路径。
     */
    public static void saveCookie(HttpServletResponse response, String name, String value, int saveTime,
                                  String domain, String path) {
        if (value == null)
            value = "";
        Cookie cookie = new Cookie(name, value);
        cookie.setDomain(domain);
        cookie.setMaxAge(saveTime);
        if (hasText(path))
            cookie.setPath(path);
        else
            cookie.setPath("/");
        cookie.setSecure(false);
        response.addCookie(cookie);
    }

    /**
     * 保存一组给定的 {@code Cookie}。
     * <ul>
     * <li>若其中 {@code Cookie} 中的 {@code name} 和 {@code value} 属性为空，则该项 {@code Cookie}
     * 不会进行保存；</li>
     * <li>若其中 {@code Cookie} 中的 {@code path} 属性为空，则默认为 &quot;/&quot;；</li>
     * <li>若其中 {@code Cookie} 中的 {@code secure} 属性为 {@code true}，则只能在 {@code HTTPS}
     * 下才能发送保存；</li>
     * </ul>
     *
     * @param response 当前Web应用的 {@code HttpServletResponse} 服务器响应对象。
     * @param cookies  要保存的一组 {@code Cookie}。
     */
    public static void saveCookies(HttpServletResponse response, List<Cookie> cookies) {
        if (cookies == null || cookies.isEmpty()) {
            LOGGER.warn("Cookies(List<Cookie>) cannot be saved.");
            return;
        }
        StringBuilder warnMessages = new StringBuilder();
        for (int i = 0, len = cookies.size(); i < len; i++) {
            Cookie c = cookies.get(i);
            if (c != null && hasText(c.getName()) && !hasText(c.getValue())) {
                if (!hasText(c.getPath()))
                    c.setPath("/");
                response.addCookie(c);
            } else {
                warnMessages.append("\nCookies(List<Cookie>) index of ");
                warnMessages.append(i).append(" entry name or value is empty.");
            }
        }
        if (warnMessages.length() > 0) {
            if (LOGGER.isWarnEnabled())
                LOGGER.warn(warnMessages.toString());
        }
    }

    /**
     * 保存一组给定的 {@code Cookie}。具体请参见 {@link #saveCookies(javax.servlet.http.HttpServletResponse, java.util.List)}。
     *
     * @param response 当前Web应用的 {@code HttpServletResponse} 服务器响应对象。
     * @param cookies  要保存的一组 {@code Cookie}。
     */
    public static void saveCookies(HttpServletResponse response, Cookie... cookies) {
        if (cookies != null && cookies.length > 0) {
            List<Cookie> cookieList = new LinkedList<Cookie>();
            for (Cookie c : cookies) {
                cookieList.add(c);
            }
            saveCookies(response, cookieList);
        }
    }

    /**
     * 获取给定键的 {@code Cookie} 值。若给定的 {@code Cookie} 的 {@code name} 不存在， 则返回 {@code null}。
     *
     * @param request 当前Web应用的 {@code HttpServletRequest} 请求对象。
     * @param name    给定的 {@code Cookie} 的键值。
     * @return 返回给定键的 {@code Cookie} 值。
     */
    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0)
            return null;
        for (Cookie cookie : cookies) {
            if (hasText(cookie.getValue()) && cookie.getName().equals(name)) {
                String value = cookie.getValue();
                return value;
            }
        }
        return null;
    }

    /**
     * 使用给定的算法加密一段字符串。若要加密的字符串为 {@code null}，则返回 {@code null}。
     * <p>
     * <strong>注意：空字符串（&quot;&quot;）也能被加密</strong>
     * </p>
     *
     * @param plaintext 要加密的字符串。
     * @return 返回给定字符串的加密密文。
     */
    public static String encryptCookie(String plaintext) {
        throw new UnsupportedOperationException();
    }

    /**
     * 使用给定的算法解密一段密文字符串。若要解密的字符串为 {@code null} 或空字符串，则返回 {@code null}。
     * <p>
     * <strong>注意：只要返回的字符串不为 {@code null}，则表示解密成功。因为该方法不会抛出任何的异常。</strong>
     * </p>
     *
     * @param encrypttext 要解密的密文字符串。
     * @return 返回给定密文字符串的原生字符串。
     */
    public static String decryptCookie(String encrypttext) {
        throw new UnsupportedOperationException();
    }

    /**
     * 获取给定键值的 {@code Cookie}。若给定的 {@code Cookie} 键不存在，则返回 {@code null}。
     *
     * @param request 当前Web应用的 {@code HttpServletRequest} 请求对象。
     * @param name    给定的 {@code Cookie} 的键值。
     * @return 返回给定键的 {@code Cookie} 对象。
     */
    public static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies(); // 获得Cookie的集合
        if (cookies == null || !hasText(name))
            return null;
        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                return cookie;
            }
        }
        return null;
    }

    /**
     * 获取当前Http请求的客户端的真实IP。若取不到IP值，则返回空字符串（&quot;&quot;）。
     *
     * @param request 当前Web应用的 {@code HttpServletRequest} 请求对象。
     * @return 当前Http请求的客户端的真实IP串。
     */
    public static String getClientIp(HttpServletRequest request) {
        if (request == null)
            return "";
        String ip = request.getHeader("X-Forwarded-For");
        if (!hasText(ip) || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (!hasText(ip) || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (!hasText(ip) || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (!hasText(ip) || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (!hasText(ip) || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取当前Http请求的客户端的真实IP。若取不到IP值，则返回空字符串（&quot;&quot;）。 该方法与
     * {@link #getIp(javax.servlet.http.HttpServletRequest)} 方法效果相同。
     *
     * @param request 当前Web应用的 {@code HttpServletRequest} 请求对象。
     * @return 当前Http请求的客户端的真实IP串。
     */
    public static String getIp(HttpServletRequest request) {
        return getClientIp(request);
    }

    /**
     * 返回当前应用的URL地址。
     *
     * @param request 当前Web应用的 {@code HttpServletRequest} 请求对象。
     * @return 当前应用的URL地址。
     */
    public static String getAppURL(HttpServletRequest request) {
        StringBuilder buf = new StringBuilder();
        int port = request.getServerPort();
        if (port < 0) {
            port = 80;
        }
        String scheme = request.getScheme();
        buf.append(scheme).append("://").append(request.getServerName());
        if (("http".equals(scheme) && port != 80) || ("https".equals(scheme) && port != 443)) {
            buf.append(":").append(port);
        }
        buf.append(request.getContextPath());
        return buf.toString();
    }

    /**
     * 从 {@code HttpServletRequest} 请求中检查 {@code Header} 头部信息的缓存设置。
     *
     * @param expireSeconds         过期时间（单位：秒）。
     * @param modelLastModifiedDate 模块最后修改时间。
     * @param request               当前Web应用的 {@code HttpServletRequest} 请求对象。
     * @param response              当前Web应用的 {@code HttpServletResponse} 服务器响应对象。
     * @return 如果需要从服务器刷新，则返回 {@code true} 。
     */
    public static boolean checkHeaderCache(long expireSeconds, long modelLastModifiedDate,
                                           HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("modelExpire", expireSeconds);

        // convert seconds to ms.
        long expireMilliseconds = expireSeconds * 1000;
        long header = request.getDateHeader("If-Modified-Since");
        long now = System.currentTimeMillis();
        if (header > 0 && expireMilliseconds > 0) {
            if (modelLastModifiedDate > header) {
                // expireSeconds = 0; // reset
                response.setStatus(HttpServletResponse.SC_OK);
                return true;
            }
            if (header + expireMilliseconds > now) {
                // during the period happend modified
                response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                return false;
            }
        }

        // if over expire data, see the Etags;
        // ETags if ETags no any modified
        String previousToken = request.getHeader("If-None-Match");
        if (previousToken != null && previousToken.equals(Long.toString(modelLastModifiedDate))) {
            // not modified
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            return false;
        }
        // if th model has modified , setup the new modified date
        response.setHeader("ETag", Long.toString(modelLastModifiedDate));
        setResponseHeaderCache(expireSeconds, request, response);
        return true;
    }

    /**
     * 设置服务器的响应头部缓存信息。
     *
     * @param adddays  过期时间。
     * @param request  当前Web应用的 {@code HttpServletRequest} 请求对象。
     * @param response 当前Web应用的 {@code HttpServletResponse} 服务器响应对象。
     * @return 返回 {@code true}。
     */
    public static boolean setResponseHeaderCache(long adddays, HttpServletRequest request,
                                                 HttpServletResponse response) {
        request.setAttribute("modelExpire", adddays);

        long adddaysM = adddays * 1000;
        String maxAgeDirective = "max-age=" + adddays;
        response.setHeader("Cache-Control", maxAgeDirective);
        response.setStatus(HttpServletResponse.SC_OK);
        response.addDateHeader("Last-Modified", System.currentTimeMillis());
        response.addDateHeader("Expires", System.currentTimeMillis() + adddaysM);
        return true;
    }
}