/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) Constellation.java 2013-09-30 14:15
 */

package com.ibbface.domain.model.common;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.ibbface.domain.shared.AbstractValueObject;
import org.joda.time.DateTime;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * The Constellation value object class.
 *
 * @author Fuchun
 * @since 1.0
 */
@JSONType(orders = {"id", "name", "ename", "s_m", "s_d", "e_m", "e_d"})
public class Constellation extends AbstractValueObject<Constellation>
        implements Comparable<Constellation> {
    private static final long serialVersionUID = 1L;

    private static final Map<Short, Constellation> DATA;

    static {
        ImmutableMap.Builder<Short, Constellation> builder = ImmutableMap.builder();
        DATA = builder
                .put((short) 1, new Constellation((short) 1, "白羊座", "Aries", 3, 21, 4, 20))
                .put((short) 2, new Constellation((short) 2, "金牛座", "Taurus", 4, 21, 5, 20))
                .put((short) 3, new Constellation((short) 3, "双子座", "Gemini", 5, 21, 6, 21))
                .put((short) 4, new Constellation((short) 4, "巨蟹座", "Cancer", 6, 22, 7, 22))
                .put((short) 5, new Constellation((short) 5, "狮子座", "Leo", 7, 23, 8, 22))
                .put((short) 6, new Constellation((short) 6, "处女座", "Virgo", 8, 23, 9, 22))
                .put((short) 7, new Constellation((short) 7, "天秤座", "Libra", 9, 23, 10, 23))
                .put((short) 8, new Constellation((short) 8, "天蝎座", "Scorpius", 10, 24, 11, 21))
                .put((short) 9, new Constellation((short) 9, "射手座", "Sagittarius", 11, 22, 12, 21))
                .put((short) 10, new Constellation((short) 10, "摩羯座", "Capricornus", 12, 22, 1, 19))
                .put((short) 11, new Constellation((short) 11, "水瓶座", "Aquarius", 1, 20, 2, 18))
                .put((short) 12, new Constellation((short) 12, "双鱼座", "Pisces", 2, 19, 3, 20))
                .build();
    }

    /**
     * Returns the {@link Constellation} of the specified id, or {@code null} if
     * not exists.
     *
     * @param id the constellation id.
     * @throws IllegalArgumentException if {@code id == null || id <= 0}.
     */
    public static Constellation get(Short id) {
        checkArgument(id != null && id > 0,
                "The given constellation id must not be null or negative.");
        return DATA.get(id);
    }

    /**
     * Returns the {@link Constellation} of the specified {@code name} or {@code ename},
     * or {@code null} if not exists.
     *
     * @param name the constellation name or ename.
     */
    public static Constellation get(String name) {
        if (name == null || (name = name.trim()).length() == 0) {
            return null;
        }
        for (Map.Entry<Short, Constellation> entry : DATA.entrySet()) {
            if (entry.getValue().getName().equals(name) ||
                    entry.getValue().getEname().equals(name)) {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * Returns all {@link Constellation} object.
     */
    public static List<Constellation> all() {
        List<Constellation> list = Lists.newArrayListWithExpectedSize(DATA.size());
        for (Map.Entry<Short, Constellation> entry : DATA.entrySet()) {
            list.add(entry.getValue());
        }
        Collections.sort(list);
        return list;
    }

    /**
     * Calculates and returns the {@link Constellation} of the specified {@link java.util.Date}.
     *
     * @param birthday the specified birthday date.
     * @throws IllegalArgumentException if {@code birthday == null}.
     */
    public static Constellation fromBirthday(Date birthday) {
        checkArgument(birthday != null, "The given `birthday`(Date) must not be null.");
        DateTime birth = new DateTime(birthday);
        return fromBirthday(birth);
    }

    public static Constellation fromBirthday(DateTime birth) {
        checkArgument(birth != null, "The given `birth`(DateTime) must not be null.");
        assert birth != null;
        for (Map.Entry<Short, Constellation> entry : DATA.entrySet()) {
            Constellation c = entry.getValue();
            if ((birth.getMonthOfYear() == c.getStartMonth() &&
                    birth.getDayOfMonth() >= c.getStartDay()) ||
                    (birth.getMonthOfYear() == c.getEndMonth() &&
                            birth.getDayOfMonth() <= c.getEndDay())) {
                return c;
            }
        }
        return null;
    }

    @JSONCreator
    public static Constellation of(@JSONField(name = "id") Short id) {
        return get(id);
    }

    private final Short id;
    private final String name;
    private final String ename;
    private final int startMonth;
    private final int startDay;
    private final int endMonth;
    private final int endDay;

    Constellation(Short id, String name, String ename, int startMonth,
                  int startDay, int endMonth, int endDay) {
        this.id = id;
        this.name = name;
        this.ename = ename;
        this.startMonth = startMonth;
        this.startDay = startDay;
        this.endMonth = endMonth;
        this.endDay = endDay;
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

    @JSONField(name = "s_m")
    public int getStartMonth() {
        return startMonth;
    }

    @JSONField(name = "s_d")
    public int getStartDay() {
        return startDay;
    }

    @JSONField(name = "e_m")
    public int getEndMonth() {
        return endMonth;
    }

    @JSONField(name = "e_d")
    public int getEndDay() {
        return endDay;
    }

    @Override
    public int compareTo(Constellation c) {
        return getId().compareTo(c.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Constellation)) return false;

        Constellation that = (Constellation) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + startMonth;
        result = 31 * result + startDay;
        result = 31 * result + endMonth;
        result = 31 * result + endDay;
        return result;
    }

    @Override
    public String toString() {
        return String.format("Constellation{id=%s, name='%s', startMonth=%d, startDay=%d, " +
                "endMonth=%d, endDay=%d}", getId(), getName(), getStartMonth(),
                getStartDay(), getEndMonth(), getEndDay());
    }
}
