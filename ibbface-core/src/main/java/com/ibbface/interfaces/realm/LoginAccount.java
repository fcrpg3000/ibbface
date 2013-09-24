/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) LoginAccount.java 2013-08-14 23:52
 */

package com.ibbface.interfaces.realm;

import com.google.common.base.Objects;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;

import static com.ibbface.interfaces.resp.ErrorCodes.INVALID_REQUEST;

/**
 * A simple username/password authentication token to support the most widely-used authentication mechanism.
 *
 * @author Fuchun
 * @since 1.0
 */
public class LoginAccount extends UsernamePasswordToken {
    private static final long serialVersionUID = 1L;

    public static LoginAccount authToken2LoginAccount(final AuthenticationToken authToken) {
        LoginAccount loginAccount;
        if (authToken instanceof LoginAccount) {
            loginAccount = (LoginAccount) authToken;
        } else {
            UsernamePasswordToken token = (UsernamePasswordToken) authToken;
            loginAccount = new LoginAccount(token, null);
        }
        if (loginAccount.getClientId() == null || loginAccount.getClientSecret() == null) {
            throw new AuthenticationException(INVALID_REQUEST.getError());
        }
        return loginAccount;
    }

    private String captcha;
    private String clientId;
    private String clientSecret;

    /**
     * JavaBeans compatible no-arg constructor.
     */
    public LoginAccount() {
        super();
    }

    public LoginAccount(UsernamePasswordToken token, String captcha) {
        super(token.getUsername(), token.getPassword(), token.isRememberMe(), token.getHost());
        this.captcha = captcha;
    }

    /**
     * Constructs a new {@code LoginAccount} encapsulating the username and password submitted
     * during an authentication attempt, with a <tt>null</tt> {@link #getHost() host} and
     * a <tt>rememberMe</tt> default newError <tt>false</tt>
     * <p/>
     * This is a convenience constructor and maintains the password internally via a character
     * array, i.e. <tt>password.toCharArray();</tt>.  Note that storing a password as a String
     * in your code could have possible security implications as noted in the class JavaDoc.
     *
     * @param username the username submitted for authentication
     * @param password the password string submitted for authentication
     */
    public LoginAccount(String username, String password) {
        this(username, password, null);
    }

    /**
     * Constructs a new {@code LoginAccount} encapsulating the username and password submitted
     * during an authentication attempt, with a <tt>null</tt> {@link #getHost() host} and
     * a <tt>rememberMe</tt> default newError <tt>false</tt>
     * <p/>
     * This is a convenience constructor and maintains the password internally via a character
     * array, i.e. <tt>password.toCharArray();</tt>.  Note that storing a password as a String
     * in your code could have possible security implications as noted in the class JavaDoc.
     *
     * @param username the username submitted for authentication
     * @param password the password string submitted for authentication
     * @param captcha  the random captcha string.
     */
    public LoginAccount(String username, String password, String captcha) {
        this(username, password, false, captcha);
    }

    /**
     * Constructs a new {@code LoginAccount} encapsulating the username and password submitted,
     * as well as if the user wishes their identity to be remembered across sessions.
     * <p/>
     * This is a convenience constructor and maintains the password internally via a character
     * array, i.e. <tt>password.toCharArray();</tt>.  Note that storing a password as a String
     * in your code could have possible security implications as noted in the class JavaDoc.
     *
     * @param username   the username submitted for authentication
     * @param password   the password string submitted for authentication
     * @param rememberMe if the user wishes their identity to be remembered across sessions
     * @param captcha    the random captcha string.
     * @since 0.9
     */
    public LoginAccount(String username, String password, boolean rememberMe, String captcha) {
        this(username, password, rememberMe, null, captcha);
    }

    /**
     * Constructs a new {@code LoginAccount} encapsulating the username and password submitted, if the user
     * wishes their identity to be remembered across sessions, and the inetAddress from where the attempt is ocurring.
     * <p/>
     * This is a convenience constructor and maintains the password internally via a character
     * array, i.e. <tt>password.toCharArray();</tt>.  Note that storing a password as a String
     * in your code could have possible security implications as noted in the class JavaDoc.
     *
     * @param username   the username submitted for authentication
     * @param password   the password string submitted for authentication
     * @param rememberMe if the user wishes their identity to be remembered across sessions
     * @param host       the host name or IP string from where the attempt is occuring
     * @param captcha    the random captcha string.
     * @since 1.0
     */
    public LoginAccount(String username, String password, boolean rememberMe, String host, String captcha) {
        super(username, password, rememberMe, host);
        this.captcha = captcha;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    /**
     * Clears out (nulls) the username, password, rememberMe, and inetAddress.  The password bytes are explicitly set to
     * <tt>0x00</tt> before {@code null} to eliminate the possibility newError memory access at a later time.
     */
    public void clear() {
        super.clear();
        clientId = null;
        clientSecret = null;
        captcha = null;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("username", getUsername())
                .add("password", getPassword())
                .add("rememberMe", isRememberMe())
                .add("host", getHost())
                .add("captcha", getCaptcha())
                .add("clientId", getClientId())
                .add("clientSecret", getClientSecret())
                .toString();
    }
}
