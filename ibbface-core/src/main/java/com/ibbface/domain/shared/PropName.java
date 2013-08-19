/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) PropName.java 2013-07-28 00:08
 */

package com.ibbface.domain.shared;

/**
 * Commons entity's property name constants interface.
 *
 * @author Fuchun
 * @since 1.0
 */
public interface PropName {

    /**
     * Default name for PK property id.
     */
    String PROP_ID = "id";

    /**
     * The entity's id alias property name.
     */
    String PROP_ID_ALIAS = "idAlias";

    /**
     * user's id property name.
     */
    String PROP_USER_ID = "userId";

    /**
     * user's name property name.
     */
    String PROP_USER_NAME = "userName";

    /**
     * The `name` property name.
     */
    String PROP_NAME = "name";

    /**
     * Record's status property name.
     */
    String PROP_STATUS = "status";

    /**
     * Record created time property name.
     */
    String PROP_CREATED_TIME = "createdTime";

    /**
     * Record last modified time property name.
     */
    String PROP_LAST_MODIFIED_TIME = "lastModifiedTime";

    /**
     * Record last modified by whose property name.
     */
    String PROP_LAST_MODIFIED_BY = "lastModifiedBy";
}
