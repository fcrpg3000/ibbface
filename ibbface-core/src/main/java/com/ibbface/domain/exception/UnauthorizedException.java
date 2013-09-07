/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) UnauthorizedException.java 2013-08-02 12:16
 */

package com.ibbface.domain.exception;

/**
 * Thrown to indicate a requested operation or access to a requested resource is not allowed.
 *
 * @author Fuchun
 * @since 1.0
 */
public class UnauthorizedException extends AuthenticationException {

    public UnauthorizedException() {
        super();
    }

    public UnauthorizedException(String s) {
        super(s);
    }

    public UnauthorizedException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public UnauthorizedException(Throwable throwable) {
        super(throwable);
    }
}
