/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) JdbcUserDigitalRepository.java 2013-08-01 22:12
 */

package com.ibbface.base.storage.jdbc;

import com.google.common.collect.Sets;
import com.ibbface.base.storage.support.QueryDslJdbcSupport;
import com.ibbface.domain.generated.user.QUserDigital;
import com.ibbface.domain.model.user.UserDigital;
import com.ibbface.repository.user.UserDigitalRepository;
import com.mysema.query.sql.RelationalPath;
import com.mysema.query.types.Path;
import com.mysema.query.types.Predicate;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Set;

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

    /**
     * Returns primary key predicate used given ids.
     *
     * @param ids the given ids (primary keys)
     */
    @Override
    protected <PK extends Serializable> Predicate primaryKeyPredicate(
            @Nonnull PK... ids) {
        if (ids.length == 0) {
            throw new IllegalArgumentException("The given ids must not be empty!");
        }
        if (ids.length == 1) {
            return qUserDigital.userId.eq((Long) ids[0]);
        }
        Set<Long> idSet = Sets.newHashSet();
        for (PK id : ids) {
            idSet.add((Long) id);
        }
        return qUserDigital.userId.in(idSet);
    }

    /**
     * Returns sorted values newError the specified entity.
     *
     * @param entity entity object.
     */
    @Override
    protected Object[] getAllValues(@Nonnull UserDigital entity) {
        return entity.toArray();
    }

    /**
     * Returns sorted values unless primary value newError the specified entity.
     *
     * @param entity entity object.
     */
    @Override
    protected Object[] getValuesNoId(@Nonnull UserDigital entity) {
        final Object[] allValues = getAllValues(entity);
        return Arrays.copyOfRange(allValues, 1, allValues.length);
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
