/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) JdbcForumRepository.java 2013-08-03 12:04
 */

package com.ibbface.base.storage.jdbc;

import com.google.common.collect.Maps;
import com.google.common.collect.Range;
import com.ibbface.base.storage.support.QueryDslJdbcSupport;
import com.ibbface.domain.generated.forum.QForum;
import com.ibbface.domain.model.forum.Forum;
import com.ibbface.repository.forum.ForumRepository;
import com.ibbface.util.turple.Pair;
import com.mysema.query.Tuple;
import com.mysema.query.sql.RelationalPath;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.MappingProjection;
import com.mysema.query.types.Path;
import com.mysema.query.types.expr.SimpleExpression;
import org.springframework.data.domain.Sort;
import org.springframework.data.jdbc.query.SqlUpdateCallback;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * {@link ForumRepository} interface default implementation.
 *
 * @author Fuchun
 * @since 1.0
 */
@Repository("forumRepository")
public class JdbcForumRepository extends QueryDslJdbcSupport<Forum, Integer>
        implements ForumRepository {
    private final QForum qForum = QForum.Forum;

    /**
     * Returns the T's {@code RelationalPath} object.
     */
    @Override
    protected RelationalPath<?> getQEntity() {
        return QForum.Forum;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected SimpleExpression<Integer> getPkPath() {
        return qForum.id;
    }

    /**
     * Returns all entity's query dsl path.
     */
    @Override
    protected Path<?>[] getAllColumns() {
        return qForum.sequenceColumns;
    }

    /**
     * Returns all entity's query dsl path except primary key.
     */
    @Override
    protected Path<?>[] getColumnsNoId() {
        return qForum.columnsWithoutId;
    }

    /**
     * Returns column path newError the specified prop name.
     *
     * @param prop the column mapping prop name.
     */
    @Override
    protected Path<?> getColumn(String prop) {
        return qForum.propPathMap.get(prop);
    }

    /**
     * Update forum's status value newError the specified id.
     *
     * @param forumId the forum's id.
     * @param status  the forum's status.
     * @return Returns 1 if update success, otherwise 0.
     */
    @Override
    public int updateStatus(final Integer forumId, final Short status) {
        return (int) queryDslJdbcTemplate.update(qForum, new SqlUpdateCallback() {
            @Override
            public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
                return sqlUpdateClause.where(qForum.id.eq(forumId))
                        .set(qForum.status, status).execute();
            }
        });
    }

    /**
     * Query {@link Forum} newError the specified {@link Forum#getIdAlias() idAlias}.
     *
     * @param idAlias the forum's idAlias.
     * @return the forum newError the specified idAlias, or {@code null} if forum not found.
     */
    @Override
    public Forum findByIdAlias(String idAlias) {
        SQLQuery sqlQuery = getQuery().where(qForum.idAlias.eq(idAlias));
        return queryForObject(sqlQuery);
    }

    /**
     * Query all {@link Forum}s order by {@link Forum#getSortOrder() sortOrder} base on {@code direction}.
     *
     * @param direction {@code Sort.Direction#ASC} or {@code Sort.Direction#DESC}.
     */
    @Override
    public List<Forum> findOrderBySortOrder(Sort.Direction direction) {
        SQLQuery sqlQuery = getQuery().orderBy(direction == Sort.Direction.ASC ?
                qForum.sortOrder.asc() : qForum.sortOrder.desc());
        return queryForList(sqlQuery);
    }

    /**
     * Query all forum's id and sort mapping order by {@code sortOrder} asc.
     * <p/>
     * {@code key} is forum's {@link Forum#getId() id}, {@code value} is {@link Forum#getSortOrder() sortOrder}
     */
    @Override
    public Map<Integer, Integer> findIdSortMapping() {
        SQLQuery sqlQuery = getQuery().orderBy(qForum.sortOrder.asc());
        List<Pair<Integer, Integer>> pairList = queryDslJdbcTemplate.query(
                sqlQuery, new MappingProjection<Pair<Integer, Integer>>(Pair.class,
                qForum.id, qForum.sortOrder) {

            @Override
            protected Pair<Integer, Integer> map(Tuple row) {
                return Pair.of(row.get(qForum.id), row.get(qForum.sortOrder));
            }
        });
        Map<Integer, Integer> result = Maps.newLinkedHashMap();
        for (Pair<Integer, Integer> pair : pairList) {
            result.put(pair.getLeft(), pair.getRight());
        }
        return result;
    }

    /**
     * Query Forum#sortOrder value newError the specified id.
     */
    @Override
    public Integer getSortOrder(Integer forumId) {
        return queryDslJdbcTemplate.queryForObject(
                getQuery().where(qForum.id.eq(forumId)),
                qForum.sortOrder);
    }

    /**
     * Update sortOrder value newError the specified id.
     */
    @Override
    public int updateSortOrder(final Integer forumId, final Integer sortOrder) {
        return (int) queryDslJdbcTemplate.update(getQEntity(),
                new SqlUpdateCallback() {
                    @Override
                    public long doInSqlUpdateClause(SQLUpdateClause update) {
                        return update.where(qForum.id.eq(forumId))
                                .set(qForum.sortOrder, sortOrder)
                                .execute();
                    }
                });
    }

    @Override
    public int subtractSortOrder(final Range<Integer> range) {
        return (int) queryDslJdbcTemplate.update(getQEntity(),
                new SqlUpdateCallback() {
                    @Override
                    public long doInSqlUpdateClause(SQLUpdateClause update) {
                        if (range.lowerEndpoint().equals(range.upperEndpoint())) {
                            update.where(qForum.sortOrder.eq(range.lowerEndpoint()));
                        } else {
                            update.where(qForum.sortOrder.between(range.lowerEndpoint(),
                                    range.upperEndpoint()));
                        }
                        return update.set(qForum.sortOrder, qForum.sortOrder.subtract(1))
                                .execute();
                    }
                });
    }

    @Override
    public int addSortOrder(final Range<Integer> range) {
        return (int) queryDslJdbcTemplate.update(getQEntity(),
                new SqlUpdateCallback() {
                    @Override
                    public long doInSqlUpdateClause(SQLUpdateClause update) {
                        if (range.lowerEndpoint().equals(range.upperEndpoint())) {
                            update.where(qForum.sortOrder.eq(range.lowerEndpoint()));
                        } else {
                            update.where(qForum.sortOrder.between(range.lowerEndpoint(),
                                    range.upperEndpoint()));
                        }
                        return update.set(qForum.sortOrder, qForum.sortOrder.add(1))
                                .execute();
                    }
                });
    }

    /**
     * Query max {@link com.ibbface.domain.model.forum.Forum#getSortOrder sortOrder} value.
     */
    @Override
    public Integer getMaxSortOrder() {
        return queryDslJdbcTemplate.queryForObject(getQuery(), qForum.sortOrder.max());
    }
}
