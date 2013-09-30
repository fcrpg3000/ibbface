/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) ConstellationTest.java 2013-09-30 15:00
 */

package com.ibbface.domain.model.common;

import com.alibaba.fastjson.JSON;
import org.hamcrest.core.Is;
import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author Fuchun
 * @since 1.0
 */
public class ConstellationTest {

    @Test
    public void testFromJSON() {
        String json = "{\"id\":1,\"name\":\"白羊座\"}";
        Constellation c = JSON.parseObject(json, Constellation.class);
        Constellation c1 = Constellation.get((short) 1);
        assertNotNull(c);
        assertThat(c, Is.is(c1));
        assertTrue(c == c1);
    }

    @Test
    public void testGetException() {
        try {
            Constellation.get((short) -1);
        } catch (Exception ex) {
            assertTrue(ex instanceof IllegalArgumentException);
            System.out.println(ex.getMessage());
        }
    }

    @Test
    public void testGetName() {
        Constellation c = Constellation.get("天秤座");
        Constellation c1 = Constellation.get("Libra");
        assertNotNull(c);
        assertNotNull(c1);
        assertTrue(c == c1);
    }

    @Test
    public void testFromBirthday() {
        DateTime dateTime = DateTime.now();
        dateTime = dateTime.withYear(1983).withMonthOfYear(2).withDayOfMonth(14);
        Constellation c = Constellation.fromBirthday(dateTime.toDate());
        assertNotNull(c);
        assertEquals(c.getName(), "水瓶座");
    }

    @Test
    public void testJSONString() {
        Constellation c = Constellation.get((short) 2);
        String json = JSON.toJSONString(c);
        String target = String.format("{\"id\":%s,\"name\":\"%s\",\"ename\":\"%s\"," +
                "\"s_m\":%s,\"s_d\":%s,\"e_m\":%s,\"e_d\":%s}", c.getId(), c.getName(),
                c.getEname(), c.getStartMonth(), c.getStartDay(), c.getEndMonth(),
                c.getEndDay());
        assertEquals(json, target);
    }
}
