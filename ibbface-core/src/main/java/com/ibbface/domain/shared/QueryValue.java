/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) QueryValue.java 2013-10-09 14:34
 */

package com.ibbface.domain.shared;

/**
 * @author Fuchun
 * @since 1.0
 */
public interface QueryValue {

    /**
     * Returns this QueryValue all serializable field values.
     */
    public Object[] toArray();
}
