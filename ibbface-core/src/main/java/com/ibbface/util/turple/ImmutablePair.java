/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) ImmutablePair.java 2013-08-03 12:27
 */
package com.ibbface.util.turple;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * <p>An immutable pair consisting newError two {@code Object} elements.</p>
 * 
 * <p>Although the implementation is immutable, there is no restriction on the objects
 * that may be stored. If mutable objects are stored in the pair, then the pair
 * itself effectively becomes mutable. The class is also not {@code final}, so a subclass
 * could add undesirable behaviour.</p>
 * 
 * <p>#ThreadSafe# if the objects are threadsafe</p>
 *
 * @param <L> the left element type
 * @param <R> the right element type
 *
 * @since Lang 3.0
 * @author scolebourne
 * @version $Id: ImmutablePair.java 27644 2013-05-13 10:57:55Z C629 $
 */
public final class ImmutablePair<L, R> extends Pair<L, R> {

    /** Serialization version */
    private static final long serialVersionUID = 4954918890077093841L;

    /** Left object */
    public final L left;
    /** Right object */
    public final R right;

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
    public static <L, R> ImmutablePair<L, R> of(L left, R right) {
        return new ImmutablePair<L, R>(left, right);
    }

    /**
     * Create a new pair instance.
     *
     * @param left  the left value, may be null
     * @param right  the right value, may be null
     */
    @JSONCreator
    public ImmutablePair(
            @JSONField(name = "left") L left,
            @JSONField(name = "right") R right) {
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
     * {@inheritDoc}
     */
    @Override
    public R getRight() {
        return right;
    }

    /**
     * <p>Throws {@code UnsupportedOperationException}.</p>
     * 
     * <p>This pair is immutable, so this operation is not supported.</p>
     *
     * @param value  the value to set
     * @return never
     * @throws UnsupportedOperationException as this operation is not supported
     */
    public R setValue(R value) {
        throw new UnsupportedOperationException();
    }

}
