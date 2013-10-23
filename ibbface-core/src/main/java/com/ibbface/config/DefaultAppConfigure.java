package com.ibbface.config;

import com.google.common.base.Joiner;
import com.google.common.net.InternetDomainName;

/**
 * Default {@link AppConfigure} interface implementation.
 *
 * @author Fuchun
 * @since 1.0
 */
public class DefaultAppConfigure implements AppConfigure {
    private static final long serialVersionUID = 1L;

    private static final DefaultAppConfigure INSTANCE = new DefaultAppConfigure();

    private static final String DEFAULT_URL_HTTP_PORT = "80";
    private static final String DEFAULT_URL_HTTPS_PORT = "443";

    private String mainDomain;
    private String topPrivateDomain;
    private String cookieDomain;
    private String staticDomain;
    private String urlHttpPort = DEFAULT_URL_HTTP_PORT;
    private String urlHttpsPort = DEFAULT_URL_HTTPS_PORT;
    private String contextPath = "/";
    private String urlHttpPrefix;
    private String urlHttpsPrefix;
    private Theme theme;
    private boolean isDevMode = false;

    private String imagesUrl;
    private String stylesUrl;
    private String scriptsUrl;
    private String skinImagesUrl;
    private String skinStylesUrl;

    DefaultAppConfigure() {
        setMainDomain("api.ibbface.com");
        setThemeName("default");
    }

    /**
     * Returns the {@link DefaultAppConfigure} instance.
     */
    public static DefaultAppConfigure getInstance() {
        return INSTANCE;
    }

    @Override
    public String getMainDomain() {
        return mainDomain;
    }

    public void setMainDomain(String mainDomain) {
        this.mainDomain = mainDomain;
        initByMainDomain();
    }

    private void initByMainDomain() {
        if (mainDomain == null) {
            return;
        }
        if (InternetDomainName.isValid(mainDomain)) {
            InternetDomainName mainDomainName = InternetDomainName.from(mainDomain);
            topPrivateDomain = mainDomainName.topPrivateDomain().toString();
            cookieDomain = "." + topPrivateDomain;
        } else { // IPv4 or IPv6
            topPrivateDomain = mainDomain;
            cookieDomain = mainDomain;
        }
    }

    @Override
    public String getTopPrivateDomain() {
        return topPrivateDomain;
    }

    @Override
    public String getCookieDomain() {
        return cookieDomain;
    }

    @SuppressWarnings("unused")
    public void setCookieDomain(String cookieDomain) {
        this.cookieDomain = cookieDomain;
    }

    @Override
    public String getStaticDomain() {
        return staticDomain;
    }

    public void setStaticDomain(String staticDomain) {
        this.staticDomain = staticDomain;
    }

    @Override
    public String getUrlHttpPort() {
        return urlHttpPort;
    }

    public void setUrlHttpPort(String urlHttpPort) {
        this.urlHttpPort = urlHttpPort;
    }

    @Override
    public String getUrlHttpsPort() {
        return urlHttpsPort;
    }

    public void setUrlHttpsPort(String urlHttpsPort) {
        this.urlHttpsPort = urlHttpsPort;
    }

    @Override
    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    @Override
    public String getUrlHttpPrefix() {
        if (urlHttpPrefix == null) {
            urlHttpPrefix = Joiner.on("").join(
                    "http://",
                    getMainDomain(),
                    (DEFAULT_URL_HTTP_PORT.equals(getUrlHttpPort()) ? "" : ":" + getUrlHttpPort()),
                    getContextPath());
        }
        return urlHttpPrefix;
    }

    @Override
    public String getUrlHttpsPrefix() {
        if (urlHttpsPrefix == null) {
            urlHttpsPrefix = Joiner.on("").join(
                    "https://",
                    getMainDomain(),
                    (DEFAULT_URL_HTTPS_PORT.equals(getUrlHttpsPort()) ? "" : ":" + getUrlHttpsPort()),
                    getContextPath());
        }
        return urlHttpsPrefix;
    }

    @Override
    public String getImagesUrl() {
        if (imagesUrl == null) {
            imagesUrl = buildStaticUrl("/images");
        }
        return imagesUrl;
    }

    @Override
    public String getStylesUrl() {
        if (stylesUrl == null) {
            stylesUrl = buildStaticUrl("/styles");
        }
        return stylesUrl;
    }

    @Override
    public String getScriptsUrl() {
        if (scriptsUrl == null) {
            scriptsUrl = buildStaticUrl("/scripts");
        }
        return scriptsUrl;
    }

    private String buildStaticUrl(final String ctxPath) {
        return Joiner.on("").join("http://",
                getStaticDomain(),
                (DEFAULT_URL_HTTP_PORT.equals(getUrlHttpPort()) ? "" : ":" + getUrlHttpPort()),
                getContextPath(), ctxPath);
    }

    @Override
    public String getSkinImagesUrl() {
        if (skinImagesUrl == null) {
            skinImagesUrl = Joiner.on("/").join(getImagesUrl(), "skins", getTheme().getName());
        }
        return skinImagesUrl;
    }

    @Override
    public String getSkinStylesUrl() {
        if (skinStylesUrl == null) {
            skinStylesUrl = Joiner.on("/").join(getStylesUrl(), "skins", getTheme().getName());
        }
        return skinStylesUrl;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public void setThemeName(String themeName) {
        setTheme(Theme.simplifiedChinese(themeName));
    }

    @Override
    public boolean isDevMode() {
        return isDevMode;
    }

    public void setDevMode(boolean devMode) {
        isDevMode = devMode;
    }

    @Override
    public String toString() {
        return "DefaultAppConfigure{" +
                "mainDomain='" + getMainDomain() + '\'' +
                ", topPrivateDomain='" + getTopPrivateDomain() + '\'' +
                ", cookieDomain='" + getCookieDomain() + '\'' +
                ", staticDomain='" + getStaticDomain() + '\'' +
                ", urlHttpPort='" + getUrlHttpPort() + '\'' +
                ", urlHttpsPort='" + getUrlHttpsPort() + '\'' +
                ", contextPath='" + getContextPath() + '\'' +
                ", theme=" + getTheme() +
                ", isDevMode=" + isDevMode() +
                '}';
    }
}
