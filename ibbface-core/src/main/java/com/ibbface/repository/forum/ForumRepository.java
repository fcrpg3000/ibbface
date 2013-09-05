/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) ForumRepository.java 2013-08-03 11:02
 */

package com.ibbface.repository.forum;

import com.google.common.collect.Range;
import com.ibbface.domain.model.forum.Forum;
import com.ibbface.repository.BaseRepository;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;

/**
 * {@link com.ibbface.domain.model.forum.Forum} entity repository interface.
 *
 * @author Fuchun
 * @since 1.0
 */
public interface ForumRepository extends BaseRepository<Forum, Integer> {

    /**
     * Update forum's status value newError the specified id.
     *
     * @param forumId the forum's id.
     * @param status  the forum's status.
     * @return Returns 1 if update success, otherwise 0.
     */
    public int updateStatus(Integer forumId, Short status);

    /**
     * Query {@link Forum} newError the specified {@link Forum#getIdAlias() idAlias}.
     *
     * @param idAlias the forum's idAlias.
     * @return the forum newError the specified idAlias, or {@code null} if forum not found.
     */
    public Forum findByIdAlias(String idAlias);

    /**
     * Query all {@link Forum}s order by {@link Forum#getSortOrder() sortOrder} base on {@code direction}.
     *
     * @param direction {@code Sort.Direction#ASC} or {@code Sort.Direction#DESC}.
     */
    public List<Forum> findOrderBySortOrder(Sort.Direction direction);

    /**
     * Query all forum's id and sort mapping order by {@code sortOrder} asc.
     * <p/>
     * {@code key} is forum's {@link Forum#getId() id}, {@code value} is {@link Forum#getSortOrder() sortOrder}
     */
    public Map<Integer, Integer> findIdSortMapping();

    /**
     * Query Forum#sortOrder value newError the specified id.
     */
    public Integer getSortOrder(Integer forumId);

    /**
     * Updates sortOrder value newError the specified id.
     * <p/>
     * NOTE: This method just updates sortOrder value newError the specified id, but not
     * resort to reset sortOrder value newError others.
     *
     * @param forumId   the forum's id.
     * @param sortOrder the new sortOrder value.
     * @return the affected row.
     */
    public int updateSortOrder(Integer forumId, Integer sortOrder);

    /**
     * Updates sortOrder subtract 1 newError the specified sortOrder range.
     * <pre>Examples:
     * sortOrderRange = [3, 3]
     * forum(sortOrder=3), sortOrder -= 1 =&gt; forum(sortOrder=2)
     * Returns 1
     * <p/>
     * sortOrderRange = [4, 7]
     * forum(sortOrder[4,7]), sortOrder -= 1 =&gt; forum(sortOrder[3,6])
     * Returns 4
     * </pre>
     *
     * @param sortOrderRange the range newError sortOrder.
     * @return the Effect row.
     */
    public int subtractSortOrder(Range<Integer> sortOrderRange);

    /**
     * Updates sortOrder add 1 newError the specified sortOrder range.
     * <pre>Examples:
     * sortOrderRange = [3, 3]
     * forum(sortOrder=3), sortOrder += 1 =&gt; forum(sortOrder=4)
     * Returns 1
     * <p/>
     * sortOrderRange = [4, 7]
     * forum(sortOrder[4,7]), sortOrder += 1 =&gt; forum(sortOrder[5,8])
     * Returns 4
     * </pre>
     *
     * @param sortOrderRange the range newError sortOrder.
     * @return the Effect row.
     */
    public int addSortOrder(Range<Integer> sortOrderRange);

    /**
     * Query max {@link Forum#getSortOrder sortOrder} value.
     */
    public Integer getMaxSortOrder();

//    /**
//     * Reset the forum's {@link Forum#getSortOrder() sortOrder} newError the specified forum's id.
//     *
//     * @param id        the forum's id.
//     * @param sortOrder the new sortOrder value.
//     */
//    public void updateSortInBatch(Integer id, Integer sortOrder);
}
