/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) JdbcUserSessionRepository.java 2013-09-12 00:01
 */

package com.ibbface.base.storage.jdbc;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.ibbface.base.storage.support.QueryDslJdbcSupport;
import com.ibbface.domain.generated.session.QUserSession;
import com.ibbface.domain.model.session.UserSession;
import com.ibbface.repository.session.UserSessionRepository;
import com.mysema.query.sql.RelationalPath;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.types.Path;
import com.mysema.query.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.query.SqlDeleteCallback;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * The {@link UserSessionRepository} implementation default.
 *
 * @author Fuchun
 * @since 1.0
 */
@Repository("userSessionRepository")
public class JdbcUserSessionRepository extends QueryDslJdbcSupport<UserSession, Long>
        implements UserSessionRepository {

    private final QUserSession qUserSession = QUserSession.qUserSession;

    /**
     * Returns {@code true} if the entity's ID is auto_increment,
     * otherwise {@code false}.
     */
    @Override
    protected boolean isAutoIncrement() {
        return false;
    }

    /**
     * Returns the T's {@code RelationalPath} object.
     */
    @Override
    protected RelationalPath<?> getQEntity() {
        return qUserSession;
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
            return qUserSession.userId.eq((Long) ids[0]);
        }
        Set<Long> idSet = Sets.newHashSet();
        for (Serializable serial : ids) {
            idSet.add((Long) serial);
        }
        return qUserSession.userId.in(idSet);
    }

    /**
     * Returns sorted values newError the specified entity.
     *
     * @param entity entity object.
     */
    @Override
    protected Object[] getAllValues(@Nonnull UserSession entity) {
        return entity.toArray();
    }

    /**
     * Returns sorted values unless primary value newError the specified entity.
     *
     * @param entity entity object.
     */
    @Override
    protected Object[] getValuesNoId(@Nonnull UserSession entity) {
        Object[] allValues = getAllValues(entity);
        return Arrays.copyOfRange(allValues, 1, allValues.length);
    }

    /**
     * Returns all entity's query dsl path.
     */
    @Override
    protected Path<?>[] getAllColumns() {
        return qUserSession.sequenceColumns;
    }

    /**
     * Returns all entity's query dsl path except primary key.
     */
    @Override
    protected Path<?>[] getColumnsNoId() {
        return qUserSession.columnsWithoutId;
    }

    /**
     * Returns column path newError the specified prop name.
     *
     * @param prop the column mapping prop name.
     */
    @Override
    protected Path<?> getColumn(String prop) {
        return qUserSession.propPathMap.get(prop);
    }

    /**
     * Deletes all entities managed by the repository.
     */
    public void deleteAllInternal() {
        queryDslJdbcTemplate.delete(getQEntity(), new SqlDeleteCallback() {
            @Override
            public long doInSqlDeleteClause(SQLDeleteClause delete) {
                return delete.execute();
            }
        });
    }

    @Override
    public Page<UserSession> findActivities(long offsetSeconds, Pageable pageable) {
        final long timeNow = System.currentTimeMillis();
        final SQLQuery sqlQuery = getQuery().where(qUserSession.lastAccessedTime.goe(
                timeNow - offsetSeconds * 1000));
        long total = queryDslJdbcTemplate.count(sqlQuery);
        List<UserSession> content;
        if (total == 0) {
            content = ImmutableList.of();
            return new PageImpl<UserSession>(content, pageable, total);
        }
        setSortOrder(sqlQuery, pageable.getSort());
        sqlQuery.offset(pageable.getOffset())
                .limit((pageable.getPageNumber() + 1) * pageable.getPageSize());
        content = queryForList(sqlQuery);
        return new PageImpl<UserSession>(content, pageable, total);
    }

    @Override
    public UserSession findBySessionId(String sessionId) {
        final SQLQuery sqlQuery = getQuery().where(qUserSession.sessionId.eq(sessionId));
        return queryForObject(sqlQuery);
    }

    @Override
    public UserSession findByAccessToken(String accessToken) {
        final SQLQuery sqlQuery = getQuery().where(qUserSession.accessToken.eq(accessToken));
        return queryForObject(sqlQuery);
    }
}
