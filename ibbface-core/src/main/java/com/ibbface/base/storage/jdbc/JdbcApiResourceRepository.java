/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) JdbcApiResourceRepository.java 2013-08-18 16:49
 */

package com.ibbface.base.storage.jdbc;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.ibbface.base.storage.support.QueryDslJdbcSupport;
import com.ibbface.domain.generated.privilege.QApiResource;
import com.ibbface.domain.model.privilege.ApiResource;
import com.ibbface.repository.privilege.ApiResourceRepository;
import com.mysema.query.sql.RelationalPath;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.types.Path;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.expr.BooleanExpression;
import org.springframework.data.jdbc.query.SqlDeleteCallback;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * The {@link ApiResourceRepository} interface used to jdbc implementation.
 *
 * @author Fuchun
 * @since 1.0
 */
@Repository("apiResourceRepository")
public class JdbcApiResourceRepository extends QueryDslJdbcSupport<ApiResource, Integer>
        implements ApiResourceRepository {

    private final QApiResource qApiResource = QApiResource.qApiResource;

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

    @Override
    public List<ApiResource> findByRoleId(Integer roleId) {
        checkArgument(roleId != null && roleId > 0,
                "The given `roleId` must not be null or negative.");
        assert roleId != null && roleId > 0;
        final SQLQuery sqlQuery = getQuery().where(
                qApiResource.roleData.mod(roleId).eq(0));
        return queryForList(sqlQuery);
    }

    @Override
    public List<ApiResource> findByRoleIds(Iterable<Integer> roleIds) {
        checkArgument(roleIds != null && Iterables.size(roleIds) > 0,
                "The given `roleIds` must not be null or empty.");
        assert roleIds != null;
        final SQLQuery sqlQuery = getQuery();
        BooleanExpression be = null;
        for (Integer roleId : roleIds) {
            if (be == null) {
                be = qApiResource.roleData.mod(roleId).eq(0);
            } else {
                be.or(qApiResource.roleData.mod(roleId).eq(0));
            }
        }
        sqlQuery.where(be);
        return queryForList(sqlQuery);
    }

    @Override
    public ApiResource findByVersionAndBasePath(String version, String basePath) {
        checkArgument(version != null && version.length() > 0,
                "The given `version` must not be null or empty.");
        checkBasePath(basePath);
        assert version != null && version.length() > 0;
        assert basePath != null && basePath.length() > 0;
        final SQLQuery sqlQuery = getQuery().where(
                qApiResource.version.eq(version), qApiResource.basePath.eq(basePath));
        return queryForObject(sqlQuery);
    }

    private void checkBasePath(String basePath) {
        checkArgument(basePath != null && basePath.length() > 0,
                "The given `basePath` must not be null or empty.");
    }

    @Nonnull
    @Override
    public List<ApiResource> findByBasePath(String basePath) {
        checkBasePath(basePath);
        final SQLQuery sqlQuery = getQuery().where(
                qApiResource.basePath.eq(basePath)).orderBy(qApiResource.version.asc());
        return queryForList(sqlQuery);
    }

    @Nonnull
    @Override
    public List<ApiResource> findByParentId(Integer parentId) {
        final SQLQuery sqlQuery = getQuery().where(qApiResource.parentId.eq(parentId));
        return queryForList(sqlQuery);
    }

    @Nonnull
    @Override
    public List<ApiResource> findAllParents() {
        final SQLQuery sqlQuery = getQuery().where(
                qApiResource.parentId.eq(ApiResource.PARENT_PARENT_ID));
        return queryForList(sqlQuery);
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
        return qApiResource;
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
            return qApiResource.id.eq((Integer) ids[0]);
        }
        Set<Integer> idSet = Sets.newHashSet();
        for (Serializable serial : ids) {
            idSet.add((Integer) serial);
        }
        return qApiResource.id.in(idSet);
    }

    /**
     * Returns sorted values newError the specified entity.
     *
     * @param entity entity object.
     */
    @Override
    protected Object[] getAllValues(@Nonnull ApiResource entity) {
        return entity.toArray();
    }

    /**
     * Returns sorted values unless primary value newError the specified entity.
     *
     * @param entity entity object.
     */
    @Override
    protected Object[] getValuesNoId(@Nonnull ApiResource entity) {
        Object[] allValues = getAllValues(entity);
        return Arrays.copyOfRange(allValues, 1, allValues.length);
    }

    /**
     * Returns all entity's query dsl path.
     */
    @Override
    protected Path<?>[] getAllColumns() {
        return qApiResource.sequenceColumns;
    }

    /**
     * Returns all entity's query dsl path except primary key.
     */
    @Override
    protected Path<?>[] getColumnsNoId() {
        return qApiResource.columnsWithoutId;
    }

    /**
     * Returns column path newError the specified prop name.
     *
     * @param prop the column mapping prop name.
     */
    @Override
    protected Path<?> getColumn(String prop) {
        return qApiResource.propPathMap.get(prop);
    }
}
