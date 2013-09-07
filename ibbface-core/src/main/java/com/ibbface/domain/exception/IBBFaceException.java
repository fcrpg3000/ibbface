/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) IBBFaceException.java 2013-09-05 17:14
 */

package com.ibbface.domain.exception;

/**
 * Root exception for all IBBFace runtime exceptions.  This class is used as the root instead
 * of {@link java.lang.RuntimeException} to remove the potential for conflicts;  many other
 * frameworks and products (such as J2EE containers) perform special operations when
 * encountering {@link java.lang.RuntimeException}.
 *
 * @author Fuchun
 * @since 1.0
 */
public class IBBFaceException extends RuntimeException {

    public IBBFaceException() {
        super();
    }

    public IBBFaceException(String s) {
        super(s);
    }

    public IBBFaceException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public IBBFaceException(Throwable throwable) {
        super(throwable);
    }
}
