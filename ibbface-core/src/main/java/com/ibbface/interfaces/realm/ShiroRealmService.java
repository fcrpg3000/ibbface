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
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
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
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user = (User) getAvailablePrincipal(principals);
        if (user == null) {
            throw new AuthorizationException("The account not found in application.");
        }
        authorizationInfo.setRoles(user.getRoleNames());
        return authorizationInfo;
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
        LoginAccount loginAccount = LoginAccount.authToken2LoginAccount(authToken);
//        String password = new String(loginAccount.getPassword());

        User user = userService.getEnabledUser(loginAccount.getUsername());
        if (user == null) {
            return null;
        }
//        String hashPassword = User.hashPassword(password, user.getPasswordSalt());
//        ((LoginAccount) authToken).setPassword(hashPassword.toCharArray());
//        if (!user.match(password)) {
//            throw new AuthenticationException("authc.m.passwordError");
//        }
        
        return new SimpleAuthenticationInfo(user, user.getHashPassword(), getName());
    }

    /**
     * Returns the key under which {@link org.apache.shiro.authc.AuthenticationInfo} instances are cached if authentication caching is enabled.
     * This implementation defaults to returning the token's
     * {@link org.apache.shiro.authc.AuthenticationToken#getPrincipal() principal}, which is usually a username in
     * most applications.
     * <h3>Cache Invalidation on Logout</h3>
     * <b>NOTE:</b> If you want to be able to invalidate an account's cached {@code AuthenticationInfo} on logout, you
     * must ensure the {@link #getAuthenticationCacheKey(org.apache.shiro.subject.PrincipalCollection)} method returns
     * the same value as this method.
     *
     * @param token the authentication token for which any successful authentication will be cached.
     * @return the cache key to use to cache the associated {@link org.apache.shiro.authc.AuthenticationInfo} after a successful authentication.
     * @since 1.2
     */
    @Override
    protected Object getAuthenticationCacheKey(AuthenticationToken token) {
        return String.format("authc_info:%s", token.getPrincipal());
    }

    @Override
    protected Object getAuthenticationCacheKey(PrincipalCollection principals) {
        User user = (User) super.getAuthenticationCacheKey(principals);
        if (user != null) {
            return String.format("authc_info:%s", user.getEmail());
        }
        return principals;
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
}
