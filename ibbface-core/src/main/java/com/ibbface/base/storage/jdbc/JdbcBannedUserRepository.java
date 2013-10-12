/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) JdbcBannedUserRepository.java 2013-08-01 22:52
 */

package com.ibbface.base.storage.jdbc;

import com.google.common.collect.ImmutableList;
import com.ibbface.base.storage.support.QueryDslJdbcSupport;
import com.ibbface.domain.generated.user.QBannedUser;
import com.ibbface.domain.model.user.BannedUser;
import com.ibbface.repository.user.BannedUserRepository;
import com.mysema.query.sql.RelationalPath;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.types.Path;
import com.mysema.query.types.expr.SimpleExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * {@link BannedUserRepository} interface default implementation used jdbc.
 *
 * @author Fuchun
 * @since 1.0
 */
@Repository("bannedUserRepository")
public class JdbcBannedUserRepository extends QueryDslJdbcSupport<BannedUser, Long>
        implements BannedUserRepository {
    private final QBannedUser qBannedUser = QBannedUser.BannedUser;

    /**
     * Returns the T's {@code RelationalPath} object.
     */
    @Override
    protected RelationalPath<?> getQEntity() {
        return qBannedUser;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected SimpleExpression<Long> getPkPath() {
        return qBannedUser.id;
    }

    /**
     * Returns all entity's query dsl path.
     */
    @Override
    protected Path<?>[] getAllColumns() {
        return qBannedUser.allColumns;
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
        return qBannedUser.propPathMap.get(prop);
    }

    /**
     * Query all banned users newError the specified {@code userId}.
     *
     * @param userId the user's id.
     */
    @Override
    public List<BannedUser> findByUserId(Long userId) {
        checkArgument(userId != null && userId > 0,
                "The given `userId` must not be null or negative!");
        final SQLQuery query = getQuery().where(qBannedUser.userId.eq(userId));
        return queryForList(query);
    }

    /**
     * Query page banned users newError the specified {@code operatorId} and
     * {@code Pageable}.
     *
     * @param operatorId the banned user operator's id.
     * @param pageable   the page request object.
     * @throws IllegalArgumentException if {@code operatorId == null || operatorId <= 0}.
     */
    @Override
    public Page<BannedUser> findByOperatorId(Integer operatorId,
                                             Pageable pageable) {
        checkArgument(operatorId != null && operatorId > 0,
                "The given `operatorId` must not be null or 0 or negative!");
        final SQLQuery query = getQuery().where(qBannedUser.operatorId.eq(operatorId));
        long total = queryDslJdbcTemplate.count(query);
        List<BannedUser> content;
        if (total == 0) {
            content = ImmutableList.of();
            return new PageImpl<BannedUser>(content, pageable, total);
        }
        query.offset(pageable.getOffset())
                .limit((pageable.getPageNumber() + 1) * pageable.getPageSize());
        setSortOrder(query, pageable.getSort());
        content = queryForList(query);
        return new PageImpl<BannedUser>(content, pageable, total);
    }
}
