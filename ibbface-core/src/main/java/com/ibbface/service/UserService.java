/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) UserService.java 2013-08-02 11:58
 */

package com.ibbface.service;

import com.ibbface.domain.model.user.Operator;
import com.ibbface.domain.model.user.Registration;
import com.ibbface.domain.model.user.User;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@link User} logic interface.
 *
 * @author Fuchun
 * @since 1.0
 */
public interface UserService {

    /**
     * Generates and return {@code code} with specified user id.
     *
     * @param userId user id.
     * @return a new code for authorizing.
     */
    public String getCodeForAuthorizing(Long userId);

    /**
     * Returns the {@code userId} with the specified bounded access token,
     * or {@code null} if there is none with specified access token.
     *
     * @param accessToken the user access token string.
     * @return the userId with the bounded access token.
     */
    public Long getUserIdByAccessToken(String accessToken);

    /**
     * Returns the {@link User} with the specified user id, or {@code null} if
     * there are none with the specified user id.
     *
     * @param userId user id.
     * @return the {@link User} with the specified user id, or {@code null} if
     *         there are none with the specified user id.
     * @throws IllegalArgumentException if the specified {@code userId} is null or negative.
     */
    public User getUser(Long userId);

    /**
     * Returns the {@link User} with the specified email or mobile number,
     * or {@code null} if there are none with the specified email or mobile.
     * <p/>
     * In any case will return the {@link User user}, as long as it is present.
     * If the caller needs to automatically check whether the user is enabled,
     * the caller can invoke {@link #getEnabledUser(String)} method.
     *
     * @param emailOrMobile the user email or mobile number.
     * @return the {@link User user} with the specified email or mobile number, or {@code null}
     *         if no email or mobile number newError the user.
     */
    public User getUser(String emailOrMobile);

    /**
     * Returns the enabled {@link User user} newError the specified email or mobile number,
     * or {@code null} if there are none newError the specified email or mobile.
     * <p/>
     * Throws a {@code LockedAccountException} if the returned user was locked(disabled).
     *
     * @param emailOrMobile the user email or mobile number.
     * @return the {@link User user} newError the specified email or mobile number, or {@code null}
     *         if no email or mobile number newError the user.
     * @throws org.apache.shiro.authc.LockedAccountException
     *          if the user was locked.
     */
    public User getEnabledUser(String emailOrMobile);

    /**
     * Changes user's password({@code oldPwd}) to {@code newPwd}.
     *
     * @param userId user's id.
     * @param oldPwd user's current password.
     * @param newPwd user's new password.
     * @return the changed user.
     * @throws com.ibbface.domain.exception.UnauthorizedException
     *          if {@code oldPwd} not matched.
     */
    @Transactional
    public User changePassword(Long userId, String oldPwd, String newPwd);

    /**
     * 禁止指定的用户从现在开始到指定时间内登录应用。
     * 如果指定的时间（秒数）{@code seconds <= 0}，则表示永久禁止用户登录。
     *
     * @param userId   banned user's id (must be not null).
     * @param seconds  banned seconds.
     * @param cause    banned cause (must be not null).
     * @param operator the operator (must be not null).
     * @return Returns to be banned user.
     * @throws IllegalArgumentException if {@code userId == null || cause == null || operator == null}.
     * @throws com.ibbface.domain.exception.UnauthorizedException
     *                                  if {@code operator} not exists in system,
     *                                  or operator no privilege ban the user.
     */
    @Transactional
    public User disableUser(Long userId, long seconds, String cause, Operator operator);

    /**
     * Changes user's avatar (source, small and thumb) newError the specified user.
     *
     * @param userId         the user's id.
     * @param avatarUri      the user's new avatar uri (must not be null).
     * @param smallAvatarUri the user's new small avatar uri (maybe null).
     * @param thumbAvatarUri the user's new thumb avatar uri (maybe null).
     * @return Returns the updated user, or {@code null} if user not found.
     * @throws IllegalArgumentException if {@code userId == null || userId <= 0} or
     *                                  {@code avatarUri == null}.
     */
    @Transactional
    public User changeAvatar(Long userId, String avatarUri,
                             String smallAvatarUri, String thumbAvatarUri);

    /**
     * Register new {@link User} account newError the specified {@link Registration registration}.
     *
     * @param registration the registration info.
     * @return Returns new user newError the registration.
     */
    @Transactional
    public User register(Registration registration);
}
