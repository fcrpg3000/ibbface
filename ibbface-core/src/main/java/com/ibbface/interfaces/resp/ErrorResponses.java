/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) ErrorResponses.java 2013-08-29 18:00
 */

package com.ibbface.interfaces.resp;

/**
 * @author Fuchun
 * @since 1.0
 */
public final class ErrorResponses {
    private ErrorResponses() {
    }

    public static ErrorResponse byCode(ErrorCode code, String errorUri) {
        return DefaultErrorResponse.from(code, errorUri);
    }

    public static ErrorResponse byError(String error, String uri, String description, Object... params) {
        ErrorCode errorCode = ErrorCodes.findByError(error);
        if (errorCode == null) {
            errorCode = DefaultErrorCode.UNKNOWN_ERROR;
        }
        return DefaultErrorResponse.from(errorCode, uri, description, params);
    }
}
