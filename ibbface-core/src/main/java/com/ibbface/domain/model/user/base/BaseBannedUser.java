/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) BaseBannedUser.java 2013-07-28 16:17
 */

package com.ibbface.domain.model.user.base;

import com.google.common.base.Objects;
import com.ibbface.domain.model.user.BannedUser;
import com.ibbface.domain.shared.AbstractEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * Banned user base entity.
 *
 * @author Fuchun
 * @since 1.0
 */
public abstract class BaseBannedUser extends AbstractEntity<Long, BannedUser>
        implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String PROP_OPERATOR_ID = "operatorId";
    public static final String PROP_OPERATOR_NAME = "operatorName";
    public static final String PROP_BANNED_CAUSE = "bannedCause";
    public static final String PROP_IS_PERMANENT = "permanent";
    public static final String PROP_BANNED_TIME = "bannedTime";
    public static final String PROP_UNBANNED_TIME = "unbannedTime";

    private Long userId;
    private String userName;
    private Integer operatorId;
    private String operatorName;
    private String bannedCause;
    private boolean isPermanent;
    private Date bannedTime;
    private Date unbannedTime;
    private Date createdTime;
    private Date lastModifiedTime;

    protected BaseBannedUser() {
    }

    protected BaseBannedUser(Long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getBannedCause() {
        return bannedCause;
    }

    public void setBannedCause(String bannedCause) {
        this.bannedCause = bannedCause;
    }

    public boolean isPermanent() {
        return isPermanent;
    }

    public void setPermanent(boolean permanent) {
        isPermanent = permanent;
    }

    public Date getBannedTime() {
        return bannedTime;
    }

    public void setBannedTime(Date bannedTime) {
        this.bannedTime = bannedTime;
    }

    public Date getUnbannedTime() {
        return unbannedTime;
    }

    public void setUnbannedTime(Date unbannedTime) {
        this.unbannedTime = unbannedTime;
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
                .add(PROP_USER_ID, getUserId())
                .add(PROP_USER_NAME, getUserName())
                .add(PROP_OPERATOR_ID, getOperatorId())
                .add(PROP_OPERATOR_NAME, getOperatorName())
                .add(PROP_BANNED_CAUSE, getBannedCause())
                .add(PROP_IS_PERMANENT, isPermanent())
                .add(PROP_BANNED_TIME, getBannedTime())
                .add(PROP_UNBANNED_TIME, getUnbannedTime())
                .add(PROP_CREATED_TIME, getCreatedTime())
                .add(PROP_LAST_MODIFIED_TIME, getLastModifiedTime())
                .toString();
    }
}
