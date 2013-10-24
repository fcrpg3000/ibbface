package com.ibbface.interfaces.oauth;

import com.ibbface.domain.model.user.User;
import com.ibbface.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.ibbface.interfaces.resp.ErrorCode.AUTH_FAILED;
import static com.ibbface.interfaces.resp.ErrorCode.USERNAME_OR_PASSWORD_ERROR;

/**
 * @author Fuchun
 * @since 1.0
 */
public class OAuthRealmService extends AuthorizingRealm {

    private static final Logger LOGGER = LoggerFactory.getLogger(OAuthRealmService.class);

    private UserService userService;

    @Override
    protected Object getAuthorizationCacheKey(PrincipalCollection principals) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("AuthorizationCacheKey principals(PrincipalCollection): " +
                    "{}, concrete class: {}", principals, principals.getClass());
        }
        User user = (User) getAvailablePrincipal(principals);
        if (user != null) {
            return String.format("authz_info:%s", user.getUserId());
        }
        return principals;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user = (User) getAvailablePrincipal(principals);
        if (user == null) {
            throw new AuthorizationException("The account not found in application.");
        }
        authorizationInfo.setRoles(user.getRoleNames());
        return authorizationInfo;
    }

    @Override
    protected Object getAuthenticationCacheKey(AuthenticationToken token) {
        if (token == null) {
            return null;
        }
        User user = userService.getUser((String) token.getPrincipal());
        return user == null ? null : String.format("authc_info:%s", user.getUserId());
    }

    @Override
    protected Object getAuthenticationCacheKey(PrincipalCollection principals) {
        User user = (User) super.getAuthenticationCacheKey(principals);
        if (user != null) {
            return String.format("authc_info:%s", user.getUserId());
        }
        return principals;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        if (token instanceof OAuthUserAccessToken) {
            return doGetAuthenticationInfo((OAuthUserAccessToken) token);
        } else {
            return doGetAuthenticationInfo((OAuthAccountPasswordToken) token);
        }
    }

    protected AuthenticationInfo doGetAuthenticationInfo(OAuthUserAccessToken token)
            throws AuthenticationException {
        User user = userService.getUser(token.getUserId());
        if (user == null) {
            return null;
        }
        return new SimpleAuthenticationInfo(user, user.getHashPassword(), getName());
    }

    protected AuthenticationInfo doGetAuthenticationInfo(OAuthAccountPasswordToken token)
            throws AuthenticationException {
        User user = userService.getEnabledUser(token.getAccount());
        if (user == null) {
            return null;
        }
        return new SimpleAuthenticationInfo(user, user.getHashPassword(), getName());
    }

    @Override
    protected void assertCredentialsMatch(AuthenticationToken token, AuthenticationInfo info)
            throws AuthenticationException {
        CredentialsMatcher cm = getCredentialsMatcher();
        if (cm != null) {
            if (!cm.doCredentialsMatch(token, info)) {
                //not successful - throw an exception to indicate this:
                LOGGER.debug("Submitted credentials for token [{}] did not match the expected credentials.", token);

                throw new IncorrectCredentialsException(USERNAME_OR_PASSWORD_ERROR);
            }
        } else {
            LOGGER.debug("A CredentialsMatcher must be configured in order to verify " +
                    "credentials during authentication.  If you do not wish for credentials to be examined, you " +
                    "can configure an {} instance.",
                    AllowAllCredentialsMatcher.class.getName());
            throw new AuthenticationException(AUTH_FAILED);
        }
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
