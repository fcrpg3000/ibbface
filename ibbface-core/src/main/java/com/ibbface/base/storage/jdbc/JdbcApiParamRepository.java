/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) JdbcApiParamRepository.java 2013-08-18 17:07
 */

package com.ibbface.base.storage.jdbc;

import com.ibbface.base.storage.support.QueryDslJdbcSupport;
import com.ibbface.domain.generated.privilege.QApiParam;
import com.ibbface.domain.model.privilege.ApiParam;
import com.ibbface.repository.privilege.ApiParamRepository;
import com.mysema.query.sql.RelationalPath;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.types.Path;
import com.mysema.query.types.expr.SimpleExpression;
import org.springframework.data.jdbc.query.SqlDeleteCallback;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.util.List;

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
     * Returns the T's {@code RelationalPath} object.
     */
    @Override
    protected RelationalPath<?> getQEntity() {
        return qApiParam;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected SimpleExpression<Integer> getPkPath() {
        return qApiParam.id;
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
