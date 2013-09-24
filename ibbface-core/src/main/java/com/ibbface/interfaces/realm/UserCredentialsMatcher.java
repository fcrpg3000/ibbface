/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) UserCredentialsMatcher.java 2013-09-24 14:59
 */

package com.ibbface.interfaces.realm;

import com.ibbface.domain.model.user.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.springframework.stereotype.Component;

import static com.ibbface.interfaces.resp.ErrorCodes.INVALID_REQUEST;

/**
 * {@code CredentialsMatcher} sha1 hash implementation.
 *
 * @author Fuchun
 * @since 1.0
 */
@Component
public final class UserCredentialsMatcher implements CredentialsMatcher {

    /**
     * Returns {@code true} if the provided token credentials match the stored account credentials,
     * {@code false} otherwise.
     *
     * @param token the {@code AuthenticationToken} submitted during the authentication attempt
     * @param info  the {@code AuthenticationInfo} stored in the system.
     * @return {@code true} if the provided token credentials match the stored account credentials,
     *         {@code false} otherwise.
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        LoginAccount loginAccount = authToken2LoginAccount(token);
        User user = (User) info.getPrincipals().getPrimaryPrincipal();
        assert user != null;
        return user.match(new String(loginAccount.getPassword()));
    }

    private LoginAccount authToken2LoginAccount(final AuthenticationToken authToken) {
        LoginAccount loginAccount;
        if (authToken instanceof LoginAccount) {
            loginAccount = (LoginAccount) authToken;
        } else {
            UsernamePasswordToken token = (UsernamePasswordToken) authToken;
            loginAccount = new LoginAccount(token, null);
        }
        if (loginAccount.getClientId() == null || loginAccount.getClientSecret() == null) {
            throw new AuthenticationException(INVALID_REQUEST.getError());
        }
        return loginAccount;
    }
}
