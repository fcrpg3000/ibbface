/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) LoginFormAuthenticationFilter.java 2013-08-15 00:48
 */

package com.ibbface.web;

import com.ibbface.interfaces.realm.LoginAccount;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author Fuchun
 * @since 1.0
 */
public class LoginFormAuthenticationFilter extends FormAuthenticationFilter {

    /**
     * Default {@code captcha} param name.
     */
    protected static final String DEFAULT_CAPTCHA_PARAM_NAME = "captcha";

    private String captchaParam = DEFAULT_CAPTCHA_PARAM_NAME;

    public String getCaptchaParam() {
        return captchaParam;
    }

    public void setCaptchaParam(String captchaParam) {
        this.captchaParam = captchaParam;
    }

    public String getCaptcha(ServletRequest request) {
        return WebUtils.getCleanParam(request, getCaptchaParam());
    }

    @Override
    protected LoginAccount createToken(ServletRequest request, ServletResponse response) {
        String username = getUsername(request);
        String password = getPassword(request);
        boolean rememberMe = isRememberMe(request);
        String host = getHost(request);
        String captcha = getCaptcha(request);
        return new LoginAccount(username, password, rememberMe, host, captcha);
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        LoginAccount token = createToken(request, response);

        try {
            Subject subject = getSubject(request, response);
            subject.login(token);

            return onLoginSuccess(token, subject, request, response);
        } catch (AuthenticationException ex) {
            return onLoginFailure(token, ex, request, response);
        }
    }
}
