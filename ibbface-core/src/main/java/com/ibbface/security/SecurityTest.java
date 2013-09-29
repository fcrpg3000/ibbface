/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) SecurityTest.java 2013-09-26 13:35
 */

package com.ibbface.security;

import com.google.common.base.Charsets;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

/**
 * @author Fuchun
 * @since 1.0
 */
public class SecurityTest {

    public static void main(String[] args) throws Exception {
        Security.addProvider(new BouncyCastleProvider());

//        KeyGenerator keyGen = KeyGenerator.getInstance("AES", "BC");
//        keyGen.init(256);
//        SecretKey key = keyGen.generateKey();
        SecretKey key = new SecretKeySpec(Hex.decodeHex(
                "231822993da78f5f0449b1165451e8fb7a7e66d5dab710aadebe0df4ce9d3780".toCharArray()),
                "AES");

//        byte[] iv = RandomStrings.randomAlphanumeric(16).getBytes();
        byte[] iv = Hex.decodeHex("5456634d314b6d664a67474377535056".toCharArray());
        Cipher in = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
        in.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));

        String srcContent = ")(*&^%$#@!\u4e2d\u56fd\u4eba";
        byte[] encryptBytes = in.doFinal(srcContent.getBytes(Charsets.UTF_8));
        System.out.println(String.format("Key text:     %s", Hex.encodeHexString(key.getEncoded())));
        System.out.println(String.format("IV text:      %s", Hex.encodeHexString(iv)));
        System.out.println(String.format("source  text: %s", srcContent));
        System.out.println(String.format("encrypt text: %s", Hex.encodeHexString(encryptBytes)));

        Cipher out = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
        out.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));

        byte[] decryptBytes = out.doFinal(encryptBytes);
        System.out.println(String.format("decrypt text: %s", new String(decryptBytes, Charsets.UTF_8)));
    }
}
