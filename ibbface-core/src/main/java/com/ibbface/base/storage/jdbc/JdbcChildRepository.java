/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) JdbcChildRepository.java 2013-09-30 16:55
 */

package com.ibbface.base.storage.jdbc;

import com.ibbface.base.storage.support.QueryDslJdbcSupport;
import com.ibbface.domain.generated.user.QChild;
import com.ibbface.domain.model.user.Child;
import com.ibbface.repository.user.ChildRepository;
import com.mysema.query.sql.RelationalPath;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.types.Path;
import com.mysema.query.types.expr.SimpleExpression;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Fuchun
 * @since 1.0
 */
@Repository("childRepository")
public class JdbcChildRepository extends QueryDslJdbcSupport<Child, Long>
        implements ChildRepository {

    private final QChild qChild = QChild.qChild;

    /**
     * Query and return all {@link com.ibbface.domain.model.user.Child} of the specified user's.
     * <p/>
     * This method never returned {@code null}.
     *
     * @param userId the user id.
     * @return all {@link com.ibbface.domain.model.user.Child} of the specified user's, or empty list.
     */
    @Override
    public List<Child> findByUserId(Long userId) {
        final SQLQuery sqlQuery = getQuery().where(qChild.userId.eq(userId));
        return queryForList(sqlQuery);
    }

    /**
     * Returns the T's {@code RelationalPath} object.
     */
    @Override
    protected RelationalPath<?> getQEntity() {
        return qChild;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected SimpleExpression<Long> getPkPath() {
        return qChild.id;
    }

    /**
     * Returns all entity's query dsl path.
     */
    @Override
    protected Path<?>[] getAllColumns() {
        return qChild.sequenceColumns;
    }

    /**
     * Returns all entity's query dsl path except primary key.
     */
    @Override
    protected Path<?>[] getColumnsNoId() {
        return qChild.columnsWithoutId;
    }

    /**
     * Returns column path newError the specified prop name.
     *
     * @param prop the column mapping prop name.
     */
    @Override
    protected Path<?> getColumn(String prop) {
        return qChild.propPathMap.get(prop);
    }
}
