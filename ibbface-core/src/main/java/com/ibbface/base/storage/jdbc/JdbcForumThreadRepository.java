/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) JdbcForumThreadRepository.java 2013-08-05 00:17
 */

package com.ibbface.base.storage.jdbc;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Range;
import com.ibbface.base.storage.support.QueryDslJdbcSupport;
import com.ibbface.domain.exception.EntityNotFoundException;
import com.ibbface.domain.generated.forum.QForumThread;
import com.ibbface.domain.model.forum.ForumThread;
import com.ibbface.repository.forum.ForumThreadRepository;
import com.mysema.query.sql.RelationalPath;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.Path;
import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.expr.SimpleExpression;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.query.SqlUpdateCallback;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * {@link ForumThreadRepository} interface default implementation.
 *
 * @author Fuchun
 * @since 1.0
 */
@Repository("forumThreadRepository")
public class JdbcForumThreadRepository extends QueryDslJdbcSupport<ForumThread, Long>
        implements ForumThreadRepository {

    private final QForumThread qForumThread = QForumThread.Thread;

    /**
     * {@inheritDoc}
     */
    @Override
    protected RelationalPath<?> getQEntity() {
        return qForumThread;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected SimpleExpression<Long> getPkPath() {
        return qForumThread.id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Path<?>[] getAllColumns() {
        return qForumThread.sequenceColumns;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Path<?>[] getColumnsNoId() {
        return qForumThread.columnsWithoutId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Path<?> getColumn(String prop) {
        return qForumThread.propPathMap.get(prop);
    }

    @Override
    public ForumThread findByIdAlias(String idAlias) {
        return queryForObject(getQuery().where(qForumThread.idAlias.eq(idAlias)));
    }

    @Override
    public Page<ForumThread> findByAll(Integer forumId, Long userId, Short[] status,
                                       Boolean isTop, Boolean isGood, @Nonnull Pageable pageable) {
        SQLQuery sqlQuery = getQuery();
        if (forumId != null) {
            sqlQuery.where(qForumThread.forumId.eq(forumId));
        }
        if (userId != null) {
            sqlQuery.where(qForumThread.userId.eq(userId));
        }
        buildStatusExpr(status, sqlQuery);
        if (isTop != null) {
            sqlQuery.where(isTop ? qForumThread.isTop.isTrue() : qForumThread.isTop.isFalse());
        }
        if (isGood != null) {
            sqlQuery.where(isGood ? qForumThread.isGood.isTrue() : qForumThread.isGood.isFalse());
        }
        return getPageForumThreads(sqlQuery, pageable);
    }

    private Page<ForumThread> getPageForumThreads(final SQLQuery sqlQuery, final Pageable pageable) {
        long total = queryDslJdbcTemplate.count(sqlQuery);
        List<ForumThread> content;
        if (total == 0) {
            content = ImmutableList.of();
            return new PageImpl<ForumThread>(content, pageable, total);
        }
        setSortOrder(sqlQuery, pageable.getSort());
        sqlQuery.offset(pageable.getOffset())
                .limit((pageable.getPageNumber() + 1) * pageable.getPageSize());
        content = queryForList(sqlQuery);
        return new PageImpl<ForumThread>(content, pageable, total);
    }

    @Override
    public Page<ForumThread> findByUserId(Long userId, Pageable pageable) {
        return findByAll(null, userId, new Short[]{ForumThread.STATUS_PUBLISHED},
                null, null, pageable);
    }

    @Override
    public Page<ForumThread> findGoodByUserId(Long userId, Pageable pageable) {
        return findByAll(null, userId, new Short[]{ForumThread.STATUS_PUBLISHED},
                null, true, pageable);
    }

    @Override
    public Page<ForumThread> findByForumIdAndUserId(Integer forumId, Long userId, Pageable pageable) {
        return findByAll(forumId, userId, new Short[]{ForumThread.STATUS_PUBLISHED},
                null, null, pageable);
    }

    @Override
    public Page<ForumThread> findByForumId(Integer forumId, Pageable pageable) {
        return findByAll(forumId, null, new Short[]{ForumThread.STATUS_PUBLISHED},
                null, null, pageable);
    }

    @Override
    public Page<ForumThread> findGoodByForumId(Integer forumId, Pageable pageable) {
        return findByAll(forumId, null, new Short[]{ForumThread.STATUS_PUBLISHED},
                null, true, pageable);
    }

    @Override
    public Page<ForumThread> findByStatus(Short status, Pageable pageable) {
        return findByAll(null, null, new Short[]{status}, null, null, pageable);
    }

    @Override
    public Page<ForumThread> findByCreatedTime(
            Date createdTime, Short[] status, Pageable pageable) {
        Calendar calFrom = Calendar.getInstance(), calTo = Calendar.getInstance();
        calFrom.setTimeInMillis(createdTime.getTime());
        calTo.setTimeInMillis(createdTime.getTime());
        calFrom.set(Calendar.HOUR_OF_DAY, 0);
        calFrom.set(Calendar.MINUTE, 0);
        calFrom.set(Calendar.SECOND, 0);
        calFrom.set(Calendar.MILLISECOND, 0);
        calTo.set(Calendar.HOUR_OF_DAY, 23);
        calTo.set(Calendar.MINUTE, 59);
        calTo.set(Calendar.SECOND, 59);
        calTo.set(Calendar.MILLISECOND, 999);
        SQLQuery sqlQuery = getQuery();
        buildStatusExpr(status, sqlQuery);
        sqlQuery.where(
                qForumThread.createdTime.between(new Timestamp(calFrom.getTimeInMillis()),
                        new Timestamp(calTo.getTimeInMillis())));
        return getPageForumThreads(sqlQuery, pageable);
    }

    private void buildStatusExpr(final Short[] status, final SQLQuery sqlQuery) {
        if (status == null || status.length <= 0) {
            return;
        }
        if (status.length == 1) {
            sqlQuery.where(qForumThread.status.eq(status[0]));
        } else {
            BooleanExpression be = null;
            for (Short s : status) {
                if (be == null) {
                    be = qForumThread.status.eq(s);
                } else {
                    be.or(qForumThread.status.eq(s));
                }
            }
            sqlQuery.where(be);
        }
    }

    @Override
    public Page<ForumThread> findByCreatedTimeRange(
            Range<Date> range, Short[] status, Pageable pageable) {
        DateTime from = new DateTime(range.lowerEndpoint()),
                to = new DateTime(range.upperEndpoint());
        // if range is same day
        if (from.getYear() == to.getYear() && from.getMonthOfYear() == to.getMonthOfYear()
                && from.getDayOfMonth() == to.getDayOfMonth()) {
            return findByCreatedTime(from.toDate(), status, pageable);
        }
        SQLQuery sqlQuery = getQuery();
        buildStatusExpr(status, sqlQuery);
        sqlQuery.where(
                qForumThread.createdTime.between(new Timestamp(from.getMillis()),
                        new Timestamp(to.getMillis())));
        return getPageForumThreads(sqlQuery, pageable);
    }

    @Override
    public List<ForumThread> findOrderByPostTimeDescLimit(Integer forumId, int size) {
        SQLQuery sqlQuery = getQuery();
        if (forumId != null) {
            sqlQuery.where(qForumThread.forumId.eq(forumId));
        }
        sqlQuery.where(qForumThread.status.eq(ForumThread.STATUS_PUBLISHED))
                .orderBy(qForumThread.lastPostTime.desc())
                .limit(size);
        return queryForList(sqlQuery);
    }

    @Override
    public void setReplyCount(final Long id, final Integer replyCount) {
        checkThreadId(id);
        int row = (int) queryDslJdbcTemplate.update(qForumThread,
                new SqlUpdateCallback() {
                    @Override
                    public long doInSqlUpdateClause(SQLUpdateClause update) {
                        return update.where(qForumThread.id.eq(id))
                                .set(qForumThread.replyCount, replyCount)
                                .execute();
                    }
                });
        if (row == 0) {
            throw new EntityNotFoundException(id, ForumThread.class);
        }
    }

    @Override
    public void setViewCount(final Long id, final Integer viewCount) {
        checkThreadId(id);
        int row = (int) queryDslJdbcTemplate.update(qForumThread,
                new SqlUpdateCallback() {
                    @Override
                    public long doInSqlUpdateClause(SQLUpdateClause update) {
                        return update.where(qForumThread.id.eq(id))
                                .set(qForumThread.viewCount, viewCount)
                                .execute();
                    }
                });
        if (row == 0) {
            throw new EntityNotFoundException(id, ForumThread.class);
        }
    }

    @Override
    public void setTopGood(final Long id, final Boolean isTop, final Boolean isGood) {
        checkThreadId(id);
        int row = (int) queryDslJdbcTemplate.update(qForumThread,
                new SqlUpdateCallback() {
                    @Override
                    public long doInSqlUpdateClause(SQLUpdateClause update) {
                        update.where(qForumThread.id.eq(id));
                        if (isTop != null) {
                            update.set(qForumThread.isTop, isTop);
                        }
                        if (isGood != null) {
                            update.set(qForumThread.isGood, isGood);
                        }
                        return update.execute();
                    }
                });
        if (row == 0) {
            throw new EntityNotFoundException(id, ForumThread.class);
        }
    }

    @Override
    public void setTop(Long id, boolean isTop) {
        setTopGood(id, isTop, null);
    }

    @Override
    public void setGood(Long id, boolean isGood) {
        setTopGood(id, null, isGood);
    }

    private void checkThreadId(Long id) {
        checkArgument(id != null && id > 0,
                "The given `ForumThread` id must be not null and greater than 0.");
    }
}
