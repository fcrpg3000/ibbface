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

    // authorized error
    // ----------------------------------------------------------------------------------
    public static final ErrorCode REDIRECT_URI_MISMATCH = newError("21321", "redirect_uri_mismatch",
            "Redirect URI mismatch");
    /**
     * The request is missing a required parameter, includes an unsupported parameter value,
     * repeats a parameter, includes multiple credentials, utilizes more than one mechanism
     * for authenticating the client, or is otherwise malformed.
     */
    public static final ErrorCode INVALID_REQUEST = newError("21322", "invalid_request", "Invalid request");

    /**
     * Client authentication failed (e.g. unknown client, no client authentication included,
     * or unsupported authentication method). The authorization server MAY return an HTTP
     * {@code 401} (Unauthorized) status code to indicate which HTTP authentication schemes
     * are supported. If the client attempted to authenticate via the "Authorization" request
     * header field, the authorization server MUST respond with an HTTP {@code 401} (
     * Unauthorized) status code, and include the "WWW-Authenticate" response header field
     * matching the authentication scheme used by the client.
     */
    public static final ErrorCode INVALID_CLIENT = newError("21323", "invalid_client", "Invalid Client");

    /**
     * The provided authorization grant (e.g. authorization code, resource owner credentials,
     * client credentials) is invalid, expired, revoked, does not match the redirection
     * URI used in the authorization request, or was issued to another client.
     */
    public static final ErrorCode INVALID_GRANT = newError("21324", "invalid_grant", "Invalid Grant");

    /**
     * The authenticated client is not authorized to use this authorization grant type.
     */
    public static final ErrorCode UNAUTHORIZED_CLIENT = newError("21325", "unauthorized_client",
            "Unauthorized Client");

    /**
     * The access token is expired.
     */
    public static final ErrorCode EXPIRED_TOKEN = newError("21326", "expired_token",
            "The access token is expired.");

    /**
     * The authorization grant type is not supported by the authorization server.
     */
    public static final ErrorCode UNSUPPORTED_GRANT_TYPE = newError("21327",
            "unsupported_grant_type", "Unsupported Grant type");

    /**
     * The authorization server does not supported obtaining an authorization code
     * using the method.
     */
    public static final ErrorCode UNSUPPORTED_RESPONSE_TYPE = newError("21328",
            "unsupported_response_type", "Unsupported Response type");

    /**
     * The resource owner or authorization server denied the request.
     */
    public static final ErrorCode ACCESS_DENIED = newError("21329", "access_denied",
            "The resource owner or authorization server denied the request.");

    /**
     * The authorization server is currently unable to handle the request due to
     * a temporary overloading or maintenance of the server.
     */
    public static final ErrorCode TEMPORARILY_UNAVAILABLE = newError("21330",
            "temporarily_unavailable", "Temporarily Unavailable");

    // systems error
    // ----------------------------------------------------------------------------------
    public static final ErrorCode SYSTEM_ERROR = newError("10001", "system_error", "System error");
    public static final ErrorCode SERVICE_UNAVAILABLE = newError("10002", "service_unavailable",
            "Service unavailable");
    public static final ErrorCode REMOTE_SERVICE_ERROR = newError("10003", "remote_service_error",
            "Remote service error");
    public static final ErrorCode IP_LIMIT = newError("10004", "ip_limit", "IP limit");
    public static final ErrorCode PERMISSION_DENIED = newError("10005", "permission_denied",
            "Permission denied. need a high level client_id");
    public static final ErrorCode MISS_CLIENT_ID = newError("10006", "miss_client_id",
            "The client id is missing");
    public static final ErrorCode UNSUPPORTED_MEDIA_TYPE = newError("10007", "unsupported_media_type",
            "Unsupported media type (%s)");
    public static final ErrorCode PARAM_ERROR = newError("10008", "param_error",
            "Param error, see doc for more info.");
    public static final ErrorCode SYSTEM_BUSY = newError("10009", "system_busy",
            "Too many pending tasks, system is busy.");
    public static final ErrorCode JOB_EXPIRED = newError("10010", "job_expired", "Job expired");
    public static final ErrorCode RPC_ERROR = newError("10011", "rpc_error", "RPC error");
    public static final ErrorCode ILLEGAL_REQUEST = newError("10012", "illegal_request", "Illegal request");
    public static final ErrorCode INVALID_USER = newError("10013", "invalid_user", "Invalid user");
    public static final ErrorCode INVALID_PERMISSION = newError("10014", "invalid_permission", "Invalid Permission");
    public static final ErrorCode REQUIRED_PARAMS = newError("10016", "required_params", "Miss reqired (100%)," +
            "parameters(%s), see doc for more info.");
    public static final ErrorCode ILLEGAL_PARAM = newError("10017", "illegal_param", "Parameter(%s) value " +
            "invalid, expect(%s), but get (%s), see doc for more info.");
    public static final ErrorCode REQUEST_BODY_LENGTH = newError("10018", "length_over_limit",
            "Request body length over limit");
    public static final ErrorCode API_NOT_FOUND = newError("10020", "api_not_found", "Request API not found");
    public static final ErrorCode UNSUPPORTED_HTTP_METHOD = newError("10021", "unsupported_http_method",
            "HTTP method is not supported for this request");
    public static final ErrorCode IP_REQUESTS_LIMIT = newError("10022", "ip_requests_limit",
            "IP requests out of rate limit");
    public static final ErrorCode USER_REQUESTS_LIMIT = newError("10023", "user_requests_limit",
            "User requests out of rate limit");
    public static final ErrorCode USER_REQUESTS_LIMIT2 = newError("10024", "user_requests_limit2",
            "User requests for %s out of rate limit");

    public static ErrorCode findByCode(final String code) {
        return DefaultErrorCode.findByCode(code);
    }

    public static ErrorCode findByError(final String error) {
        return DefaultErrorCode.findByError(error);
    }
}
