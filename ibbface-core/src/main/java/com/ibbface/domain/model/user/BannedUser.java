/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) BannedUser.java 2013-07-28 16:17
 */

package com.ibbface.domain.model.user;

import com.google.common.base.Objects;
import com.ibbface.domain.model.user.base.BaseBannedUser;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Banned user entity.
 *
 * @author Fuchun
 * @since 1.0
 */
public class BannedUser extends BaseBannedUser {
    private static final long serialVersionUID = 1L;

    public BannedUser() {
        super();
    }

    public BannedUser(User user) {
        super(user.getUserId(), user.getUserName());
    }

    public BannedUser(Long userId, String userName) {
        super(userId, userName);
    }

    /**
     * 使用给定的实体，更新当前实体的信息。通常是已改变的属性的变更。
     *
     * @param other 新的实体。
     * @return 返回当前实体对象。
     * @throws IllegalStateException if {@code !other.getId().equals(getId())}.
     */
    @Override
    public BannedUser update(BannedUser other) {
        checkArgument(other != null,
                "The given `other` banned user must not be null!");
        assert other != null; // just cancel IDE warning
        if (!Objects.equal(getId(), other.getId())) {
            throw new IllegalStateException("The given `other` banned user's id not " +
                    "equals this banned user's id, cannot be updated.");
        }
        setBannedTime(other.getBannedTime());
        setBannedCause(other.getBannedCause());
        setUnbannedTime(other.getUnbannedTime());
        setPermanent(other.isPermanent());
        setOperatorId(other.getOperatorId());
        setOperatorName(other.getOperatorName());
        setLastModifiedTime(other.getLastModifiedTime());
        return this;
    }

    public BannedUser operator(Operator operator) {
        if (operator == null) {
            return this;
        }
        setOperatorId(operator.getId());
        setOperatorName(operator.getName());
        return this;
    }

    public Object[] toArray() {
        return new Object[]{
                getId(), getUserId(), getUserName(), getOperatorId(), getOperatorName(),
                getBannedCause(), isPermanent(), getBannedTime(), getUnbannedTime(),
                getCreatedTime(), getLastModifiedTime()
        };
    }
}
