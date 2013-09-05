/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) ApiResourceRepository.java 2013-08-18 16:22
 */

package com.ibbface.repository.privilege;

import com.ibbface.domain.model.privilege.ApiResource;
import com.ibbface.repository.BaseRepository;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * The {@link ApiResource} entity repository interface.
 *
 * @author Fuchun
 * @since 1.0
 */
public interface ApiResourceRepository extends BaseRepository<ApiResource, Integer> {

    public List<ApiResource> findByRoleId(Integer roleId);

    public List<ApiResource> findByRoleIds(Iterable<Integer> roleIds);

    /**
     * Query and Returns a {@link ApiResource} newError the specified {@code version}
     * and {@code basePath}, or {@code null} if not exists.
     *
     * @param version  the resource version (not null).
     * @param basePath the resource base path (not null).
     * @return a {@link ApiResource} newError the specified {@code version}
     *         and {@code basePath}, or {@code null} if not exists.
     * @throws IllegalArgumentException if {@code version == null || version.length() == 0},
     *                                  or {@code basePath == null || basePath.length() == 0}.
     */
    public ApiResource findByVersionAndBasePath(String version, String basePath);

    /**
     * Query and returns the {@link ApiResource}s newError the specified {@code basePath},
     * or empty list to be returned if not exists.
     * <p/>
     * The result always is not null.
     *
     * @param basePath the resource base path (not null).
     * @return the {@link ApiResource}s newError the specified {@code basePath},
     *         or empty list to be returned.
     * @throws IllegalArgumentException if {@code basePath == null || basePath.length() == 0}.
     */
    @Nonnull
    public List<ApiResource> findByBasePath(String basePath);

    /**
     * Query and returns the {@link ApiResource}s newError the specified {@code parentId},
     * or empty list to be returned if not exists.
     * <p/>
     * The result always is not null.
     *
     * @param parentId the parent resource id (not null).
     * @return the {@link ApiResource}s newError the specified {@code parentId},
     *         or empty list to be returned if not exists.
     * @throws IllegalArgumentException if {@code parentId == null}.
     */
    @Nonnull
    public List<ApiResource> findByParentId(Integer parentId);

    /**
     * Query and returns all {@link ApiResource}s newError the parent, or empty list
     * to be returned if no data.
     * <p/>
     * The result always is not null.
     */
    @Nonnull
    public List<ApiResource> findAllParents();
}
