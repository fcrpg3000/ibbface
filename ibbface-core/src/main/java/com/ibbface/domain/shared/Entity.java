/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) Entity.java 2013-07-27 23:57
 */

package com.ibbface.domain.shared;

import java.io.Serializable;

/**
 * 实现此接口的类，表示一个实体类。
 *
 * @author Fuchun
 * @since 1.0
 */
public interface Entity<PK extends Serializable, T>
        extends PropName, Serializable {

    /**
     * ISO8601 Date format pattern.
     */
    String ISO8601_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss,SSS";

    /**
     * 返回当前实体的主键。
     */
    public PK getId();

    /**
     * 设置当前实体的主键。
     *
     * @param id 当前实体的主键。
     */
    public void setId(PK id);

    /**
     * 检查当前是否是一个新的实体类。
     *
     * @return 如果当前实体是一个实体类，则返回 {@code true}，否则 {@code false}。
     */
    public boolean isNew();

    /**
     * 实体类对象是否相等，只比较唯一标识（PK），不比较其他属性。
     * <p/>
     * 此接口方法类似于 sameIdentityAs 方法，但 sameIdentityAs 方法常用于比较两个对象是否相等。
     *
     * @param other 要比较的对象。
     * @return 如果实体相等，则返回 {@code true}，否则 {@code false}。
     */
    boolean sameIdentityAs(T other);

    /**
     * 使用给定的实体，更新当前实体的信息。通常是已改变的属性的变更。
     *
     * @param other 新的实体。
     * @return 返回当前实体对象。
     * @throws IllegalStateException if {@code !other.getId().equals(getId())}.
     */
    public T update(T other);
}
