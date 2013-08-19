/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) EntityNotFoundException.java 2013-08-03 22:59
 */

package com.ibbface.domain.exception;

import java.io.Serializable;

/**
 * Define the runtime exception for the entity not found in system.
 *
 * @author Fuchun
 * @since 1.0
 */
public class EntityNotFoundException extends RuntimeException {

    private final Serializable entityId;
    private final Class<?> entityClass;

    public EntityNotFoundException(Serializable entityId, Class<?> entityClass) {
        this(entityId, entityClass, null);
    }

    public EntityNotFoundException(Serializable entityId, Class<?> entityClass, String s) {
        super(s);
        this.entityId = entityId;
        this.entityClass = entityClass;
    }

    public Serializable getEntityId() {
        return entityId;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }
}
