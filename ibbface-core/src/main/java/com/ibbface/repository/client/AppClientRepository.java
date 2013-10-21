/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) AppClientRepository.java 2013-08-04 23:03
 */

package com.ibbface.repository.client;

import com.ibbface.domain.model.client.AppClient;
import com.ibbface.repository.BaseRepository;

import java.util.List;

/**
 * {@link AppClient} entity repository interface.
 *
 * @author Fuchun
 * @since 1.0
 */
public interface AppClientRepository extends BaseRepository<AppClient, Integer> {

    public List<AppClient> findByVersion(String version);

    public AppClient findByTypeAndVersion(Short typeCode, String version);
}
