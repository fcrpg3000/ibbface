/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) BaseThread.java 2013-07-28 17:36
 */

package com.ibbface.domain.model.forum.base;

import com.ibbface.domain.model.forum.ForumThread;
import com.ibbface.domain.shared.BaseContent;

import java.util.Date;

/**
 * The forum's thread base entity.
 *
 * @author Fuchun
 * @since 1.0
 */
public abstract class BaseForumThread extends BaseContent<ForumThread> {
    private static final long serialVersionUID = 1L;

    public static final String PROP_FORUM_ID = "forumId";
    public static final String PROP_LAST_POST_ID = "lastPostId";
    public static final String PROP_LAST_POST_USER_ID = "lastPostUserId";
    public static final String PROP_LAST_POST_TIME = "lastPostTime";

    private Integer forumId;
    private Long lastPostId;
    private Long lastPostUserId;
    private Date lastPostTime;

    protected BaseForumThread() {
    }

    protected BaseForumThread(Long id) {
        super.setId(id);
    }

    public Integer getForumId() {
        return forumId;
    }

    public void setForumId(Integer forumId) {
        this.forumId = forumId;
    }

    public Long getLastPostId() {
        return lastPostId;
    }

    public void setLastPostId(Long lastPostId) {
        this.lastPostId = lastPostId;
    }

    public Long getLastPostUserId() {
        return lastPostUserId;
    }

    public void setLastPostUserId(Long lastPostUserId) {
        this.lastPostUserId = lastPostUserId;
    }

    public Date getLastPostTime() {
        return lastPostTime;
    }

    public void setLastPostTime(Date lastPostTime) {
        this.lastPostTime = lastPostTime;
    }

    @Override
    public String toString() {
        return toStringHelper().add(PROP_FORUM_ID, getForumId())
                .add(PROP_LAST_POST_ID, getLastPostId())
                .add(PROP_LAST_POST_USER_ID, getLastPostUserId())
                .add(PROP_LAST_POST_TIME, getLastPostTime())
                .toString();
    }
}
