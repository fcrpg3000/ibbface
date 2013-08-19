/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) ApiParamRepository.java 2013-08-18 16:23
 */

package com.ibbface.repository.privilege;

import com.ibbface.domain.model.privilege.ApiParam;
import com.ibbface.repository.BaseRepository;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * The {@link ApiParam} entity repository interface.
 *
 * @author Fuchun
 * @since 1.0
 */
public interface ApiParamRepository extends BaseRepository<ApiParam, Integer> {

    /**
     * Query and Returns all enabled {@link ApiParam}s list of the specified resource id.
     * <p/>
     * The result always is not {@link null}.
     *
     * @param resourceId the resource id.
     * @return all enabled {@link ApiParam}s list of the specified resource id.
     */
    @Nonnull
    public List<ApiParam> findByResourceId(Integer resourceId);

    /**
     * Query and Returns all enabled {@link ApiParam}s list of the specified
     * resource id and since version.
     * <p/>
     * The result always is not {@link null}.
     *
     * @param resourceId the resource id.
     * @param since      the since version.
     * @return all enabled {@link ApiParam}s list of the specified
     *         resource id and since version.
     */
    @Nonnull
    public List<ApiParam> findByResourceIdAndSince(Integer resourceId, String since);
}
