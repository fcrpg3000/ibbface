/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) ForumField.java 2013-07-28 17:14
 */

package com.ibbface.domain.model.forum;

import com.google.common.base.Objects;
import com.ibbface.domain.model.forum.base.BaseForumField;
import com.ibbface.domain.shared.QueryValue;

/**
 * {@link Forum} field information entity.
 *
 * @author Fuchun
 * @since 1.0
 */
public class ForumField extends BaseForumField implements QueryValue {
    private static final long serialVersionUID = 1L;

    public ForumField() {
        super();
    }

    public ForumField(Long id) {
        super(id);
    }

    /**
     * @see com.ibbface.domain.shared.Entity#update(Object)
     */
    @Override
    public ForumField update(ForumField other) {
        if (other == null) {
            return this;
        }
        if (!Objects.equal(other.getId(), getId())) {
            throw new IllegalStateException("The given other `ForumField` not equals this.");
        }
        setForumId(other.getForumId());
        setFieldName(other.getFieldName());
        setSummary(other.getSummary());
        setFieldRule(other.getFieldRule());
        setFieldType(other.getFieldType());
        setSortOrder(other.getSortOrder());
        setRequired(other.isRequired());
        setOptions(other.getOptions());
        return this;
    }

    @Override
    public Object[] toArray() {
        return new Object[]{
                getId(), getForumId(), getFieldName(), getSummary(),
                getFieldRule(), getFieldType(), getSortOrder(), isRequired(),
                getOptions()
        };
    }
}
