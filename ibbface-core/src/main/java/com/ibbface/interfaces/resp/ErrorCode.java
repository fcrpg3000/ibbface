/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) ErrorCodes.java 2013-08-29 15:43
 */

package com.ibbface.interfaces.resp;

import java.io.Serializable;

/**
 * The API response error code interface.
 *
 * @author Fuchun
 * @since 1.0
 */
public interface ErrorCode extends Serializable {

    String ERROR_CODE_KEY = "error_code";

    String ERROR_KEY = "error";

    String ERROR_DESCRIPTION_KEY = "error_description";

    /**
     * Returns this error code.
     */
    public String getCode();

    /**
     * Returns this error message.
     */
    public String getError();

    /**
     * Returns this error description.
     */
    public String getDescription();
}
