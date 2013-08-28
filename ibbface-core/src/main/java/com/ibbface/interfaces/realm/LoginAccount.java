/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) LoginAccount.java 2013-08-14 23:52
 */

package com.ibbface.interfaces.realm;

import com.google.common.base.Objects;
import com.ibbface.domain.shared.AbstractValueObject;

/**
 * A simple account/password authentication token to support the most widely-used
 * authentication mechanism.
 *
 * @author Fuchun
 * @since 1.0
 */
public class LoginAccount extends AbstractValueObject<LoginAccount> {
    private static final long serialVersionUID = 1L;

    public static final String PROP_ACCOUNT = "account";
    public static final String PROP_PASSWORD = "password";
    public static final String PROP_REMEMBER_ME = "rememberMe";
    public static final String PROP_HOST = "host";
    public static final String PROP_CAPTCHA = "captcha";

    private String account;
    private String password;
    private boolean rememberMe;
    private String host;
    private String captcha;

    /**
     * JavaBeans compatible no-arg constructor.
     */
    public LoginAccount() {
        super();
    }

    /**
     * Constructs a new {@code LoginAccount} encapsulating the account and password submitted
     * during an authentication attempt, with a <tt>null</tt> {@link #getHost() host} and
     * a <tt>rememberMe</tt> default of <tt>false</tt>
     * <p/>
     * This is a convience constructor and maintains the password internally via a character
     * array, i.e. <tt>password.toCharArray();</tt>.  Note that storing a password as a String
     * in your code could have possible security implications as noted in the class JavaDoc.
     *
     * @param account the account submitted for authentication
     * @param password the password string submitted for authentication
     */
    public LoginAccount(String account, String password) {
        this(account, password, null);
    }

    /**
     * Constructs a new {@code LoginAccount} encapsulating the account and password submitted
     * during an authentication attempt, with a <tt>null</tt> {@link #getHost() host} and
     * a <tt>rememberMe</tt> default of <tt>false</tt>
     * <p/>
     * This is a convenience constructor and maintains the password internally via a character
     * array, i.e. <tt>password.toCharArray();</tt>.  Note that storing a password as a String
     * in your code could have possible security implications as noted in the class JavaDoc.
     *
     * @param account the account submitted for authentication
     * @param password the password string submitted for authentication
     * @param captcha  the random captcha string.
     */
    public LoginAccount(String account, String password, String captcha) {
        this(account, password, false, captcha);
    }

    /**
     * Constructs a new {@code LoginAccount} encapsulating the account and password submitted,
     * as well as if the user wishes their identity to be remembered across sessions.
     * <p/>
     * This is a convenience constructor and maintains the password internally via a character
     * array, i.e. <tt>password.toCharArray();</tt>.  Note that storing a password as a String
     * in your code could have possible security implications as noted in the class JavaDoc.
     *
     * @param account   the account submitted for authentication
     * @param password   the password string submitted for authentication
     * @param rememberMe if the user wishes their identity to be remembered across sessions
     * @param captcha    the random captcha string.
     * @since 0.9
     */
    public LoginAccount(String account, String password, boolean rememberMe, String captcha) {
        this(account, password, rememberMe, null, captcha);
    }

    /**
     * Constructs a new {@code LoginAccount} encapsulating the account and password submitted, if the user
     * wishes their identity to be remembered across sessions, and the inetAddress from where the attempt is ocurring.
     * <p/>
     * This is a convenience constructor and maintains the password internally via a character
     * array, i.e. <tt>password.toCharArray();</tt>.  Note that storing a password as a String
     * in your code could have possible security implications as noted in the class JavaDoc.
     *
     * @param account   the account submitted for authentication
     * @param password   the password string submitted for authentication
     * @param rememberMe if the user wishes their identity to be remembered across sessions
     * @param host       the host name or IP string from where the attempt is occuring
     * @param captcha    the random captcha string.
     * @since 1.0
     */
    public LoginAccount(String account, String password, boolean rememberMe, String host, String captcha) {
        this.account = account;
        this.password = password;
        this.rememberMe = rememberMe;
        this.host = host;
        this.captcha = captcha;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    /**
     * Clears out (nulls) the account, password, rememberMe, and inetAddress.  The password bytes are explicitly set to
     * <tt>0x00</tt> before nulling to eliminate the possibility of memory access at a later time.
     */
    public void clear() {
        this.account = null;
        this.password = null;
        this.host = null;
        this.rememberMe = false;
        this.captcha = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoginAccount)) return false;

        LoginAccount that = (LoginAccount) o;

        if (rememberMe != that.rememberMe) return false;
        if (!account.equals(that.account)) return false;
        if (captcha != null ? !captcha.equals(that.captcha) : that.captcha != null) return false;
        if (!host.equals(that.host)) return false;
        if (!password.equals(that.password)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = account.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + (rememberMe ? 1 : 0);
        result = 31 * result + host.hashCode();
        result = 31 * result + (captcha != null ? captcha.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add(PROP_ACCOUNT, getAccount())
                .add(PROP_PASSWORD, getPassword())
                .add(PROP_REMEMBER_ME, isRememberMe())
                .add(PROP_HOST, getHost())
                .add(PROP_CAPTCHA, getCaptcha())
                .toString();
    }
}
