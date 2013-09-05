/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) AppContext.java 2013-08-24 14:43
 */
package com.ibbface.context;

import com.google.common.collect.Maps;
import com.ibbface.domain.model.common.ClientInfo;
import com.ibbface.web.ServletUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static org.springframework.util.StringUtils.hasText;

/**
 * 应用的上下文对象。
 *
 * @author Fuchun
 * @version $Id: AppContext.java 30410 2013-05-28 01:11:32Z C629 $
 * @since 1.0
 */
public final class AppContext implements Serializable {

    private static final long serialVersionUID = 8866916596410260904L;

    private static final String KEY_OF_CLIENT_IP = "AppContext.clientIp";

    private static final String KEY_OF_CLIENT_INFO = "AppContext.clientInfo";

    private static final String KEY_OF_ACCESS_TOKEN = "AppContext.accessToken";

    private static final ThreadLocal<AppContext> contexts = new ThreadLocal<AppContext>();

    private final Map<String, Object> attributes;

    private AppContext() {
        attributes = Maps.newConcurrentMap();
    }

    /**
     * 初始化并返回应用上下文对象。如果上下文对象不存在，则自动创建。
     */
    public static AppContext initAppContext() {
        AppContext ctx = contexts.get();
        if (ctx == null) {
            ctx = new AppContext();
            contexts.set(ctx);
        }
        return ctx;
    }

    /**
     * 初始化并返回应用上下文对象。如果上下文对象不存在，则自动创建。
     */
    public static AppContext initAppContext(HttpServletRequest request) {
        initAppContext();
        String clientIp = ServletUtils.getClientIp(request);
        ClientInfo clientInfo = ClientInfo.fromUserAgent(request.getHeader("User-Agent"));
        String authorizationHeader = request.getHeader("Authorization");
        String accessToken = authorizationHeader == null ? null :
                (authorizationHeader.contains(" ") ? authorizationHeader.split(" ")[1] : null);
        setClientIp(clientIp);
        setClientInfo(clientInfo);
        setAccessToken(accessToken);
        return getContext();
    }

    /**
     * 返回应用上下文对象。如果不存在，则返回 {@code null}。
     */
    public static AppContext getContext() {
        return contexts.get();
    }

    /**
     * 设置应用当前上下文对象。如果 {@code ctx == null}，则移除当前上下文对象。
     *
     * @param ctx 要设置的上下文对象。
     */
    public static void setContext(AppContext ctx) {
        if (ctx == null) {
            contexts.remove();
        } else {
            contexts.set(ctx);
        }
    }

    /**
     * 重置应用当前上下文对象。
     */
    public static void reset() {
        AppContext ctx = contexts.get();
        if (ctx != null) {
            ctx.attributes.clear();
        }
        contexts.remove();
    }

    /**
     * 返回应用当前上下文中的客户端IP地址。如果当前上下文为 {@code null}，或者没设置客户端IP地址，则返回 {@code null}。
     */
    public static String getClientIp() {
        AppContext ctx = getContext();
        if (ctx == null) {
            return null;
        }
        return (String) ctx.attributes.get(KEY_OF_CLIENT_IP);
    }

    /**
     * 设置应当前上下文中的客户端IP地址。
     *
     * @param clientIp 客户端IP地址。
     */
    public static void setClientIp(String clientIp) {
        AppContext ctx = getContext();
        if (ctx == null) {
            return;
        }
        ctx.attributes.put(KEY_OF_CLIENT_IP, clientIp);
    }

    /**
     * 返回应用当前上下文中的 {@code UserAgent}。
     */
    public static ClientInfo getClientInfo() {
        AppContext ctx = getContext();
        if (ctx == null) {
            return null;
        }
        return (ClientInfo) ctx.attributes.get(KEY_OF_CLIENT_INFO);
    }

    /**
     * 设置应用当前上下文中的 {@code ClientInfo}。
     *
     * @param ua 应用当前上下文中的 {@code ClientInfo}。
     */
    public static void setClientInfo(ClientInfo ua) {
        AppContext ctx = getContext();
        if (ctx == null) {
            return;
        }
        ctx.attributes.put(KEY_OF_CLIENT_INFO, ua);
    }

    public static String getAccessToken() {
        AppContext ctx = getContext();
        if (ctx == null) {
            return null;
        }
        return (String) ctx.attributes.get(KEY_OF_ACCESS_TOKEN);
    }

    public static void setAccessToken(String accessToken) {
        AppContext ctx = getContext();
        if (ctx == null) {
            return;
        }
        ctx.attributes.put(KEY_OF_ACCESS_TOKEN, accessToken);
    }

    /**
     * 返回应用当前上下文中的指定键的值。
     *
     * @param key 指定键。
     * @return 返回应用当前上下文中的指定键的值。
     */
    public Object getIfAbsent(String key) {
        if (!hasText(key)) {
            return null;
        }
        return attributes.get(key);
    }

    /**
     * 给应用当前上下文添加属性值。如果 {@code key != null && val == null}，则删除指定键的值。
     *
     * @param key 指定键。
     * @param val 指定的值。
     * @return 返回指定键关联的旧值，如果旧值不存在，则返回 {@code null}。
     * @throws IllegalArgumentException 如果 {@code key == null || key.trim().length() == 0}
     *                                  ，抛出此异常。
     */
    public Object put(String key, Object val) {
        checkArgument(hasText(key), "the key must not be null or empty.");
        if (val == null) {
            return attributes.remove(key);
        }
        return attributes.put(key, val);
    }
}
