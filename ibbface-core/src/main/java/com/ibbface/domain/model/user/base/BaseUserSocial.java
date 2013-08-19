/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) BaseUserSocial.java 2013-08-10 10:25
 */

package com.ibbface.domain.model.user.base;

import com.google.common.base.Objects;
import com.ibbface.domain.model.user.UserSocial;
import com.ibbface.domain.shared.AbstractEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * The user social base entity class.
 *
 * @author Fuchun
 * @since 1.0
 */
public abstract class BaseUserSocial extends AbstractEntity<Long, UserSocial>
        implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String PROP_OPEN_UID = "openUid";
    public static final String PROP_OPEN_PROVIDER_CODE = "openProviderCode";
    public static final String PROP_ACCESS_TOKEN = "accessToken";
    public static final String PROP_EXPIRES_IN = "expiresIn";

    private Long userId;
    private String openUid;
    private Short openProviderCode;
    private String accessToken;
    private Long expiresIn;
    private Date createdTime;
    private Date lastModifiedTime;

    protected BaseUserSocial() {
    }

    protected BaseUserSocial(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOpenUid() {
        return openUid;
    }

    public void setOpenUid(String openUid) {
        this.openUid = openUid;
    }

    public Short getOpenProviderCode() {
        return openProviderCode;
    }

    public void setOpenProviderCode(Short openProviderCode) {
        this.openProviderCode = openProviderCode;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(getClass())
                .add(PROP_ID, getId())
                .add(PROP_USER_ID, getUserId())
                .add(PROP_OPEN_UID, getOpenUid())
                .add(PROP_OPEN_PROVIDER_CODE, getOpenProviderCode())
                .add(PROP_ACCESS_TOKEN, getAccessToken())
                .add(PROP_EXPIRES_IN, getExpiresIn())
                .add(PROP_CREATED_TIME, getCreatedTime())
                .add(PROP_LAST_MODIFIED_TIME, getLastModifiedTime())
                .toString();
    }
}
