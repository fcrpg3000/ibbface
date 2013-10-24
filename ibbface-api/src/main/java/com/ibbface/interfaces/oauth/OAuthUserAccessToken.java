package com.ibbface.interfaces.oauth;

import org.apache.shiro.authc.HostAuthenticationToken;

/**
 * @author Fuchun
 * @since 1.0
 */
public class OAuthUserAccessToken implements HostAuthenticationToken {

    private Long userId;
    private String accessToken;
    private String host;

    public OAuthUserAccessToken() {
    }

    public OAuthUserAccessToken(Long userId, String accessToken) {
        this(userId, accessToken, null);
    }

    public OAuthUserAccessToken(Long userId, String accessToken, String host) {
        this.userId = userId;
        this.accessToken = accessToken;
        this.host = host;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public Object getPrincipal() {
        return getUserId();
    }

    @Override
    public Object getCredentials() {
        return getAccessToken();
    }

    public void clear() {
        userId = null;
        accessToken = null;
        host = null;
    }

    @Override
    public String toString() {
        return "OAuthUserAccessToken{" +
                "userId=" + getUserId() +
                ", accessToken='" + getAccessToken() + '\'' +
                ", host='" + getHost() + '\'' +
                '}';
    }
}
