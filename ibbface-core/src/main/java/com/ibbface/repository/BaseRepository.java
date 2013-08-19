/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) BaseRepository.java 2013-07-28 22:11
 */

package com.ibbface.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;
import java.util.List;

/**
 * Base repository interface.
 *
 * @author Fuchun
 * @since 1.0
 */
public interface BaseRepository<T, ID extends Serializable> extends
        PagingAndSortingRepository<T, ID> {
    /*
	 * (non-Javadoc)
	 * @see org.springframework.data.repository.CrudRepository#findAll()
	 */
    List<T> findAll();

    /*
     * (non-Javadoc)
     * @see org.springframework.data.repository.PagingAndSortingRepository#findAll(org.springframework.data.domain.Sort)
     */
    List<T> findAll(Sort sort);

    /*
     * (non-Javadoc)
     * @see org.springframework.data.repository.CrudRepository#save(java.lang.Iterable)
     */
    <S extends T> List<S> save(Iterable<S> entities);

    /**
     * Flushes all pending changes to the database.
     */
    void flush();

    /**
     * Saves an entity and flushes changes instantly.
     *
     * @param entity
     * @return the saved entity
     */
    T saveAndFlush(T entity);

    /**
     * Deletes the given entities in a batch.
     *
     * @param entities
     */
    void deleteInBatch(Iterable<T> entities);

    /**
     * Deletes all entites in a batch call.
     */
    void deleteAllInBatch();
}
