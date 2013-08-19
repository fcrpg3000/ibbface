/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) ApiParamTest.java 2013-08-18 22:58
 */

package com.ibbface.domain.model.privilege;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * The {@link ApiParam} domain model test case.
 *
 * @author Fuchun
 * @since 1.0
 */
public class ApiParamTest {

    private ApiParam stringParam, boolParam, numberParam,
            decimalParam, bigintParam;

    @Before
    public void setUp() throws Exception {
        stringParam = ApiParam.create(null, "name", "\\w+",
                ParameterType.STRING, null, "", "1", 0, true);

        boolParam = ApiParam.create(null, "auto", null,
                ParameterType.BOOLEAN, Boolean.FALSE.toString(), "", "1", 0, false);

        numberParam = ApiParam.create(null, "num", "\\d+",
                ParameterType.INT8, "0", null, "1", 0, true);

        decimalParam = ApiParam.create(null, "decimal", null,
                ParameterType.DECIMAL, "0.0", null, "1", 0, false);

        bigintParam = ApiParam.create(null, "bigint", null,
                ParameterType.BIGINT, "100000000000000000000000000000", null, "1", 0, false);
    }

    @Test
    public void testBoolParam() {
        Object defVal = boolParam.defaultValue();
        assertNotNull(defVal);
        assertTrue(defVal instanceof Boolean);
        assertFalse((Boolean) defVal);

        boolParam.setDefaultValue("1");
        defVal = boolParam.defaultValue();
        assertTrue(defVal instanceof Boolean);
        assertTrue((Boolean) defVal);

        boolParam.setDefaultValue("yes");
        defVal = boolParam.defaultValue();
        assertTrue(defVal instanceof Boolean);
        assertTrue((Boolean) defVal);

        boolParam.setDefaultValue("on");
        defVal = boolParam.defaultValue();
        assertTrue(defVal instanceof Boolean);
        assertTrue((Boolean) defVal);

        boolParam.setDefaultValue("true");
        defVal = boolParam.defaultValue();
        assertTrue(defVal instanceof Boolean);
        assertTrue((Boolean) defVal);
    }

    @Test
    public void testStringParam() {
        Object defVal = stringParam.defaultValue();
        assertNull(defVal);

        stringParam.setDefaultValue("System");
        defVal = stringParam.defaultValue();
        assertNotNull(defVal);
        assertTrue(defVal instanceof String);
    }

    @Test
    public void testNumberParam() {
        Object defVal = numberParam.defaultValue();
        assertNotNull(defVal);
        assertTrue(defVal instanceof Number);
        assertTrue(defVal instanceof Byte);

        numberParam.setType(ParameterType.INT16);
        defVal = numberParam.defaultValue();
        assertTrue(defVal instanceof Short);

        numberParam.setType(ParameterType.INT32);
        defVal = numberParam.defaultValue();
        assertTrue(defVal instanceof Integer);

        numberParam.setType(ParameterType.INT64);
        defVal = numberParam.defaultValue();
        assertTrue(defVal instanceof Long);
    }

    @Test
    public void testDecimalParam() {
        Object defVal = decimalParam.defaultValue();
        assertNotNull(defVal);
        assertTrue(defVal instanceof BigDecimal);
    }

    @Test
    public void testBigintParam() {
        Object defVal = bigintParam.defaultValue();
        assertNotNull(defVal);
        assertTrue(defVal instanceof BigInteger);
    }
}
