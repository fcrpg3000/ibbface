/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) ValidationError.java 2013-07-28 18:30
 */

package com.ibbface.domain.validation;

import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * The validation error information. Contains key and message.
 *
 * @author Fuchun
 * @since 1.0
 */
public final class ValidationError implements Serializable {
    private static final long serialVersionUID = 1L;

    public static ValidationError newError(String message) {
        return new ValidationError(null, message);
    }

    private String key;
    private String message;

    public ValidationError() {
    }

    public ValidationError(String key, String message) {
        this.key = key;
        this.message = message;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValidationError)) return false;

        ValidationError that = (ValidationError) o;

        if (key != null ? !key.equals(that.key) : that.key != null) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(getClass())
                .add("key", getKey())
                .add("message", getMessage()).toString();
    }
}
