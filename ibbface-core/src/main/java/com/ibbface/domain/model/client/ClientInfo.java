/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) ClientInfo.java 2013-09-03 16:25
 */

package com.ibbface.domain.model.client;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.ibbface.domain.model.common.Browser;
import org.apache.commons.codec.digest.DigestUtils;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ibbface.domain.model.client.ClientOS.*;

/**
 * The application client information.
 *
 * @author Fuchun
 * @since 1.0
 */
public abstract class ClientInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    public static ClientInfo fromUserAgent(String ua) {
        return new UserAgentClientInfo(ua);
    }

    /**
     * Returns APP client identity code (id in cookie if client is browser).
     */
    public abstract String getId();

    /**
     * Returns APP client Operation System information.
     */
    public abstract ClientOS getClientOS();

    /**
     * Returns APP client enumeration object.
     * if this value equals {@link com.ibbface.domain.model.client.ClientType#WEB} or {@link com.ibbface.domain.model.common.Client#WEB_PE}, {@link #getBrowser()} value is not null.
     */
    public abstract ClientType getClientType();

    /**
     * Returns remote requested client browser, or {@code null} if user used APP client.
     */
    public abstract Browser getBrowser();

    /**
     * Returns APP client version (e.g. Browser, Mobile or tablet APP version).
     */
    public abstract String getVersion();

    /**
     * Returns APP client model string.
     */
    public abstract String getModel();

    static class ImmutableClientInfo extends ClientInfo {
        private static final long serialVersionUID = 1L;

        private final String id;
        private final ClientType client;
        private final ClientOS clientOS;
        private final Browser browser;
        private final String version;
        private final String model;

        ImmutableClientInfo(String id, ClientType client, ClientOS clientOS,
                            Browser browser, String version, String model) {
            this.id = id;
            this.client = client;
            this.clientOS = clientOS;
            this.browser = browser;
            this.version = version;
            this.model = model;
        }

        /**
         * Returns APP client identity code (id in cookie if client is browser).
         */
        @Override
        public String getId() {
            return id;
        }

        /**
         * Returns APP client Operation System information.
         */
        @Override
        public ClientOS getClientOS() {
            return clientOS;
        }

        /**
         * Returns APP client enumeration object.
         * if this value equals {@link com.ibbface.domain.model.client.ClientType#WEB} or {@link com.ibbface.domain.model.client.ClientType#WEB_PE}, {@link #getBrowser()} value is not null.
         */
        @Override
        public ClientType getClientType() {
            return client;
        }

        /**
         * Returns remote requested client browser, or {@code null} if user used APP client.
         */
        @Override
        public Browser getBrowser() {
            return browser;
        }

        /**
         * Returns APP client version (e.g. Browser, Mobile or tablet APP version).
         */
        @Override
        public String getVersion() {
            return version;
        }

        /**
         * Returns APP client model string.
         */
        @Override
        public String getModel() {
            return model;
        }
    }

    static class UserAgentClientInfo extends ClientInfo {
        private static final long serialVersionUID = 1L;

        static final Pattern WIN_OS_PATTERN = Pattern.compile("windows nt ([0-9\\.]+)");
        static final Pattern MAC_OS_PATTERN = Pattern.compile("mac os x ([0-9_\\.]+)");
        static final Pattern IOS_PATTERN = Pattern.compile("cpu(?: iphone)? os ([0-9_]+)");
        static final Pattern IOS_VERSION = Pattern.compile("(\\d+)_(\\d+)(?:_(\\d+))?");
        static final Pattern ANDROID_OS_PATTERN = Pattern.compile("android\\s*([0-9\\.]+)");
        static final Pattern WP_OS_PATTERN = Pattern.compile("windows phone(?: os)? ([0-9\\.]+)");
        static final Pattern ANDROID_MODEL_PATTER = Pattern.compile(
                "\\(linux[:;] android [\\d\\._]+; (.*?)\\)", Pattern.CASE_INSENSITIVE);

        private final ImmutableClientInfo clientInfo;

        UserAgentClientInfo(String userAgent) {
            clientInfo = parse(userAgent);
        }

        private ImmutableClientInfo parse(@Nonnull String userAgent) {
            // King4/IBBFace.Com/(os_name)/(os_version)/(os_model)/(app_version)/(app_id)
            if (userAgent.contains("King4")) { // mobile client
                return parseAppClientUserAgent(userAgent.trim().toLowerCase());
            } else { // Browser
                return parseBrowserUserAgent(userAgent);
            }
        }

        private ImmutableClientInfo parseAppClientUserAgent(String userAgent) {
            String[] parts = userAgent.split("/");
            if (parts.length != 7) {
                return null;
            }
            String name = parts[2];
            String version = parts[3];
            String model = parts[4];
            String appVer = parts[5];
            String id = parts[6];
            ClientOS clientOS = ClientOS.get(name, version).orNull();
            ClientType clientType = ClientType.of(name);
            return new ImmutableClientInfo(id, clientType, clientOS, null, appVer, model);
        }

        private ImmutableClientInfo parseBrowserUserAgent(String userAgent) {
            String ua = userAgent.trim().toLowerCase();
            Browser browser = Browser.from(ua);
            ClientType clientType = ClientType.WEB;
            ClientOS clientOS = null;
            Matcher m;
            String name, vStr = null, version = null, model = null;
            if (ua.contains(ANDROID) && ua.contains("mozilla")) {
                name = ANDROID;
                m = ANDROID_OS_PATTERN.matcher(ua);
                if (m.find()) {
                    vStr = m.group(1);
                }
                if (vStr != null) {
                    version = vStr.split("\\.").length > 2 ? vStr.replaceFirst("(\\d+)$", "x") : vStr;
                }
                Matcher modelM = ANDROID_MODEL_PATTER.matcher(userAgent);
                if (modelM.find()) {
                    model = modelM.group(1);
                }
                clientOS = ClientOS.get(name, version).or(UNKNOWN_ANDROID);
            } else if (ua.contains("like mac os x")) {
                m = IOS_PATTERN.matcher(ua);
                if (m.find()) {
                    vStr = m.group(1);
                }
                if (vStr != null) {
                    Matcher vm = IOS_VERSION.matcher(vStr);
                    if (vm.find()) {
                        version = String.format("%s.%s", vm.group(1), vm.group(2));
                    }
                }
                if (ua.contains(IPHONE)) {
                    name = IPHONE;
                } else if (ua.contains(IPAD)) {
                    name = IPAD;
                } else if (ua.contains(ClientOS.IPOD)) {
                    name = IPOD;
                } else {
                    name = IPHONE;
                }
                clientOS = ClientOS.get(name, version).or(UNKNOWN_IPHONE);
            } else if (ua.contains("windows nt")) {
                name = WINDOWS;
                m = WIN_OS_PATTERN.matcher(ua);
                if (m.find()) {
                    vStr = m.group(1);
                }
                version = vStr;
                clientOS = ClientOS.get(name, version).or(UNKNOWN_WINDOWS);
            } else if (ua.contains(MAC_OS_X) && !ua.contains("like mac os x")) {
                m = MAC_OS_PATTERN.matcher(ua);
                if (m.find()) {
                    vStr = m.group(1);
                }
                if (vStr != null) {
                    if (ua.contains("firefox")) {
                        version = vStr;
                    } else {
                        Matcher vm = IOS_VERSION.matcher(vStr);
                        if (vm.find()) {
                            version = String.format("%s.%s", vm.group(1), vm.group(2));
                        }
                    }
                }
                clientOS = ClientOS.get(MAC_OS_X, version).or(UNKNOWN_MAX_OS_X);
            } else if (ua.contains(WINDOWS_PHONE)) {
                name = WINDOWS_PHONE;
                m = WP_OS_PATTERN.matcher(ua);
                if (m.find()) {
                    vStr = m.group(1);
                }
                version = vStr;
                clientOS = ClientOS.get(name, version).or(UNKNOWN_WINDOWS_PHONE);
                Iterable<String> parts = Splitter.on(";").trimResults().split(userAgent);
                int size = Iterables.size(parts);
                model = String.format("%s; %s", Iterables.get(parts, size - 2),
                        Iterables.getLast(parts));
            }
            String id = DigestUtils.md5Hex(ua);
            return new ImmutableClientInfo(id, clientType, clientOS, browser, version, model);
        }

        /**
         * Returns APP client identity code (id in cookie if client is browser).
         */
        @Override
        public String getId() {
            return clientInfo.getId();
        }

        /**
         * Returns APP client Operation System information.
         */
        @Override
        public ClientOS getClientOS() {
            return clientInfo.getClientOS();
        }

        /**
         * Returns APP client enumeration object.
         * if this value equals {@link com.ibbface.domain.model.client.ClientType#WEB} or
         * {@link com.ibbface.domain.model.client.ClientType#WEB_PE}, {@link #getBrowser()} value is not null.
         */
        @Override
        public ClientType getClientType() {
            return clientInfo.getClientType();
        }

        /**
         * Returns remote requested client browser, or {@code null} if user used APP client.
         */
        @Override
        public Browser getBrowser() {
            return clientInfo.getBrowser();
        }

        /**
         * Returns APP client version (e.g. Browser, Mobile or tablet APP version).
         */
        @Override
        public String getVersion() {
            return clientInfo.getVersion();
        }

        /**
         * Returns APP client model string.
         */
        @Override
        public String getModel() {
            return clientInfo.getModel();
        }

        public static void main(String[] args) {
            String iphoneUA = "Mozilla/5.0 (iPhone; U; CPU iPhone OS 5_1_1 like Mac OS X; en) " +
                    "AppleWebKit/534.46.0 (KHTML, like Gecko) CriOS/19.0.1084.60 Mobile/9B206 " +
                    "Safari/7534.48.3";
            String maxUA = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_4) AppleWebKit/537.36 " +
                    "(KHTML, like Gecko) Chrome/28.0.1500.95 Safari/537.36";
            String windowsUA = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 " +
                    "(KHTML, like Gecko) Chrome/28.0.1500.71 Safari/537.36";
            String winPhone75UA = "Mozilla/5.0 (compatible; MSIE 9.0; Windows Phone OS 7.5; " +
                    "Trident/5.0; IEMobile/9.0; SAMSUNG; SGH-i917";
            String winPhone80UA = "Mozilla/5.0 (compatible; MSIE 10.0; Windows Phone 8.0; " +
                    "Trident/6.0; IEMobile/10.0; ARM; Touch; NOKIA; Lumia 920";
            String androidUA = "Mozilla/5.0 (Linux; Android 4.0.4; Galaxy Nexus Build/IMM76B) " +
                    "AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.133 Mobile " +
                    "Safari/535.19";
            ClientInfo clientInfo = fromUserAgent(iphoneUA);
            ClientInfo macCI = fromUserAgent(maxUA);
            ClientInfo windows = fromUserAgent(windowsUA);
            ClientInfo winPhone75 = fromUserAgent(winPhone75UA);
            ClientInfo winPhone80 = fromUserAgent(winPhone80UA);
            ClientInfo android = fromUserAgent(androidUA);
            System.out.println(JSON.toJSONString(clientInfo, true));
            System.out.println(JSON.toJSONString(macCI, true));
            System.out.println(JSON.toJSONString(windows, true));
            System.out.println(JSON.toJSONString(winPhone75, true));
            System.out.println(JSON.toJSONString(winPhone80, true));
            System.out.println(JSON.toJSONString(android, true));
        }
    }
}
