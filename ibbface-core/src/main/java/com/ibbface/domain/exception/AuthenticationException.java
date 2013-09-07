/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) AuthenticationException.java 2013-09-05 18:08
 */

package com.ibbface.domain.exception;

/**
 * General exception thrown due to an error during the Authentication process.
 *
 * @author Fuchun
 * @since 1.0
 */
public class AuthenticationException extends IBBFaceException {

    public AuthenticationException() {
    }

    public AuthenticationException(String s) {
        super(s);
    }

    public AuthenticationException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public AuthenticationException(Throwable throwable) {
        super(throwable);
    }
}
