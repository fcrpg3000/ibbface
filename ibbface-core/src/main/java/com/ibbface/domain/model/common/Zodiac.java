/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) Zodiac.java 2013-09-30 16:36
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
public class Zodiac extends AbstractValueObject<Zodiac>
        implements Comparable<Zodiac> {
    private static final long serialVersionUID = 1L;

    private static final Map<Short, Zodiac> DATA;

    static {
        ImmutableMap.Builder<Short, Zodiac> builder = ImmutableMap.builder();
        DATA = builder
                .put((short) 1, new Zodiac(1, "鼠", "Rat"))
                .put((short) 2, new Zodiac(2, "牛", "Ox"))
                .put((short) 3, new Zodiac(3, "虎", "Tiger"))
                .put((short) 4, new Zodiac(4, "兔", "Hare"))
                .put((short) 5, new Zodiac(5, "龙", "Dragon"))
                .put((short) 6, new Zodiac(6, "蛇", "Snake"))
                .put((short) 7, new Zodiac(7, "马", "Horse"))
                .put((short) 8, new Zodiac(8, "羊", "Sheep"))
                .put((short) 9, new Zodiac(9, "猴", "Monkey"))
                .put((short) 10, new Zodiac(10, "鸡", "Cock"))
                .put((short) 11, new Zodiac(11, "狗", "Dog"))
                .put((short) 12, new Zodiac(12, "猪", "Boar"))
                .build();
    }

    /**
     * Returns the {@link Zodiac} of the specified id, or {@code null} if
     * not exists.
     *
     * @param id  the Zodiac id (must not be null or negative).
     * @param <T> the id type.
     * @return a {@link Zodiac} of the specified id, or {@code null} if
     *         not exists.
     * @throws IllegalArgumentException if {@code id == null || id <= 0}.
     */
    public static <T extends Number> Zodiac get(T id) {
        checkArgument(id != null && id.intValue() > 0,
                "The given Zodiac id must not be null or negative.");
        assert id != null && id.intValue() > 0;
        return DATA.get(id.shortValue());
    }

    /**
     * Returns the {@link Zodiac} of the specified name(ename), or {@code null}
     * if not exists or {@code name} is {@code null} or empty string.
     *
     * @param name the Zodiac name or ename.
     */
    public static Zodiac get(String name) {
        if (name == null || (name = name.trim()).length() == 0) {
            return null;
        }
        for (Map.Entry<Short, Zodiac> entry : DATA.entrySet()) {
            if (entry.getValue().getName().equals(name) ||
                    entry.getValue().getEname().equals(name)) {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * Returns all {@link Zodiac} of sort by id.
     */
    public static List<Zodiac> all() {
        List<Zodiac> list = Lists.newArrayListWithExpectedSize(DATA.size());
        for (Map.Entry<Short, Zodiac> entry : DATA.entrySet()) {
            list.add(entry.getValue());
        }
        Collections.sort(list);
        return list;
    }

    @JSONCreator
    public static Zodiac of(@JSONField(name = "id") Short id) {
        if (id == null || id.intValue() <= 0) {
            return null;
        }
        return DATA.get(id);
    }

    private final Short id;
    private final String name;
    private final String ename;

    Zodiac(int id, String name, String ename) {
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
        if (!(o instanceof Zodiac)) return false;

        Zodiac zodiac = (Zodiac) o;

        return !(id != null ? !id.equals(zodiac.id) : zodiac.id != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (ename != null ? ename.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Zodiac zodiac) {
        return getId().compareTo(zodiac.getId());
    }

    @Override
    public String toString() {
        return String.format("Zodiac{id=%s, name='%s', ename='%s'}",
                getId(), getName(), getEname());
    }
}
