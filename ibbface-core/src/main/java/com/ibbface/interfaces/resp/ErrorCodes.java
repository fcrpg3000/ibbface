/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) ErrorCodes.java 2013-08-29 16:57
 */

package com.ibbface.interfaces.resp;

import static com.ibbface.interfaces.resp.DefaultErrorCode.newError;

/**
 * @author Fuchun
 * @since 1.0
 */
public final class ErrorCodes {
    private ErrorCodes() {
    }

    /**
     * The request is missing a required parameter, includes an unsupported parameter value,
     * repeats a parameter, includes multiple credentials, utilizes more than one mechanism
     * for authenticating the client, or is otherwise malformed.
     */
    public static final ErrorCode INVALID_REQUEST = newError("20001", "invalid_request", "Invalid request");

    /**
     * Client authentication failed (e.g. unknown client, no client authentication included,
     * or unsupported authentication method). The authorization server MAY return an HTTP
     * {@code 401} (Unauthorized) status code to indicate which HTTP authentication schemes
     * are supported. If the client attempted to authenticate via the "Authorization" request
     * header field, the authorization server MUST respond with an HTTP {@code 401} (
     * Unauthorized) status code, and include the "WWW-Authenticate" response header field
     * matching the authentication scheme used by the client.
     */
    public static final ErrorCode INVALID_CLIENT = newError("20002", "invalid_client", "Invalid Client");

    /**
     * The provided authorization grant (e.g. authorization code, resource owner credentials,
     * client credentials) is invalid, expired, revoked, does not match the redirection
     * URI used in the authorization request, or was issued to another client.
     */
    public static final ErrorCode INVALID_GRANT = newError("20003", "invalid_grant", "Invalid Grant");

    /**
     * The authenticated client is not authorized to use this authorization grant type.
     */
    public static final ErrorCode UNAUTHORIZED_CLIENT = newError("20004", "unauthorized_client",
            "The authenticated client is not authorized to use this authorization grant type.");

    /**
     * The access token is expired.
     */
    public static final ErrorCode EXPIRED_TOKEN = newError("20005", "expired_token",
            "The access token is expired.");

    /**
     * The authorization grant type is not supported by the authorization server.
     */
    public static final ErrorCode UNSUPPORTED_GRANT_TYPE = newError("20006", "unsupported_grant_type",
            "The authorization grant type is not supported by the authorization server.");

    /**
     * The authorization server does not supported obtaining an authorization code
     * using the method.
     */
    public static final ErrorCode UNSUPPORTED_RESPONSE_TYPE = newError("20007",
            "unsupported_response_type",
            "The authorization server does not supported obtaining an authorization " +
                    "code using the method.");

    /**
     * The resource owner or authorization server denied the request.
     */
    public static final ErrorCode ACCESS_DENIED = newError("20008", "access_denied",
            "The resource owner or authorization server denied the request.");

    /**
     * The authorization server is currently unable to handle the request due to
     * a temporary overloading or maintenance of the server.
     */
    public static final ErrorCode TEMPORARILY_UNAVAILABLE = newError("20009",
            "temporarily_unavailable", "The authorization server is currently " +
            "unable to handle the request due to a temporary overloading or " +
            "maintenance of the server.");
}
