/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) District.java 2013-08-09 18:12
 */

package com.ibbface.domain.model.common;

import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * The district entity class.
 *
 * @author Fuchun
 * @since 1.0
 */
public class District extends Place<District> implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String PROP_SHORT_NAME = "shortName";
    public static final String PROP_FULL_NAME = "fullName";

    private Integer countryId;
    private Integer proId;
    private Integer cityId;
    private String shortName;
    private String fullName;

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public Integer getProId() {
        return proId;
    }

    public void setProId(Integer proId) {
        this.proId = proId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @throws UnsupportedOperationException cannot update district.
     */
    @Override
    public District update(District other) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add(PROP_ID, getId())
                .add(PROP_CN_NAME, getCnName())
                .add(PROP_EN_NAME, getEnName())
                .add(PROP_CODE, getCode())
                .add(PROP_COUNTRY_ID, getCountryId())
                .add(PROP_PROVINCE_ID, getProId())
                .add(PROP_CITY_ID, getCityId())
                .add(PROP_SHORT_NAME, getShortName())
                .add(PROP_FULL_NAME, getFullName())
                .toString();
    }
}
