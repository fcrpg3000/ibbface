/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) ApiResource.java 2013-08-17 00:04
 */

package com.ibbface.domain.model.privilege;

import com.google.common.base.Joiner;
import com.ibbface.domain.model.privilege.base.BaseApiResource;
import com.ibbface.domain.model.user.UserRole;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

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
        ApiResource resource = new ApiResource(parentId, UserRole.GUEST.getId(),
                basePath, description, version, grade.getCode(), httpMethod,
                "JSON", others, requireLogin, enabled);
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
    private Set<UserRole> roles;

    public ApiResource() {
        super();
    }

    public ApiResource(Integer id) {
        super(id);
    }

    public ApiResource(Integer parentId, Integer roleData, String basePath,
                       String description, String version, Short accessGrade,
                       String httpMethod, String dataType, String others,
                       boolean requireLogin, boolean enabled) {
        super(parentId, roleData, basePath, description, version, accessGrade,
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

    @Override
    public void setRoleData(Integer roleData) {
        super.setRoleData(roleData);
        if (roleData != null) {
            roles = UserRole.fromRoleData(roleData);
        }
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
        if (roles != null && roles.size() > 0) {
            super.setRoleData(UserRole.toRoleData(roles));
        }
    }

    public boolean containsRole(String roleName) {
        if (roleName == null || roleName.length() == 0) {
            return false;
        }
        return UserRole.findId(roleName) != null;
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

    /**
     * Returns a URI string of this resource of the specified {@code url}.
     *
     * @param url the api url prefix. like https://api.ibbface.com
     * @return a uri string of this resource.
     */
    public String getURIString(final String url) {
        checkArgument(url != null && url.length() > 0,
                "The given url prefix must be not null or empty.");
        return Joiner.on("/").join(url, getVersion(), getBasePath());
    }

    public Object[] toArray() {
        return new Object[]{
                getId(), getParentId(), getRoleData(), getBasePath(), getDescription(),
                getVersion(), getGradeCode(), getHttpMethod(), getDataType(),
                getOthers(), isRequireLogin(), isEnabled(), getCreatedTime()
        };
    }
}
