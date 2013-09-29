/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) PBE.java 2013-09-29 12:49
 */

package com.ibbface.security.crypto;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.ibbface.util.RandomStrings;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;

/**
 * @author Fuchun
 * @since 1.0
 */
public final class PBE extends AbstractCrypto {

    private static final Logger pbeLog = LoggerFactory.getLogger("PBE");

    /**
     * 默认的密钥加密密码。
     */
    public static final String DEFAULT_PASSWORD = "00000000";

    /**
     * 默认的PBE算法名称（PBEWithMD5AndDES）。
     */
    public static final String MD5_AND_DES = "PBEWithMD5AndDES";

    /**
     * PBEWithMD5AndTripleDES 算法转换名称。
     */
    public static final String MD5_AND_TRIPLE_DES = "PBEWithMD5AndTripleDES";

    /**
     * PBEWithSHA1AndDESede 算法转换名称。
     */
    public static final String SHA1_AND_DESEDE = "PBEWithSHA1AndDESede";

    /**
     * PBEWithSHA1AndRC2_40 算法转换名称。
     */
    public static final String SHA1_AND_RC2_40 = "PBEWithSHA1AndRC2_40";

    private static SecretKeyFactory DEFAULT_KEY_FACTORY;

    static {
        try {
            DEFAULT_KEY_FACTORY = SecretKeyFactory.getInstance(
                    MD5_AND_DES);
        } catch (NoSuchAlgorithmException ex) {
            throw new CryptoException(String.format(
                    "The current system have no %s algorithm?", MD5_AND_DES), ex);
        }
//        } catch (NoSuchProviderException ex) {
//            if (pbeLog.isWarnEnabled()) {
//                pbeLog.warn("Please check org.bouncycastle:bcprov-jdk16:1.46 library, " +
//                        "or you have not adding provider to Security?");
//            }
//            try {
//                DEFAULT_KEY_FACTORY = SecretKeyFactory.getInstance(MD5_AND_DES);
//            } catch (NoSuchAlgorithmException e) {
//                // ignore.
//            }
//        }
    }

    /**
     * Returns this default key factory.
     */
    public static SecretKeyFactory getDefaultKeyFactory() {
        return DEFAULT_KEY_FACTORY;
    }

    /**
     * Returns a random salt bytes.
     */
    public static byte[] randomSalt() {
        return RandomStrings.random(8,
                "~!@#$%^&*()_+`1234567890-=[]\\{}|;':\",./<>?").getBytes();
    }

    public static Key password2Key(String password, SecretKeyFactory... factories) {
        if (Strings.isNullOrEmpty(password)) {
            if (pbeLog.isDebugEnabled()) {
                pbeLog.debug("The given password is a empty(null), using default password {}",
                        DEFAULT_PASSWORD);
            }
            password = DEFAULT_PASSWORD;
        }
        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
        SecretKeyFactory skf = getDefaultKeyFactory();
        if (factories != null && factories.length > 0 && factories[0] != null) {
            skf = factories[0];
        }
        try {
            return skf.generateSecret(keySpec);
        } catch (InvalidKeySpecException ex) {
            pbeLog.warn("PBEKeySpec keySpec is error - " + ex.getMessage());
        }
        return null;
    }

    public static PBE md5AndDES(String password, byte[] salt) {
        return newInstance(MD5_AND_DES, password, salt);
    }

    public static PBE md5AndDES(String password) {
        return md5AndDES(password, null);
    }

    public static PBE md5AndTripleDES(String password, byte[] salt) {
        return newInstance(MD5_AND_TRIPLE_DES, password, salt);
    }

    public static PBE md5AndTripleDES(String password) {
        return md5AndTripleDES(password, null);
    }

    public static PBE sha1AndDESede(String password, byte[] salt) {
        return newInstance(SHA1_AND_DESEDE, password, salt);
    }

    public static PBE sha1AndDESede(String password) {
        return sha1AndDESede(password, null);
    }

    public static PBE sha1AndRC2_40(String password, byte[] salt) {
        return newInstance(SHA1_AND_RC2_40, password, salt);
    }

    public static PBE sha1AndRC2_40(String password) {
        return sha1AndRC2_40(password, null);
    }

    protected static PBE newInstance(String transformation, String password, byte[] salt) {
        byte[] key = password.getBytes();
        if (salt == null || salt.length == 0) {
            salt = randomSalt();
        }
        return new PBE(transformation, key, salt);
    }

    private final byte[] salt;
    private final String password;

    PBE(String transformation, byte[] key, byte[] parameters) {
        super(transformation, null, key, parameters);
        this.salt = parameters;
        this.password = new String(key, Charsets.UTF_8);
    }

    @Override
    protected Key toKey(byte[] keyBytes, String algorithm) {
        String password = new String(keyBytes, Charsets.UTF_8);
        return password2Key(password);
    }

    @Override
    protected AlgorithmParameterSpec toParameterSpec(byte[] parameters) {
        return new PBEParameterSpec(parameters, 100);
    }

    public byte[] getSalt() {
        return salt;
    }

    public String getHexSalt() {
        return Hex.encodeHexString(getSalt());
    }

    public String getPassword() {
        return password;
    }
}
