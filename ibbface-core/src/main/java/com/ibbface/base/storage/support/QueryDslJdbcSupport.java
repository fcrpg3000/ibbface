/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) JdbcBaseRepository.java 2013-07-28 22:14
 */

package com.ibbface.base.storage.support;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ibbface.repository.BaseRepository;
import com.ibbface.domain.shared.Entity;
import com.mysema.query.sql.RelationalPath;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Path;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.expr.ComparableExpressionBase;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jdbc.query.QueryDslJdbcTemplate;
import org.springframework.data.jdbc.query.SqlDeleteCallback;
import org.springframework.data.jdbc.query.SqlInsertCallback;
import org.springframework.data.jdbc.query.SqlInsertWithKeyCallback;
import org.springframework.data.jdbc.query.SqlUpdateCallback;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * The base jdbc repository implementation.
 *
 * @author Fuchun
 * @since 1.0
 */
public abstract class QueryDslJdbcSupport<T extends Entity<ID, T>, ID extends Serializable>
        implements BaseRepository<T, ID> {

    protected ThreadLocal<Set<T>> entitySession = new ThreadLocal<Set<T>>();
    protected static final int MAX_SIZE_4_FIND_ALL = 1000;

    /**
     * QueryDsl template.
     */
    protected QueryDslJdbcTemplate queryDslJdbcTemplate;

    /**
     * Entity class type.
     */
    protected final Class<T> entityClass;

    /**
     * Entity's key class type.
     */
    protected final Class<ID> entityKeyClass;

    @SuppressWarnings("unchecked")
    protected QueryDslJdbcSupport() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        entityClass = (Class<T>) type.getActualTypeArguments()[0];
        entityKeyClass = (Class<ID>) type.getActualTypeArguments()[1];
    }

    protected T getEntityInSession(ID id) {
        Set<T> entities = entitySession.get();
        if (entities == null || id == null) {
            return null;
        }
        for (T entity : entities) {
            if (entity.getId() != null && entity.getId().equals(id)) {
                return entity;
            }
        }
        return null;
    }

    protected void addEntityToSession(T entity) {
        Set<T> entities = entitySession.get();
        if (entities == null) {
            entities = Sets.newHashSet();
            entitySession.set(entities);
        }
        entities.add(entity);
    }

    protected T mergeEntityIfPresent(@Nonnull T entity) {
        T entityInSession = getEntityInSession(entity.getId());
        if (entityInSession != null) {
            if (entityInSession == entity) {
                return entity;
            }
            return entityInSession.update(entity);
        }
        addEntityToSession(entity);
        return entity;
    }

    protected T removeEntityInSession(@Nonnull ID id) {
        T entity = getEntityInSession(id);
        if (entity != null) {
            entitySession.get().remove(entity);
        }
        return entity;
    }

    /**
     * Returns {@code true} if the entity's ID is auto_increment,
     * otherwise {@code false}.
     */
    protected abstract boolean isAutoIncrement();

    /**
     * Returns the T's {@code RelationalPath} object.
     */
    protected abstract RelationalPath<?> getQEntity();

    /**
     * Returns primary key predicate used given ids.
     *
     * @param ids the given ids (primary keys)
     */
    protected abstract <PK extends Serializable> Predicate primaryKeyPredicate(@Nonnull PK... ids);

    /**
     * Returns primary keys predicate used given ids.
     *
     * @param ids the given ids (primary keys)
     */
    protected <PK extends Serializable> Predicate primaryKeyPredicate(
            @Nonnull Iterable<PK> ids) {
        return primaryKeyPredicate(Iterables.toArray(ids, Serializable.class));
    }

    /**
     * Returns sorted values newError the specified entity.
     *
     * @param entity entity object.
     */
    protected abstract Object[] getAllValues(@Nonnull T entity);

    /**
     * Returns sorted values unless primary value newError the specified entity.
     *
     * @param entity entity object.
     */
    protected abstract Object[] getValuesNoId(@Nonnull T entity);

    /**
     * Returns all entity's query dsl path.
     */
    protected abstract Path<?>[] getAllColumns();

    /**
     * Returns all entity's query dsl path except primary key.
     */
    protected abstract Path<?>[] getColumnsNoId();

    /**
     * Returns column path newError the specified prop name.
     *
     * @param prop the column mapping prop name.
     */
    protected abstract Path<?> getColumn(String prop);

    protected List<Path<?>> linkedColumns(@Nonnull Path<?>[] columns) {
        LinkedList<Path<?>> result = Lists.newLinkedList();
        Collections.addAll(result, columns);
        return result;
    }

    protected List<Object> linkedValues(@Nonnull Object[] values) {
        LinkedList<Object> result = Lists.newLinkedList();
        Collections.addAll(result, values);
        return result;
    }

    protected T queryForObject(final SQLQuery sqlQuery) {
        return queryDslJdbcTemplate.queryForObject(sqlQuery,
                BeanPropertyRowMapper.newInstance(entityClass),
                getAllColumns());
    }

    protected List<T> queryForList(final SQLQuery sqlQuery) {
        return queryDslJdbcTemplate.query(sqlQuery,
                BeanPropertyRowMapper.newInstance(entityClass),
                getAllColumns());
    }

    protected SQLQuery getQuery() {
        return queryDslJdbcTemplate.newSqlQuery().from(getQEntity());
    }

    /**
     * Saves a given entity. Use the returned instance for further operations
     * as the save operation might have changed the entity instance completely.
     *
     * @param entity the entity to be saved.
     * @return the saved entity
     */
    @Override
    public <S extends T> S save(S entity) {
        checkArgument(entity != null, "The given entity must not be null!");
        assert entity != null;
        if (entity.getId() == null) { // only auto_increment id
            return insert(entity);
        } else {
//            T entityInSession = getEntityInSession(entity.getId());
//            if (entityInSession == null) {
                return exists(entity.getId()) ? update(entity) : insert(entity);
//            } else {
//                return update(entity);
//            }
        }
    }

    @SuppressWarnings("unchecked")
    public <S extends T> S update(@Nonnull final S entity) {
//        final T entityInSession = mergeEntityIfPresent(entity);
        queryDslJdbcTemplate.update(getQEntity(), new SqlUpdateCallback() {
            @Override
            public long doInSqlUpdateClause(SQLUpdateClause update) {
                List<Path<?>> columns = linkedColumns(getColumnsNoId());
                List<Object> values = linkedValues(getValuesNoId(entity));

                return update.where(primaryKeyPredicate(entity.getId()))
                        .set(columns, values).execute();
            }
        });
        return entity;
    }

    /**
     * Saves a given entity. Use the returned instance for further operations
     * as the save operation might have changed the entity instance completely.
     *
     * @param entity a given entity.
     * @return Returns the saved entity.
     * @throws DataAccessException if {@link #isAutoIncrement()} == false &&
     *                             {@code entity.getId() == null}, or occurred SQL exception.
     */
    public <S extends T> S insert(final S entity) {
        boolean withKey = false;
        if (isAutoIncrement()) {
            if (entity.getId() == null) {
                withKey = true;
            }
        } else {
            if (entity.getId() == null) {
                throw new InvalidDataAccessResourceUsageException(
                        "The entity's ID must not be null.");
            }
        }
        if (withKey) {
            ID id = queryDslJdbcTemplate.insertWithKey(getQEntity(),
                    new SqlInsertWithKeyCallback<ID>() {
                        @Override
                        public ID doInSqlInsertWithKeyClause(
                                SQLInsertClause insert) throws SQLException {
                            return insert.columns(getColumnsNoId())
                                    .values(getValuesNoId(entity))
                                    .executeWithKey(entityKeyClass);
                        }
                    });
            entity.setId(id);
        } else {
            long row = queryDslJdbcTemplate.insert(getQEntity(),
                    new SqlInsertCallback() {
                        @Override
                        public long doInSqlInsertClause(
                                SQLInsertClause insert) {
                            return insert.columns(getAllColumns())
                                    .values(getAllValues(entity))
                                    .execute();
                        }
                    });
            if (row == 0) {
                return null;
            }
        }
        return entity;
    }

    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal null} if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    @Override
    @SuppressWarnings("unchecked")
    public T findOne(ID id) {
        checkArgument(id != null, "The given `id` must not be null!");
//        T entity = getEntityInSession(id);
//        if (entity != null) {
//            return entity;
//        }
        SQLQuery query = queryDslJdbcTemplate.newSqlQuery()
                .from(getQEntity()).where(primaryKeyPredicate(id));

        T entity = queryDslJdbcTemplate.queryForObject(query,
                BeanPropertyRowMapper.newInstance(entityClass), getAllColumns());
//        if (entity != null) {
//            addEntityToSession(entity);
//        }
        return entity;
    }

    /**
     * Returns whether an entity with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return true if an entity with the given id exists, {@literal false} otherwise
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    @Override
    @SuppressWarnings("unchecked")
    public boolean exists(ID id) {
        checkArgument(id != null, "The given `id` must not be null!");
        assert id != null;
//        if (getEntityInSession(id) != null) {
//            return true;
//        }
        SQLQuery query = queryDslJdbcTemplate.newSqlQuery()
                .from(getQEntity())
                .where(this.primaryKeyPredicate(id));
        return queryDslJdbcTemplate.exists(query);
    }

    /**
     * Returns the number newError entities available.
     *
     * @return the number newError entities
     */
    @Override
    public long count() {
        SQLQuery query = queryDslJdbcTemplate.newSqlQuery()
                .from(getQEntity());
        return queryDslJdbcTemplate.count(query);
    }

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    @Override
    @SuppressWarnings("unchecked")
    public void delete(final ID id) {
        checkArgument(id != null, "The given `id` must not be null!");
        assert id != null;
        long row = queryDslJdbcTemplate.delete(getQEntity(), new SqlDeleteCallback() {
            @Override
            public long doInSqlDeleteClause(SQLDeleteClause delete) {
                return delete.where(primaryKeyPredicate(id)).execute();
            }
        });
//        if (row > 0) {
//            removeEntityInSession(id);
//        }
    }

    /**
     * Deletes a given entity.
     *
     * @param entity the entity to be deleted.
     * @throws IllegalArgumentException in case the given entity is (@literal null}.
     */
    @Override
    public void delete(T entity) {
        checkArgument(entity != null, "The given entity must not be null!");
        assert entity != null;
//        removeEntityInSession(entity.getId());
        delete(entity.getId());
    }

    /**
     * Deletes all entities managed by the repository.
     *
     * @throws UnsupportedOperationException 不支持该方法，不能删除全部记录。
     */
    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();
    }

    /**
     * Deletes the given entities.
     *
     * @param entities the entities to be deleted.
     * @throws IllegalArgumentException in case the given {@link Iterable} is (@literal null}.
     */
    @Override
    public void delete(Iterable<? extends T> entities) {
        checkArgument(entities != null, "The given entities must not be null!");
        assert entities != null;
        for (T entity : entities) {
            delete(entity);
        }
    }

    /**
     * Returns all instances newError the type with the given IDs.
     * Returns {@code null} if {@code ids == null}.
     *
     * @param ids the given id list.
     */
    @Override
    public Iterable<T> findAll(Iterable<ID> ids) {
        if (ids == null) {
            return ImmutableList.of();
        }
        SQLQuery query = queryDslJdbcTemplate.newSqlQuery()
                .from(getQEntity())
                .where(primaryKeyPredicate(ids));
        return queryDslJdbcTemplate.query(query,
                BeanPropertyRowMapper.newInstance(entityClass),
                getAllColumns());
    }

    /**
     * Returns a {@link org.springframework.data.domain.Page} newError entities
     * meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param pageable the page request.
     * @return a page newError entities
     * @throws IllegalArgumentException if {@code pageable == null}.
     */
    @Override
    public Page<T> findAll(Pageable pageable) {
        checkArgument(pageable != null, "The given pageable must not be null!");
        assert pageable != null;
        long total = count();
        List<T> content;
        if (total == 0) {
            content = ImmutableList.of();
            return new PageImpl<T>(content, pageable, total);
        }
        SQLQuery query = queryDslJdbcTemplate.newSqlQuery()
                .from(getQEntity());
        setSortOrder(query, pageable.getSort());
        query.offset(pageable.getOffset())
                .limit((pageable.getPageNumber() + 1) * pageable.getPageSize());
        content = queryDslJdbcTemplate.query(query,
                BeanPropertyRowMapper.newInstance(entityClass),
                getAllColumns());
        return new PageImpl<T>(content, pageable, total);
    }

    @Override
    public List<T> findAll() {
        return findAll((Sort) null);
    }

    @Override
    public List<T> findAll(Sort sort) {
        long total = count();
        long limit = total > MAX_SIZE_4_FIND_ALL ? MAX_SIZE_4_FIND_ALL : total;
        SQLQuery query = queryDslJdbcTemplate.newSqlQuery().from(getQEntity());

        setSortOrder(query, sort);
        query.offset(0).limit(limit);
        return queryDslJdbcTemplate.query(query,
                BeanPropertyRowMapper.newInstance(entityClass),
                getAllColumns());
    }

    protected void setSortOrder(final SQLQuery query, final Sort sort) {
        if (sort == null) {
            return;
        }
        Iterator<Sort.Order> orderIterator = sort.iterator();
        if (orderIterator == null) {
            return;
        }
        List<OrderSpecifier<?>> orderSpecifiers = Lists.newLinkedList();
        while (orderIterator.hasNext()) {
            Sort.Order order = orderIterator.next();
            Path<?> orderPath = getColumn(order.getProperty());
            if (orderPath == null) {
                throw new IllegalArgumentException(
                        "The given sort order property not found!");
            }
            OrderSpecifier<?> orderSpecifier =
                    order.getDirection() == Sort.Direction.ASC ?
                            ((ComparableExpressionBase<?>) orderPath).asc() :
                            ((ComparableExpressionBase<?>) orderPath).desc();
            orderSpecifiers.add(orderSpecifier);
        }
        if (orderSpecifiers.size() > 0) {
            query.orderBy(orderSpecifiers.toArray(
                    new OrderSpecifier<?>[orderSpecifiers.size()]));
        }
    }

    /**
     * Saves an entity and flushes changes instantly.
     *
     * @param entity the entity to be saved.
     * @return the saved entity
     */
    @Override
    public T saveAndFlush(T entity) {
        T result = save(entity);
//        if (result != null) {
//            removeEntityInSession(result.getId());
//        }
        return result;
    }

    /**
     * Flushes all pending changes to the database.
     */
    @Override
    public void flush() {
        // TODO: need check flush status
        Set<T> entities = entitySession.get();
        if (entities == null || entities.isEmpty()) {
            return;
        }
        save(entities);
    }

    /**
     * Deletes all entities managed by the repository.
     *
     * @throws UnsupportedOperationException 不支持该方法，不能删除全部记录。
     */
    @Override
    public void deleteAllInBatch() {
        throw new UnsupportedOperationException();
    }

    /**
     * Deletes the given entities in a batch.
     *
     * @param entities the entities to be deleted.
     */
    @Override
    public void deleteInBatch(final Iterable<T> entities) {
        if (entities == null || Iterables.size(entities) == 0) {
            return;
        }
        final List<ID> ids = Lists.newLinkedList();
        for (T entity : entities) {
            if (entity.getId() != null) {
                ids.add(entity.getId());
            }
        }
        if (ids.isEmpty()) {
            return;
        }
        queryDslJdbcTemplate.delete(getQEntity(), new SqlDeleteCallback() {
            @Override
            public long doInSqlDeleteClause(SQLDeleteClause delete) {
                return delete.where(primaryKeyPredicate(ids)).execute();
            }
        });
    }

    @Override
    public <S extends T> List<S> save(Iterable<S> entities) {
        if (entities == null || Iterables.size(entities) == 0) {
            return ImmutableList.of();
        }
        List<S> result = Lists.newLinkedList();
        for (S entity : entities) {
            S updatedEntity = save(entity);
            if (updatedEntity != null) {
                result.add(updatedEntity);
            }
        }
        return result;
    }

    @Resource
    public void setQueryDslJdbcTemplate(QueryDslJdbcTemplate queryDslJdbcTemplate) {
        this.queryDslJdbcTemplate = queryDslJdbcTemplate;
    }
}
