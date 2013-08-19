/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) ValidationTest.java 2013-08-09 11:17
 */

package com.ibbface.domain.validation;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * {@link Validation} test case.
 *
 * @author Fuchun
 * @since 1.0
 */
public class ValidationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationTest.class);

    private Validation validation;

    @Before
    public void setUp() throws Exception {
        validation = Validation.newValidation();
    }

    @After
    public void tearDown() throws Exception {
        validation.clear();
    }

    @Test
    public void testGetErrors() throws Exception {
        List<ValidationError> errors = validation.getErrors();

        assertNotNull(errors);
        assertTrue(errors.isEmpty());
        assertFalse(validation.hasError());

        validation.apply(Validator.required(), null).key("testKey").message("test.message");

        assertTrue(validation.hasError());
        errors = validation.getErrors();
        assertFalse(errors.isEmpty());

        LOGGER.info("The getErrors test case, errors: {}", errors);
    }

    @Test
    public void testClear() throws Exception {
        validation.required(null).key("testKey").message("test.message");
        assertTrue(validation.hasError());
        validation.clear();
        assertFalse(validation.hasError());
    }

    @Test
    public void testAsMap() throws Exception {
        validation.required(null).key("testKey").message("test.message");
        Map<String, ValidationError> map = validation.asMap();
        assertNotNull(map);
        assertFalse(map.isEmpty());
        assertTrue(map.containsKey("testKey"));
    }

    @Test
    public void testError() throws Exception {

    }

    @Test
    public void testApply() throws Exception {
        validation.apply(Validator.match("[1-9]\\d+"), "apple001").key("numberKey").message("numberKey.error");
        assertTrue(validation.hasError());
        assertTrue(validation.asMap().containsKey("numberKey"));
        validation.clear();

        validation.apply(Validator.match("\\w+"), "apple001").key("userName").message("userName format error");
        assertFalse(validation.hasError());
    }

    @Test
    public void testRequired() throws Exception {
        String string = null;
        Integer nullNumber = null, zeroNumber = 0;
        Float floatNumber = 0.0f;
        Double doubleNumber = 0.0d;
        Map<String, String> nullMap = null, emptyMap = ImmutableMap.of();
        List<String> nullList = null, emptyList = ImmutableList.of();
        Boolean boolTrue = Boolean.TRUE, boolFalse = Boolean.FALSE;

        validation.required(string).key("nullString").message("null.string");
        assertTrue(validation.hasError());
        validation.clear();

        validation.required(nullNumber).key("nullNumber").message("null.number");
        assertTrue(validation.hasError());
        validation.clear();

        // zero number is required, must be not equals 0
        validation.required(zeroNumber).key("zeroNumber").message("zero.number");
        assertTrue(validation.hasError());
        validation.clear();

        // zero float is required, must be not equals 0.0f
        validation.required(floatNumber).key("zeroFloat").message("zero.float");
        assertTrue(validation.hasError());
        validation.clear();

        validation.required(doubleNumber).key("zeroDouble").message("zero.double");
        assertTrue(validation.hasError());
        validation.clear();

        // null map or empty map is error when it required.
        validation.required(nullMap).key("nullMap").message("null.map");
        assertTrue(validation.hasError());
        validation.clear();
        validation.required(emptyMap).key("emptyKey").message("empty.map");
        assertTrue(validation.hasError());
        validation.clear();

        // null list or empty list is error when it required
        validation.required(nullList).key("nullList").message("null.list");
        assertTrue(validation.hasError());
        validation.clear();
        validation.required(emptyList).key("emptyList").message("empty.list");
        assertTrue(validation.hasError());
        validation.clear();

        // boolean required
        validation.required(boolTrue);
        assertFalse(validation.hasError());
        validation.required(boolFalse).key("boolFalse").message("bool.false");
        assertTrue(validation.hasError());
    }

    @Test
    public void testMin() throws Exception {
        Integer zeroInt = 0, oneInt = 1, twoInt = 2;
        BigDecimal zeroDecimal = BigDecimal.ZERO, oneDecimal = BigDecimal.ONE,
                twoDecimal = BigDecimal.valueOf(2);

        // min: the value must greater than or equals min value
        validation.min(zeroInt, 1).key("minNumber").message("number.min");
        assertTrue(validation.hasError());
        assertTrue(validation.asMap().containsKey("minNumber"));
        validation.clear();

        validation.min(oneInt, 1).key("minNumber").message("number.min");
        assertFalse(validation.hasError());
        validation.min(twoInt, 2).key("minNumber").message("number.min");
        assertFalse(validation.hasError());

        validation.min(zeroDecimal, BigDecimal.ONE).key("minDecimal").message("decimal.min");
        assertTrue(validation.hasError());
        validation.clear();
        validation.min(oneDecimal, BigDecimal.ONE).key("minDecimal").message("decimal.min");
        assertFalse(validation.hasError());
        validation.min(twoDecimal, BigDecimal.ONE).key("minDecimal").message("decimal.min");
        assertFalse(validation.hasError());

    }

    @Test
    public void testMax() throws Exception {
        Integer zeroInt = 0, oneInt = 1, twoInt = 2;
        BigDecimal zeroDecimal = BigDecimal.ZERO, oneDecimal = BigDecimal.ONE,
                twoDecimal = BigDecimal.valueOf(2);

        // max: the value must less than or equals max value
        validation.max(twoInt, 1).key("maxNumber").message("number.max");
        assertTrue(validation.hasError());
        assertTrue(validation.asMap().containsKey("maxNumber"));
        validation.clear();

        validation.max(oneInt, 1).key("maxNumber").message("number.max");
        assertFalse(validation.hasError());
        validation.max(zeroInt, 2).key("maxNumber").message("number.max");
        assertFalse(validation.hasError());

        validation.max(twoDecimal, BigDecimal.ONE).key("maxDecimal").message("decimal.max");
        assertTrue(validation.hasError());
        validation.clear();
        validation.max(oneDecimal, BigDecimal.ONE).key("maxDecimal").message("decimal.max");
        assertFalse(validation.hasError());
        validation.max(zeroDecimal, BigDecimal.ONE).key("maxDecimal").message("decimal.max");
        assertFalse(validation.hasError());
    }

    @Test
    public void testMinSize() throws Exception {
        String emptyStr = "", lengthStr = "lengthStr";
        String[] emptyArray = new String[0], tags = new String[] { "Tag1" };
        List<Integer> emptyList = ImmutableList.of(), ids = Lists.newArrayList(1, 2);

        {
            validation.minSize(emptyStr, 5).key("minLength").message("min.length");
            assertTrue(validation.hasError());
            validation.clear();

            validation.minSize(lengthStr, 12).key("minLength").message("min.length");
            assertTrue(validation.hasError());
            validation.clear();

            validation.minSize(lengthStr, 8).key("minLength").message("min.length");
            assertFalse(validation.hasError());
        }
        {
            validation.minSize(emptyArray, 1).key("atLeastOne").message("tag.need.one");
            assertTrue(validation.hasError());
            validation.clear();

            validation.minSize(tags, 1).key("atLeastOne").message("tag.need.one");
            assertFalse(validation.hasError());
        }
        {
            validation.minSize(emptyList, 1).key("ids").message("ids.empty");
            assertTrue(validation.hasError());
            validation.clear();

            validation.minSize(ids, 1).key("ids").message("ids.empty");
            assertFalse(validation.hasError());
        }
    }

    @Test
    public void testMaxSize() throws Exception {
        String emptyStr = "", lengthStr = "lengthStr";
        String[] emptyArray = new String[0], tags = new String[] { "Tag1", "Tag2" };
        List<Integer> emptyList = ImmutableList.of(), ids = Lists.newArrayList(1, 2);

        {
            validation.maxSize(emptyStr, 5).key("maxLength").message("max.length");
            assertFalse(validation.hasError());

            validation.maxSize(lengthStr, 8).key("maxLength").message("max.length");
            assertTrue(validation.hasError());
            validation.clear();

            validation.maxSize(lengthStr, 12).key("maxLength").message("max.length");
            assertFalse(validation.hasError());
        }
        {
            validation.maxSize(emptyArray, 2).key("atLeastOne").message("tag.need.one");
            assertFalse(validation.hasError());

            validation.maxSize(tags, 1).key("atLeastOne").message("tag.need.one");
            assertTrue(validation.hasError());
            validation.clear();
        }
        {
            validation.maxSize(emptyList, 1).key("ids").message("ids.empty");
            assertFalse(validation.hasError());

            validation.maxSize(ids, 1).key("ids").message("ids.empty");
            assertTrue(validation.hasError());
        }
    }

    @Test
    public void testRange() throws Exception {
        {
            Range<Integer> ageRange = Range.closed(18, 30);

            validation.range(17, ageRange.lowerEndpoint(), ageRange.upperEndpoint())
                    .key("age").message("age.notMatch");
            assertTrue(validation.hasError());
            assertTrue(validation.asMap().containsKey("age"));
            validation.clear();

            validation.range(24, ageRange.lowerEndpoint(), ageRange.upperEndpoint())
                    .key("age").message("age.notMatch");
            assertFalse(validation.hasError());
        }

        {
            Range<Integer> nameLength = Range.closed(4, 15);
            String name1 = "宀", name2 = "小宝";

            validation.range(name1.getBytes(Charsets.UTF_8).length, nameLength.lowerEndpoint(),
                    nameLength.upperEndpoint()).key("name").message("name.length");
            assertTrue(validation.hasError());
            validation.clear();

            validation.range(name2.getBytes(Charsets.UTF_8).length, nameLength.lowerEndpoint(),
                    nameLength.upperEndpoint()).key("name").message("name.length");
            assertFalse(validation.hasError());
        }
    }

    @Test
    public void testMatch() throws Exception {
        { // match chinese string
            String zhRegexp = "^[\\u4E00-\\u9FA5]+$", errName = "我是56", name1 = "風雲", name2 = "张三丰";

            validation.match(errName, zhRegexp).key("name").message("realName.error");
            assertTrue(validation.hasError());
            validation.clear();

            validation.match(name1, zhRegexp).key("name").message("realName.error");
            assertFalse(validation.hasError());

            validation.match(name2, zhRegexp).key("name").message("realName.error");
            assertFalse(validation.hasError());
        }
    }

    @Test
    public void testEmail() throws Exception {
        String email1 = "a@a.a", email2 = "@a.a", email3 = "email3",
                email4 = "a-a@a.a", email5 = "a.a@a.a";

        validation.email(email1).key("email").message("email.error");
        assertFalse(validation.hasError());

        validation.email(email2).key("email").message("email.error");
        assertTrue(validation.hasError());
        validation.clear();

        validation.email(email3).key("email").message("email.error");
        assertTrue(validation.hasError());
        validation.clear();

        validation.email(email4).key("email").message("email.error");
        assertFalse(validation.hasError());

        validation.email(email5).key("email").message("email.error");
        assertFalse(validation.hasError());
    }

    @Test
    public void testCheck() throws Exception {
        String userName1 = "", userName2 = "DFJ", userName3 = "oo0_SleepingSun_0oo",
                userName4 = "DaFeiJi";
        Validator[] userNameValidators = new Validator[] {
                Validator.required(),
                Validator.minSize(6),
                Validator.maxSize(16),
                Validator.match("^[a-zA-Z]\\w+$")
        };

        validation.check(userName1, userNameValidators).key("userName").message("userName.error");
        assertTrue(validation.hasError());
        validation.clear();

        validation.check(userName2, userNameValidators).key("userName").message("userName.error");
        assertTrue(validation.hasError());
        validation.clear();

        validation.check(userName3, userNameValidators).key("userName").message("userName.error");
        assertTrue(validation.hasError());
        validation.clear();

        validation.check(userName4, userNameValidators).key("userName").message("userName.error");
        assertFalse(validation.hasError());
    }
}