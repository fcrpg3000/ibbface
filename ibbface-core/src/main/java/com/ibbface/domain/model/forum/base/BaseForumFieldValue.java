/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) BaseForumFieldValue.java 2013-07-28 17:28
 */

package com.ibbface.domain.model.forum.base;

import com.google.common.base.Objects;
import com.ibbface.domain.model.forum.ForumFieldValue;
import com.ibbface.domain.shared.AbstractEntity;

import java.io.Serializable;

/**
 * The forum's field value base entity.
 *
 * @author Fuchun
 * @since 1.0
 */
public abstract class BaseForumFieldValue extends AbstractEntity<Long, ForumFieldValue>
        implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String PROP_FIELD_ID = "fieldId";
    public static final String PROP_PARENT_ID = "parentId";
    public static final String PROP_FIELD_NAME = "fieldName";
    public static final String PROP_FIELD_VALUE = "fieldValue";
    public static final String PROP_SORT_ORDER = "sortOrder";
    public static final String PROP_IS_DEFAULT = "isDefault";

    private Long fieldId;
    private Long parentId;
    private String fieldName;
    private String fieldValue;
    private Integer sortOrder;
    private boolean isDefault;

    protected BaseForumFieldValue() {
    }

    protected BaseForumFieldValue(Long id) {
        super.setId(id);
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(getClass())
                .add(PROP_ID, getId())
                .add(PROP_FIELD_ID, getFieldId())
                .add(PROP_PARENT_ID, getParentId())
                .add(PROP_FIELD_NAME, getFieldName())
                .add(PROP_FIELD_VALUE, getFieldValue())
                .add(PROP_SORT_ORDER, getSortOrder())
                .add(PROP_IS_DEFAULT, getIsDefault())
                .toString();
    }
}
