/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) CryptoTest.java 2013-09-29 14:08
 */

package com.ibbface.security.crypto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Fuchun
 * @since 1.0
 */
public abstract class CryptoTest {

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    protected static final String ONLY_NUMBER = "0123456789";
    protected static final String ONLY_ALPHA = "abcdefghijklmnABCDEFGHIJKLMN";
    protected static final String ALPHA_NUMBER = ONLY_ALPHA + ONLY_NUMBER;
    protected static final String ONLY_SPECIALS = ")(*&^%$#@!~}|{\":?><,./;'[]\\=-";
    protected static final String PART_ASCII = ALPHA_NUMBER + ONLY_SPECIALS;
    protected static final String UNICODE = PART_ASCII + "中国人";

    protected void testGivenSource(final AbstractCrypto crypto) {
        String encryptText = crypto.encryptString2Hex(ONLY_NUMBER);
        assertNotNull(encryptText);
        String decryptText = crypto.decryptHex2String(encryptText);
        assertNotNull(decryptText);
        assertEquals(ONLY_NUMBER, decryptText);
        LOGGER.debug("----- source type: only number -----");
        LOGGER.debug(" Source text: {}", ONLY_NUMBER);
        LOGGER.debug("Encrypt text: {}", encryptText);
        LOGGER.debug("Decrypt text: {}", decryptText);

        encryptText = crypto.encryptString2Hex(ONLY_ALPHA);
        assertNotNull(encryptText);
        decryptText = crypto.decryptHex2String(encryptText);
        assertNotNull(decryptText);
        assertEquals(ONLY_ALPHA, decryptText);
        LOGGER.debug("----- source type: only alpha -----");
        LOGGER.debug(" Source text: {}", ONLY_ALPHA);
        LOGGER.debug("Encrypt text: {}", encryptText);
        LOGGER.debug("Decrypt text: {}", decryptText);

        encryptText = crypto.encryptString2Hex(ALPHA_NUMBER);
        assertNotNull(encryptText);
        decryptText = crypto.decryptHex2String(encryptText);
        assertNotNull(decryptText);
        assertEquals(ALPHA_NUMBER, decryptText);
        LOGGER.debug("----- source type: alpha and number -----");
        LOGGER.debug(" Source text: {}", ALPHA_NUMBER);
        LOGGER.debug("Encrypt text: {}", encryptText);
        LOGGER.debug("Decrypt text: {}", decryptText);

        encryptText = crypto.encryptString2Hex(ONLY_SPECIALS);
        assertNotNull(encryptText);
        decryptText = crypto.decryptHex2String(encryptText);
        assertNotNull(decryptText);
        assertEquals(ONLY_SPECIALS, decryptText);
        LOGGER.debug("----- source type: only specials -----");
        LOGGER.debug(" Source text: {}", ONLY_SPECIALS);
        LOGGER.debug("Encrypt text: {}", encryptText);
        LOGGER.debug("Decrypt text: {}", decryptText);

        encryptText = crypto.encryptString2Hex(PART_ASCII);
        assertNotNull(encryptText);
        decryptText = crypto.decryptHex2String(encryptText);
        assertNotNull(decryptText);
        assertEquals(PART_ASCII, decryptText);
        LOGGER.debug("----- source type: part ascii -----");
        LOGGER.debug(" Source text: {}", PART_ASCII);
        LOGGER.debug("Encrypt text: {}", encryptText);
        LOGGER.debug("Decrypt text: {}", decryptText);

        encryptText = crypto.encryptString2Hex(UNICODE);
        assertNotNull(encryptText);
        decryptText = crypto.decryptHex2String(encryptText);
        assertNotNull(decryptText);
        assertEquals(UNICODE, decryptText);
        LOGGER.debug("----- source type: unicode string -----");
        LOGGER.debug(" Source text: {}", UNICODE);
        LOGGER.debug("Encrypt text: {}", encryptText);
        LOGGER.debug("Decrypt text: {}", decryptText);
    }
}
