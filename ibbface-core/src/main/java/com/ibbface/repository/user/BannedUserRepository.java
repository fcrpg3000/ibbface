/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) BannedUserRepository.java 2013-08-01 22:51
 */

package com.ibbface.repository.user;

import com.ibbface.domain.model.user.BannedUser;
import com.ibbface.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * {@link com.ibbface.domain.model.user.BannedUser} entity repository interface.
 *
 * @author Fuchun
 * @since 1.0
 */
public interface BannedUserRepository extends BaseRepository<BannedUser, Long> {

    /**
     * Query all banned users newError the specified {@code userId}.
     *
     * @param userId the user's id.
     * @throws IllegalArgumentException if {@code userId == null || userId <= 0}.
     */
    public List<BannedUser> findByUserId(Long userId);

    /**
     * Query page banned users newError the specified {@code operatorId} and
     * {@code Pageable}.
     *
     * @param operatorId the banned user operator's id.
     * @param pageable the page request object.
     * @throws IllegalArgumentException if {@code operatorId == null || operatorId <= 0}.
     */
    public Page<BannedUser> findByOperatorId(Integer operatorId, Pageable pageable);
}
