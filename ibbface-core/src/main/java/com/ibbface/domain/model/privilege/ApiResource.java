/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) ApiResource.java 2013-08-17 00:04
 */

package com.ibbface.domain.model.privilege;

import com.ibbface.domain.model.privilege.base.BaseApiResource;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

/**
 * The api resource entity class.
 *
 * @author Fuchun
 * @since 1.0
 */
public class ApiResource extends BaseApiResource {
    private static final long serialVersionUID = 1L;

    public static final Integer PARENT_PARENT_ID = 0;
    public static final String POST = "POST";
    public static final String GET = "GET";

    public static ApiResource newResource(
            @Nullable ApiResource parent, String basePath, String description,
            String version, Grade grade, String httpMethod, String others,
            boolean requireLogin, boolean enabled) {
        Integer parentId = 0;
        if (parent != null) {
            parentId = parent.getId();
        }
        ApiResource resource = new ApiResource(parentId, basePath, description, version,
                grade.getCode(), httpMethod, "JSON", others, requireLogin, enabled);
        return resource;
    }

    public static ApiResource parent(
            String basePath, String description, String version, Grade grade,
            String httpMethod, String others, boolean requireLogin, boolean enabled) {
        return newResource(null, basePath, description, version, grade,
                httpMethod, others, requireLogin, enabled);
    }

    public static ApiResource sub(
            @Nonnull ApiResource parent, String basePath, String description,
            Grade grade, String httpMethod, String others, boolean requireLogin, boolean enabled) {
        return newResource(parent, basePath, description, parent.getVersion(),
                grade, httpMethod, others, requireLogin, enabled);
    }

    private ApiResource parent;
    private Grade grade;
    private Set<ApiParam> params;

    public ApiResource() {
        super();
    }

    public ApiResource(Integer id) {
        super(id);
    }

    public ApiResource(Integer parentId, String basePath, String description,
                       String version, Short accessGrade, String httpMethod,
                       String dataType, String others, boolean requireLogin,
                       boolean enabled) {
        super(parentId, basePath, description, version, accessGrade,
                httpMethod, dataType, others, requireLogin, enabled);
    }

    public ApiResource getParent() {
        return parent;
    }

    public void setParent(ApiResource parent) {
        this.parent = parent;
        if (parent != null) {
            super.setParentId(parent.getId());
        }
    }

    @Override
    public void setGradeCode(Short gradeCode) {
        super.setGradeCode(gradeCode);
        if (gradeCode != null) {
            this.grade = Grade.of(gradeCode, Grade.NORMAL);
        }
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
        if (grade != null) {
            super.setGradeCode(grade.getCode());
        }
    }

    public Set<ApiParam> getParams() {
        return params;
    }

    public void setParams(Set<ApiParam> params) {
        this.params = params;
    }

    /**
     * 使用给定的实体，更新当前实体的信息。通常是已改变的属性的变更。
     *
     * @param other 新的实体。
     * @return 返回当前实体对象。
     * @throws IllegalStateException if {@code !other.getId().equals(getId())}.
     */
    @Override
    public ApiResource update(ApiResource other) {
        return null;
    }

    public String getURL(final String urlPrefix) {
        return String.format("%s/%s/%s", urlPrefix, getVersion(), getBasePath());
    }

    public Object[] toArray() {
        return new Object[]{
                getId(), getParentId(), getBasePath(), getDescription(), getVersion(),
                getGradeCode(), getHttpMethod(), getDataType(),
                getOthers(), isRequireLogin(), isEnabled(), getCreatedTime()
        };
    }
}
