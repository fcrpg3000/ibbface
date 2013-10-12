/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) AbstractCrypto.java 2013-09-26 14:24
 */

package com.ibbface.security.crypto;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.primitives.Bytes;
import com.google.common.util.concurrent.Monitor;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.emptyToNull;
import static com.google.common.base.Strings.nullToEmpty;

/**
 * @author Fuchun
 * @since 1.0
 */
public abstract class AbstractCrypto {

    /**
     * A constant of the empty byte array.
     */
    public static final byte[] EMPTY_ARRAY_BYTES = new byte[0];

    public static final String BOUNCY_CASTLE_PROVIDER = "BC";

    static {
        Security.insertProviderAt(new BouncyCastleProvider(), 1);
    }

    protected final Monitor ENCRYPT_MONITOR = new Monitor();
    protected final Monitor DECRYPT_MONITOR = new Monitor();

    /**
     * The logging.
     */
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
    /**
     * The crypto key.
     */
    protected final Key key;
    /**
     * The crypto iv spec.
     */
    protected final AlgorithmParameterSpec parameterSpec;
    /**
     * Encrypt cipher.
     */
    protected Cipher enc;
    /**
     * Decrypt cipher.
     */
    protected Cipher dec;

    protected AbstractCrypto(String transformation, String provider, byte[] key, byte[] iv) {
        provider = emptyToNull(nullToEmpty(provider).trim());
        checkArgument(transformation != null &&
                (transformation = transformation.trim()).length() > 0,
                "The transformation must not be null or empty (blank) string.");
        checkArgument(key != null && key.length > 0,
                "The key bytes must not be null or empty!");
        assert transformation != null && transformation.length() > 0;
        assert key != null && key.length > 0;

        String algorithm = transformation.contains("/") ?
                transformation.split("/")[0] : transformation;
        this.key = toKey(key, algorithm);
        parameterSpec = toParameterSpec(iv);

        this.enc = newCipher(Cipher.ENCRYPT_MODE, transformation, provider);
        this.dec = newCipher(Cipher.DECRYPT_MODE, transformation, provider);
    }

    protected static void checkIV(byte[] iv) {
        checkArgument(iv != null && iv.length > 0,
                "The given iv must not be null or empty byte array.");
    }

    protected Cipher getCipher(String transformation, String provider) throws
            NoSuchAlgorithmException, NoSuchPaddingException {
        Cipher c;
        try {
            c = provider == null ?
                    Cipher.getInstance(transformation) :
                    Cipher.getInstance(transformation, provider);
            return c;
        } catch (NoSuchProviderException ex) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("The current system have no such '{}' provider, " +
                        "using default JCE provider.", provider);
            }
            return Cipher.getInstance(transformation);
        }
    }

    protected Cipher newCipher(int opmode, String transformation, String provider) {
        Cipher c;
        try {
            c = getCipher(transformation, provider);
        } catch (NoSuchAlgorithmException ex) {
            throw new CryptoException(String.format(
                    "Current system have no such '%s' algorithm.", transformation), ex);
        } catch (NoSuchPaddingException ex) {
            throw new CryptoException(String.format(
                    "Current system have no such '%s' padding.", transformation), ex);
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Current provider is {}.", c.getProvider().getName());
        }
        initCipher(c, opmode, transformation);
        return c;
    }

    protected void initCipher(final Cipher c, final int opmode, final String transformation) {
        try {
            if (parameterSpec == null) {
                c.init(opmode, key);
            } else {
                c.init(opmode, key, parameterSpec);
            }
        } catch (InvalidKeyException ex) {
            throw new CryptoException(String.format(
                    "The key hex[%s] is invalid for '%s' transformation.",
                    Hex.encodeHexString(key.getEncoded()), transformation), ex);
        } catch (InvalidAlgorithmParameterException ex) {
            throw new CryptoException(String.format(
                    "The algorithm '%s' is invalid or inappropriate parameter.",
                    transformation), ex);
        }
    }

    public static byte[] createSequence(byte start, int length, byte step) {
        byte[] result = new byte[length];
        int i, next;
        if (step == 0) {
            for (i = 0; i < length; i++) {
                result[i] = start;
            }
            return result;
        }
        for (i = 0; i < length; i++) {
            result[i] = start;
            next = (int) start + (int) step;
            if (next < Byte.MIN_VALUE || next > Byte.MAX_VALUE) {
                // ASC
                if (step > 0) {
                    start = Byte.MAX_VALUE;
                    step = 0;
                } else {
                    start = Byte.MIN_VALUE;
                    step = 0;
                }
            } else {
                start = Integer.valueOf(next).byteValue();
            }
        }
        return result;
    }

    protected byte[] padding(byte[] bytes, int blockSize) {
        int len = bytes.length;
        if (len == 0) {
            return bytes;
        }
        int pad;
        byte[] pads;
        if (len < blockSize) {
            pad = blockSize - len;
            pads = createSequence((byte) 0, pad, (byte) 0);
            return Bytes.concat(bytes, pads);
        } else {
            pad = len % blockSize;
            if (pad != 0) {
                pads = createSequence((byte) 0, blockSize - pad, (byte) 0);
                return Bytes.concat(bytes, pads);
            }
            return bytes;
        }
    }

    protected Key toKey(byte[] keyBytes, String algorithm) {
        return new SecretKeySpec(keyBytes, algorithm);
    }

    protected AlgorithmParameterSpec toParameterSpec(byte[] parameters) {
        return parameters == null || parameters.length == 0 ?
                null : new IvParameterSpec(parameters);
    }

    /**
     * Encrypts data in a single-part operation, or finishes a multiple-part operation.
     *
     * @param data input buffer.
     * @return The new buffer with result.
     */
    public byte[] encrypt(byte[] data) {
        if (data == null || data.length == 0) {
            return EMPTY_ARRAY_BYTES;
        }
        byte[] input;
        if (enc.getAlgorithm().endsWith("NoPadding")) {
            input = padding(data, enc.getBlockSize());
        } else {
            input = data;
        }
        // multi threads may update data in Cipher.
        ENCRYPT_MONITOR.enter();
        try {
            return enc.doFinal(input);
        } catch (Exception ex) {
            writeEncryptLogger(ex);
            initCipher(enc, Cipher.ENCRYPT_MODE, enc.getAlgorithm());
        } finally {
            ENCRYPT_MONITOR.leave();
        }
        return EMPTY_ARRAY_BYTES;
    }

    public byte[] decrypt(byte[] data) {
        if (data == null || data.length == 0) {
            return EMPTY_ARRAY_BYTES;
        }
        // multi threads may update data in Cipher.
        DECRYPT_MONITOR.enter();
        try {
            byte[] result = dec.doFinal(data);
            if (dec.getAlgorithm().endsWith("NoPadding")) {
                int idx = result.length;
                for (int i = result.length; --i >= 0; ) {
                    if (result[i] == (byte) 0) {
                        idx = i;
                    } else {
                        break;
                    }
                }
                // auto remove padding 00
                return Arrays.copyOfRange(result, 0, idx);
            }
            return result;
        } catch (Exception ex) {
            writeDecryptLogger(ex);
            initCipher(dec, Cipher.DECRYPT_MODE, dec.getAlgorithm());
        } finally {
            DECRYPT_MONITOR.leave();
        }
        return EMPTY_ARRAY_BYTES;
    }

    public String encrypt2Hex(byte[] data) {
        byte[] bytes = encrypt(data);
        return bytes.length == 0 ? null : Hex.encodeHexString(bytes);
    }

    public byte[] decryptFromHex(String hexStr) {
        if (Strings.isNullOrEmpty(hexStr)) {
            return EMPTY_ARRAY_BYTES;
        }
        byte[] bytes;
        try {
            bytes = Hex.decodeHex(hexStr.toCharArray());
        } catch (DecoderException ex) {
            LOGGER.error("The given `%s` is not a hex string?", hexStr);
            return EMPTY_ARRAY_BYTES;
        }
        return decrypt(bytes);
    }

    public byte[] encryptString(String input) {
        return encryptString(input, Charsets.UTF_8);
    }

    public byte[] encryptString(String input, Charset charset) {
        if (Strings.isNullOrEmpty(input)) {
            return EMPTY_ARRAY_BYTES;
        }
        if (charset == null) {
            charset = Charsets.UTF_8;
        }
        byte[] data;
        try {
            data = input.getBytes(charset);
        } catch (UnsupportedOperationException ex) {
            // hardly occurs
            writeEncryptLogger(ex);
            return EMPTY_ARRAY_BYTES;
        }
        return encrypt(data);
    }

    public String decrypt2String(byte[] data) {
        return decrypt2String(data, Charsets.UTF_8);
    }

    public String decrypt2String(byte[] data, Charset charset) {
        byte[] result = decrypt(data);
        if (result.length == 0) {
            return null;
        }
        if (charset == null) {
            charset = Charsets.UTF_8;
        }
        try {
            return new String(result, charset);
        } catch (UnsupportedOperationException ex) {
            // Hardly occurs
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("The current system unsupported {} encoding?", charset.displayName());
            }
            return null;
        }
    }

    public String encryptString2Hex(String input) {
        return encryptString2Hex(input, Charsets.UTF_8);
    }

    public String encryptString2Hex(String input, Charset charset) {
        byte[] bytes = encryptString(input, charset);
        if (bytes.length == 0) {
            return null;
        }
        return Hex.encodeHexString(bytes);
    }

    public String decryptHex2String(String hexStr) {
        return decryptHex2String(hexStr, Charsets.UTF_8);
    }

    public String decryptHex2String(String hexStr, Charset charset) {
        byte[] data;
        try {
            data = Hex.decodeHex(hexStr.toCharArray());
        } catch (DecoderException ex) {
            LOGGER.error("The given `{}` is not a hex string.", hexStr);
            return null;
        }
        return decrypt2String(data, charset);
    }

    protected void writeEncryptLogger(Throwable cause) {
        LOGGER.error("Cipher encrypt failed, cause: {}", cause.getMessage());
    }

    protected void writeDecryptLogger(Throwable cause) {
        LOGGER.error("Cipher decrypt failed, cause: {}", cause.getMessage());
    }

    public Key getKey() {
        return key;
    }

    public AlgorithmParameterSpec getParameterSpec() {
        return parameterSpec;
    }

    public Cipher getEnc() {
        return enc;
    }

    public Cipher getDec() {
        return dec;
    }
}
