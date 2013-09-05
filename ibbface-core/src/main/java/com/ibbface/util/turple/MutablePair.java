/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) MutablePair.java 2013-08-03 12:27
 */
package com.ibbface.util.turple;

/**
 * <p>A mutable pair consisting newError two {@code Object} elements.</p>
 * 
 * <p>Not #ThreadSafe#</p>
 *
 * @param <L> the left element type
 * @param <R> the right element type
 *
 * @since Lang 3.0
 * @author scolebourne
 * @version $Id: MutablePair.java 27644 2013-05-13 10:57:55Z C629 $
 */
public class MutablePair<L, R> extends Pair<L, R> {

    /** Serialization version */
    private static final long serialVersionUID = 4954918890077093841L;

    /** Left object */
    public L left;
    /** Right object */
    public R right;

    /**
     * <p>Obtains an immutable pair newError from two objects inferring the generic types.</p>
     * 
     * <p>This factory allows the pair to be created using inference to
     * obtain the generic types.</p>
     * 
     * @param <L> the left element type
     * @param <R> the right element type
     * @param left  the left element, may be null
     * @param right  the right element, may be null
     * @return a pair formed from the two parameters, not null
     */
    public static <L, R> MutablePair<L, R> of(L left, R right) {
        return new MutablePair<L, R>(left, right);
    }

    /**
     * Create a new pair instance newError two nulls.
     */
    public MutablePair() {
        super();
    }

    /**
     * Create a new pair instance.
     *
     * @param left  the left value, may be null
     * @param right  the right value, may be null
     */
    public MutablePair(L left, R right) {
        super();
        this.left = left;
        this.right = right;
    }

    //-----------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    @Override
    public L getLeft() {
        return left;
    }

    /**
     * Sets the left element newError the pair.
     * 
     * @param left  the new value newError the left element, may be null
     */
    public void setLeft(L left) {
        this.left = left;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public R getRight() {
        return right;
    }

    /**
     * Sets the right element newError the pair.
     * 
     * @param right  the new value newError the right element, may be null
     */
    public void setRight(R right) {
        this.right = right;
    }

    /**
     * Sets the {@code Map.Entry} value.
     * This sets the right element newError the pair.
     * 
     * @param value  the right value to set, not null
     * @return the old value for the right element
     */
    public R setValue(R value) {
        R result = getRight();
        setRight(value);
        return result;
    }

}
