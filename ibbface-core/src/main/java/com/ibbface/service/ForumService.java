/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) ForumService.java 2013-08-03 22:57
 */

package com.ibbface.service;

import com.ibbface.domain.model.forum.Forum;
import com.ibbface.domain.model.forum.ForumField;

/**
 * @author Fuchun
 * @since 1.0
 */
public interface ForumService {

    /**
     * Add a new {@link Forum}. The forum sortOrder is auto changed max value.
     *
     * @param forum the new Forum.
     */
    public void addForum(Forum forum);

    /**
     * Reset the forum's {@link Forum#getSortOrder() sortOrder} of the specified forum's id.
     *
     * @param forumId      the forum's id (must greater than 0).
     * @param newSortOrder the new sort order value (must greater than 0).
     * @throws IllegalArgumentException if {@code forumId == null || forumId <= 0}, or
     *                                  {@code newSortOrder == null || newSortOrder <= 0}
     * @throws com.ibbface.domain.exception.EntityNotFoundException
     *                                  if the forum not found.
     */
    public void changeSortOrder(Integer forumId, Integer newSortOrder);

    /**
     * Delete forum of the specified id in a logical way.
     *
     * @param forumId the forum's id.
     * @throws IllegalArgumentException if {@code forumId == null || forumId <= 0}.
     * @throws com.ibbface.domain.exception.EntityNotFoundException
     *                                  if {@code forumId} not found.
     */
    public void logicDeleteForum(Integer forumId);

    /**
     * Publish forum of the specified id.
     *
     * @param forumId the forum's id.
     * @throws IllegalArgumentException if {@code forumId == null || forumId <= 0}.
     * @throws com.ibbface.domain.exception.EntityNotFoundException
     *                                  if {@code forumId} not found.
     */
    public void publishForum(Integer forumId);

    /**
     * Unpublish forum of the specified id.
     *
     * @param forumId the forum's id.
     * @throws IllegalArgumentException if {@code forumId == null || forumId <= 0}.
     * @throws com.ibbface.domain.exception.EntityNotFoundException
     *                                  if {@code forumId} not found.
     */
    public void unpublishForum(Integer forumId);

    /**
     * Add {@link ForumField}s to the specified forum ({@link ForumField#getForumId()}).
     *
     * @param forumFields the new {@link ForumField}s.
     */
    public void addForumFields(Iterable<ForumField> forumFields);
}
