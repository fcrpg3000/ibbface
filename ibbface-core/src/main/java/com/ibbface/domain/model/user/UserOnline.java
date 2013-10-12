/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) UserOnline.java 2013-08-06 00:32
 */

package com.ibbface.domain.model.user;

import com.google.common.base.Objects;
import com.ibbface.domain.model.user.base.BaseUserOnline;
import com.ibbface.domain.shared.QueryValue;
import com.ibbface.util.RandomStrings;
import org.apache.commons.codec.digest.DigestUtils;

import javax.annotation.Nonnull;
import java.util.Date;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author Fuchun
 * @since 1.0
 */
public class UserOnline extends BaseUserOnline implements QueryValue {
    private static final long serialVersionUID = 1L;

    public static String generateSessionId(@Nonnull Long userId) {
        return DigestUtils.md5Hex(String.format("%s{%s}", userId,
                RandomStrings.random(8, true, true))).toUpperCase();
    }

    public static UserOnline create(@Nonnull Long userId, String clientIp, Date loginTime) {
        UserOnline userOnline = new UserOnline(userId);
        userOnline.setLastClientIp(clientIp);
        userOnline.setLastLoginTime(loginTime);
        // TODO: generate session id and accessToken
        userOnline.setSessionId(generateSessionId(userId));
        userOnline.setAccessToken(RandomStrings.random(16, true, true));
        return userOnline;
    }

    public UserOnline() {
        super();
    }

    public UserOnline(Long userId) {
        super(userId);
    }

    /**
     * 使用给定的实体，更新当前实体的信息。通常是已改变的属性的变更。
     *
     * @param other 新的实体。
     * @return 返回当前实体对象。
     * @throws IllegalStateException if {@code !other.getId().equals(getId())}.
     */
    @Override
    public UserOnline update(UserOnline other) {
        checkArgument(other != null, "The given `other` UserOnline must not be null!");
        assert other != null; // just cancel IDE warning
        if (!Objects.equal(getId(), other.getId())) {
            throw new IllegalStateException("The given `other` UserOnline userId not " +
                    "equals this UserOnline userId, cannot be updated.");
        }
        setSessionId(other.getSessionId());
        setAccessToken(other.getAccessToken());
        setPrevClientIp(other.getPrevClientIp());
        setLastClientIp(other.getLastClientIp());
        setPrevLoginTime(other.getPrevLoginTime());
        setLastLoginTime(other.getLastLoginTime());
        setTotalLoginCount(other.getTotalLoginCount());
        setThatLoginCount(other.getThatLoginCount());
        setTotalOnlineTime(other.getTotalOnlineTime());
        setThatOnlineTime(other.getThatOnlineTime());
        setLastAccessedTime(other.getLastAccessedTime());
        return this;
    }

    @Override
    public Object[] toArray() {
        return new Object[]{
                getUserId(), getSessionId(), getAccessToken(), getPrevClientIp(),
                getLastClientIp(), getPrevLoginTime(), getLastLoginTime(),
                getTotalLoginCount(), getThatLoginCount(), getTotalOnlineTime(),
                getThatOnlineTime(), getLastAccessedTime()
        };
    }
}
