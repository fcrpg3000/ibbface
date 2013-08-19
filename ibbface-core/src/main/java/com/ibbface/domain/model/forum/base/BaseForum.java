/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) BaseForum.java 2013-07-28 17:01
 */

package com.ibbface.domain.model.forum.base;

import com.google.common.base.Objects;
import com.ibbface.domain.model.forum.Forum;
import com.ibbface.domain.shared.AbstractEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * Forum base entity.
 *
 * @author Fuchun
 * @since 1.0
 */
public abstract class BaseForum extends AbstractEntity<Integer, Forum>
        implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String PROP_TITLE = "title";
    public static final String PROP_SUMMARY = "summary";
    public static final String PROP_SORT_ORDER = "sortOrder";
    public static final String PROP_OPTIONS = "options";

    private String idAlias;
    private String title;
    private String summary;
    private Integer sortOrder;
    private Short status;
    private Integer options;
    private Date createdTime;
    private Date lastModifiedTime;

    protected BaseForum() {
    }

    protected BaseForum(Integer id) {
        super.setId(id);
    }

    public String getIdAlias() {
        return idAlias;
    }

    public void setIdAlias(String idAlias) {
        this.idAlias = idAlias;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Integer getOptions() {
        return options;
    }

    public void setOptions(Integer options) {
        this.options = options;
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
                .add(PROP_ID_ALIAS, getIdAlias())
                .add(PROP_TITLE, getTitle())
                .add(PROP_SUMMARY, getSummary())
                .add(PROP_SORT_ORDER, getSortOrder())
                .add(PROP_STATUS, getStatus())
                .add(PROP_OPTIONS, getOptions())
                .add(PROP_CREATED_TIME, getCreatedTime())
                .add(PROP_LAST_MODIFIED_TIME, getLastModifiedTime())
                .toString();
    }
}
