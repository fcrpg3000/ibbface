/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) Browser.java 2013-09-04 13:54
 */

package com.ibbface.domain.model.common;

import com.google.common.primitives.Floats;
import com.ibbface.domain.shared.ValueObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * The browser enumeration.
 *
 * @author Fuchun
 * @since 1.0
 */
public enum Browser implements ValueObject<Browser> {

    /**
     * Other browser.
     */
    OTHER("Other", ""),

    /**
     * Microsoft InternetExplorer core.
     */
    IE("MSIE", ""),

    /**
     * MS IE6
     */
    IE6(IE.name, "6"),

    /**
     * MS IE7
     */
    IE7(IE.name, "7"),

    /**
     * MS IE8
     */
    IE8(IE.name, "8"),

    /**
     * MS IE9
     */
    IE9(IE.name, "9"),

    /**
     * MS IE10
     */
    IE10(IE.name, "10"),
    /**
     * Chrome browser for any version.
     */
    CHROME("chrome", ""),
    /**
     * Chrome browser for v10-19.
     */
    CHROME_1X(CHROME.name, "1x"),
    /**
     * Chrome browser for v20-29.
     */
    CHROME_2X(CHROME.name, "2x"),
    /**
     * Chrome browser for v30-39.
     */
    CHROME_3x(CHROME.name, "3x"),
    /**
     * Firefox browser for any version.
     */
    FIREFOX("firefox", ""),
    /**
     * Firefox browser for v10-19.
     */
    FIREFOX_1X(FIREFOX.name, "1x"),
    /**
     * Firefox browser for v20-29.
     */
    FIREFOX_2X(FIREFOX.name, "2x"),

    /**
     * Opera浏览器。
     */
    OPERA("Opera", ""),
    /**
     * Opera浏览器。
     */
    OPERA_9(OPERA.name, "9"),
    /**
     * Opera浏览器。
     */
    OPERA_10(OPERA.name, "10"),
    /**
     * Opera浏览器。
     */
    OPERA_11(OPERA.name, "11"),
    /**
     * Opera浏览器。
     */
    OPERA_12(OPERA.name, "12"),
    /**
     * Safari浏览器。
     */
    SAFARI("Safari", ""),
    /**
     * Safari浏览器。
     */
    SAFARI_4(SAFARI.name, "4"),
    /**
     * Safari浏览器。
     */
    SAFARI_5(SAFARI.name, "5"),
    /**
     * Safari浏览器。
     */
    SAFARI_6(SAFARI.name, "6");

    private final String name;
    private final String version;

    private Browser(String name, String version) {
        this.name = name;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    /**
     * 值对象类对象是否相等，需要比较对象内的属性值，它没有唯一标识。
     *
     * @param other 其他的值对象。
     * @return 如果比较的值对象和当前对象相等，则返回 {@code true}，否则 {@code false}。
     */
    @Override
    public boolean sameValueAs(Browser other) {
        return other != null && this == other;
    }

    private static final Pattern PATTERN_MSIE_VERSION = Pattern.compile("msie ([\\d\\.]*)");
    private static final Pattern PATTERN_OPERA_VERSION = Pattern.compile("version/([\\d\\.]*)");
    private static final Pattern PATTERN_FF_VERSION = Pattern.compile("firefox/([\\d\\.]*)");
    private static final Pattern PATTERN_CHROME_VERSION = Pattern.compile("(?:chrome|crios)/([\\d\\.]*)");
    private static final Pattern PATTERN_SAFARI_VERSION = Pattern.compile("version/([\\d\\.]*)");

    public static Browser of(String name, String version) {
        if (name == null && version == null) {
            return OTHER;
        }
        if (IE.name.equalsIgnoreCase(name)) {
            if (version == null || version.length() == 0) {
                return IE;
            }
            if (IE9.getVersion().equals(version)) {
                return IE9;
            } else if (IE8.getVersion().equals(version)) {
                return IE8;
            } else if (IE6.getVersion().equals(version)) {
                return IE6;
            } else if (IE7.getVersion().equals(version)) {
                return IE7;
            } else if (IE10.getVersion().equals(version)) {
                return IE10;
            } else {
                return IE;
            }
        } else if (CHROME.name.equalsIgnoreCase(name) || "crios".equalsIgnoreCase(name)) {
            if (version == null || version.length() == 0) {
                return CHROME;
            }
            if (version.startsWith("1")) {
                return CHROME_1X;
            } else if (version.startsWith("2")) {
                return CHROME_2X;
            } else {
                return CHROME_3x;
            }
        } else if (FIREFOX.name.equalsIgnoreCase(name)) {
            if (version == null || version.length() == 0) {
                return FIREFOX;
            }
            if (version.startsWith("1")) {
                return FIREFOX_1X;
            } else if (version.startsWith("2")) {
                return FIREFOX_2X;
            } else {
                return FIREFOX;
            }
        } else if (SAFARI.name.equalsIgnoreCase(name)) {
            if (version == null || version.length() == 0) {
                return SAFARI;
            }
            if (SAFARI_4.getVersion().equals(version)) {
                return SAFARI_4;
            } else if (SAFARI_5.getVersion().equals(version)) {
                return SAFARI_5;
            } else if (SAFARI_6.getVersion().equals(version)) {
                return SAFARI_6;
            } else {
                return SAFARI;
            }
        } else if (OPERA.name.equalsIgnoreCase(name)) {
            if (version == null || version.length() == 0) {
                return OPERA;
            }
            if (OPERA_12.getVersion().equals(version)) {
                return OPERA_12;
            } else if (OPERA_11.getVersion().equals(version)) {
                return OPERA_11;
            } else if (OPERA_10.getVersion().equals(version)) {
                return OPERA_10;
            } else {
                return OPERA_9;
            }
        }
        return OTHER;
    }

    public static Browser from(String srcUserAgent) {
        if (isNullOrEmpty(srcUserAgent)) {
            return OTHER;
        }
        String userAgent = srcUserAgent.trim().toLowerCase();
        Matcher m;
        Float fVersion = null;
        if (userAgent.contains("compatible") && userAgent.contains("msie")) {
            m = PATTERN_MSIE_VERSION.matcher(userAgent);
            if (m.find()) {
                fVersion = Floats.tryParse(m.group(1));
            }
            if (fVersion == null) {
                return IE;
            } else {
                return of(IE.name, String.valueOf(fVersion.intValue()));
            }
        } else if (userAgent.contains(CHROME.name) || userAgent.contains("crios")) {
            m = PATTERN_CHROME_VERSION.matcher(userAgent);
            if (m.find()) {
                String vStr = m.group(1);
                fVersion = Floats.tryParse(vStr.split("\\.")[0]);
            }
            if (fVersion == null) {
                return CHROME;
            } else {
                return of(CHROME.name, String.valueOf(fVersion.intValue()));
            }
        } else if (userAgent.contains(FIREFOX.name)) {
            m = PATTERN_FF_VERSION.matcher(userAgent);
            if (m.find()) {
                fVersion = Floats.tryParse(m.group(1));
            }
            if (fVersion == null) {
                return FIREFOX;
            } else {
                return of(FIREFOX.name, String.valueOf(fVersion.intValue()));
            }
        } else if (userAgent.contains(SAFARI.name) &&
                !userAgent.contains(CHROME.name)) {
            m = PATTERN_SAFARI_VERSION.matcher(userAgent);
            if (m.find()) {
                fVersion = Floats.tryParse(m.group(1));
            }
            if (fVersion == null) {
                return SAFARI;
            } else {
                return of(SAFARI.name, String.valueOf(fVersion.intValue()));
            }
        } else if (userAgent.contains(OPERA.name)) {
            m = PATTERN_OPERA_VERSION.matcher(userAgent);
            if (m.find()) {
                fVersion = Floats.tryParse(m.group(1));
            }
            if (fVersion == null) {
                return OPERA;
            } else {
                return of(OPERA.name, String.valueOf(fVersion.intValue()));
            }
        }
        return OTHER;
    }
}
