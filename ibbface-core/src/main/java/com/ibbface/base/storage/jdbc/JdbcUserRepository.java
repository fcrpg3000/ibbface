/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) JdbcUserRepository.java 2013-07-29 13:06
 */

package com.ibbface.base.storage.jdbc;

import com.google.common.collect.Sets;
import com.ibbface.base.storage.support.QueryDslJdbcSupport;
import com.ibbface.domain.generated.user.QUser;
import com.ibbface.domain.model.user.User;
import com.ibbface.repository.user.UserRepository;
import com.mysema.query.sql.RelationalPath;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.types.Path;
import com.mysema.query.types.Predicate;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Set;

/**
 * @author Fuchun
 * @since 1.0
 */
@Repository("userRepository")
public class JdbcUserRepository extends QueryDslJdbcSupport<User, Long>
        implements UserRepository {

    private final QUser qUser = QUser.User;

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
        return qUser;
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
            return qUser.userId.eq((Long) ids[0]);
        }
        Set<Long> idSet = Sets.newHashSet();
        for (Serializable serial : ids) {
            idSet.add((Long) serial);
        }
        return qUser.userId.in(idSet);
    }

    /**
     * Returns sorted values of the specified entity.
     *
     * @param entity entity object.
     */
    @Override
    protected Object[] getAllValues(@Nonnull User entity) {
        return entity.toArray();
    }

    /**
     * Returns sorted values unless primary value of the specified entity.
     *
     * @param entity entity object.
     */
    @Override
    protected Object[] getValuesNoId(@Nonnull User entity) {
        Object[] allValues = getAllValues(entity);
        return Arrays.copyOfRange(allValues, 1, allValues.length);
    }

    /**
     * Returns all entity's query dsl path.
     */
    @Override
    protected Path<?>[] getAllColumns() {
        return qUser.sequenceColumns;
    }

    /**
     * Returns all entity's query dsl path except primary key.
     */
    @Override
    protected Path<?>[] getColumnsNoId() {
        return qUser.columnsWithoutId;
    }

    /**
     * Returns column path of the specified prop name.
     *
     * @param prop the column mapping prop name.
     */
    @Override
    protected Path<?> getColumn(String prop) {
        return qUser.propPathMap.get(prop);
    }

    @Override
    public User findByEmail(String email) {
        final SQLQuery sqlQuery = getQuery().where(qUser.email.eq(email));
        return queryForObject(sqlQuery);
    }

    @Override
    public User findByUserName(String userName) {
        final SQLQuery sqlQuery = getQuery().where(qUser.userName.eq(userName));
        return queryForObject(sqlQuery);
    }

    @Override
    public User findByMobileNo(String mobileNo) {
        final SQLQuery sqlQuery = getQuery().where(qUser.mobileNo.eq(mobileNo));
        return queryForObject(sqlQuery);
    }
}
