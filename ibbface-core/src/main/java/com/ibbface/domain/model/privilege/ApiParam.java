/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) ApiParam.java 2013-08-17 00:37
 */

package com.ibbface.domain.model.privilege;

import com.ibbface.domain.model.privilege.base.BaseApiParam;
import com.ibbface.domain.shared.QueryValue;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * The {@link ApiResource} parameter entity class.
 *
 * @author Fuchun
 * @since 1.0
 */
public class ApiParam extends BaseApiParam implements QueryValue {
    private static final long serialVersionUID = 1L;

    public static ApiParam create(
            @Nullable ApiResource resource, String name, String rule,
            ParameterType type, String defVal, String desc, String since,
            Integer order, boolean isRequired) {
        Integer resourceId = 0;
        if (resource != null) {
            resourceId = resource.getId();
        }
        ApiParam param = new ApiParam(resourceId, name, rule, type.getName(),
                defVal, desc, since, order, isRequired, false);
        param.setType(type);
        return param;
    }

    private ParameterType type;

    public ApiParam() {
        super();
    }

    public ApiParam(Integer id) {
        super(id);
    }

    public ApiParam(Integer resId, String name, String rule, String type,
                    String defVal, String desc, String since, Integer order,
                    boolean isRequired, boolean isDeprecated) {
        super(resId, name, rule, type, defVal, desc, since, order, isRequired, isDeprecated);
    }

    @Override
    public void setParamType(String paramType) {
        super.setParamType(paramType);
        if (paramType != null && paramType.length() > 0) {
            type = ParameterType.of(paramType, ParameterType.STRING);
        }
    }

    public ParameterType getType() {
        return type;
    }

    public void setType(ParameterType type) {
        this.type = type;
        if (type != null) {
            super.setParamType(type.getName());
        }
    }

    /**
     * 使用给定的实体，更新当前实体的信息。通常是已改变的属性的变更。
     *
     * @param other 新的实体。
     * @return 返回当前实体对象。
     * @throws IllegalStateException if {@code !other.getId().equals(getId())}.
     */
    @Override
    public ApiParam update(ApiParam other) {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[]{
                getId(), getResourceId(), getParamName(), getParamRule(),
                getParamType(), getDefaultValue(), getDescription(), getSince(),
                getSortOrder(), isRequired(), isDeprecated()
        };
    }

    /**
     * Returns the default value actual type.
     */
    public Object defaultValue() {
        if (getDefaultValue() == null || getDefaultValue().length() == 0) {
            return null;
        }
        if (getType() == null) {
            return getDefaultValue();
        }
        Class<?> pType = getType().getType();
        if (pType == Long.class) {
            return Long.valueOf(getDefaultValue());
        } else if (pType == Integer.class) {
            return Integer.valueOf(getDefaultValue());
        } else if (pType == Short.class) {
            return Short.valueOf(getDefaultValue());
        } else if (pType == Byte.class) {
            return Byte.valueOf(getDefaultValue());
        } else if (pType == Boolean.class) {
            return "1".equals(getDefaultValue()) || "yes".equals(getDefaultValue()) ||
                    "true".equals(getDefaultValue()) || "on".equals(getDefaultValue())
                    ? Boolean.TRUE : Boolean.FALSE;
        } else if (pType == BigDecimal.class) {
            return new BigDecimal(getDefaultValue());
        } else if (pType == BigInteger.class) {
            return new BigInteger(getDefaultValue());
        } else {
            // default string
            return getDefaultValue();
        }
    }
}
