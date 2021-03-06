/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) JdbcUserDigitalRepository.java 2013-08-01 22:12
 */

package com.ibbface.base.storage.jdbc;

import com.ibbface.base.storage.support.QueryDslJdbcSupport;
import com.ibbface.domain.generated.user.QUserDigital;
import com.ibbface.domain.model.user.UserDigital;
import com.ibbface.repository.user.UserDigitalRepository;
import com.mysema.query.sql.RelationalPath;
import com.mysema.query.types.Path;
import com.mysema.query.types.expr.SimpleExpression;
import org.springframework.stereotype.Repository;

import java.util.Arrays;

/**
 * {@link UserDigitalRepository} default implementation used query dsl jdbc.
 *
 * @author Fuchun
 * @since 1.0
 */
@Repository("userDigitalRepository")
public class JdbcUserDigitalRepository extends QueryDslJdbcSupport<UserDigital, Long>
        implements UserDigitalRepository {

    private final QUserDigital qUserDigital = QUserDigital.UserDigital;

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
        return QUserDigital.UserDigital;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected SimpleExpression<Long> getPkPath() {
        return qUserDigital.userId;
    }

    /**
     * Returns all entity's query dsl path.
     */
    @Override
    protected Path<?>[] getAllColumns() {
        return qUserDigital.allColumns;
    }

    /**
     * Returns all entity's query dsl path except primary key.
     */
    @Override
    protected Path<?>[] getColumnsNoId() {
        final Path<?>[] allColumns = getAllColumns();
        return Arrays.copyOfRange(allColumns, 1, allColumns.length);
    }

    /**
     * Returns column path newError the specified prop name.
     *
     * @param prop the column mapping prop name.
     */
    @Override
    protected Path<?> getColumn(String prop) {
        return qUserDigital.propPathMap.get(prop);
    }
}
