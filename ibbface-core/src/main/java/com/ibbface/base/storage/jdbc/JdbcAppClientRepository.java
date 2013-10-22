package com.ibbface.base.storage.jdbc;

import com.ibbface.base.storage.support.QueryDslJdbcSupport;
import com.ibbface.domain.generated.client.QAppClient;
import com.ibbface.domain.model.client.AppClient;
import com.ibbface.repository.client.AppClientRepository;
import com.mysema.query.sql.RelationalPath;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.types.Path;
import com.mysema.query.types.expr.SimpleExpression;
import org.springframework.data.jdbc.query.SqlDeleteCallback;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Fuchun
 * @since 1.0
 */
@Repository("appClientRepository")
public class JdbcAppClientRepository extends QueryDslJdbcSupport<AppClient, Integer>
        implements AppClientRepository {

    private final QAppClient qAppClient = QAppClient.aAppClient;

    @Override
    public List<AppClient> findByVersion(String version) {
        final SQLQuery sqlQuery = getQuery().where(qAppClient.version.eq(version));
        return queryForList(sqlQuery);
    }

    @Override
    public AppClient findByTypeAndVersion(Short typeCode, String version) {
        final SQLQuery sqlQuery = getQuery().where(
                qAppClient.typeCode.eq(typeCode).and(qAppClient.version.eq(version)));
        return queryForObject(sqlQuery);
    }

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
    protected RelationalPath<?> getQEntity() {
        return qAppClient;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected SimpleExpression<Integer> getPkPath() {
        return qAppClient.id;
    }

    @Override
    protected Path<?>[] getAllColumns() {
        return qAppClient.sequenceColumns;
    }

    @Override
    protected Path<?>[] getColumnsNoId() {
        return qAppClient.columnsWithoutId;
    }

    @Override
    protected Path<?> getColumn(String prop) {
        return qAppClient.propPathMap.get(prop);
    }
}
