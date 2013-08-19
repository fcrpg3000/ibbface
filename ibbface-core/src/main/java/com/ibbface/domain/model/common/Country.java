/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) Country.java 2013-08-09 12:06
 */

package com.ibbface.domain.model.common;

import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * The country entity class.
 *
 * @author Fuchun
 * @since 1.0
 */
public class Country extends Place<Country> implements Serializable {
    private static final long serialVersionUID = 1L;

    public Country() {
        super();
    }

    /**
     * @throws UnsupportedOperationException Cannot update country information.
     */
    @Override
    public Country update(Country other) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add(PROP_ID, getId())
                .add(PROP_CN_NAME, getCnName())
                .add(PROP_EN_NAME, getEnName())
                .add(PROP_CODE, getCode())
                .toString();
    }
}
