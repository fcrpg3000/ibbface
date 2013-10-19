package com.ibbface.interfaces.realm;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author Fuchun
 * @since 1.0
 */
public class OAuthAuthenticationFilter extends FormAuthenticationFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String DEFAULT_ACCOUNT_PARAM = "account";
    public static final String DEFAULT_CAPTCHA_PARAM = "captcha";

    private String captchaParam = DEFAULT_CAPTCHA_PARAM;

    public OAuthAuthenticationFilter() {
        setCaptchaParam(DEFAULT_CAPTCHA_PARAM);
    }

    public String getCaptchaParam() {
        return captchaParam;
    }

    public void setCaptchaParam(String captchaParam) {
        this.captchaParam = captchaParam;
    }

    protected String getCaptcha(ServletRequest request) {
        return WebUtils.getCleanParam(request, getCaptchaParam());
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
//        String authorizationHeader = getAuthzHeader(request);
        String account = getUsername(request);
        String password = getPassword(request);
        String host = getHost(request);
        String captcha = getCaptcha(request);
        boolean rememberMe = isRememberMe(request);
        LoginAccount loginAccount = new LoginAccount(account, password, rememberMe, host, captcha);

//        String[] clientInfo = getClientInfo(authorizationHeader, request);
//        if (clientInfo != null) {
//            loginAccount.setClientId(clientInfo[0]);
//            loginAccount.setClientSecret(clientInfo[1]);
//        }
        return loginAccount;
    }
}
