package com.ibbface.domain.model.client;

import com.ibbface.domain.shared.ValueObject;

/**
 * The application client type enumeration.
 *
 * @author Fuchun
 * @since 1.0
 */
public enum ClientType implements ValueObject<ClientType> {

    /**
     * 未知的。
     */
    UNKNOWN(0, "未知的客户端", false),

    /**
     * Web 版（浏览器）
     */
    WEB(1, "普通版", false),

    /**
     * Web 专业版
     */
    WEB_PE(2, "专业版", false),

    /**
     * Android 客户端。
     */
    ANDROID(3, "Android客户端", true),

    /**
     * iPhone 客户端。
     */
    IPHONE(4, "iPhone客户端", true),

    /**
     * WindowsPhone 客户端。
     */
    WINDOWS_PHONE(5, "WindowsPhone客户端", true),

    /**
     * iPad 客户端。
     */
    IPAD(6, "iPad客户端", true),

    /**
     * iPod 客户端。
     */
    IPOD(7, "iPod客户端", true),

    /**
     * FirefoxOS 客户端。
     */
    FIREFOX_OS(8, "FirefoxOS客户端", true);

    final short code;
    final String name;
    final boolean isMobile;

    private ClientType(int code, String name, boolean mobile) {
        this.code = (short) code;
        this.name = name;
        isMobile = mobile;
    }

    public short getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public boolean isMobile() {
        return isMobile;
    }

    /**
     * 值对象类对象是否相等，需要比较对象内的属性值，它没有唯一标识。
     *
     * @param other 其他的值对象。
     * @return 如果比较的值对象和当前对象相等，则返回 {@code true}，否则 {@code false}。
     */
    @Override
    public boolean sameValueAs(ClientType other) {
        return other != null && this == other;
    }

    public boolean isWeb() {
        return WEB == this || WEB_PE == this;
    }

    /**
     * Returns this enum object JSON string.
     * <p/>
     * {"code":n,"name":"Name","isMobile":true|false}
     */
    public String toJSONString() {
        return String.format("{\"code\":%s,\"name\":\"%s\",\"isMobile\":%s}",
                code, name, Boolean.valueOf(isMobile).toString());
    }

    /**
     * Returns all verified {@link ClientType}s JSON string.
     */
    public static String allJSONString() {
        StringBuilder builder = new StringBuilder("[");
        for (ClientType c : values()) {
            if (c == UNKNOWN) {
                continue;
            }
            builder.append(c.toJSONString()).append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.append("]").toString();
    }

    /**
     * 返回指定标识的 {@link ClientType} 枚举，如果指定的 {@code code == null}
     * 或者不存在，则返回 {@link #UNKNOWN}。
     *
     * @param code 客户端标识。
     * @param <T>  客户端标识类型。
     * @return 返回指定标识的 {@link ClientType} 枚举。
     */
    public static <T extends Number> ClientType of(T code) {
        if (code == null) {
            return UNKNOWN;
        }
        for (ClientType c : values()) {
            if (c.code == code.shortValue()) {
                return c;
            }
        }
        return UNKNOWN;
    }

    public static ClientType of(String name) {
        if (name == null) {
            return UNKNOWN;
        }
        for (ClientType c : values()) {
            if (c.name().equalsIgnoreCase(name)) {
                return c;
            }
        }
        return UNKNOWN;
    }
}
