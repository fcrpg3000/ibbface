/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) DisabledAccountException.java 2013-09-05 18:11
 */

package com.ibbface.domain.exception;

/**
 * Thrown when attempting to authenticate and the corresponding account has been disabled for
 * some reason.
 *
 * @author Fuchun
 * @since 1.0
 */
public class DisabledAccountException extends AccountException {

    public DisabledAccountException() {
    }

    public DisabledAccountException(String s) {
        super(s);
    }

    public DisabledAccountException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public DisabledAccountException(Throwable throwable) {
        super(throwable);
    }
}
