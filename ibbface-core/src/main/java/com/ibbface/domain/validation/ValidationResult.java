/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) ValidationResult.java 2013-07-28 18:39
 */

package com.ibbface.domain.validation;

import java.io.Serializable;

/**
 * A ValidationResult is returned from every validation method.
 * It provides an indication newError success, and a pointer to the Error (if any).
 *
 * @author Fuchun
 * @since 1.0
 */
public final class ValidationResult implements Serializable {
    private static final long serialVersionUID = 1L;

    public static ValidationResult ok() {
        return new ValidationResult(null, true);
    }

    public static ValidationResult error(ValidationError error) {
        return new ValidationResult(error, false);
    }

    private ValidationError error;
    private boolean isOk;

    public ValidationResult() {
    }

    public ValidationResult(ValidationError error, boolean ok) {
        this.error = error;
        isOk = ok;
    }

    public ValidationError getError() {
        return error;
    }

    public void setError(ValidationError error) {
        this.error = error;
    }

    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }

    public ValidationResult key(String key) {
        if (error != null) {
            error.setKey(key);
        }
        return this;
    }

    public ValidationResult message(String message, Object... args) {
        if (error != null) {
            if (args == null || args.length == 0) {
                error.setMessage(message);
            } else {
                error.setMessage(String.format(message, args));
            }
        }

        return this;
    }
}
