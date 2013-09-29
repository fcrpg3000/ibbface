/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) CryptoException.java 2013-09-26 14:52
 */

package com.ibbface.security.crypto;

/**
 * @author Fuchun
 * @since 1.0
 */
public class CryptoException extends RuntimeException {

    public CryptoException() {
    }

    public CryptoException(String s) {
        super(s);
    }

    public CryptoException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public CryptoException(Throwable throwable) {
        super(throwable);
    }
}
