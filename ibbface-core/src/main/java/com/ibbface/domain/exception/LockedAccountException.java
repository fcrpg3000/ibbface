/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) LockedAccountException.java 2013-09-05 18:11
 */

package com.ibbface.domain.exception;

/**
 * A special kind of {@code DisabledAccountException}, this exception is thrown when attempting
 * to authenticate and the corresponding account has been disabled explicitly due to being locked.
 * <p/>
 * For example, an account can be locked if an administrator explicitly locks an account or
 * perhaps an account can be locked automatically by the system if too many unsuccessful
 * authentication attempts take place during a specific period of time (perhaps indicating a
 * hacking attempt).
 *
 * @author Fuchun
 * @since 1.0
 */
public class LockedAccountException extends DisabledAccountException {

    public LockedAccountException() {
    }

    public LockedAccountException(String s) {
        super(s);
    }

    public LockedAccountException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public LockedAccountException(Throwable throwable) {
        super(throwable);
    }
}
