/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) AccountNotFoundException.java 2013-08-15 01:32
 */

package com.ibbface.domain.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * Define user not found in application exception.
 *
 * @author Fuchun
 * @since 1.0
 */
public class AccountNotFoundException extends AuthenticationException {

    private final String account;
    private final String message;

    public AccountNotFoundException(String account) {
        this(account, null);
    }

    public AccountNotFoundException(String account, String message) {
        this.account = account;
        this.message = message;
    }

    public String getAccount() {
        return account;
    }

    public String getMessage() {
        return message;
    }
}
