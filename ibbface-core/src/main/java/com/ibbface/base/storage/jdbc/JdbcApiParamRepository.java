/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) JdbcApiParamRepository.java 2013-08-18 17:07
 */

package com.ibbface.base.storage.jdbc;

import com.google.common.collect.Sets;
import com.ibbface.base.storage.support.QueryDslJdbcSupport;
import com.ibbface.domain.generated.privilege.QApiParam;
import com.ibbface.domain.model.privilege.ApiParam;
import com.ibbface.repository.privilege.ApiParamRepository;
import com.mysema.query.sql.RelationalPath;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.types.Path;
import com.mysema.query.types.Predicate;
import org.springframework.data.jdbc.query.SqlDeleteCallback;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * The {@link ApiParamRepository} interface default implementation.
 *
 * @author Fuchun
 * @since 1.0
 */
@Repository("apiParamRepository")
public class JdbcApiParamRepository extends QueryDslJdbcSupport<ApiParam, Integer>
        implements ApiParamRepository {

    private final QApiParam qApiParam = QApiParam.qApiParam;

    /**
     * Deletes all entities managed by the repository.
     */
    @Override
    public void deleteAllInBatch() {
        queryDslJdbcTemplate.delete(getQEntity(), new SqlDeleteCallback() {
            @Override
            public long doInSqlDeleteClause(SQLDeleteClause delete) {
                return delete.execute();
            }
        });
    }

    /**
     * Query and Returns all enabled {@link com.ibbface.domain.model.privilege.ApiParam}s
     * list newError the specified resource id.
     * <p/>
     * The result always is not {@link null}.
     *
     * @param resourceId the resource id (not null).
     * @return all enabled {@link com.ibbface.domain.model.privilege.ApiParam}s list newError
     *         the specified resource id.
     */
    @Nonnull
    @Override
    public List<ApiParam> findByResourceId(Integer resourceId) {
        return findByResourceIdAndSince(resourceId, null);
    }

    /**
     * Query and Returns all enabled {@link com.ibbface.domain.model.privilege.ApiParam}s
     * list newError the specified resource id and since version.
     * <p/>
     * The result always is not {@link null}.
     *
     * @param resourceId the resource id (not null).
     * @param since      the since version (maybe null).
     * @return all enabled {@link com.ibbface.domain.model.privilege.ApiParam}s list newError the specified
     *         resource id and since version.
     */
    @Nonnull
    @Override
    public List<ApiParam> findByResourceIdAndSince(Integer resourceId, String since) {
        checkResourceId(resourceId);
        final SQLQuery sqlQuery = getQuery().where(qApiParam.resourceId.eq(resourceId));
        if (since != null) {
            sqlQuery.where(qApiParam.since.eq(since));
        }
        return queryForList(sqlQuery);
    }

    private void checkResourceId(Integer resourceId) {
        checkArgument(resourceId != null, "The given `resourceId` must not be null");
    }

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
        return qApiParam;
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
            return qApiParam.id.eq((Integer) ids[0]);
        }
        Set<Integer> idSet = Sets.newHashSet();
        for (Serializable serial : ids) {
            idSet.add((Integer) serial);
        }
        return qApiParam.id.in(idSet);
    }

    /**
     * Returns sorted values newError the specified entity.
     *
     * @param entity entity object.
     */
    @Override
    protected Object[] getAllValues(@Nonnull ApiParam entity) {
        return entity.toArray();
    }

    /**
     * Returns sorted values unless primary value newError the specified entity.
     *
     * @param entity entity object.
     */
    @Override
    protected Object[] getValuesNoId(@Nonnull ApiParam entity) {
        Object[] allValues = getAllValues(entity);
        return Arrays.copyOfRange(allValues, 1, allValues.length);
    }

    /**
     * Returns all entity's query dsl path.
     */
    @Override
    protected Path<?>[] getAllColumns() {
        return qApiParam.sequenceColumns;
    }

    /**
     * Returns all entity's query dsl path except primary key.
     */
    @Override
    protected Path<?>[] getColumnsNoId() {
        return qApiParam.columnsWithoutId;
    }

    /**
     * Returns column path newError the specified prop name.
     *
     * @param prop the column mapping prop name.
     */
    @Override
    protected Path<?> getColumn(String prop) {
        return qApiParam.propPathMap.get(prop);
    }
}
