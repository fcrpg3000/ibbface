/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) JdbcUserRepository.java 2013-07-29 13:06
 */

package com.ibbface.base.storage.jdbc;

import com.ibbface.base.storage.support.QueryDslJdbcSupport;
import com.ibbface.domain.generated.user.QUser;
import com.ibbface.domain.model.user.User;
import com.ibbface.repository.user.UserRepository;
import com.mysema.query.sql.RelationalPath;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.types.Path;
import com.mysema.query.types.expr.SimpleExpression;
import org.springframework.stereotype.Repository;

/**
 * @author Fuchun
 * @since 1.0
 */
@Repository("userRepository")
public class JdbcUserRepository extends QueryDslJdbcSupport<User, Long>
        implements UserRepository {

    private final QUser qUser = QUser.User;

    /**
     * Returns the T's {@code RelationalPath} object.
     */
    @Override
    protected RelationalPath<?> getQEntity() {
        return qUser;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected SimpleExpression<Long> getPkPath() {
        return qUser.userId;
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
     * Returns column path newError the specified prop name.
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
