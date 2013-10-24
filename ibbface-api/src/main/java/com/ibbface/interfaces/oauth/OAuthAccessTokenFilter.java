package com.ibbface.interfaces.oauth;

import com.google.common.base.Charsets;
import com.google.common.net.HttpHeaders;
import com.ibbface.interfaces.resp.ErrorCodes;
import com.ibbface.interfaces.resp.ErrorResponse;
import com.ibbface.interfaces.resp.ErrorResponses;
import com.ibbface.service.UserService;
import com.ibbface.util.turple.Pair;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Locale;

/**
 * OAuth authentication filter.
 *
 * @author Fuchun
 * @since 1.0
 */
public class OAuthAccessTokenFilter extends AuthenticatingFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(OAuthAccessTokenFilter.class);

    private String authzScheme = "Bearer";

    private UserService userService;

    public String getAuthzScheme() {
        return authzScheme;
    }

    public void setAuthzScheme(String authzScheme) {
        this.authzScheme = authzScheme;
    }

    @Override
    protected AuthenticationToken createToken(
            ServletRequest request, ServletResponse response) throws Exception {
        String authzHeader = getAuthzHeader(request);
        if (authzHeader == null || authzHeader.length() == 0) {
            // Create an empty authentication token since there is no
            // Authorization header.
            return createToken("", "", request, response);
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Attempting to execute login with headers [{}]", authzHeader);
        }

        Pair<Long, String> pcPair = getPrincipalsAndCredentials(authzHeader, request);
        if (pcPair == null) {
            // Create an authentication token with null userId,
            // since one hasn't been provided in the request.
            return createToken((Long) null, "", request, response);
        }
        return createToken(pcPair.getLeft(), pcPair.getRight(), request, response);
    }

    protected AuthenticationToken createToken(Long userId, String accessToken,
                                              ServletRequest request, ServletResponse response) {
        final String host = getHost(request);
        return new OAuthUserAccessToken(userId, accessToken, host);
    }

    protected Pair<Long, String> getPrincipalsAndCredentials(String authzHeader, ServletRequest request) {
        if (authzHeader == null) {
            return null;
        }

        String[] authTokens = authzHeader.split(" ");
        if (authTokens == null || authTokens.length < 2) {
            return null;
        }

        return getPrincipalsAndCredentials(authTokens[0], authTokens[1]);
    }

    private Pair<Long, String> getPrincipalsAndCredentials(String scheme, String encodedToken) {
        if (!getAuthzScheme().equals(scheme)) {
            return null;
        }
        String token = Base64.decodeToString(encodedToken.getBytes());
        Long userId = userService.getUserIdByAccessToken(token);
        return Pair.of(userId, token);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        boolean loggedIn = false; //false by default or we wouldn't be in this method
        if (isLoginAttempt(request, response)) {
            loggedIn = executeLogin(request, response);
        }
        if (!loggedIn) {
            sendError(request, response);
        }
        return false;
    }

    protected boolean sendError(ServletRequest request, ServletResponse response) throws Exception {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Authentication required: sending 200 and JSON error information response");
        }
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        String requestURI = httpRequest.getRequestURI();
        ErrorResponse errorResponse = ErrorResponses.byCode(ErrorCodes.MISS_APP_KEY, requestURI);
        httpResponse.setCharacterEncoding(Charsets.UTF_8.displayName());
        PrintWriter out = httpResponse.getWriter();
        out.print(errorResponse.toJSONString());
        out.close();
        return false;
    }

    /**
     * Returns the {@link HttpHeaders#AUTHORIZATION AUTHORIZATION} from the specified ServletRequest.
     * <p/>
     * This implementation merely casts the request to an <code>HttpServletRequest</code> and returns the header:
     * <p/>
     * <code>HttpServletRequest httpRequest = {@link org.apache.shiro.web.util.WebUtils#toHttp(javax.servlet.ServletRequest) toHttp(reaquest)};<br/>
     * return httpRequest.getHeader({@link HttpHeaders#AUTHORIZATION AUTHORIZATION});</code>
     *
     * @param request the incoming {@code ServletRequest}.
     * @return the {@code Authorization} header's value.
     */
    protected String getAuthzHeader(ServletRequest request) {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        return httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
    }

    /**
     * Determines whether the incoming request is an attempt to log in.
     * <p/>
     * The default implementation obtains the value of the request's
     * {@link HttpHeaders#AUTHORIZATION AUTHORIZATION}, and if it is not {@code null}, delegates
     * to {@link #isLoginAttempt(String) isLoginAttempt(authzHeaderValue)}. If the header is {@code null},
     * <code>false</code> is returned.
     *
     * @param request  incoming ServletRequest
     * @param response outgoing ServletResponse
     * @return true if the incoming request is an attempt to log in based, false otherwise
     */
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        String authzHeader = getAuthzHeader(request);
        return authzHeader != null && isLoginAttempt(authzHeader);
    }

    /**
     * Default implementation that returns <code>true</code> if the specified <code>authzHeader</code>
     * starts with the same (case-insensitive) characters specified by the
     * {@link #getAuthzScheme() authzScheme}, <code>false</code> otherwise.
     * <p/>
     * That is:
     * <p/>
     * <code>String authzScheme = getAuthzScheme().toLowerCase();<br/>
     * return authzHeader.toLowerCase().startsWith(authzScheme);</code>
     *
     * @param authzHeader the 'Authorization' header value (guaranteed to be non-null if the
     *                    {@link #isLoginAttempt(javax.servlet.ServletRequest, javax.servlet.ServletResponse)} method is not overriden).
     * @return <code>true</code> if the authzHeader value matches that configured as defined by
     *         the {@link #getAuthzScheme() authzScheme}.
     */
    protected boolean isLoginAttempt(String authzHeader) {
        //SHIRO-415: use English Locale:
        String authzScheme = getAuthzScheme().toLowerCase(Locale.ENGLISH);
        return authzHeader.toLowerCase(Locale.ENGLISH).startsWith(authzScheme);
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
