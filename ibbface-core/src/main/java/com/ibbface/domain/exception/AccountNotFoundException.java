/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) AccountNotFoundException.java 2013-08-15 01:32
 */

package com.ibbface.domain.exception;

/**
 * Define user not found in application exception.
 *
 * @author Fuchun
 * @since 1.0
 */
public class AccountNotFoundException extends AccountException {

    private final Long userId;
    private final String mailOrMobile;

    public AccountNotFoundException(Long userId) {
        this(userId, null);
    }

    public AccountNotFoundException(String mailOrMobile) {
        this(mailOrMobile, null);
    }

    public AccountNotFoundException(Long userId, String message) {
        this(userId, null, message);
    }

    public AccountNotFoundException(String mailOrMobile, String message) {
        this(null, mailOrMobile, message);
    }

    public AccountNotFoundException(Long userId, String mailOrMobile, String s) {
        super(s);
        this.userId = userId;
        this.mailOrMobile = mailOrMobile;
    }

    public Long getUserId() {
        return userId;
    }

    public String getMailOrMobile() {
        return mailOrMobile;
    }
}
