/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) RealmServiceImple.java 2013-08-15 01:03
 */

package com.ibbface.service.internal;

import com.google.common.collect.Sets;
import com.ibbface.domain.exception.AccountNotFoundException;
import com.ibbface.domain.model.user.User;
import com.ibbface.domain.model.user.UserRole;
import com.ibbface.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author Fuchun
 * @since 1.0
 */
@Service("realmService")
public class RealmServiceImpl extends AuthorizingRealm {

    private UserService userService;

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
        String username = (String) principals.fromRealm(getName()).iterator().next();
        if (username == null)
            return null;
        User user;
        try {
            user = userService.getEnabledUser(username);
        } catch (LockedAccountException ex) {
            return null;
        }
        if (user == null) {
            return null;
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> roleNames = Sets.newHashSet();
        for (UserRole role : user.getRoles()) {
            roleNames.add(role.getName());
        }
        info.addRoles(roleNames);
        return info;
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
        UsernamePasswordToken token = (UsernamePasswordToken) authToken;
        String username = token.getUsername();
        if (username == null || username.length() == 0) {
            return null;
        }

        User user = userService.getEnabledUser(username);
        if (user == null) {
            throw new AccountNotFoundException(username);
        }

        return new SimpleAuthenticationInfo(user.isMobileVerified() ? user.getMobileNo() :
                user.getEmail(), user.getHashPassword(), getName());
    }

    @Resource
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
