/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) AES.java 2013-09-26 14:23
 */

package com.ibbface.security.crypto;

import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

/**
 * @author Fuchun
 * @since 1.0
 */
public final class AES extends AbstractCrypto {

    public static final String DEFAULT_TRANSFORMATION = "AES/CBC/PKCS5Padding";

    private static final KeyGenerator keyGen;
    private static final KeyGenerator keyGen192;
    private static final KeyGenerator keyGen256;

    static {
        try {
            keyGen = KeyGenerator.getInstance("AES", BOUNCY_CASTLE_PROVIDER);
            keyGen192 = KeyGenerator.getInstance("AES", BOUNCY_CASTLE_PROVIDER);
            keyGen256 = KeyGenerator.getInstance("AES", BOUNCY_CASTLE_PROVIDER);

            keyGen.init(128, new SecureRandom());
            keyGen192.init(192, new SecureRandom());
            keyGen256.init(256, new SecureRandom());
        } catch (NoSuchAlgorithmException ex) {
            throw new CryptoException("The current system have no AES algorithm?", ex);
        } catch (NoSuchProviderException ex) {
            throw new CryptoException("Please check org.bouncycastle:bcprov-jdk16:1.46 library, " +
                    "or you have not adding provider to Security?", ex);
        }
    }

    public static Key getKey128() {
        return keyGen.generateKey();
    }

    public static Key getKey192() {
        return keyGen192.generateKey();
    }

    public static Key getKey256() {
        return keyGen256.generateKey();
    }

    /**
     * Create and return {@link AES} instance of the specified CBC mode, PKCS5 padding,
     * crypto key and IV.
     *
     * @param key the aes crypto key (length: 16, 32).
     * @param iv the aes crypto iv (length: 16).
     */
    public static AES cbcPKCS5Padding(byte[] key, byte[] iv) {
        checkIV(iv);
        return new AES(DEFAULT_TRANSFORMATION, key, iv);
    }

    /**
     * Create and return {@link AES} instance of the specified CBC mode, No padding,
     * crypto key and IV.
     *
     * @param key the aes crypto key (length: 16, 32).
     * @param iv the aes crypto iv (length: 16).
     */
    public static AES cbcNoPadding(byte[] key, byte[] iv) {
        checkIV(iv);
        return new AES("AES/CBC/NoPadding", key, iv);
    }

    /**
     * Create and return {@link AES} instance of the specified ECB mode, PKCS5 padding and
     * crypto key.
     *
     * @param key the aes crypto key (length: 16, 32).
     */
    public static AES ecbPKCS5Padding(byte[] key) {
        return new AES("AES/ECB/PKCS5Padding", key, null);
    }

    /**
     * Create and return {@link AES} instance of the specified ECB mode, No padding and
     * crypto key.
     *
     * @param key the aes crypto key (length: 16, 32).
     */
    public static AES ecbNoPadding(byte[] key) {
        return new AES("AES/ECB/NoPadding", key, null);
    }

    /**
     * Constructor a {@code AES} of the specified transformation,
     * crypto key and iv.
     *
     * @param transformation the name of the transformation, e.g., AES/CBC/PKCS5Padding.
     * @param key the aes crypto key (length: 16, 32).
     * @param iv the aes crypto iv (length: 16).
     */
    protected AES(String transformation, byte[] key, byte[] iv) {
        super(transformation, BOUNCY_CASTLE_PROVIDER, key, iv);
    }
}
