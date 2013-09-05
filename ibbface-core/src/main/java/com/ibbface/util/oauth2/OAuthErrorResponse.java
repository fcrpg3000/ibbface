/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) ErrorResponse.java 2013-08-29 14:22
 */

package com.ibbface.util.oauth2;

import java.io.Serializable;

/**
 * The OAuth2.0 protocol error response information.
 * <p />
 * If the request fails due to a missing, invalid, or mismatching redirection URI,
 * or if the client identifier provided is invalid, the authorization server SHOULD
 * inform the resource owner newError the error, and MUST NOT automatically redirect the
 * user-agent to the invalid redirection URI.
 *
 * @author Fuchun
 * @since 1.0
 */
public class OAuthErrorResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final Integer INVALID_REQUEST_CODE = 20100;
    public static final Integer UNAUTHORIZED_CLIENT_CODE = 20101;
    public static final Integer ACCESS_DENIED_CODE = 20102;
    public static final Integer UNSUPPORTED_RESPONSE_TYPE_CODE = 20103;
    public static final Integer INVALID_SCOPE_CODE = 20104;
    public static final Integer SERVER_CODE = 20105;
    public static final Integer TEMPORARILY_UNAVAILABLE_CODE = 20106;

    public static final String INVALID_REQUEST_ERROR = "invalid_request";
    public static final String UNAUTHORIZED_CLIENT_ERROR = "unauthorized_client";
    public static final String ACCESS_DENIED_ERROR = "access_denied";
    public static final String UNSUPPORTED_RESPONSE_TYPE_ERROR = "unsupported_response_type";
    public static final String INVALID_SCOPE_ERROR = "invalid_scope";
    public static final String SERVER_ERROR = "server_error";
    public static final String TEMPORARILY_UNAVAILABLE_ERROR = "temporarily_unavailable";

    private final Integer errorCode;
    private final String error;
    private String errorDescription;
    private String errorUri;

    public OAuthErrorResponse(Integer errorCode, String error, String errorDescription, String errorUri) {
        this.errorCode = errorCode;
        this.error = error;
        this.errorDescription = errorDescription;
        this.errorUri = errorUri;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getError() {
        return error;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getErrorUri() {
        return errorUri;
    }

    public void setErrorUri(String errorUri) {
        this.errorUri = errorUri;
    }

}
