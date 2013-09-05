/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) DefaultErrorCode.java 2013-08-29 15:49
 */

package com.ibbface.interfaces.resp;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * The {@link ErrorCode} interface default implementation.
 *
 * @author Fuchun
 * @since 1.0
 */
public final class DefaultErrorCode implements ErrorCode {
    private static final long serialVersionUID = 1L;

    @JSONCreator
    public static DefaultErrorCode newError(
            @JSONField(name = ERROR_CODE_KEY) String code,
            @JSONField(name = ERROR_KEY) String error,
            @JSONField(name = ERROR_DESCRIPTION_KEY) String description) {
        return new DefaultErrorCode(code, error, description);
    }

    private final String code;
    private final String error;
    private final String description;

    DefaultErrorCode(String code, String error, String description) {
        this.code = code;
        this.error = error;
        this.description = description;
    }

    /**
     * Returns this error code.
     */
    @Override
    @JSONField(name = ERROR_CODE_KEY)
    public String getCode() {
        return code;
    }

    /**
     * Returns this error message.
     */
    @Override
    @JSONField(name = ERROR_KEY)
    public String getError() {
        return error;
    }

    /**
     * Returns this error description.
     */
    @Override
    @JSONField(serialize = false)
    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DefaultErrorCode)) return false;

        DefaultErrorCode that = (DefaultErrorCode) o;

        if (!code.equals(that.code)) return false;
        if (!description.equals(that.description)) return false;
        if (!error.equals(that.error)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = code.hashCode();
        result = 31 * result + error.hashCode();
        result = 31 * result + description.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return String.format("DefaultErrorCode{code='%s', error='%s', description='%s'}",
                getCode(), getError(), getDescription());
    }
}
