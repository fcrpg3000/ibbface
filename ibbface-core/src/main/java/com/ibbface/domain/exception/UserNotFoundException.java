/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) UserNotFoundException.java 2013-08-02 22:56
 */

package com.ibbface.domain.exception;

/**
 * Define user not found in application exception.
 *
 * @author Fuchun
 * @since 1.0
 * @deprecated used to {@link AccountNotFoundException}.
 */
@Deprecated
public class UserNotFoundException extends IBBFaceException {

    private final Long userId;
    private final String message;

    public UserNotFoundException(Long userId) {
        this(userId, null);
    }

    public UserNotFoundException(Long userId, String message) {
        this.userId = userId;
        this.message = message;
    }

    public Long getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }
}
