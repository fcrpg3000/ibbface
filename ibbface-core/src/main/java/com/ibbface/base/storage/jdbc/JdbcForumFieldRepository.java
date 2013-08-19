/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) JdbcForumFieldRepository.java 2013-08-04 23:15
 */

package com.ibbface.base.storage.jdbc;

import com.google.common.collect.Maps;
import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import com.ibbface.base.storage.support.QueryDslJdbcSupport;
import com.ibbface.domain.generated.forum.QForumField;
import com.ibbface.domain.model.forum.ForumField;
import com.ibbface.repository.forum.ForumFieldRepository;
import com.ibbface.util.turple.Pair;
import com.mysema.query.Tuple;
import com.mysema.query.sql.RelationalPath;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.MappingProjection;
import com.mysema.query.types.Path;
import com.mysema.query.types.Predicate;
import org.springframework.data.jdbc.query.SqlUpdateCallback;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * {@link ForumFieldRepository} interface default implementation.
 *
 * @author Fuchun
 * @since 1.0
 */
@Repository("forumFieldRepository")
public class JdbcForumFieldRepository extends QueryDslJdbcSupport<ForumField, Long>
        implements ForumFieldRepository {

    private final QForumField qForumField = QForumField.ForumField;

    /**
     * Returns {@code true} if the entity's ID is auto_increment,
     * otherwise {@code false}.
     */
    @Override
    protected boolean isAutoIncrement() {
        return true;
    }

    /**
     * Returns the T's {@code RelationalPath} object.
     */
    @Override
    protected RelationalPath<?> getQEntity() {
        return QForumField.ForumField;
    }

    /**
     * Returns primary key predicate used given ids.
     *
     * @param ids the given ids (primary keys)
     */
    @Override
    protected <PK extends Serializable> Predicate primaryKeyPredicate(@Nonnull PK... ids) {
        if (ids.length == 0) {
            throw new IllegalArgumentException(
                    "The given primary key `ids` must not be empty.");
        }
        if (ids.length == 1) {
            return qForumField.id.eq((Long) ids[0]);
        }
        Set<Long> idSet = Sets.newHashSet();
        for (Serializable serial : ids) {
            idSet.add((Long) serial);
        }
        return qForumField.id.in(idSet);
    }

    /**
     * Returns sorted values of the specified entity.
     *
     * @param entity entity object.
     */
    @Override
    protected Object[] getAllValues(@Nonnull ForumField entity) {
        return entity.toArray();
    }

    /**
     * Returns sorted values unless primary value of the specified entity.
     *
     * @param entity entity object.
     */
    @Override
    protected Object[] getValuesNoId(@Nonnull ForumField entity) {
        Object[] allValues = getAllValues(entity);
        return Arrays.copyOfRange(allValues, 1, allValues.length);
    }

    /**
     * Returns all entity's query dsl path.
     */
    @Override
    protected Path<?>[] getAllColumns() {
        return qForumField.sequenceColumns;
    }

    /**
     * Returns all entity's query dsl path except primary key.
     */
    @Override
    protected Path<?>[] getColumnsNoId() {
        return qForumField.columnsWithoutId;
    }

    /**
     * Returns column path of the specified prop name.
     *
     * @param prop the column mapping prop name.
     */
    @Override
    protected Path<?> getColumn(String prop) {
        return qForumField.propPathMap.get(prop);
    }

    /**
     * Query ForumField#sortOrder value of the specified id.
     */
    @Override
    public Integer getSortOrder(Long id) {
        return queryDslJdbcTemplate.queryForObject(
                getQuery().where(qForumField.id.eq(id)), qForumField.sortOrder);
    }

    /**
     * Query max {@link com.ibbface.domain.model.forum.ForumField#getSortOrder() sortOrder} value of the specified {@link com.ibbface.domain.model.forum.Forum forum} id.
     */
    @Override
    public Integer getMaxSortOrder(Integer forumId) {
        return queryDslJdbcTemplate.queryForObject(
                getQuery().where(qForumField.forumId.eq(forumId)), qForumField.sortOrder);
    }

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
    @Override
    public int updateSortOrder(final Long id, final Integer newSortOrder) {
        return (int) queryDslJdbcTemplate.update(qForumField,
                new SqlUpdateCallback() {
                    @Override
                    public long doInSqlUpdateClause(SQLUpdateClause update) {
                        return update.where(qForumField.id.eq(id))
                                .set(qForumField.sortOrder, newSortOrder)
                                .execute();
                    }
                });
    }

    /**
     * Query all {@link com.ibbface.domain.model.forum.ForumField}s of the specified {@code forumId}
     * order by {@link com.ibbface.domain.model.forum.ForumField#getSortOrder() sortOrder} ASC.
     *
     * @param forumId the forum id.
     */
    @Override
    public List<ForumField> findByForumId(Integer forumId) {
        SQLQuery sqlQuery = getQuery().where(qForumField.forumId.eq(forumId))
                .orderBy(qForumField.sortOrder.asc());
        return queryForList(sqlQuery);
    }

    /**
     * Query all forum field id and sort mapping order by {@code sortOrder}
     * asc of the specified forum id.
     * <p/>
     * {@code key} is forum field {@link com.ibbface.domain.model.forum.ForumField#getId() id}, {@code value} is
     * {@link com.ibbface.domain.model.forum.ForumField#getSortOrder() sortOrder}.
     */
    @Override
    public Map<Long, Integer> findIdSortMapping(Integer forumId) {
        SQLQuery sqlQuery = getQuery().where(qForumField.forumId.eq(forumId))
                .orderBy(qForumField.sortOrder.asc());
        List<Pair<Long, Integer>> pairList = queryDslJdbcTemplate.query(
                sqlQuery, new MappingProjection<Pair<Long, Integer>>(Pair.class,
                qForumField.id, qForumField.sortOrder) {
            @Override
            protected Pair<Long, Integer> map(Tuple row) {
                return Pair.of(row.get(qForumField.id), row.get(qForumField.sortOrder));
            }
        });
        Map<Long, Integer> result = Maps.newLinkedHashMap();
        for (Pair<Long, Integer> pair : pairList) {
            result.put(pair.getLeft(), pair.getRight());
        }
        return result;
    }

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
    @Override
    public int subtractSortOrder(final Integer forumId, final Range<Integer> range) {
        return (int) queryDslJdbcTemplate.update(getQEntity(),
                new SqlUpdateCallback() {
                    @Override
                    public long doInSqlUpdateClause(SQLUpdateClause update) {
                        update.where(qForumField.forumId.eq(forumId));
                        if (range.lowerEndpoint().equals(range.upperEndpoint())) {
                            update.where(qForumField.sortOrder.eq(range.lowerEndpoint()));
                        } else {
                            update.where(qForumField.sortOrder.between(range.lowerEndpoint(),
                                    range.upperEndpoint()));
                        }
                        return update.set(qForumField.sortOrder, qForumField.sortOrder.subtract(1))
                                .execute();
                    }
                });
    }

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
    @Override
    public int addSortOrder(final Integer forumId, final Range<Integer> range) {
        return (int) queryDslJdbcTemplate.update(getQEntity(),
                new SqlUpdateCallback() {
                    @Override
                    public long doInSqlUpdateClause(SQLUpdateClause update) {
                        update.where(qForumField.forumId.eq(forumId));
                        if (range.lowerEndpoint().equals(range.upperEndpoint())) {
                            update.where(qForumField.sortOrder.eq(range.lowerEndpoint()));
                        } else {
                            update.where(qForumField.sortOrder.between(range.lowerEndpoint(),
                                    range.upperEndpoint()));
                        }
                        return update.set(qForumField.sortOrder, qForumField.sortOrder.add(1))
                                .execute();
                    }
                });
    }
}
