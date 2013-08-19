/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) BaseContent.java 2013-07-28 17:48
 */

package com.ibbface.domain.shared;

import com.google.common.base.Objects;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Fuchun
 * @since 1.0
 */
public abstract class BaseContent<T> extends AbstractEntity<Long, T>
        implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String PROP_TYPE_ID = "typeId";
    public static final String PROP_TITLE = "title";
    public static final String PROP_CONTENT = "content";
    public static final String PROP_TAGS = "tags";
    public static final String PROP_SOURCE_URL = "sourceUrl";
    public static final String PROP_VIEW_COUNT = "viewCount";
    public static final String PROP_REPLY_COUNT = "replyCount";
    public static final String PROP_IS_TOP = "isTop";
    public static final String PROP_IS_GOOD = "isGood";
    public static final String PROP_CLIENT_CODE = "clientCode";
    public static final String PROP_CLIENT_IP = "clientIp";
    public static final String PROP_OPTIONS = "options";

    private String idAlias;
    private Long userId;
    private Short typeId;
    private String title;
    private String content;
    private String tags;
    private String sourceUrl;
    private Integer viewCount;
    private Integer replyCount;
    private boolean isTop;
    private boolean isGood;
    private Short clientCode;
    private String clientIp;
    private Date createdTime;
    private Date lastModifiedTime;
    private Integer options;
    private Short status;

    public String getIdAlias() {
        return idAlias;
    }

    public void setIdAlias(String idAlias) {
        this.idAlias = idAlias;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Short getTypeId() {
        return typeId;
    }

    public void setTypeId(Short typeId) {
        this.typeId = typeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    public boolean getIsTop() {
        return isTop;
    }

    public void setIsTop(boolean top) {
        isTop = top;
    }

    public boolean getIsGood() {
        return isGood;
    }

    public void setIsGood(boolean good) {
        isGood = good;
    }

    public Short getClientCode() {
        return clientCode;
    }

    public void setClientCode(Short clientCode) {
        this.clientCode = clientCode;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
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

    public Integer getOptions() {
        return options;
    }

    public void setOptions(Integer options) {
        this.options = options;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    protected Objects.ToStringHelper toStringHelper() {
        return Objects.toStringHelper(getClass())
                .add(PROP_ID, getId())
                .add(PROP_ID_ALIAS, getIdAlias())
                .add(PROP_USER_ID, getUserId())
                .add(PROP_TYPE_ID, getTypeId())
                .add(PROP_TITLE, getTitle())
                .add(PROP_CONTENT, getContent())
                .add(PROP_TAGS, getTags())
                .add(PROP_SOURCE_URL, getSourceUrl())
                .add(PROP_VIEW_COUNT, getViewCount())
                .add(PROP_REPLY_COUNT, getReplyCount())
                .add(PROP_IS_TOP, getIsTop())
                .add(PROP_IS_GOOD, getIsGood())
                .add(PROP_CLIENT_CODE, getClientCode())
                .add(PROP_CLIENT_IP, getClientIp())
                .add(PROP_OPTIONS, getOptions())
                .add(PROP_STATUS, getStatus())
                .add(PROP_CREATED_TIME, getCreatedTime())
                .add(PROP_LAST_MODIFIED_TIME, getLastModifiedTime());
    }
}
