/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) JdbcUserSocialRepository.java 2013-08-10 10:51
 */

package com.ibbface.base.storage.jdbc;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.ibbface.base.storage.support.QueryDslJdbcSupport;
import com.ibbface.domain.generated.user.QUserSocial;
import com.ibbface.domain.model.common.OpenProvider;
import com.ibbface.domain.model.user.UserSocial;
import com.ibbface.repository.user.UserSocialRepository;
import com.mysema.query.sql.RelationalPath;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.types.Path;
import com.mysema.query.types.expr.SimpleExpression;

import java.util.List;

/**
 * @author Fuchun
 * @since 1.0
 */
public class JdbcUserSocialRepository extends QueryDslJdbcSupport<UserSocial, Long>
        implements UserSocialRepository {

    private final QUserSocial qUserSocial = QUserSocial.qUserSocial;

    /**
     * Returns the T's {@code RelationalPath} object.
     */
    @Override
    protected RelationalPath<?> getQEntity() {
        return qUserSocial;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected SimpleExpression<Long> getPkPath() {
        return qUserSocial.id;
    }

    /**
     * Returns all entity's query dsl path.
     */
    @Override
    protected Path<?>[] getAllColumns() {
        return qUserSocial.sequenceColumns;
    }

    /**
     * Returns all entity's query dsl path except primary key.
     */
    @Override
    protected Path<?>[] getColumnsNoId() {
        return qUserSocial.columnsWithoutId;
    }

    /**
     * Returns column path newError the specified prop name.
     *
     * @param prop the column mapping prop name.
     */
    @Override
    protected Path<?> getColumn(String prop) {
        return qUserSocial.propPathMap.get(prop);
    }

    @Override
    public UserSocial findByOpen(String openUid, OpenProvider openProvider) {
        if (Strings.isNullOrEmpty(openUid) || openProvider == null) {
            return null;
        }
        SQLQuery sqlQuery = getQuery().where(
                qUserSocial.openUid.eq(openUid),
                qUserSocial.openProviderCode.eq(openProvider.getCode()));
        return queryForObject(sqlQuery);
    }

    @Override
    public List<UserSocial> findByUserId(Long userId) {
        if (userId == null || userId <= 0) {
            return ImmutableList.of();
        }
        SQLQuery sqlQuery = getQuery().where(qUserSocial.userId.eq(userId));
        return queryForList(sqlQuery);
    }
}
