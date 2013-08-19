/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) Province.java 2013-08-09 12:29
 */

package com.ibbface.domain.model.common;

import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * The province entity class.
 *
 * @author Fuchun
 * @since 1.0
 */
public class Province extends Place<Province> implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String PROP_SHORT_NAME = "shortName";
    public static final String PROP_FULL_NAME = "fullName";
    public static final String PROP_IS_STATE = "isState";
    public static final String PROP_IS_MUNICIPALITY = "municipality";

    private Integer countryId;
    private String shortName;
    private String fullName;
    private boolean isState;
    private boolean isMunicipality;

    private Country country;

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
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

    public boolean getIsState() {
        return isState;
    }

    public void setIsState(boolean state) {
        isState = state;
    }

    public boolean isMunicipality() {
        return isMunicipality;
    }

    public void setMunicipality(boolean municipality) {
        isMunicipality = municipality;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    /**
     * @throws UnsupportedOperationException cannot update province.
     */
    @Override
    public Province update(Province other) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add(PROP_ID, getId())
                .add(PROP_COUNTRY_ID, getCountryId())
                .add(PROP_CN_NAME, getCnName())
                .add(PROP_EN_NAME, getEnName())
                .add(PROP_SHORT_NAME, getShortName())
                .add(PROP_FULL_NAME, getFullName())
                .add(PROP_CODE, getCode())
                .add(PROP_IS_STATE, getIsState())
                .add(PROP_IS_MUNICIPALITY, isMunicipality())
                .toString();
    }
}
