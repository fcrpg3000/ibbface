package com.ibbface.interfaces.oauth;

import com.ibbface.domain.validation.Validation;
import com.ibbface.interfaces.realm.AccountPasswordToken;
import com.ibbface.interfaces.resp.ErrorResponse;

import static com.ibbface.interfaces.resp.ErrorCodes.INVALID_REQUEST;
import static com.ibbface.interfaces.resp.ErrorResponses.byCode;

/**
 * @author Fuchun
 * @since 1.0
 */
public class OAuthAccountPasswordToken extends AccountPasswordToken {

    private final OAuthParameter oAuthParameter;

    public OAuthAccountPasswordToken(String account, String password, OAuthParameter oAuthParameter) {
        super(account, password);
        this.oAuthParameter = oAuthParameter;
    }

    public OAuthAccountPasswordToken(String account, String password, boolean rememberMe, OAuthParameter oAuthParameter) {
        super(account, password, rememberMe);
        this.oAuthParameter = oAuthParameter;
    }

    public OAuthAccountPasswordToken(String account, String password, String host, OAuthParameter oAuthParameter) {
        super(account, password, host);
        this.oAuthParameter = oAuthParameter;
    }

    public OAuthAccountPasswordToken(String account, String password, boolean rememberMe, String host, OAuthParameter oAuthParameter) {
        super(account, password, rememberMe, host);
        this.oAuthParameter = oAuthParameter;
    }

    public OAuthParameter getoAuthParameter() {
        return oAuthParameter;
    }

    public ErrorResponse validateDoAuthorize(final Validation v, final String requestURI) {
        if (v == null) {
            throw new IllegalArgumentException("The given Validation(v) must not be null.");
        }
        ErrorResponse errorResponse;
        if (oAuthParameter == null) {
            errorResponse = byCode(INVALID_REQUEST, requestURI);
        } else {
            errorResponse = oAuthParameter.validateAuthorizeRequest(v, requestURI);
        }
        if (errorResponse != null) {
            return errorResponse;
        }
        v.required(getAccount()).key("account").message("v.required.account");
        v.required(getPassword()).key("password").message("v.required.password");
        return null;
    }

    @Override
    public String toString() {
        return toStringHelper().add("oAuthParameter", oAuthParameter).toString();
    }
}
