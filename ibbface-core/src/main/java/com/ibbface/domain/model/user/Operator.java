/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) Operator.java 2013-08-02 12:48
 */

package com.ibbface.domain.model.user;

import com.ibbface.domain.shared.AbstractValueObject;

import java.io.Serializable;

/**
 * @author Fuchun
 * @since 1.0
 */
public class Operator extends AbstractValueObject<Operator> implements Serializable {
    private static final long serialVersionUID = 1L;

    public static Operator of(Integer id, String name) {
        return new Operator(id, name);
    }

    private final Integer id;
    private final String name;

    Operator(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Operator)) return false;

        Operator operator = (Operator) o;

        return !(id != null ? !id.equals(operator.id) : operator.id != null) &&
                !(name != null ? !name.equals(operator.name) : operator.name != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("Operator{id=%d, name='%s'}", id, name);
    }
}
