/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) UserOnlineRepository.java 2013-08-06 00:52
 */

package com.ibbface.repository.user;

import com.ibbface.domain.model.user.UserOnline;
import com.ibbface.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * {@link com.ibbface.domain.model.user.UserOnline} entity repository interface.
 *
 * @author Fuchun
 * @since 1.0
 */
public interface UserOnlineRepository extends BaseRepository<UserOnline, Long> {

    /**
     * Query the {@link UserOnline} of the specified {@code sessionId}.
     *
     * @param sessionId the sessionId.
     * @return Returns the {@link UserOnline} of the specified {@code sessionId}, or {@code null} if not found.
     */
    public UserOnline findBySessionId(String sessionId);

    /**
     * Query the {@link UserOnline} of the specified {@code accessToken}.
     *
     * @param accessToken the accessToken.
     * @return Returns the {@link UserOnline} of the specified {@code accessToken}, or {@code null} if not found.
     */
    public UserOnline findByAccessToken(String accessToken);

    public List<UserOnline> findTop(Sort sort, int size);

    public List<UserOnline> findOrderByThatOnlineTimeDesc(int size);

    public List<UserOnline> findOrderByTotalOnlineTimeDesc(int size);

    /**
     * Query page {@link UserOnline} of the specified last access time offset.
     *
     * @param offset   the last access time offset.
     * @param pageable the page request.
     * @return the page {@link UserOnline}.
     */
    public Page<UserOnline> findOnline(long offset, Pageable pageable);
}
