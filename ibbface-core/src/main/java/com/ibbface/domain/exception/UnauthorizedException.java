/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) UnauthorizedException.java 2013-08-02 12:16
 */

package com.ibbface.domain.exception;

/**
 * user unauthorized fail the exception.
 *
 * @author Fuchun
 * @since 1.0
 */
public class UnauthorizedException extends RuntimeException {

    private final String message;

    public UnauthorizedException(String s) {
        this.message = s;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
