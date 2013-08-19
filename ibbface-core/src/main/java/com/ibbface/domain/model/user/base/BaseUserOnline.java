/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) BaseUserOnline.java 2013-08-06 00:31
 */

package com.ibbface.domain.model.user.base;

import com.ibbface.domain.model.user.UserOnline;
import com.ibbface.domain.shared.AbstractEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Fuchun
 * @since 1.0
 */
public abstract class BaseUserOnline extends AbstractEntity<Long, UserOnline>
        implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String PROP_SESSION_ID = "sessionId";
    public static final String PROP_ACCESS_TOKEN = "accessToken";
    public static final String PROP_PREV_CLIENT_IP = "prevClientIp";
    public static final String PROP_LAST_CLIENT_IP = "lastClientIp";
    public static final String PROP_PREV_LOGIN_TIME = "prevLoginTime";
    public static final String PROP_LAST_LOGIN_TIME = "lastLoginTime";
    public static final String PROP_TOTAL_LOGIN_COUNT = "totalLoginCount";
    public static final String PROP_THAT_LOGIN_COUNT = "thatLoginCount";
    public static final String PROP_TOTAL_ONLINE_TIME = "totalOnlineTime";
    public static final String PROP_THAT_ONLINE_TIME = "thatOnlineTime";
    public static final String PROP_LAST_ACCESSED_TIME = "lastAccessedTime";

    private Long userId;
    private String sessionId;
    private String accessToken;
    private String prevClientIp;
    private String lastClientIp;
    private Date prevLoginTime;
    private Date lastLoginTime;
    private Integer totalLoginCount;
    private Integer thatLoginCount;
    private Long totalOnlineTime;
    private Long thatOnlineTime;
    private Long lastAccessedTime;

    protected BaseUserOnline() {
        this(null);
    }

    protected BaseUserOnline(Long userId) {
        setUserId(userId);
        totalLoginCount = 0;
        thatLoginCount = 0;
        totalOnlineTime = 0L;
        thatOnlineTime = 0L;
        lastAccessedTime = System.currentTimeMillis();
    }

    /**
     * 返回当前实体的主键。
     */
    @Override
    public Long getId() {
        return getUserId();
    }

    /**
     * 设置当前实体的主键。
     *
     * @param id 当前实体的主键。
     */
    @Override
    public void setId(Long id) {
        super.setId(id);
        this.userId = id;
    }

    /**
     * 检查当前是否是一个新的实体类。
     *
     * @return 如果当前实体是一个实体类，则返回 {@code true}，否则 {@code false}。
     */
    @Override
    public boolean isNew() {
        return totalLoginCount == 0 && thatLoginCount == 0 &&
                totalOnlineTime == 0 && thatOnlineTime == 0;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        super.setId(userId);
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getPrevClientIp() {
        return prevClientIp;
    }

    public void setPrevClientIp(String prevClientIp) {
        this.prevClientIp = prevClientIp;
    }

    public String getLastClientIp() {
        return lastClientIp;
    }

    public void setLastClientIp(String lastClientIp) {
        this.lastClientIp = lastClientIp;
    }

    public Date getPrevLoginTime() {
        return prevLoginTime;
    }

    public void setPrevLoginTime(Date prevLoginTime) {
        this.prevLoginTime = prevLoginTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getTotalLoginCount() {
        return totalLoginCount;
    }

    public void setTotalLoginCount(Integer totalLoginCount) {
        this.totalLoginCount = totalLoginCount;
    }

    public Integer getThatLoginCount() {
        return thatLoginCount;
    }

    public void setThatLoginCount(Integer thatLoginCount) {
        this.thatLoginCount = thatLoginCount;
    }

    public Long getTotalOnlineTime() {
        return totalOnlineTime;
    }

    public void setTotalOnlineTime(Long totalOnlineTime) {
        this.totalOnlineTime = totalOnlineTime;
    }

    public Long getThatOnlineTime() {
        return thatOnlineTime;
    }

    public void setThatOnlineTime(Long thatOnlineTime) {
        this.thatOnlineTime = thatOnlineTime;
    }

    public Long getLastAccessedTime() {
        return lastAccessedTime;
    }

    public void setLastAccessedTime(Long lastAccessedTime) {
        this.lastAccessedTime = lastAccessedTime;
    }
}
