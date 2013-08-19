/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) Place.java 2013-08-09 13:06
 */

package com.ibbface.domain.model.common;

import com.ibbface.domain.shared.AbstractEntity;

import java.io.Serializable;

/**
 * It indicates place (country, province(state), city or district).
 *
 * @author Fuchun
 * @since 1.0
 */
public abstract class Place<T> extends AbstractEntity<Integer, T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 国家ID属性名。
     */
    public static final String PROP_COUNTRY_ID = "countryId";

    /**
     * 省（直辖市）ID属性名。
     */
    public static final String PROP_PROVINCE_ID = "proId";

    /**
     * 城市ID属性名。
     */
    public static final String PROP_CITY_ID = "cityId";

    /**
     * 地区中文名称属性名。
     */
    public static final String PROP_CN_NAME = "cnName";

    /**
     * 地区英文名称属性名。
     */
    public static final String PROP_EN_NAME = "enName";

    /**
     * 地址编码（国家：国际编码、省市县：身份证编码或对应编码）属性名
     */
    public static final String PROP_CODE = "code";

    private String cnName;
    private String enName;
    private String code;

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
