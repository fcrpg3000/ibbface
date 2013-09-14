/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) UserSessionRepository.java 2013-09-11 23:57
 */

package com.ibbface.repository.session;

import com.ibbface.domain.model.session.UserSession;
import com.ibbface.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * The {@link UserSession} entity repository interface.
 *
 * @author Fuchun
 * @since 1.0
 */
public interface UserSessionRepository extends BaseRepository<UserSession, Long> {

    /**
     * Query and return the {@link UserSession} of the specified {@code sessionId} value,
     * or {@code null} if the {@code sessionId} is not exist.
     *
     * @param sessionId the user session id.
     * @return a {@link UserSession} of the specified {@code sessionId} value,
     *         or {@code null} if the {@code sessionId} is not exist.
     */
    public UserSession findBySessionId(String sessionId);

    /**
     * Query and returns the {@link UserSession} of the specified {@code accessToken} value,
     * or {@code null} if the {@code accessToken} is not exist.
     *
     * @param accessToken the user access token.
     * @return a {@link UserSession} of the specified {@code accessToken} value.
     */
    public UserSession findByAccessToken(String accessToken);

    /**
     * Returns the page {@link UserSession} of the specified {@code lastAccessedTime} offset seconds.
     * <pre>Just like this:
     * long timeNow = System.currentTimeMillis();
     * lastAccessedTime &gt;= (timeNow - offsetSeconds * 1000)
     * </pre>
     *
     * @param offsetSeconds the offset seconds of the {@code lastAccessedTime}.
     * @param pageable      the page request.
     * @return the page data information.
     */
    public Page<UserSession> findActivities(long offsetSeconds, Pageable pageable);
}
