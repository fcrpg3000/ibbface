/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) JdbcUserOnlineRepository.java 2013-08-06 00:56
 */

package com.ibbface.base.storage.jdbc;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.ibbface.base.storage.support.QueryDslJdbcSupport;
import com.ibbface.domain.generated.user.QUserOnline;
import com.ibbface.domain.model.user.UserOnline;
import com.ibbface.repository.user.UserOnlineRepository;
import com.mysema.query.sql.RelationalPath;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.types.Path;
import com.mysema.query.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * {@link UserOnlineRepository} interface default implementation.
 *
 * @author Fuchun
 * @since 1.0
 */
@Repository("userOnlineRepository")
public class JdbcUserOnlineRepository extends QueryDslJdbcSupport<UserOnline, Long>
        implements UserOnlineRepository {

    private final QUserOnline qUserOnline = QUserOnline.qUserOnline;

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
        return qUserOnline;
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
            return qUserOnline.userId.eq((Long) ids[0]);
        }
        Set<Long> idSet = Sets.newHashSet();
        for (Serializable serial : ids) {
            idSet.add((Long) serial);
        }
        return qUserOnline.userId.in(idSet);
    }

    /**
     * Returns sorted values of the specified entity.
     *
     * @param entity entity object.
     */
    @Override
    protected Object[] getAllValues(@Nonnull UserOnline entity) {
        return entity.toArray();
    }

    /**
     * Returns sorted values unless primary value of the specified entity.
     *
     * @param entity entity object.
     */
    @Override
    protected Object[] getValuesNoId(@Nonnull UserOnline entity) {
        Object[] allValues = getAllValues(entity);
        return Arrays.copyOfRange(allValues, 1, allValues.length);
    }

    /**
     * Returns all entity's query dsl path.
     */
    @Override
    protected Path<?>[] getAllColumns() {
        return qUserOnline.sequenceColumns;
    }

    /**
     * Returns all entity's query dsl path except primary key.
     */
    @Override
    protected Path<?>[] getColumnsNoId() {
        return qUserOnline.columnsWithoutId;
    }

    /**
     * Returns column path of the specified prop name.
     *
     * @param prop the column mapping prop name.
     */
    @Override
    protected Path<?> getColumn(String prop) {
        return qUserOnline.propPathMap.get(prop);
    }

    @Override
    public UserOnline findBySessionId(String sessionId) {
        return queryForObject(getQuery().where(qUserOnline.sessionId.eq(sessionId)));
    }

    @Override
    public UserOnline findByAccessToken(String accessToken) {
        return queryForObject(getQuery().where(qUserOnline.accessToken.eq(accessToken)));
    }

    @Override
    public List<UserOnline> findTop(Sort sort, int size) {
        SQLQuery sqlQuery = getQuery();
        if (sort == null) {
            sort = new Sort(Sort.Direction.DESC, UserOnline.PROP_TOTAL_ONLINE_TIME,
                    UserOnline.PROP_THAT_ONLINE_TIME);
        }
        setSortOrder(sqlQuery, sort);
        sqlQuery.limit(size);
        return queryForList(sqlQuery);
    }

    @Override
    public List<UserOnline> findOrderByThatOnlineTimeDesc(int size) {
        Sort sort = new Sort(Sort.Direction.DESC, UserOnline.PROP_THAT_ONLINE_TIME);
        return findTop(sort, size);
    }

    @Override
    public List<UserOnline> findOrderByTotalOnlineTimeDesc(int size) {
        Sort sort = new Sort(Sort.Direction.DESC, UserOnline.PROP_TOTAL_ONLINE_TIME,
                UserOnline.PROP_THAT_ONLINE_TIME);
        return findTop(sort, size);
    }

    @Override
    public Page<UserOnline> findOnline(long offset, Pageable pageable) {
        final long timeNow = System.currentTimeMillis();
        SQLQuery sqlQuery = getQuery()
                .where(qUserOnline.lastAccessedTime.between(timeNow - offset, timeNow));
        long total = queryDslJdbcTemplate.count(sqlQuery);
        List<UserOnline> content;
        if (total == 0) {
            content = ImmutableList.of();
            return new PageImpl<UserOnline>(content, pageable, total);
        }
        setSortOrder(sqlQuery, pageable.getSort());
        sqlQuery.offset(pageable.getOffset())
                .limit((pageable.getPageNumber() + 1) * pageable.getPageSize());
        content = queryForList(sqlQuery);
        return new PageImpl<UserOnline>(content, pageable, total);
    }

}
