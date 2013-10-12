/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) Forum.java 2013-07-28 17:01
 */

package com.ibbface.domain.model.forum;

import com.google.common.base.Objects;
import com.ibbface.domain.model.forum.base.BaseForum;
import com.ibbface.domain.shared.QueryValue;

import java.util.Date;
import java.util.List;

/**
 * The forum entity class.
 *
 * @author Fuchun
 * @since 1.0
 */
public class Forum extends BaseForum implements Comparable<Forum>, QueryValue {
    private static final long serialVersionUID = 1L;

    /**
     * This status value indicates the forum was deleted.
     */
    public static final Short STATUS_DELETED = -1;
    /**
     * This status value indicates the forum is unpublished.
     */
    public static final Short STATUS_UNPUBLISHED = 0;
    /**
     * This status value indicates the forum is published.
     */
    public static final Short STATUS_PUBLISHED = 1;

    public static Forum newForum(String idAlias, String title, String summary) {
        Forum f = new Forum();
        f.setIdAlias(idAlias);
        f.setTitle(title);
        f.setSummary(summary);
        f.setStatus(STATUS_PUBLISHED);
        f.preInsert();
        return f;
    }

    private transient boolean modified;

    private List<ForumField> forumFields;

    public Forum() {
        super();
    }

    public Forum(Integer id) {
        super(id);
    }

    public List<ForumField> getForumFields() {
        return forumFields;
    }

    public void setForumFields(List<ForumField> forumFields) {
        this.forumFields = forumFields;
    }

    /**
     * Returns {@code true} if this forum is deleted, otherwise {@code false}.
     */
    public boolean isDeleted() {
        return STATUS_DELETED.equals(getStatus());
    }

    /**
     * Returns {@code true} if this forum is published, otherwise {@code false}.
     */
    public boolean isPublished() {
        return STATUS_PUBLISHED.equals(getStatus());
    }

    /**
     * Returns {@code true} if this forum is unpublished, otherwise {@code false}.
     */
    public boolean isUnpublished() {
        return STATUS_UNPUBLISHED.equals(getStatus());
    }

    /**
     * 使用给定的实体，更新当前实体的信息。通常是已改变的属性的变更。
     *
     * @param other 新的实体。
     * @return 返回当前实体对象。
     * @throws IllegalStateException if {@code !other.getId().equals(getId())}.
     */
    @Override
    public Forum update(Forum other) {
        if (other == null) {
            return this;
        }
        if (!Objects.equal(other.getId(), getId())) {
            throw new IllegalStateException("The given other `Forum` not equals this.");
        }
        setIdAlias(other.getIdAlias());
        setTitle(other.getTitle());
        setSummary(other.getSummary());
        setSortOrder(other.getSortOrder());
        setStatus(other.getStatus());
        setLastModifiedTime(other.getLastModifiedTime());
        modified = true;
        return this;
    }

    @Override
    public int compareTo(Forum forum) {
        if (getId() != null && getId().equals(forum.getId())) { // same entity
            return 0;
        }
        int value;
        if (getSortOrder() != null) {
            if (forum.getSortOrder() == null) {
                value = -1;
            } else {
                value = getSortOrder().compareTo(forum.getSortOrder());
                if (value != 0) {
                    return value;
                }
                if (getId() == null) {
                    value = forum.getId() == null ? 0 : 1;
                } else {
                    value = forum.getId() == null ? -1 : getId().compareTo(forum.getId());
                }
            }
        } else {
            value = forum.getSortOrder() != null ? 1 : 0;
        }

        return value;
    }

    /**
     * Returns this {@code Forum}'s all serial fields value.
     * <pre>Sequence:
     * id, idAlias, title, summary, sortOrder,
     * status, createdTime, lastModifiedTime.
     * </pre>
     */
    @Override
    public Object[] toArray() {
        return new Object[]{
                getId(), getIdAlias(), getTitle(), getSummary(), getSortOrder(),
                getStatus(), getOptions(), getCreatedTime(), getLastModifiedTime()
        };
    }

    public void preInsert() {
        if (getCreatedTime() != null && getLastModifiedTime() != null) {
            return;
        }
        final Date dateNow = new Date();
        setCreatedTime(dateNow);
        setLastModifiedTime(dateNow);
    }
}
