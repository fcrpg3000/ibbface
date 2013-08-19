/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) UserSession.java 2013-08-10 11:32
 */

package com.ibbface.domain.model.session;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import com.ibbface.domain.shared.AbstractEntity;

import java.io.Serializable;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * The user session entity class.
 *
 * @author Fuchun
 * @since 1.0
 */
public class UserSession extends AbstractEntity<Long, UserSession> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 默认的会话Id在 Cookie 中保存的键名。
     */
    public static final String DEFAULT_SESSION_ID_NAME = "__jsessionid";

    public static final String PROP_SESSION_ID = "sessionId";
    public static final String PROP_ACCESS_TOKEN = "accessToken";
    public static final String PROP_ATTRIBUTES_DATA = "attributesData";
    public static final String PROP_IS_VALID = "isValid";
    public static final String PROP_LAST_ACCESSED_TIME = "lastAccessedTime";
    public static final String PROP_CREATION_TIME = "creationTime";

    private Long userId;
    private String sessionId;
    private String accessToken;
    private String attributesData;
    private boolean isValid;
    private long creationTime;
    private long lastAccessedTime;

    private Map<String, Object> attributesMap = Maps.newHashMap();

    /**
     * Sets the {@link UserSession} id ({@code userId}).
     *
     * @param id the user id。
     */
    @Override
    public void setId(Long id) {
        super.setId(id);
        this.userId = id;
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

    public String getAttributesData() {
        return attributesData;
    }

    public void setAttributesData(String attributesData) {
        this.attributesData = attributesData == null ? null : attributesData.trim();
        if (this.attributesData != null && this.attributesData.length() != 0) {
            this.attributesMap = JSON.parseObject(this.attributesData,
                    new TypeReference<Map<String, Object>>() {}.getType());
        }
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    /**
     * The time this session was created, in milliseconds since midnight,
     * January 1, 1970 GMT.
     */
    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public long getLastAccessedTime() {
        return lastAccessedTime;
    }

    public void setLastAccessedTime(long lastAccessedTime) {
        this.lastAccessedTime = lastAccessedTime;
    }

    public Map<String, Object> getAttributesMap() {
        return attributesMap;
    }

    public void setAttributesMap(Map<String, Object> attributesMap) {
        this.attributesMap = attributesMap;
        if (this.attributesMap != null) {
            this.attributesData = JSON.toJSONString(this.attributesMap);
        }
    }

    /**
     * 从用户会话中删除指定属性名称的绑定在会话中的对象。
     *
     * @param attrName 要删除的对象属性名称。
     */
    public void removeAttribute(String attrName) {
        if (attrName == null || attributesMap == null) {
            return;
        }
        attributesMap.remove(attrName);
    }

    /**
     * 返回指定属性名称的绑定在该用户会话中的对象。
     *
     * @param attrName 属性名称。
     * @return 指定属性名称的绑定在该用户会话中的对象。
     */
    public Object getAttribute(String attrName) {
        if (attrName == null || attributesMap == null ||
                attributesMap.isEmpty()) {
            return null;
        }
        return attributesMap.get(attrName);
    }

    /**
     * 在用户会话中绑定指定名称的值。
     *
     * @param attrName 要绑定的名称。
     * @param value    要绑定的值。
     */
    public void setAttribute(String attrName, Object value) {
        if (attrName == null) {
            return;
        }
        if (value == null) {
            removeAttribute(attrName);
            return;
        }
        attributesMap.put(attrName, value);
    }

    /**
     * 使用给定的实体，更新当前实体的信息。通常是已改变的属性的变更。
     *
     * @param other 新的实体。
     * @return 返回当前实体对象。
     * @throws IllegalStateException if {@code !other.getId().equals(getId())}.
     */
    @Override
    public UserSession update(UserSession other) {
        checkArgument(other != null, "The given `other` UserSession must not be null!");
        assert other != null; // just cancel IDE warning
        if (!Objects.equal(getId(), other.getId())) {
            throw new IllegalStateException("The given `other` UserSession userId not " +
                    "equals this UserSession userId, cannot be updated.");
        }
        setSessionId(other.getSessionId());
        setAccessToken(other.getAccessToken());
        setCreationTime(other.getCreationTime());
        setLastAccessedTime(other.getLastAccessedTime());
        setAttributesData(other.getAttributesData());
        return this;
    }

    public Object[] toArray() {
        return new Object[]{
                userId, sessionId, accessToken, attributesData,
                isValid, creationTime, lastAccessedTime
        };
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add(PROP_USER_ID, getUserId())
                .add(PROP_SESSION_ID, getSessionId())
                .add(PROP_ACCESS_TOKEN, getAccessToken())
                .add(PROP_ATTRIBUTES_DATA, getAttributesMap())
                .add(PROP_IS_VALID, isValid())
                .add(PROP_CREATION_TIME, getCreationTime())
                .add(PROP_LAST_ACCESSED_TIME, getLastAccessedTime())
                .toString();
    }
}
