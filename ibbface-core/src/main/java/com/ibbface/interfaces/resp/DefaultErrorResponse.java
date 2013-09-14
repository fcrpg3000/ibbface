/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) DefaultErrorResponse.java 2013-08-29 16:03
 */

package com.ibbface.interfaces.resp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.base.Objects;

/**
 * @author Fuchun
 * @since 1.0
 */
public class DefaultErrorResponse implements ErrorResponse {
    private static final long serialVersionUID = 1L;

    public static DefaultErrorResponse from(ErrorCode code, String errorUri, Object... descParams) {
        return new DefaultErrorResponse(code, errorUri, descParams);
    }

    public static DefaultErrorResponse from(ErrorCode code, String errorUri,
                                            String errorDescription, Object... descParams) {
        return new DefaultErrorResponse(code, errorUri, errorDescription, descParams);
    }

    final ErrorCode errorCode;
    final String errorUri;
    String errorDescription;

    DefaultErrorResponse(ErrorCode errorCode, String errorUri, Object... descParams) {
        this(errorCode, errorUri, null, descParams);
    }

    DefaultErrorResponse(ErrorCode errorCode, String errorUri,
                         String errorDescription, Object... descParams) {
        this.errorCode = errorCode;
        this.errorUri = errorUri;
        this.errorDescription = errorDescription == null ?
                (errorCode.getDescription().contains("%s") ?
                        String.format(errorCode.getDescription(), descParams) :
                        errorCode.getDescription()) : errorDescription;
    }

    /**
     * Returns this error response code.
     */
    @Override
    @JSONField(name = ErrorCode.ERROR_CODE_KEY)
    public String getErrorCode() {
        return errorCode.getCode();
    }

    /**
     * Returns this error response error message.
     */
    @Override
    @JSONField(name = ErrorCode.ERROR_KEY)
    public String getError() {
        return errorCode.getError();
    }

    @JSONField(name = ERROR_URI_KEY)
    public String getErrorUri() {
        return errorUri;
    }

    /**
     * Returns this error response description.
     */
    @Override
    @JSONField(name = ErrorCode.ERROR_DESCRIPTION_KEY)
    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DefaultErrorResponse)) return false;

        DefaultErrorResponse that = (DefaultErrorResponse) o;

        return errorCode.equals(that.errorCode) && errorUri.equals(that.errorUri);

    }

    @Override
    public int hashCode() {
        int result = errorCode.hashCode();
        result = 31 * result + errorUri.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(getClass())
                .add("errorCode", errorCode)
                .add("errorUri", getErrorUri())
                .toString();
    }

    @Override
    public String toJSONString() {
        return JSON.toJSONString(this);
    }
}
