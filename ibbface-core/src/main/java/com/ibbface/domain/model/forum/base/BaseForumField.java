/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) BaseForumField.java 2013-07-28 17:14
 */

package com.ibbface.domain.model.forum.base;

import com.google.common.base.Objects;
import com.ibbface.domain.model.forum.ForumField;
import com.ibbface.domain.shared.AbstractEntity;

import java.io.Serializable;

/**
 * The forum's field base entity.
 *
 * @author Fuchun
 * @since 1.0
 */
public abstract class BaseForumField extends AbstractEntity<Long, ForumField>
        implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String PROP_FORUM_ID = "forumId";
    public static final String PROP_FIELD_NAME = "fieldName";
    public static final String PROP_SUMMARY = "summary";
    public static final String PROP_FIELD_RULE = "fieldRule";
    public static final String PROP_FIELD_TYPE = "fieldType";
    public static final String PROP_SORT_ORDER = "sortOrder";
    public static final String PROP_IS_REQUIRED = "required";
    public static final String PROP_OPTIONS = "options";

    private Integer forumId;
    private String fieldName;
    private String summary;
    private String fieldRule;
    private Short fieldType;
    private Integer sortOrder;
    private boolean isRequired;
    private Integer options;

    protected BaseForumField() {
    }

    protected BaseForumField(Long id) {
        super.setId(id);
    }

    public Integer getForumId() {
        return forumId;
    }

    public void setForumId(Integer forumId) {
        this.forumId = forumId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getFieldRule() {
        return fieldRule;
    }

    public void setFieldRule(String fieldRule) {
        this.fieldRule = fieldRule;
    }

    public Short getFieldType() {
        return fieldType;
    }

    public void setFieldType(Short fieldType) {
        this.fieldType = fieldType;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(boolean required) {
        isRequired = required;
    }

    public Integer getOptions() {
        return options;
    }

    public void setOptions(Integer options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(getClass())
                .add(PROP_ID, getId())
                .add(PROP_FORUM_ID, getForumId())
                .add(PROP_FIELD_NAME, getFieldName())
                .add(PROP_SUMMARY, getSummary())
                .add(PROP_FIELD_RULE, getFieldRule())
                .add(PROP_FIELD_TYPE, getFieldType())
                .add(PROP_SORT_ORDER, getSortOrder())
                .add(PROP_IS_REQUIRED, isRequired())
                .add(PROP_OPTIONS, getOptions())
                .toString();
    }
}
