/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) BaseApiResource.java 2013-08-17 00:04
 */

package com.ibbface.domain.model.privilege.base;

import com.google.common.base.Objects;
import com.ibbface.domain.model.privilege.ApiResource;
import com.ibbface.domain.shared.AbstractEntity;

import java.util.Date;

/**
 * @author Fuchun
 * @since 1.0
 */
public abstract class BaseApiResource extends AbstractEntity<Integer, ApiResource> {
    private static final long serialVersionUID = 1L;

    public static final String PROP_PARENT_ID = "parentId";
    public static final String PROP_ROLE_DATA = "roleData";
    public static final String PROP_BASE_PATH = "basePath";
    public static final String PROP_DESCRIPTION = "description";
    public static final String PROP_VERSION = "version";
    public static final String PROP_GRADE_CODE = "gradeCode";
    public static final String PROP_HTTP_METHOD = "httpMethod";
    public static final String PROP_DATA_TYPE = "dataType";
    public static final String PROP_OTHERS = "others";
    public static final String PROP_REQUIRE_LOGIN = "requireLogin";
    public static final String PROP_IS_ENABLED = "enabled";

    private Integer parentId;
    private Integer roleData;
    private String basePath;
    private String description;
    private String version;
    private Short gradeCode;
    private String httpMethod;
    private String dataType;
    private String others;
    private boolean requireLogin = true;
    private boolean isEnabled = true;
    private Date createdTime;

    protected BaseApiResource() {
        parentId = 0;
    }

    protected BaseApiResource(Integer id) {
        setId(id);
        parentId = 0;
    }

    protected BaseApiResource(Integer parentId, Integer roleData, String basePath,
                              String description, String version, Short gradeCode,
                              String httpMethod, String dataType, String others,
                              boolean requireLogin, boolean enabled) {
        this.parentId = parentId;
        this.roleData = roleData;
        this.basePath = basePath;
        this.description = description;
        this.version = version;
        this.gradeCode = gradeCode;
        this.httpMethod = httpMethod;
        this.dataType = dataType;
        this.others = others;
        this.requireLogin = requireLogin;
        isEnabled = enabled;
        createdTime = new Date();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getRoleData() {
        return roleData;
    }

    public void setRoleData(Integer roleData) {
        this.roleData = roleData;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Short getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(Short gradeCode) {
        this.gradeCode = gradeCode;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public boolean isRequireLogin() {
        return requireLogin;
    }

    public void setRequireLogin(boolean requireLogin) {
        this.requireLogin = requireLogin;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(getClass())
                .add(PROP_ID, getId())
                .add(PROP_PARENT_ID, getParentId())
                .add(PROP_ROLE_DATA, getRoleData())
                .add(PROP_BASE_PATH, getBasePath())
                .add(PROP_DESCRIPTION, getDescription())
                .add(PROP_VERSION, getVersion())
                .add(PROP_GRADE_CODE, getGradeCode())
                .add(PROP_HTTP_METHOD, getHttpMethod())
                .add(PROP_DATA_TYPE, getDataType())
                .add(PROP_OTHERS, getOthers())
                .add(PROP_REQUIRE_LOGIN, isRequireLogin())
                .add(PROP_IS_ENABLED, isEnabled())
                .add(PROP_CREATED_TIME, getCreatedTime())
                .toString();
    }
}
