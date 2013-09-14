/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) DefaultErrorCode.java 2013-08-29 15:49
 */

package com.ibbface.interfaces.resp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.base.Charsets;
import com.google.common.io.Closer;
import com.google.common.io.LineReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@link ErrorCode} interface default implementation.
 *
 * @author Fuchun
 * @since 1.0
 */
public final class DefaultErrorCode implements ErrorCode {
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultErrorCode.class);

    private static final Map<String, DefaultErrorCode> data = new HashMap<String, DefaultErrorCode>(100);
    private static final Map<String, String> errorCodeMap = new HashMap<String, String>(100);

    public static final DefaultErrorCode UNKNOWN_ERROR = newError("10000", "unknown_error",
            "System unknown error");

    static {
        Closer closer = Closer.create();
        try {
            InputStream input = DefaultErrorCode.class.getResourceAsStream(
                    "/META-INF/data/service_error.data");
            InputStreamReader reader = closer.register(new InputStreamReader(input, Charsets.UTF_8));
            LineReader lineReader = new LineReader(reader);
            String line;
            while ((line = lineReader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#") || !line.startsWith("{")) {
                    continue;
                }
                JSON.parseObject(line, DefaultErrorCode.class);
            }
            LOGGER.info("Service error data reader completion!");
        } catch (Exception ex) {
            LOGGER.error("Read classpath:/META-INF/data/service_error.data file error: ", ex);
        } finally {
            try {
                closer.close();
            } catch (Exception ex) {
                // ignore
            }
        }
    }

    @JSONCreator
    public static DefaultErrorCode newError(
            @JSONField(name = "code") String code,
            @JSONField(name = "error") String error,
            @JSONField(name = "description") String description) {
        return putIfPresent(code, error, description);
    }

    static DefaultErrorCode putIfPresent(String code, String error, String description) {
        DefaultErrorCode exists = data.get(code);
        if (exists == null) {
            exists = new DefaultErrorCode(code, error, description);
            data.put(code, exists);
            errorCodeMap.put(error, code);
        }
        return exists;
    }

    static DefaultErrorCode findByCode(String code) {
        return data.get(code);
    }

    static DefaultErrorCode findByError(String error) {
        final String code = errorCodeMap.get(error);
        return code == null ? null : data.get(code);
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
