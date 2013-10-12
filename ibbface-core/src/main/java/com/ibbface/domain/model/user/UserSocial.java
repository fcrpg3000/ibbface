/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) UserSocial.java 2013-08-10 10:25
 */

package com.ibbface.domain.model.user;

import com.google.common.base.Objects;
import com.ibbface.domain.model.common.OpenProvider;
import com.ibbface.domain.model.user.base.BaseUserSocial;
import com.ibbface.domain.shared.QueryValue;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * The user social entity class.
 *
 * @author Fuchun
 * @since 1.0
 */
public class UserSocial extends BaseUserSocial implements QueryValue {
    private static final long serialVersionUID = 1L;

    private OpenProvider openProvider;

    public UserSocial() {
    }

    public UserSocial(Long userId) {
        super(userId);
    }

    @Override
    public void setOpenProviderCode(Short openProviderCode) {
        super.setOpenProviderCode(openProviderCode);
        openProvider = OpenProvider.of(openProviderCode);
    }

    public OpenProvider getOpenProvider() {
        return openProvider;
    }

    public void setOpenProvider(OpenProvider openProvider) {
        this.openProvider = openProvider;
        if (openProvider != null) {
            super.setOpenProviderCode(openProvider.getCode());
        }
    }

    /**
     * 使用给定的实体，更新当前实体的信息。通常是已改变的属性的变更。
     *
     * @param other 新的实体。
     * @return 返回当前实体对象。
     * @throws IllegalStateException if {@code !other.getId().equals(getId())}.
     */
    @Override
    public UserSocial update(UserSocial other) {
        checkArgument(other != null, "The given `other` UserSocial must not be null!");
        assert other != null; // just cancel IDE warning
        if (!Objects.equal(getId(), other.getId())) {
            throw new IllegalStateException("The given `other` UserSocial id not " +
                    "equals this UserSocial id, cannot be updated.");
        }
        // openId cannot update
        setAccessToken(other.getAccessToken());
        setExpiresIn(other.getExpiresIn());
        setLastModifiedTime(other.getLastModifiedTime());
        return this;
    }

    @Override
    public Object[] toArray() {
        return new Object[]{
                getId(), getUserId(), getOpenUid(), getOpenProviderCode(), getAccessToken(),
                getExpiresIn(), getCreatedTime(), getLastModifiedTime()
        };
    }
}
