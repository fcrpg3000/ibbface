/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) UserSocialRepository.java 2013-08-10 10:42
 */

package com.ibbface.repository.user;

import com.ibbface.domain.model.user.UserSocial;
import com.ibbface.repository.BaseRepository;
import com.ibbface.domain.model.common.OpenProvider;

import java.util.List;

/**
 * {@link com.ibbface.domain.model.user.UserSocial} entity repository interface.
 *
 * @author Fuchun
 * @since 1.0
 */
public interface UserSocialRepository extends BaseRepository<UserSocial, Long> {

    /**
     * Query {@link UserSocial} of the specified {@code openUid} and {@code openProvider}.
     * <p/>
     * Return {@code null} immediately if {@code openUid == null || openUid.length() == 0} or
     * {@code openProvider == null}.
     *
     * @param openUid      the UserSocial openUid.
     * @param openProvider the UserSocial openProvider.
     * @return Returns {@link UserSocial} of the specified {@code openUid} and {@code openProvider},
     *         or {@code null} if not found.
     */
    UserSocial findByOpen(String openUid, OpenProvider openProvider);

    /**
     * Query {@link UserSocial} of the specified {@code userId}.
     * <p/>
     * Return {@code null} immediately if {@code userId == null || userId <= 0}.
     *
     * @param userId the user id.
     * @return Returns {@link UserSocial} of the specified {@code userId}, or {@code null} if not found.
     */
    List<UserSocial> findByUserId(Long userId);
}
