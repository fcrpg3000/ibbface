/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) ForumFieldRepository.java 2013-08-04 23:03
 */

package com.ibbface.repository.forum;

import com.google.common.collect.Range;
import com.ibbface.domain.model.forum.ForumField;
import com.ibbface.repository.BaseRepository;

import java.util.List;
import java.util.Map;

/**
 * {@link com.ibbface.domain.model.forum.ForumField} entity repository interface.
 *
 * @author Fuchun
 * @since 1.0
 */
public interface ForumFieldRepository extends BaseRepository<ForumField, Long> {

    /**
     * Query ForumField#sortOrder value of the specified id.
     */
    public Integer getSortOrder(Long id);

    /**
     * Query max {@link ForumField#getSortOrder() sortOrder} value of the specified {@link com.ibbface.domain.model.forum.Forum forum} id.
     */
    public Integer getMaxSortOrder(Integer forumId);

    /**
     * Updates sortOrder value of the specified id.
     * <p/>
     * NOTE: This method just updates sortOrder value of the specified id, but not
     * resort to reset sortOrder value of others.
     *
     * @param id           the forum field id.
     * @param newSortOrder the new sortOrder value.
     * @return the affected row.
     */
    public int updateSortOrder(Long id, Integer newSortOrder);

    /**
     * Query all {@link ForumField}s of the specified {@code forumId}
     * order by {@link ForumField#getSortOrder() sortOrder} ASC.
     *
     * @param forumId the forum id.
     */
    public List<ForumField> findByForumId(Integer forumId);

    /**
     * Query all forum field id and sort mapping order by {@code sortOrder}
     * asc of the specified forum id.
     * <p/>
     * {@code key} is forum field {@link ForumField#getId() id}, {@code value} is
     * {@link ForumField#getSortOrder() sortOrder}.
     */
    public Map<Long, Integer> findIdSortMapping(Integer forumId);

    /**
     * Updates sortOrder subtract 1 of the specified {@code forumId} and sortOrder range.
     * <pre>Examples:
     * range = [3, 3]
     * forumField(sortOrder=3), sortOrder -= 1 =&gt; forumField(sortOrder=2)
     * Returns 1
     * <p/>
     * range = [4, 7]
     * forumField(sortOrder[4,7]), sortOrder -= 1 =&gt; forumField(sortOrder[3,6])
     * Returns 4
     * </pre>
     *
     * @param forumId the forum id.
     * @param range   the range of sortOrder.
     * @return the affected row.
     */
    public int subtractSortOrder(Integer forumId, Range<Integer> range);

    /**
     * Updates sortOrder add 1 of the specified {@code forumId} and sortOrder range.
     * <pre>Examples:
     * range = [3, 3]
     * forumField(sortOrder=3), sortOrder += 1 =&gt; forumField(sortOrder=4)
     * Returns 1
     * <p/>
     * range = [4, 7]
     * forumField(sortOrder[4,7]), sortOrder += 1 =&gt; forumField(sortOrder[5,8])
     * Returns 4
     * </pre>
     *
     * @param forumId the forum id.
     * @param range   the range of sortOrder.
     * @return the Effect row.
     */
    public int addSortOrder(Integer forumId, Range<Integer> range);
}
