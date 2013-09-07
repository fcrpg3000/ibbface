/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) AccountException.java 2013-09-05 18:10
 */

package com.ibbface.domain.exception;

/**
 * Exception thrown due to a problem with the account
 * under which an authentication attempt is being executed.
 *
 * @author Fuchun
 * @since 1.0
 */
public class AccountException extends AuthenticationException {

    public AccountException() {
    }

    public AccountException(String s) {
        super(s);
    }

    public AccountException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public AccountException(Throwable throwable) {
        super(throwable);
    }
}
