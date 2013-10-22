package com.ibbface.config;

import java.io.Serializable;

/**
 * Application Configure interface.
 *
 * @author Fuchun
 * @since 1.0
 */
public interface AppConfigure extends Serializable {

    /**
     * Returns this App main domain. e.g. api.ibbface.com
     */
    public String getMainDomain();

    /**
     * Returns this App top private domain. e.g. ibbface.com
     */
    public String getTopPrivateDomain();

    /**
     * Returns this App cookie's domain. e.g. .ibbface.com
     */
    public String getCookieDomain();

    /**
     * Returns this App static resource url prefix. e.g. img.ibbface.com
     */
    public String getStaticDomain();

    /**
     * Returns this App url http port. Default: 80.
     */
    public String getUrlHttpPort();

    /**
     * Returns this App url https (SSL) port. Default: 443.
     */
    public String getUrlHttpsPort();

    /**
     * Returns this App url context path. Default: /
     */
    public String getContextPath();

    /**
     * Returns this App http url prefix.
     * This value is https:// + {@link #getMainDomain()} [+ ":{@link #getUrlHttpPort()}"] +
     * {@link #getContextPath()}. No port if url http port is 80.
     * <p/>
     * NOTE: In dev mode, This value may be http://127.0.0.1:8080/ibbface-api
     */
    public String getUrlHttpPrefix();

    /**
     * Returns this App https url prefix.
     * This value is https:// + {@link #getMainDomain()} [+ ":{@link #getUrlHttpsPort()}"] +
     * {@link #getContextPath()}. No port if url https port is 443.
     * <p/>
     * NOTE: In dev mode, This value may be https://127.0.0.1:8443/ibbface-api
     */
    public String getUrlHttpsPrefix();

    /**
     * Returns this App images url prefix. like http://static.ibbface.com/images
     */
    public String getImagesUrl();

    /**
     * Returns this App styles url prefix. like http://static.ibbface.com/styles
     */
    public String getStylesUrl();

    /**
     * Returns this App scripts url prefix. like http://static.ibbface.com/scripts
     */
    public String getScriptsUrl();

    /**
     * Returns this App current skin image url prefix. like http://static.ibbface.com/images/skins/default
     */
    public String getSkinImagesUrl();

    /**
     * Returns this App current skin styles url prefix. like http://static.ibbface.com/styles/skins/default
     */
    public String getSkinStylesUrl();

    /**
     * Returns this App UI theme.
     */
    public Theme getTheme();

    /**
     * Return {@code true} if this App is dev mode, otherwise {@code false}.
     * The application can output more useful debug information in dev mode.
     * Default value is {@code false}.
     */
    public boolean isDevMode();
}
