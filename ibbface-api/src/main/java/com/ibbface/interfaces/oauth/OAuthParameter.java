package com.ibbface.interfaces.oauth;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.ibbface.util.turple.Pair;
import com.ibbface.web.ServletUtils;
import org.apache.commons.codec.binary.Base64;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * @author Fuchun
 * @since 1.0
 */
public class OAuthParameter implements Serializable {
    private static final long serialVersionUID = 1L;

    protected static final String PARAM_CLIENT_ID = "client_id";
    protected static final String PARAM_CLIENT_SECRET = "client_secret";
    protected static final String PARAM_RESPONSE_TYPE = "response_type";
    protected static final String PARAM_REDIRECT_URI = "redirect_uri";
    protected static final String PARAM_GRANT_TYPE = "grant_type";
    protected static final String PARAM_STATE = "state";
    protected static final String PARAM_CODE = "code";

    public static OAuthParameter fromRequest(final HttpServletRequest request) {
        String clientId = ServletUtils.getParameter(request, PARAM_CLIENT_ID);
        String clientSecret = ServletUtils.getParameter(request, PARAM_CLIENT_SECRET);
        final String responseType = ServletUtils.getParameter(request, PARAM_RESPONSE_TYPE, PARAM_CODE);
        final String redirectUri = ServletUtils.getParameter(request, PARAM_REDIRECT_URI);
        final String grantType = ServletUtils.getParameter(request, PARAM_GRANT_TYPE);
        final String state = ServletUtils.getParameter(request, PARAM_STATE);
        final String code = ServletUtils.getParameter(request, PARAM_CODE);

        if (clientId.length() == 0 || clientSecret.length() == 0) {
            String basic = request.getHeader("Authorization");
            Pair<String, String> clientInfo = getClientInfo(basic);
            if (clientInfo != null) {
                clientId = clientInfo.getKey();
                clientSecret = clientInfo.getValue();
            }
        }
        return new OAuthParameter(clientId, clientSecret, responseType,
                redirectUri, grantType, state, code);
    }

    protected static Pair<String, String> getClientInfo(final String basic) {
        if (!isNullOrEmpty(basic) && basic.startsWith("Basic ")) {
            String clientInfo = new String(Base64.decodeBase64(basic.substring(6)), Charsets.UTF_8);
            if (!isNullOrEmpty(clientInfo) && clientInfo.contains(":")) {
                List<String> clientList = Splitter.on(":").splitToList(clientInfo);
                return Pair.of(clientList.get(0), clientList.get(1));
            }
        }
        return null;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OAuthParameter)) return false;

        OAuthParameter that = (OAuthParameter) o;

        if (clientId != null ? !clientId.equals(that.clientId) : that.clientId != null) return false;
        if (clientSecret != null ? !clientSecret.equals(that.clientSecret) : that.clientSecret != null) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (grantType != null ? !grantType.equals(that.grantType) : that.grantType != null) return false;
        if (redirectUri != null ? !redirectUri.equals(that.redirectUri) : that.redirectUri != null) return false;
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
