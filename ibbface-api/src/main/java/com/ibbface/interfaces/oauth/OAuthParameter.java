package com.ibbface.interfaces.oauth;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.ibbface.domain.validation.Validation;
import com.ibbface.interfaces.resp.ErrorResponse;
import com.ibbface.util.turple.Pair;
import com.ibbface.web.ServletUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

import static com.google.common.base.CharMatcher.DIGIT;
import static com.google.common.base.Strings.*;
import static com.google.common.net.HttpHeaders.AUTHORIZATION;
import static com.ibbface.interfaces.resp.ErrorCodes.INVALID_REQUEST;
import static com.ibbface.interfaces.resp.ErrorCodes.UNSUPPORTED_GRANT_TYPE;
import static com.ibbface.interfaces.resp.ErrorCodes.UNSUPPORTED_RESPONSE_TYPE;
import static com.ibbface.interfaces.resp.ErrorResponses.byCode;
import static org.apache.commons.codec.binary.Base64.decodeBase64;

/**
 * The OAuth protocol parameter driver model.
 *
 * @author Fuchun
 * @since 1.0
 */
public class OAuthParameter implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String DEFAULT_RESPONSE_TYPE_VALUE = "code";
    protected static final String DEFAULT_GRANT_TYPE = "authorization_code";
    protected static final String PARAM_CLIENT_ID = "client_id";
    protected static final String PARAM_CLIENT_SECRET = "client_secret";
    protected static final String PARAM_RESPONSE_TYPE = "response_type";
    protected static final String PARAM_REDIRECT_URI = "redirect_uri";
    protected static final String PARAM_GRANT_TYPE = "grant_type";
    protected static final String PARAM_STATE = "state";
    protected static final String PARAM_CODE = "code";

    public static OAuthParameter fromRequest(final HttpServletRequest request) {
        final Pair<String, String> clientAuth = getClientAuth(request);
        final String clientId = clientAuth == null ? null : clientAuth.getLeft();
        final String clientSecret = clientAuth == null ? null : clientAuth.getRight();
        final String responseType = emptyToNull(ServletUtils.getParameter(request, PARAM_RESPONSE_TYPE));
        final String redirectUri = emptyToNull(ServletUtils.getParameter(request, PARAM_REDIRECT_URI));
        final String grantType = emptyToNull(ServletUtils.getParameter(request, PARAM_GRANT_TYPE));
        final String state = emptyToNull(ServletUtils.getParameter(request, PARAM_STATE));
        final String code = emptyToNull(ServletUtils.getParameter(request, PARAM_CODE));

        return new OAuthParameter(clientId, clientSecret, responseType,
                redirectUri, grantType, state, code);
    }

    protected static Pair<String, String> getClientAuth(final HttpServletRequest request) {
        String basic = nullToEmpty(request.getHeader(AUTHORIZATION)).trim();
        String clientId = null, clientSecret = null;
        if (basic.length() == 0) {
            clientId = emptyToNull(ServletUtils.getParameter(request, PARAM_CLIENT_ID));
            clientSecret = emptyToNull(ServletUtils.getParameter(request, PARAM_CLIENT_SECRET));
        } else {
            if (basic.startsWith("Basic ") && basic.length() > 8) {
                String clientAuth = new String(decodeBase64(basic.substring(6)), Charsets.UTF_8);
                if (!isNullOrEmpty(clientAuth)) {
                    if (clientAuth.contains(":")) {
                        List<String> clientList = Splitter.on(":").splitToList(clientAuth);
                        clientId = clientList.get(0);
                        clientSecret = emptyToNull(clientList.get(1));
                    } else {
                        clientId = clientAuth;
                    }
                }
            }
        }
        return clientId != null ? Pair.of(clientId, clientSecret) : null;
    }

    private final String clientId;
    private final String clientSecret;
    private final String responseType;
    private final String redirectUri;
    private final String grantType;
    private final String state;
    private final String code;

    public OAuthParameter(String clientId, String clientSecret, String responseType,
                          String redirectUri, String grantType, String state, String code) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.responseType = responseType;
        this.redirectUri = redirectUri;
        this.grantType = grantType;
        this.state = state;
        this.code = code;
    }

    public String getClientId() {
        return clientId;
    }

    public Integer getIntClientId() {
        return getClientId() != null && DIGIT.matchesAllOf(getClientId()) ?
                Integer.parseInt(getClientId()) : 0;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getResponseType() {
        return responseType;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public String getGrantType() {
        return grantType;
    }

    public String getState() {
        return state;
    }

    public String getCode() {
        return code;
    }

    public ErrorResponse validateAuthorizeRequest(final Validation v, final String requestURI) {
        if (v == null) {
            throw new IllegalArgumentException("The given Validation (v) must not be null.");
        }
        v.required(getClientId());
        v.required(getRedirectUri());
        v.required(getResponseType());

        if (v.hasError()) {
            v.clear();
            return byCode(INVALID_REQUEST, requestURI);
        }

        v.required(DEFAULT_RESPONSE_TYPE_VALUE.equals(getResponseType()));
        if (v.hasError()) {
            v.clear();
            return byCode(UNSUPPORTED_RESPONSE_TYPE, requestURI);
        }
        return null;
    }

    /**
     * Validates access token API request parameters.
     *
     * @param v a Validation.
     * @param requestURI the current request uri.
     * @return not {@code null} object if validation has errors, otherwise {@code null}.
     * @throws IllegalArgumentException if {@code Validation}(v) is null.
     */
    public ErrorResponse validateAccessTokenRequest(final Validation v, final String requestURI) {
        if (v == null) {
            throw new IllegalArgumentException("The given Validation (v) must not be null.");
        }
        v.required(getClientId());
        v.required(getClientSecret());
        v.required(getGrantType());
        v.required(getCode());
        v.required(getRedirectUri());

        if (v.hasError()) {
            return byCode(INVALID_REQUEST, requestURI);
        }

        // grant_type not match?
        v.required(DEFAULT_GRANT_TYPE.equals(getGrantType()));
        if (v.hasError()) {
            return byCode(UNSUPPORTED_GRANT_TYPE, requestURI);
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OAuthParameter)) return false;

        OAuthParameter that = (OAuthParameter) o;

        if (redirectUri != null ? !redirectUri.equals(that.redirectUri) : that.redirectUri != null) return false;
        if (clientId != null ? !clientId.equals(that.clientId) : that.clientId != null) return false;
        if (clientSecret != null ? !clientSecret.equals(that.clientSecret) : that.clientSecret != null) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (grantType != null ? !grantType.equals(that.grantType) : that.grantType != null) return false;
        if (responseType != null ? !responseType.equals(that.responseType) : that.responseType != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = clientId != null ? clientId.hashCode() : 0;
        result = 31 * result + (clientSecret != null ? clientSecret.hashCode() : 0);
        result = 31 * result + (responseType != null ? responseType.hashCode() : 0);
        result = 31 * result + (redirectUri != null ? redirectUri.hashCode() : 0);
        result = 31 * result + (grantType != null ? grantType.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OAuthParameter{" +
                "clientId='" + getClientId() + '\'' +
                ", clientSecret='" + getClientSecret() + '\'' +
                ", responseType='" + getResponseType() + '\'' +
                ", redirectUri='" + getRedirectUri() + '\'' +
                ", grantType='" + getGrantType() + '\'' +
                ", state='" + getState() + '\'' +
                ", code='" + getCode() + '\'' +
                '}';
    }
}
