/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) Thread.java 2013-07-28 17:37
 */

package com.ibbface.domain.model.forum;

import com.google.common.base.Objects;
import com.ibbface.domain.model.common.Client;
import com.ibbface.domain.model.forum.base.BaseForumThread;
import com.ibbface.domain.shared.QueryValue;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Date;

/**
 * The forum thread entity.
 *
 * @author Fuchun
 * @since 1.0
 */
public class ForumThread extends BaseForumThread implements QueryValue {
    private static final long serialVersionUID = 1L;

    public static final Short STATUS_DELETED = -1;

    public static final Short STATUS_DRAFT = 0;

    public static final Short STATUS_PUBLISHED = 1;

    public static ForumThread create(
            @Nonnull String idAlias, @Nonnull Integer forumId, @Nonnull Long userId,
            @Nonnull String title, @Nonnull String content, @Nonnull String clientIp,
            @Nullable Short typeId, @Nullable String tags, @Nullable String sourceUrl,
            @Nullable Short status) {
        ForumThread thread = new ForumThread();
        thread.setIdAlias(idAlias);
        thread.setForumId(forumId);
        thread.setUserId(userId);
        thread.setTitle(title);
        thread.setContent(content);
        thread.setTypeId(typeId);
        thread.setTags(tags);
        thread.setSourceUrl(sourceUrl);
        thread.setStatus(status == null ? STATUS_DRAFT : status);
        thread.setClientCode(Client.UNKNOWN.getCode());
        thread.setClientIp(clientIp);
        thread.setViewCount(0);
        thread.setReplyCount(0);
        thread.setLastPostId(0L);
        thread.setLastPostUserId(0L);
        thread.setOptions(0);
        thread.setCreatedTime(new Date());
        thread.setLastModifiedTime(thread.getCreatedTime());
        return thread;
    }

    public ForumThread() {
        super();
    }

    public ForumThread(Long id) {
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
    public ForumThread update(ForumThread other) {
        if (other == null) {
            return this;
        }
        if (!Objects.equal(other.getId(), getId())) {
            throw new IllegalStateException("The given other `ForumThread` not equals this.");
        }
        setIdAlias(other.getIdAlias());
        setTitle(other.getTitle());
        setContent(other.getContent());
        setTypeId(other.getTypeId());
        setTags(other.getTags());
        setSourceUrl(other.getSourceUrl());
        setViewCount(other.getViewCount());
        setReplyCount(other.getReplyCount());
        setLastPostId(other.getLastPostId());
        setLastPostUserId(other.getLastPostUserId());
        setLastPostTime(other.getLastPostTime());
        setIsTop(other.getIsTop());
        setIsGood(other.getIsGood());
        setClientCode(other.getClientCode());
        setClientIp(other.getClientIp());
        setOptions(other.getOptions());
        setStatus(other.getStatus());
        setCreatedTime(other.getCreatedTime());
        setLastModifiedTime(other.getLastModifiedTime());
        return this;
    }

    @Override
    public Object[] toArray() {
        return new Object[]{
                getId(), getIdAlias(), getUserId(), getForumId(), getTypeId(),
                getTitle(), getContent(), getTags(), getSourceUrl(), getViewCount(),
                getReplyCount(), getLastPostId(), getLastPostUserId(), getLastPostTime(),
                getIsTop(), getIsGood(), getClientCode(), getClientIp(), getCreatedTime(),
                getLastModifiedTime(), getOptions(), getStatus()
        };
    }
}
