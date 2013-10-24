package com.ibbface.interfaces.oauth;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Fuchun
 * @since 1.0
 */
public class OAuthFormAuthenticationFilter extends AuthenticatingFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(OAuthFormAuthenticationFilter.class);

    public static final String DEFAULT_ERROR_KEY_ATTRIBUTE_NAME = "loginFailureError";
    public static final String DEFAULT_TOKEN_KEY_ATTRIBUTE_NAME = "token";
    public static final String DEFAULT_SUBJECT_KEY_ATTRIBUTE_NAME = "subject";

    public static final String DEFAULT_ACCOUNT_PARAM = "account";
    public static final String DEFAULT_PASSWORD_PARAM = "password";
    public static final String DEFAULT_REMEMBER_ME_PARAM = "rememberMe";

    private String accountParam = DEFAULT_ACCOUNT_PARAM;
    private String passwordParam = DEFAULT_PASSWORD_PARAM;
    private String rememberMeParam = DEFAULT_REMEMBER_ME_PARAM;

    private String failureKeyAttribute = DEFAULT_ERROR_KEY_ATTRIBUTE_NAME;
    private String tokenKeyAttribute = DEFAULT_TOKEN_KEY_ATTRIBUTE_NAME;
    private String subjectKeyAttribute = DEFAULT_SUBJECT_KEY_ATTRIBUTE_NAME;

    public String getAccountParam() {
        return accountParam;
    }

    /**
     * Sets the request parameter name to look for when acquiring the account.  Unless overridden by calling this
     * method, the default is {@code account}.
     *
     * @param accountParam the name of the request param to check for acquiring the account.
     */
    public void setAccountParam(String accountParam) {
        this.accountParam = accountParam;
    }

    public String getPasswordParam() {
        return passwordParam;
    }

    /**
     * Sets the request parameter name to look for when acquiring the password.  Unless overridden by calling this
     * method, the default is <code>password</code>.
     *
     * @param passwordParam the name of the request param to check for acquiring the password.
     */
    public void setPasswordParam(String passwordParam) {
        this.passwordParam = passwordParam;
    }

    public String getRememberMeParam() {
        return rememberMeParam;
    }

    /**
     * Sets the request parameter name to look for when acquiring the rememberMe boolean value.  Unless overridden
     * by calling this method, the default is <code>rememberMe</code>.
     * <p/>
     * RememberMe will be <code>true</code> if the parameter value equals any of those supported by
     * {@link org.apache.shiro.web.util.WebUtils#isTrue(javax.servlet.ServletRequest, String) WebUtils.isTrue(request,value)}, <code>false</code>
     * otherwise.
     *
     * @param rememberMeParam the name of the request param to check for acquiring the rememberMe boolean value.
     */
    public void setRememberMeParam(String rememberMeParam) {
        this.rememberMeParam = rememberMeParam;
    }

    public String getFailureKeyAttribute() {
        return failureKeyAttribute;
    }

    public void setFailureKeyAttribute(String failureKeyAttribute) {
        this.failureKeyAttribute = failureKeyAttribute;
    }

    public String getTokenKeyAttribute() {
        return tokenKeyAttribute;
    }

    public void setTokenKeyAttribute(String tokenKeyAttribute) {
        this.tokenKeyAttribute = tokenKeyAttribute;
    }

    public String getSubjectKeyAttribute() {
        return subjectKeyAttribute;
    }

    public void setSubjectKeyAttribute(String subjectKeyAttribute) {
        this.subjectKeyAttribute = subjectKeyAttribute;
    }

    protected String getAccount(ServletRequest request) {
        return WebUtils.getCleanParam(request, getAccountParam());
    }

    protected String getPassword(ServletRequest request) {
        return WebUtils.getCleanParam(request, getPasswordParam());
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        final String account = getAccount(request);
        final String password = getPassword(request);
        return createToken(account, password, request, response);
    }

    protected AuthenticationToken createToken(String account, String password,
                                              ServletRequest request, ServletResponse response) {
        final String host = getHost(request);
        final boolean rememberMe = isRememberMe(request);
        OAuthParameter oAuthParameter = OAuthParameter.fromRequest((HttpServletRequest) request);
        return new OAuthAccountPasswordToken(account, password, rememberMe, host, oAuthParameter);
    }

    /**
     * This default implementation merely returns <code>true</code> if the request is an HTTP <code>POST</code>,
     * <code>false</code> otherwise. Can be overridden by subclasses for custom login submission detection behavior.
     *
     * @param request  the incoming ServletRequest
     * @param response the outgoing ServletResponse.
     * @return <code>true</code> if the request is an HTTP <code>POST</code>, <code>false</code> otherwise.
     */
    @SuppressWarnings({"UnusedDeclaration"})
    protected boolean isLoginSubmission(ServletRequest request, ServletResponse response) {
        return (request instanceof HttpServletRequest) && WebUtils.toHttp(request).getMethod().equalsIgnoreCase(POST_METHOD);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            // the request method is POST
            if (isLoginSubmission(request, response)) {
                if (LOGGER.isTraceEnabled()) {
                    LOGGER.trace("Login submission detected.  Attempting to execute login.");
                }
                AuthenticationToken token = createToken(request, response);
                request.setAttribute(getTokenKeyAttribute(), token);
                // to the authorize controller handles
                return true;
            } else {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Login page view.");
                }
                //allow them to see the login page ;)
                return true;
            }
        } else {
            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace("Attempting to access a path which requires authentication.  Forwarding to the " +
                        "Authentication url [{}]", getLoginUrl());
            }

            saveRequestAndRedirectToLogin(request, response);
            return false;
        }
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject,
                                     ServletRequest request, ServletResponse response) throws Exception {
        // 登录成功后，由 controller 具体处理
        request.setAttribute(getTokenKeyAttribute(), token);
        request.setAttribute(getSubjectKeyAttribute(), subject);
        return true;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e,
                                     ServletRequest request, ServletResponse response) {
        // 登录失败后，由 controller 具体处理
        request.setAttribute(getFailureKeyAttribute(), e);
        request.setAttribute(getTokenKeyAttribute(), token);
        return true;
    }
}
