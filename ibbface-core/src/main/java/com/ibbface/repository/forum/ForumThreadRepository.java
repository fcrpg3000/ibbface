/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) ForumThreadRepository.java 2013-08-05 00:15
 */

package com.ibbface.repository.forum;

import com.google.common.collect.Range;
import com.ibbface.domain.model.forum.ForumThread;
import com.ibbface.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.annotation.Nonnull;
import java.util.Date;
import java.util.List;

/**
 * {@link com.ibbface.domain.model.forum.ForumThread} entity repository interface.
 *
 * @author Fuchun
 * @since 1.0
 */
public interface ForumThreadRepository extends BaseRepository<ForumThread, Long> {

    /**
     * Query {@link ForumThread} newError the specified {@code idAlias} value.
     *
     * @param idAlias the {@code idAlias} value.
     * @return Returns {@code ForumThread} newError the specified {@code idAlias} value,
     *         or {@code null} if not found.
     */
    public ForumThread findByIdAlias(String idAlias);

    public Page<ForumThread> findByAll(Integer forumId, Long userId, Short[] status,
                                       Boolean isTop, Boolean isGood, @Nonnull Pageable pageable);

    /**
     * Query page {@link ForumThread}s newError the specified {@code userId} and {@code pageable}.
     *
     * @param userId the user id.
     * @param pageable the page request.
     * @return Returns page {@link ForumThread}s.
     */
    public Page<ForumThread> findByUserId(Long userId, Pageable pageable);

    public Page<ForumThread> findGoodByUserId(Long userId, Pageable pageable);

    public Page<ForumThread> findByForumIdAndUserId(Integer forumId, Long userId, Pageable pageable);

    public Page<ForumThread> findByForumId(Integer forumId, Pageable pageable);

    public Page<ForumThread> findGoodByForumId(Integer forumId, Pageable pageable);

    public Page<ForumThread> findByStatus(Short status, Pageable pageable);

    public Page<ForumThread> findByCreatedTime(Date createdTime, Short[] status, Pageable pageable);

    public Page<ForumThread> findByCreatedTimeRange(Range<Date> range, Short[] status, Pageable pageable);

    public List<ForumThread> findOrderByPostTimeDescLimit(Integer forumId, int size);

    /**
     * Set {@link ForumThread}'s {@code replyCount} to the specified value.
     *
     * @param id         the {@link ForumThread} id.
     * @param replyCount the reply count value.
     * @throws IllegalArgumentException if {@code id == null || id <= 0}.
     * @throws com.ibbface.domain.exception.EntityNotFoundException
     *                                  if the ForumThread not found.
     */
    public void setReplyCount(Long id, Integer replyCount);

    /**
     * Set {@link ForumThread}'s {@code viewCount} to the specified value.
     *
     * @param id        the {@link ForumThread} id.
     * @param viewCount the view count value.
     * @throws IllegalArgumentException if {@code id == null || id <= 0}.
     * @throws com.ibbface.domain.exception.EntityNotFoundException
     *                                  if the ForumThread not found.
     */
    public void setViewCount(Long id, Integer viewCount);

    /**
     * Set {@link ForumThread}'s {@code isTop} or {@code isGood} to the specified value
     * when {@code isTop != null} or {@code isGood != null}.
     * <pre>
     * This method just like:
     * ForumThread thread = ...;
     * if (isTop != null) {
     *     thread.setIsTop(isTop);
     * }
     * if (isGood != null) {
     *     thread.setIsGood(isGood);
     * }
     * </pre>
     *
     * @param id     the {@link ForumThread} id.
     * @param isTop  the {@code isTop} value (maybe null).
     * @param isGood the {@code isGood} value (maybe null).
     * @throws IllegalArgumentException if {@code id == null || id <= 0}.
     * @throws com.ibbface.domain.exception.EntityNotFoundException
     *                                  if the ForumThread not found.
     */
    public void setTopGood(Long id, Boolean isTop, Boolean isGood);

    /**
     * This method is simple {@link #setTopGood(Long, Boolean, Boolean)} invocation.
     * equals {@code setTopGood(id, isTop, null)}.
     *
     * @param id    the {@link ForumThread} id.
     * @param isTop the {@code isTop} value.
     * @throws IllegalArgumentException if {@code id == null || id <= 0}.
     * @throws com.ibbface.domain.exception.EntityNotFoundException
     *                                  if the ForumThread not found.
     */
    public void setTop(Long id, boolean isTop);

    /**
     * This method is simple {@link #setTopGood(Long, Boolean, Boolean)} invocation.
     * equals {@code setTopGood(id, null, isGood)}.
     *
     * @param id     the {@link ForumThread} id.
     * @param isGood the {@code isGood} value.
     * @throws IllegalArgumentException if {@code id == null || id <= 0}.
     * @throws com.ibbface.domain.exception.EntityNotFoundException
     *                                  if the ForumThread not found.
     */
    public void setGood(Long id, boolean isGood);
}
