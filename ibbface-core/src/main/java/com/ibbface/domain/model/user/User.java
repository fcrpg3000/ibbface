/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) User.java 2013-07-28 14:50
 */

package com.ibbface.domain.model.user;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.ibbface.domain.model.user.base.BaseUser;
import com.ibbface.domain.shared.QueryValue;
import com.ibbface.util.RandomStrings;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * User (Account) Entity.
 *
 * @author Fuchun
 * @since 1.0
 */
public class User extends BaseUser implements QueryValue {
    private static final long serialVersionUID = 1L;

    public static final String DEFAULT_HASH_PASSWORD_FORMAT = "%s{%s}";

    public static User createUser(String email, String userName, String userHandle,
                                  String password, Gender gender) {
        User user = new User();
        user.setEmail(email);
        user.setUserName(userName);
        user.setUserHandle(userHandle);

        final String salt = RandomStrings.random(16, true, true);
        user.changePassword(password, salt);
        user.setGender(gender);
        user.setRoles(Sets.newHashSet(UserRole.ROLE_USER));

        final Date dateNow = new Date();
        user.setCreatedTime(dateNow);
        user.setLastModifiedTime(dateNow);
        return user;
    }

    /**
     * 禁止指定的用户，从现在开始至指定的时间 {@code seconds} 秒内，登录应用。
     * 如果 {@code seconds <= 0}，表示永久禁用。
     *
     * @param user     被禁用的用户。
     * @param seconds  被禁用的时间（秒）。
     * @param cause    被禁用的原因说明。
     * @param operator 当前操作者。
     * @return 返回新生成的禁用的用户信息。
     * @throws IllegalArgumentException if {@code user == null || cause == null || operator == null}.
     */
    public static BannedUser disable(User user, long seconds,
                                     String cause, Operator operator) {
        checkArgument(user != null, "The given `user` must not be null!");
        checkArgument(cause != null && cause.length() > 0,
                "The given banned user cause must not be null or empty!");
        checkArgument(operator != null, "The given operator must not be null!");

        // Just cancel IDEA's warning
        assert user != null && (cause != null && cause.length() > 0) && operator != null;
        DateTime dateTime = DateTime.now();
        user.setDisabled(true);
        user.setDisabledStart(dateTime.toDate());
        if (seconds <= 0) {
            user.setDisabledEnd(null);
        } else {
            user.setDisabledEnd(dateTime.plusSeconds((int) seconds).toDate());
        }
        BannedUser bannedUser = new BannedUser(user);
        bannedUser.setPermanent(user.isPermanentDisabled());
        bannedUser.setBannedCause(cause);
        bannedUser.setBannedTime(user.getDisabledStart());
        bannedUser.setUnbannedTime(user.getDisabledEnd());
        bannedUser.setCreatedTime(user.getDisabledStart());
        bannedUser.setLastModifiedTime(user.getDisabledStart());
        return bannedUser.operator(operator);
    }

    /**
     * Generates and returns a random salt of the specified length .
     *
     * @param length the salt string length.
     */
    public static String generateSalt(int length) {
        return RandomStrings.random(length, true, true);
    }

    public static String hashPassword(final String pwd, final String salt) {
        checkArgument(pwd != null && pwd.length() > 0,
                "The given `pwd` must not be null or empty!");
        String passwordSalt = salt == null ? RandomStrings.random(16, true, true) : salt;
        return DigestUtils.sha1Hex(String.format(DEFAULT_HASH_PASSWORD_FORMAT,
                pwd, passwordSalt));
    }

    private Gender gender;
    private Set<UserRole> roles;

    public User() {
        super();
    }

    public User(Long userId) {
        super(userId);
    }

    @Override
    public void setGenderCode(Short genderCode) {
        super.setGenderCode(genderCode);
        gender = Gender.of(genderCode);
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
        if (gender != null) {
            super.setGenderCode(gender.getCode());
        }
    }

    @Override
    public void setRoleData(Integer roleData) {
        super.setRoleData(roleData);
        if (roleData != null) {
            roles = UserRole.fromRoleData(roleData);
        }
    }

    public Set<String> getRoleNames() {
        Iterable<String> names = Iterables.transform(getRoles(), new Function<UserRole, String>() {
            @Override
            public String apply(UserRole input) {
                return input.getName();
            }
        });
        return Sets.newHashSet(names);
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
        if (roles != null && roles.size() > 0) {
            super.setRoleData(UserRole.toRoleData(roles));
        }
    }

    /**
     * 根据给定的原始密码和盐，设置新的 hash 密码。hash 密码和盐同时被更新。
     *
     * @param password 原始密码。
     * @param salt     密码盐。
     * @throws IllegalArgumentException if {@code password == null || password.length() == 0}
     *                                  or {@code salt == null || salt.length() == 0}.
     */
    public User changePassword(String password, String salt) {
        checkArgument(password != null && password.length() > 0,
                "The `password` must not be null or empty!");
        checkArgument(salt != null && salt.length() > 0,
                "The `salt` must not be null or empty!");
        final String hashPwd = hashPassword(password, salt);
        super.setHashPassword(hashPwd);
        super.setPasswordSalt(salt);
        return this;
    }

    /**
     * Returns {@code true} if this {@link #getHashPassword()} equals the specified {@code password},
     * otherwise {@code false}.
     *
     * @param password the match password.
     */
    public boolean match(String password) {
        if (isNullOrEmpty(password) || getHashPassword() == null) {
            return false;
        }
        String thatHashPwd = hashPassword(password, getPasswordSalt());
        return getHashPassword().equalsIgnoreCase(thatHashPwd);
    }

    /**
     * 使用给定的实体，更新当前实体的信息。通常是已改变的属性的变更。
     *
     * @param other 新的实体。
     * @return 返回当前实体对象。
     * @throws IllegalArgumentException if {@code other == null}.
     * @throws IllegalStateException    if {@code !other.getId().equals(getId())}.
     */
    @Override
    public User update(User other) {
        checkArgument(other != null, "The given `other` user must not be null!");
        assert other != null; // just cancel IDE warning
        if (!Objects.equal(getId(), other.getId())) {
            throw new IllegalStateException("The given `other` user's id not " +
                    "equals this user's id, cannot be updated.");
        }
        // userName can be modified
        if (!Objects.equal(getUserName(), other.getUserName())) {
            setUserName(other.getUserName());
        }
        // mobile number can be modified
        if (!Objects.equal(getMobileNo(), other.getMobileNo())) {
            setMobileNo(other.getMobileNo());
        }
        if (isMobileVerified() != other.isMobileVerified()) {
            setMobileVerified(other.isMobileVerified());
        }
        if (!Objects.equal(getSpareEmail(), other.getSpareEmail())) {
            setSpareEmail(other.getSpareEmail());
        }
        if (!Objects.equal(getAvatarUri(), other.getAvatarUri())) {
            setAvatarUri(other.getAvatarUri());
        }
        if (!Objects.equal(getSmallAvatarUri(), other.getSmallAvatarUri())) {
            setSmallAvatarUri(other.getSmallAvatarUri());
        }
        if (!Objects.equal(getThumbAvatarUri(), other.getThumbAvatarUri())) {
            setThumbAvatarUri(other.getThumbAvatarUri());
        }
        if (isActivated() != other.isActivated()) {
            setActivated(other.isActivated());
        }
        return this;
    }

    public boolean isValid() {
        return !isDisabled() || (getDisabledEnd() != null &&
                getDisabledEnd().getTime() - System.currentTimeMillis() <= 0);
    }

    public boolean isPermanentDisabled() {
        return isDisabled() && (getDisabledEnd() == null ||
                new DateTime(getDisabledEnd()).getYear() -
                        new DateTime(getDisabledStart()).getYear() >= 80);
    }

    @Override
    public Object[] toArray() {
        return new Object[]{
                getUserId(), getEmail(), getUserName(), getUserHandle(),
                getHashPassword(), getGenderCode(), getPasswordSalt(),
                getMobileNo(), isMobileVerified(), getAvatarUri(), getSmallAvatarUri(),
                getThumbAvatarUri(), getSpareEmail(), getRoleData(), isActivated(), isDisabled(),
                getDisabledStart(), getDisabledEnd(), getCreatedTime(), getLastModifiedTime()
        };
    }

    public static void main(String[] args) {
        System.out.println(String.format("Basic %s",
                Base64.encodeBase64String("1234567:fuchun".getBytes())));
    }
}
