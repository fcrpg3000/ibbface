/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) ErrorResponse.java 2013-08-29 15:58
 */

package com.ibbface.interfaces.resp;

import java.io.Serializable;

/**
 * The API error response information interface.
 *
 * @author Fuchun
 * @since 1.0
 */
public interface ErrorResponse extends Serializable {

    String ERROR_URI_KEY = "error_uri";

    /**
     * Returns this error response code.
     */
    public String getErrorCode();

    /**
     * Returns this error response error message.
     */
    public String getError();

    /**
     * Returns this error response description.
     */
    public String getErrorDescription();

    /**
     * Returns this error response error uri.
     */
    public String getErrorUri();
}
