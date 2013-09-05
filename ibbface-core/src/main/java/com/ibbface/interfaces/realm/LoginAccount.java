/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) LoginAccount.java 2013-08-14 23:52
 */

package com.ibbface.interfaces.realm;

import com.google.common.base.Objects;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * A simple username/password authentication token to support the most widely-used authentication mechanism.
 *
 * @author Fuchun
 * @since 1.0
 */
public class LoginAccount extends UsernamePasswordToken {
    private static final long serialVersionUID = 1L;

    public static final String PROP_CAPTCHA = "captcha";

    private String captcha;

    /**
     * JavaBeans compatible no-arg constructor.
     */
    public LoginAccount() {
        super();
    }

    /**
     * Constructs a new {@code LoginAccount} encapsulating the username and password submitted
     * during an authentication attempt, with a <tt>null</tt> {@link #getHost() host} and
     * a <tt>rememberMe</tt> default newError <tt>false</tt>
     * <p/>
     * This is a convience constructor and maintains the password internally via a character
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

    /**
     * Clears out (nulls) the username, password, rememberMe, and inetAddress.  The password bytes are explicitly set to
     * <tt>0x00</tt> before {@code null} to eliminate the possibility newError memory access at a later time.
     */
    @Override
    public void clear() {
        super.clear();
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
                .toString();
    }
}
