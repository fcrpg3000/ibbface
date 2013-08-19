/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) City.java 2013-08-09 13:50
 */

package com.ibbface.domain.model.common;

import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * The city entity class.
 *
 * @author Fuchun
 * @since 1.0
 */
public class City extends Place<City> implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer countryId;
    private Integer proId;

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

    /**
     * @throws UnsupportedOperationException cannot update city.
     */
    @Override
    public City update(City other) {
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
                .toString();
    }
}
