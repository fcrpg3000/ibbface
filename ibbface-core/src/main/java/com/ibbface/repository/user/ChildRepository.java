/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) ChildRepository.java 2013-09-30 16:52
 */

package com.ibbface.repository.user;

import com.ibbface.domain.model.user.Child;
import com.ibbface.repository.BaseRepository;

import java.util.List;

/**
 * The {@link Child} entity repository.
 *
 * @author Fuchun
 * @since 1.0
 */
public interface ChildRepository extends BaseRepository<Child, Long> {

    /**
     * Query and return all {@link Child} of the specified user's, or empty list
     * if the specified user have not input child information.
     * <p/>
     * This method never returned {@code null}.
     *
     * @param userId the user id.
     * @return all {@link Child} of the specified user's, or empty list.
     */
    public List<Child> findByUserId(Long userId);
}
