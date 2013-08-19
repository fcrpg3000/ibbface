/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) BaseApiParam.java 2013-08-17 00:37
 */

package com.ibbface.domain.model.privilege.base;

import com.google.common.base.Objects;
import com.ibbface.domain.model.privilege.ApiParam;
import com.ibbface.domain.shared.AbstractEntity;

/**
 * @author Fuchun
 * @since 1.0
 */
public abstract class BaseApiParam extends AbstractEntity<Integer, ApiParam> {
    private static final long serialVersionUID = 1L;

    public static final String PROP_RESOURCE_ID = "resourceId";
    public static final String PROP_PARAM_NAME = "paramName";
    public static final String PROP_PARAM_RULE = "paramRule";
    public static final String PROP_PARAM_TYPE = "paramType";
    public static final String PROP_DEFAULT_VALUE = "defaultValue";
    public static final String PROP_DESCRIPTION = "description";
    public static final String PROP_SINCE = "since";
    public static final String PROP_SORT_ORDER = "sortOrder";
    public static final String PROP_IS_REQUIRED = "required";
    public static final String PROP_IS_DEPRECATED = "deprecated";

    private Integer resourceId;
    private String paramName;
    private String paramRule;
    private String paramType;
    private String defaultValue;
    private String description;
    private String since;
    private Integer sortOrder;
    private boolean required;
    private boolean deprecated;

    protected BaseApiParam() {
        super();
    }

    protected BaseApiParam(Integer id) {
        setId(id);
    }

    protected BaseApiParam(Integer resId, String name, String rule, String type,
                       String defVal, String desc, String since, Integer order,
                       boolean isRequired, boolean isDeprecated) {
        resourceId = resId;
        paramName = name;
        paramRule = rule;
        paramType = type;
        defaultValue = defVal;
        description = desc;
        sortOrder = order;
        required = isRequired;
        deprecated = isDeprecated;
        this.since = since;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamRule() {
        return paramRule;
    }

    public void setParamRule(String paramRule) {
        this.paramRule = paramRule;
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSince() {
        return since;
    }

    public void setSince(String since) {
        this.since = since;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isDeprecated() {
        return deprecated;
    }

    public void setDeprecated(boolean deprecated) {
        this.deprecated = deprecated;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(getClass())
                .add(PROP_ID, getId())
                .add(PROP_RESOURCE_ID, getResourceId())
                .add(PROP_PARAM_NAME, getParamName())
                .add(PROP_PARAM_RULE, getParamRule())
                .add(PROP_PARAM_TYPE, getParamType())
                .add(PROP_DEFAULT_VALUE, getDefaultValue())
                .add(PROP_DESCRIPTION, getDescription())
                .add(PROP_SINCE, getSince())
                .add(PROP_SORT_ORDER, getSortOrder())
                .add(PROP_IS_REQUIRED, isRequired())
                .add(PROP_IS_DEPRECATED, isDeprecated())
                .toString();
    }
}
