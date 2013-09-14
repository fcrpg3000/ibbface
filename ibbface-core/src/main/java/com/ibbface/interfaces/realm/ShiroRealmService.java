/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) ShiroRealmService.java 2013-09-07 22:36
 */

package com.ibbface.interfaces.realm;

import com.ibbface.domain.model.user.User;
import com.ibbface.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.ibbface.interfaces.resp.ErrorCode.AUTH_FAILED;
import static com.ibbface.interfaces.resp.ErrorCode.USERNAME_OR_PASSWORD_ERROR;
import static com.ibbface.interfaces.resp.ErrorCodes.INVALID_REQUEST;

/**
 * @author Fuchun
 * @since 1.0
 */
public class ShiroRealmService extends AuthorizingRealm {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShiroRealmService.class);

    private final UserService userService;

    public ShiroRealmService(UserService userService) {
        this.userService = userService;
    }

    public ShiroRealmService(CacheManager cacheManager, UserService userService) {
        super(cacheManager);
        this.userService = userService;
    }

    /**
     * Retrieves the AuthorizationInfo for the given principals from the underlying data store.  When returning
     * an instance from this method, you might want to consider using an instance of
     * {@link org.apache.shiro.authz.SimpleAuthorizationInfo SimpleAuthorizationInfo}, as it is suitable in most cases.
     *
     * @param principals the primary identifying principals of the AuthorizationInfo that should be retrieved.
     * @return the AuthorizationInfo associated with this principals.
     * @see org.apache.shiro.authz.SimpleAuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    /**
     * Retrieves authentication data from an implementation-specific datasource (RDBMS, LDAP, etc) for the given
     * authentication token.
     * <p/>
     * For most datasources, this means just 'pulling' authentication data for an associated subject/user and nothing
     * more and letting Shiro do the rest.  But in some systems, this method could actually perform EIS specific
     * log-in logic in addition to just retrieving data - it is up to the Realm implementation.
     * <p/>
     * A {@code null} return value means that no account could be associated with the specified token.
     *
     * @param authToken the authentication token containing the user's principal and credentials.
     * @return an {@link org.apache.shiro.authc.AuthenticationInfo} object containing account data resulting from the
     *         authentication ONLY if the lookup is successful (i.e. account exists and is valid, etc.)
     * @throws org.apache.shiro.authc.AuthenticationException
     *          if there is an error acquiring data or performing
     *          realm-specific authentication logic for the specified <tt>token</tt>
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken)
            throws AuthenticationException {
        LoginAccount loginAccount = authToken2LoginAccount(authToken);
        if (loginAccount.getClientId() == null || loginAccount.getClientSecret() == null) {
            throw new AuthenticationException(INVALID_REQUEST.getError());
        }
        String password = new String(loginAccount.getPassword());

        User user = userService.getEnabledUser(loginAccount.getUsername());
        if (user == null) {
            return null;
        }
        String hashPassword = User.hashPassword(password, user.getPasswordSalt());
        ((LoginAccount) authToken).setPassword(hashPassword.toCharArray());
//        if (!user.match(password)) {
//            throw new AuthenticationException("authc.m.passwordError");
//        }
        
        return new SimpleAuthenticationInfo(user, user.getHashPassword(), getName());
    }

    /**
     * Asserts that the submitted {@code AuthenticationToken}'s credentials match the stored account
     * {@code AuthenticationInfo}'s credentials, and if not, throws an {@link org.apache.shiro.authc.AuthenticationException}.
     *
     * @param token the submitted authentication token
     * @param info  the AuthenticationInfo corresponding to the given {@code token}
     * @throws org.apache.shiro.authc.AuthenticationException
     *          if the token's credentials do not match the stored account credentials.
     */
    @Override
    protected void assertCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) throws AuthenticationException {
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

    private LoginAccount authToken2LoginAccount(final AuthenticationToken authToken) {
        if (authToken instanceof LoginAccount) {
            return (LoginAccount) authToken;
        }
        UsernamePasswordToken token = (UsernamePasswordToken) authToken;
        return new LoginAccount(token, null);
    }
}
