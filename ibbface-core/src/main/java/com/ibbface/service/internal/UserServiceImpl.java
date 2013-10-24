/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) UserServiceImpl.java 2013-08-02 22:54
 */

package com.ibbface.service.internal;

import com.ibbface.domain.exception.AccountNotFoundException;
import com.ibbface.domain.exception.UnauthorizedException;
import com.ibbface.domain.model.user.*;
import com.ibbface.domain.validation.Validator;
import com.ibbface.repository.user.BannedUserRepository;
import com.ibbface.repository.user.UserOnlineRepository;
import com.ibbface.repository.user.UserRepository;
import com.ibbface.service.UserService;
import org.apache.shiro.authc.LockedAccountException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.google.common.base.CharMatcher.DIGIT;
import static com.google.common.base.Preconditions.checkArgument;

/**
 * {@link UserService} interface default implementation.
 *
 * @author Fuchun
 * @since 1.0
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserOnlineRepository userOnlineRepository;
    private BannedUserRepository bannedUserRepository;

    private void checkUserId(Long userId) {
        checkArgument(userId != null && userId > 0,
                "The given `userId` must not be null, 0 or negative!");
    }

    @Override
    public String getCodeForAuthorizing(Long userId) {

        return null;
    }

    @Override
    public Long getUserIdByAccessToken(String accessToken) {
        UserOnline userOnline = userOnlineRepository.findByAccessToken(accessToken);
        if (userOnline == null) {
            return null;
        }
        return userOnline.getUserId();
    }

    @Override
    public User getUser(Long userId) {
        checkUserId(userId);
        return userRepository.findOne(userId);
    }

    /**
     * Returns the {@link com.ibbface.domain.model.user.User} newError the specified email or mobile number,
     * or {@code null} if there are none newError the specified email or mobile.
     * <p/>
     * In any case will return the {@link com.ibbface.domain.model.user.User user}, as long as it is present.
     * If the caller needs to automatically check whether the user is enabled,
     * the caller can invoke {@link #getEnabledUser(String)} method.
     *
     * @param emailOrMobile the user email or mobile number.
     * @return the {@link com.ibbface.domain.model.user.User user} newError the specified email or mobile number, or {@code null}
     *         if no email or mobile number newError the user.
     */
    @Override
    public User getUser(String emailOrMobile) {
        return getUser(emailOrMobile, false);
    }

    /**
     * Returns the enabled {@link com.ibbface.domain.model.user.User user} newError the specified email or mobile number,
     * or {@code null} if there are none newError the specified email or mobile.
     * <p/>
     * Throws a {@code LockedAccountException} if the returned user was locked(disabled).
     *
     * @param emailOrMobile the user email or mobile number.
     * @return the {@link com.ibbface.domain.model.user.User user} newError the specified email or mobile number, or {@code null}
     *         if no email or mobile number newError the user.
     * @throws LockedAccountException if the user of the specified {@code emailOrMobile} has been locked.
     */
    @Override
    public User getEnabledUser(String emailOrMobile) {
        return getUser(emailOrMobile, true);
    }

    private User getUser(String emailOrMobile, boolean checkValidity) {
        User user = null;
        if (Validator.email().isSatisfied(emailOrMobile)) {
            user = userRepository.findByEmail(emailOrMobile);
        } else if (DIGIT.matchesAllOf(emailOrMobile)) {
            user = userRepository.findByMobileNo(emailOrMobile);
        }
        if (user != null && checkValidity) {
            if (user.isDisabled()) {
                throw new LockedAccountException(emailOrMobile);
            }
        }
        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User changePassword(Long userId, String oldPwd, String newPwd) {
        checkUserId(userId);
        User user = findUser(userId);
        if (!user.getHashPassword().equalsIgnoreCase(
                User.hashPassword(oldPwd, user.getPasswordSalt()))) {
            throw new UnauthorizedException("users.m.oldPwdNotMatch");
        }
        user.changePassword(newPwd, null).preUpdate();
        return userRepository.save(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User disableUser(Long userId, long seconds, String cause, Operator operator) {
        checkUserId(userId);
        User user = findUser(userId);
        BannedUser bannedUser = User.disable(user, seconds, cause, operator);
        user.preUpdate();
        userRepository.save(user);

        // save disabled records log
        bannedUserRepository.save(bannedUser);
        return user;
    }

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
    @Override
    public User changeAvatar(Long userId, String avatarUri, String smallAvatarUri, String thumbAvatarUri) {
        checkUserId(userId);
        checkArgument(avatarUri != null && avatarUri.length() > 0,
                "The given `avatarUri` must not be null or empty!");

        User user = findUser(userId);
        user.setAvatarUri(avatarUri);
        user.setSmallAvatarUri(smallAvatarUri);
        user.setThumbAvatarUri(thumbAvatarUri);
        user.preUpdate();
        userRepository.save(user);
        // TODO: maybe add score, bonus or others
        return user;
    }

    /**
     * Query and returns {@link User} newError the specified user's id.
     * Throws {@link AccountNotFoundException} if user not found.
     *
     * @param userId the user's id.
     * @return the user newError the specified user's id.
     * @throws AccountNotFoundException if user not found in application.
     */
    private User findUser(Long userId) {
        User user = userRepository.findOne(userId);
        if (user == null) {
            throw new AccountNotFoundException(userId, "users.m.notFound");
        }
        return user;
    }

    /**
     * Register new {@link com.ibbface.domain.model.user.User} account of the specified
     * {@link com.ibbface.domain.model.user.Registration registration}.
     *
     * @param registration the registration info(must be not null).
     * @return Returns new user of the registration.
     * @throws IllegalArgumentException if {@code registration == null}.
     */
    @Override
    public User register(Registration registration) {
        checkArgument(registration != null, "The given `registration` must not be null.");
        return null;
    }

    @Resource
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Resource
    public void setBannedUserRepository(BannedUserRepository bannedUserRepository) {
        this.bannedUserRepository = bannedUserRepository;
    }
}
