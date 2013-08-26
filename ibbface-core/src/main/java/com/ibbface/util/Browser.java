/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) Browser.java 2013-08-24 13:59
 */
package com.ibbface.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用户登录时使用的浏览器。
 * 
 * @author fuchun
 * @version $Id: Browser.java 27644 2013-05-13 10:57:55Z C629 $
 * @since 1.0
 */
public enum Browser {
    /** 谷歌浏览器。 */
    CHROME("Chrome", -1),
    /** 谷歌浏览器。 */
    CHROME_10(CHROME.name, 10),
    /** 谷歌浏览器。*/
    CHROME_20(CHROME.name, 20),
    /** 全新引擎的Chrome。*/
    CHROME_29(CHROME.name, 29),
    /** IE浏览器。 */
    IE("MSIE", -1),
    /** IE浏览器。 */
    IE6(IE.name, 6),
    /** IE浏览器。 */
    IE7(IE.name, 7),
    /** IE浏览器。 */
    IE8(IE.name, 8),
    /** IE浏览器。 */
    IE9(IE.name, 9),
    /** IE浏览器。 */
    IE10(IE.name, 10),
    /** Firefox浏览器。 */
    FIREFOX("Firefox", -1),
    /** Firefox 浏览器 (3 - 10)。 */
    FIREFOX_09(FIREFOX.name, 9),
    /** Firefox 浏览器 (11 - 19)*/
    FIREFOX_10(FIREFOX.name, 10),
    /** Firefox 浏览器 (21 - 29)。*/
    FIREFOX_20(FIREFOX.name, 20),
    /** Opera浏览器。 */
    OPERA("Opera", -1),
    /** Opera浏览器。 */
    OPERA_9(OPERA.name, 9),
    /** Opera浏览器。 */
    OPERA_10(OPERA.name, 10),
    /** Opera浏览器。 */
    OPERA_11(OPERA.name, 11),
    /** Opera浏览器。 */
    OPERA_12(OPERA.name, 12),
    /** Safari浏览器。 */
    SAFARI("Safari", -1),
    /** Safari浏览器。 */
    SAFARI_4(SAFARI.name, 4),
    /** Safari浏览器。 */
    SAFARI_5(SAFARI.name, 5),
    /** Safari浏览器。 */
    SAFARI_6(SAFARI.name, 6),
    /** 其他浏览器。 */
    OTHER("Other", -1);

    private final String name;
    private final int version;

    private Browser(String name, int version) {
        this.name = name;
        this.version = version;
    }

    /** 返回浏览器名称。 */
    public String getName() {
        return name;
    }

    /** 返回包括版本号的浏览器全称。 */
    public String getFullname() {
        return String.format("%s %s", name, version);
    }

    /** 返回浏览器版本。 */
    public int getVersion() {
        return version;
    }

    public boolean isIE() {
        return this.name.equals(IE.name);
    }

    public boolean isIE6() {
        return IE6 == this;
    }

    public boolean isIE7() {
        return IE7 == this;
    }

    public boolean isIE8() {
        return IE8 == this;
    }

    public boolean isIE9() {
        return IE9 == this;
    }

    public boolean isFirefox() {
        return this.name.equals(FIREFOX.name);
    }

    public boolean isChrome() {
        return CHROME.name.equals(this.name);
    }

    public boolean isOpera() {
        return OPERA.name.equals(this.name);
    }

    /**
     * 根据指定的名称和版本，返回浏览器枚举实例。
     * 
     * @param name 浏览器名称。
     * @param version 浏览器版本。
     * @return 指定的名称和版本的浏览器枚举实例。
     */
    public static Browser of(String name, int version) {
        if (IE.name.equalsIgnoreCase(name)) {
            switch (version) {
            case 8:
                return IE8;
            case 6:
                return IE6;
            case 7:
                return IE7;
            case 9:
                return IE9;
            case 10:
                return IE10;
            default:
                return IE;
            }
        } else if (CHROME.name.equalsIgnoreCase(name)) {
            if (version >= 9 && version <= 19) {
                return CHROME_10;
            } else if (version >= 20 && version <= 28) {
                return CHROME_20;
            } else if (version == 29) {
                return CHROME_29;
            } else {
                return CHROME;
            }
        } else if (FIREFOX.name.equalsIgnoreCase(name)) {
            if (version >= 3 && version <= 9) {
                return FIREFOX_09;
            } else if (version >= 10 && version <= 19) {
                return FIREFOX_10;
            } else if (version >= 20 && version <= 29) {
                return FIREFOX_20;
            } else {
                return FIREFOX;
            }
        } else if (OPERA.name.equalsIgnoreCase(name)) {
            switch (version) {
            case 12:
                return OPERA_12;
            case 11:
                return OPERA_11;
            case 10:
                return OPERA_10;
            case 9:
                return OPERA_9;
            default:
                return OPERA;
            }
        } else if (SAFARI.name().equalsIgnoreCase(name)) {
            switch (version) {
                case 6:
                    return SAFARI_6;
                case 5:
                    return SAFARI_5;
                case 4:
                    return SAFARI_4;
                default:
                    return SAFARI;
            }
        }
        return OTHER;
    }

    private static final Pattern PATTERN_MSIE_VERSION = Pattern.compile("msie ([\\d\\.]*)");
    private static final Pattern PATTERN_OPERA_VERSION = Pattern.compile("version/([\\d\\.]*)");
    private static final Pattern PATTERN_FF_VERSION = Pattern.compile("firefox/([\\d\\.]*)");
    private static final Pattern PATTERN_CHROME_VERSION = Pattern.compile("chrome/([\\d\\.]*)");
    private static final Pattern PATTERN_SAFARI_VERSION = Pattern.compile("version/([\\d\\.]*)");

    /**
     * 根据浏览器提供的用户信息解析浏览器名称及版本。
     *
     * TODO: contains maybe a bug?
     * @param srcUserAgent 用户浏览器客户端信息。
     * @return 浏览器枚举。
     */
    public static Browser parse(String srcUserAgent) {
        if (srcUserAgent == null || srcUserAgent.trim().length() == 0)
            return OTHER;
        String userAgent = srcUserAgent.toLowerCase();
        Matcher m;
        int version = -1;
        if (userAgent.contains("compatible")
                && userAgent.contains(IE.name)) {
            m = PATTERN_MSIE_VERSION.matcher(userAgent);
            if (m.find()) {
                version = Float.valueOf(m.group(1)).intValue();
            }
            if (version == -1) {
                return IE;
            } else {
                return of(IE.name, version);
            }
        } else if (userAgent.contains(CHROME.name)) {
            m = PATTERN_CHROME_VERSION.matcher(userAgent);
            if (m.find()) {
                version = Float.valueOf(m.group(1)).intValue();
            }
            if (version == -1) {
                return CHROME;
            } else {
                return of(CHROME.name, version);
            }
        } else if (userAgent.contains(FIREFOX.name)
                && userAgent.contains("rv:")) {
            m = PATTERN_FF_VERSION.matcher(userAgent);
            if (m.find()) {
                version = Float.valueOf(m.group(1)).intValue();
            }
            if (version == -1) {
                return FIREFOX;
            } else {
                return of(FIREFOX.name, version);
            }
        } else if (userAgent.contains(OPERA.name)
                && userAgent.contains("Presto")) {
            m = PATTERN_OPERA_VERSION.matcher(userAgent);
            if (m.find()) {
                version = Float.valueOf(m.group(1)).intValue();
            }
            if (version == -1) {
                return OPERA;
            } else {
                return of(OPERA.name, version);
            }
        } else if (userAgent.contains(SAFARI.name)
                && !userAgent.contains(CHROME.name)) {
            m = PATTERN_SAFARI_VERSION.matcher(userAgent);
            if (m.find()) {
                version = Float.valueOf(m.group(1)).intValue();
            }
            if (version == -1) {
                return SAFARI;
            } else {
                return of(SAFARI.name, version);
            }
        }
        return OTHER;
    }
}