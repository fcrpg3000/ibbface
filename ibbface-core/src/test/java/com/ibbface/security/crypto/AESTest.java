/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) AESTest.java 2013-09-27 14:57
 */

package com.ibbface.security.crypto;

import com.ibbface.util.RandomStrings;
import org.junit.Test;

import java.security.Key;

/**
 * @author Fuchun
 * @since 1.0
 */
public class AESTest extends CryptoTest {

    @Test
    public void testCbcPKCS5Padding() {
        Key key = AES.getKey128();
        byte[] iv = RandomStrings.randomAlphanumeric(16).getBytes();
        AES aes = AES.cbcPKCS5Padding(key.getEncoded(), iv);
        LOGGER.debug("========== mode: CBC, padding: PKCS5, keySize: 128 ==========");
        testGivenSource(aes);

        key = AES.getKey192();
        aes = AES.cbcPKCS5Padding(key.getEncoded(), iv);
        LOGGER.debug("\n========== mode: CBC, padding: PKCS5, keySize: 192 ==========");
        testGivenSource(aes);

        key = AES.getKey256();
        aes = AES.cbcPKCS5Padding(key.getEncoded(), iv);
        LOGGER.debug("\n========== mode: CBC, padding: PKCS5, keySize: 256 ==========");
        testGivenSource(aes);
    }

    @Test
    public void testCbcNoPadding() {
        Key key = AES.getKey128();
        byte[] iv = RandomStrings.randomAlphanumeric(16).getBytes();
        AES aes = AES.cbcNoPadding(key.getEncoded(), iv);
        LOGGER.debug("\n========== mode: CBC, padding: No, keySize: 128 ==========");
        testGivenSource(aes);

        key = AES.getKey192();
        aes = AES.cbcNoPadding(key.getEncoded(), iv);
        LOGGER.debug("\n========== mode: CBC, padding: No, keySize: 192 ==========");
        testGivenSource(aes);

        key = AES.getKey256();
        aes = AES.cbcNoPadding(key.getEncoded(), iv);
        LOGGER.debug("\n========== mode: CBC, padding: No, keySize: 256 ==========");
        testGivenSource(aes);
    }

    @Test
    public void testEcbPKCS5Padding() {
        Key key = AES.getKey128();
        AES aes = AES.ecbPKCS5Padding(key.getEncoded());
        LOGGER.debug("\n========== mode: ECB, padding: PKCS5, keySize: 128 ==========");
        testGivenSource(aes);

        key = AES.getKey192();
        aes = AES.ecbPKCS5Padding(key.getEncoded());
        LOGGER.debug("\n========== mode: ECB, padding: PKCS5, keySize: 192 ==========");
        testGivenSource(aes);

        key = AES.getKey256();
        aes = AES.ecbPKCS5Padding(key.getEncoded());
        LOGGER.debug("\n========== mode: ECB, padding: PKCS5, keySize: 256 ==========");
        testGivenSource(aes);
    }

    @Test
    public void testEcbNoPadding() {
        Key key = AES.getKey128();
        AES aes = AES.ecbNoPadding(key.getEncoded());
        LOGGER.debug("\n========== mode: ECB, padding: No, keySize: 128 ==========");
        testGivenSource(aes);

        key = AES.getKey192();
        aes = AES.ecbNoPadding(key.getEncoded());
        LOGGER.debug("\n========== mode: ECB, padding: No, keySize: 192 ==========");
        testGivenSource(aes);

        key = AES.getKey256();
        aes = AES.ecbNoPadding(key.getEncoded());
        LOGGER.debug("\n========== mode: ECB, padding: No, keySize: 256 ==========");
        testGivenSource(aes);
    }
}
