/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) UserRepository.java 2013-07-29 12:20
 */

package com.ibbface.repository.user;

import com.ibbface.domain.model.user.User;
import com.ibbface.repository.BaseRepository;

/**
 * {@link com.ibbface.domain.model.user.User} entity model repository interface.
 *
 * @author Fuchun
 * @since 1.0
 */
public interface UserRepository extends BaseRepository<User, Long> {

    public User findByEmail(String email);

    public User findByUserName(String userName);

    public User findByMobileNo(String mobileNo);
}
