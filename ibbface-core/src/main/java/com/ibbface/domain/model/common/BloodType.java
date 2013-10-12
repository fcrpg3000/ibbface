/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) BloodType.java 2013-09-30 16:18
 */

package com.ibbface.domain.model.common;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.ibbface.domain.shared.AbstractValueObject;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author Fuchun
 * @since 1.0
 */
public class BloodType extends AbstractValueObject<BloodType>
        implements Comparable<BloodType> {
    private static final long serialVersionUID = 1L;

    private static final Map<Short, BloodType> DATA;

    static {
        ImmutableMap.Builder<Short, BloodType> builder = ImmutableMap.builder();
        DATA = builder
                .put((short) 1, new BloodType(1, "A", "A blood group"))
                .put((short) 2, new BloodType(2, "B", "B blood group"))
                .put((short) 3, new BloodType(3, "AB", "AB blood group"))
                .put((short) 4, new BloodType(4, "O", "O blood group"))
                .put((short) 5, new BloodType(5, "其他", "Other"))
                .build();
    }

    /**
     * Returns the {@link BloodType} of the specified id, or {@code null} if
     * not exists.
     *
     * @param id  the blood type id(must be not null and greater than 0).
     * @param <T> the id type.
     * @return a {@link BloodType} of the specified id, or {@code null} if
     *         not exists.
     * @throws IllegalArgumentException if {@code id == null || id <= 0}.
     */
    public static <T extends Number> BloodType get(T id) {
        checkArgument(id != null && id.intValue() > 0,
                "The given BloodType id must not be null or negative.");
        assert id != null;
        return DATA.get(id.shortValue());
    }

    /**
     * Returns the {@link BloodType} of the specified {@code name}(name or ename),
     * or {@code null} if the name not exists or {@code name == null || name.length() == 0}.
     *
     * @param name the blood type name or ename.
     * @return a {@link BloodType} of the specified {@code name}(name or ename),
     *         or {@code null} if the name not exists.
     */
    public static BloodType get(String name) {
        if (name == null || (name = name.trim()).length() == 0) {
            return null;
        }
        for (Map.Entry<Short, BloodType> entry : DATA.entrySet()) {
            if (entry.getValue().getName().equals(name) ||
                    entry.getValue().getEname().equals(name)) {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * Returns all {@link BloodType} of sort by id.
     */
    public static List<BloodType> all() {
        List<BloodType> list = Lists.newArrayListWithExpectedSize(DATA.size());
        for (Map.Entry<Short, BloodType> entry : DATA.entrySet()) {
            list.add(entry.getValue());
        }
        Collections.sort(list);
        return list;
    }

    /**
     * Returns a exist {@link BloodType} from JSON engine.
     *
     * @param id the blood type id.
     */
    @JSONCreator
    public static BloodType of(@JSONField(name = "id") Short id) {
        if (id == null || id <= 0) {
            return null;
        }
        return DATA.get(id);
    }

    private final Short id;
    private final String name;
    private final String ename;

    BloodType(int id, String name, String ename) {
        this.id = (short) id;
        this.name = name;
        this.ename = ename;
    }

    public Short getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEname() {
        return ename;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BloodType)) return false;

        BloodType bloodType = (BloodType) o;

        return !(id != null ? !id.equals(bloodType.id) : bloodType.id != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (ename != null ? ename.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(BloodType bloodType) {
        return getId().compareTo(bloodType.getId());
    }

    @Override
    public String toString() {
        return String.format("BloodType{id=%s, name='%s', ename='%s'}",
                getId(), getName(), getEname());
    }
}
