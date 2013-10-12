/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) ForumFieldValue.java 2013-07-28 17:28
 */

package com.ibbface.domain.model.forum;

import com.ibbface.domain.model.forum.base.BaseForumFieldValue;
import com.ibbface.domain.shared.QueryValue;

/**
 * The forum's field value entity.
 *
 * @author Fuchun
 * @since 1.0
 */
public class ForumFieldValue extends BaseForumFieldValue implements QueryValue {
    private static final long serialVersionUID = 1L;

    public ForumFieldValue() {
        super();
    }

    public ForumFieldValue(Long id) {
        super(id);
    }

    /**
     * 使用给定的实体，更新当前实体的信息。通常是已改变的属性的变更。
     *
     * @param other 新的实体。
     * @return 返回当前实体对象。
     * @throws IllegalStateException if {@code !other.getId().equals(getId())}.
     */
    @Override
    public ForumFieldValue update(ForumFieldValue other) {
        return this;
    }

    /**
     * Returns this QueryValue all serializable field values.
     */
    @Override
    public Object[] toArray() {
        return new Object[] {
                getId(), getFieldId(), getParentId(), getFieldName(),
                getFieldValue(), getSortOrder(), getIsDefault()
        };
    }
}
