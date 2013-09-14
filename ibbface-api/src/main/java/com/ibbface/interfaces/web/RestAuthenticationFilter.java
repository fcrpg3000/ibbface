/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) RestAuthenticationFilter.java 2013-09-08 23:04
 */

package com.ibbface.interfaces.web;

import com.ibbface.interfaces.realm.LoginAccount;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Fuchun
 * @since 1.0
 */
public class RestAuthenticationFilter extends FormAuthenticationFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String DEFAULT_ACCOUNT_PARAM = "account";
    public static final String DEFAULT_CAPTCHA_PARAM = "captcha";

    private String captchaParam = DEFAULT_CAPTCHA_PARAM;

    public RestAuthenticationFilter() {
        setUsernameParam(DEFAULT_ACCOUNT_PARAM);
    }

    public String getCaptchaParam() {
        return captchaParam;
    }

    public void setCaptchaParam(String captchaParam) {
        this.captchaParam = captchaParam;
    }

    protected String getCaptcha(ServletRequest request) {
        return WebUtils.getCleanParam(request, getCaptchaParam());
    }

    /**
     * Returns the {@link #AUTHORIZATION_HEADER AUTHORIZATION_HEADER} from the specified ServletRequest.
     * <p/>
     * This implementation merely casts the request to an <code>HttpServletRequest</code> and returns the header:
     * <p/>
     * <code>HttpServletRequest httpRequest = {@link WebUtils#toHttp(javax.servlet.ServletRequest) toHttp(reaquest)};<br/>
     * return httpRequest.getHeader({@link #AUTHORIZATION_HEADER AUTHORIZATION_HEADER});</code>
     *
     * @param request the incoming <code>ServletRequest</code>
     * @return the <code>Authorization</code> header's value.
     */
    protected String getAuthzHeader(ServletRequest request) {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        return httpRequest.getHeader(AUTHORIZATION_HEADER);
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String authorizationHeader = getAuthzHeader(request);
        String account = getUsername(request);
        String password = getPassword(request);
        String host = getHost(request);
        String captcha = getCaptcha(request);
        boolean rememberMe = isRememberMe(request);
        LoginAccount loginAccount = new LoginAccount(account, password, rememberMe, host, captcha);

        String[] clientInfo = getClientInfo(authorizationHeader, request);
        if (clientInfo != null) {
            loginAccount.setClientId(clientInfo[0]);
            loginAccount.setClientSecret(clientInfo[1]);
        }
        return loginAccount;
    }

    protected String[] getClientInfo(String authorizationHeader, ServletRequest request) {
        if (authorizationHeader == null) {
            return null;
        }
        String[] authTokens = authorizationHeader.split(" ");
        if (authTokens == null || authTokens.length < 2) {
            return null;
        }
        return getClientInfo(authTokens[0], authTokens[1]);
    }

    protected String[] getClientInfo(String schema, String encoded) {
        if (!"Basic".equals(schema)) {
            return null;
        }
        String decoded = Base64.decodeToString(encoded);
        return decoded.split(":", 2);
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        LoginAccount loginAccount = (LoginAccount) createToken(request, response);

        try {
            Subject subject = SecurityUtils.getSubject();
            subject.login(loginAccount);

            return onLoginSuccess(loginAccount, subject, request, response);
        } catch (AuthenticationException ex) {
            return onLoginFailure(loginAccount, ex, request, response);
        }
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject,
                                     ServletRequest request, ServletResponse response) throws Exception {
        // Rest 方式不进行跳转，由 controller 处理返回信息
        request.setAttribute("authToken", token);
        request.setAttribute("subject", subject);
        return true;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e,
                                     ServletRequest request, ServletResponse response) {
        request.setAttribute("auth_exception", e);
        return true;
    }
}
