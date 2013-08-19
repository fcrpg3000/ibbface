/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) UserDigital.java 2013-07-28 15:13
 */

package com.ibbface.domain.model.user;

import com.ibbface.domain.model.user.base.BaseUserDigital;

/**
 * User digital entity class.
 *
 * @author Fuchun
 * @since 1.0
 */
public class UserDigital extends BaseUserDigital {
    private static final long serialVersionUID = 1L;

    /**
     * Creates and Returns new {@code UserDigital} factory method.
     */
    public static UserDigital newDigital() {
        return new UserDigital();
    }

    /**
     * Creates and Returns new {@code UserDigital} of the specified {@link User} factory method.
     *
     * @param user the specified {@link User}.
     * @return Returns new {@code UserDigital} instance.
     */
    public static UserDigital newDigital(User user) {
        return new UserDigital(user);
    }

    /**
     * Creates and Returns new {@code UserDigital} of the specified
     * {@code userId} and {@code userName} factory method.
     *
     * @param userId   the user's id.
     * @param userName this user's name.
     * @return Returns new {@code UserDigital} instance.
     */
    public static UserDigital newDigital(Long userId, String userName) {
        return new UserDigital(userId, userName);
    }

    public UserDigital() {
        super();
    }

    public UserDigital(User user) {
        super(user.getUserId(), user.getUserName());
    }

    public UserDigital(Long userId, String userName) {
        super(userId, userName);
    }

    /**
     * Atomically adds the given value to the current score and total score.
     *
     * @param delta the value to add.
     * @return the current {@code UserDigital}.
     */
    public UserDigital addScore(Long delta) {
        if (delta == null) {
            return this;
        }
        delta = Math.abs(delta);
        setTotalScore(getTotalScore() + delta);
        setUserScore(getUserScore() + delta);
        return this;
    }

    /**
     * Atomically adds the given value to the current balance and total amount.
     *
     * @param delta the value to add.
     * @return the current {@code UserDigital}.
     */
    public UserDigital addMoney(Long delta) {
        if (delta == null) {
            return this;
        }
        delta = Math.abs(delta);
        setTotalAmount(getTotalAmount() + delta);
        setBalance(getBalance() + delta);
        return this;
    }

    /**
     * 使用给定的实体，更新当前实体的信息。通常是已改变的属性的变更。
     *
     * @param other 新的实体。
     * @return 返回当前实体对象。
     * @throws IllegalStateException if {@code !other.getId().equals(getId())}.
     */
    @Override
    public UserDigital update(UserDigital other) {
        if (other == null) {
            throw new IllegalArgumentException(
                    "The given `other` UserDigital must not be null!");
        }
        // userName cannot be modified
        setTotalAmount(other.getTotalAmount());
        setBalance(other.getBalance());
        setUserScore(other.getUserScore());
        setTotalScore(other.getTotalScore());
        setUserGrade(other.getUserGrade());
        setThreads(other.getThreads());
        setPosts(other.getPosts());
        setPostsReplies(other.getPostsReplies());
        setNewsComments(other.getNewsComments());
        setImageComments(other.getImageComments());
        setImageCommentReplies(other.getImageCommentReplies());
        return this;
    }

    public Object[] toArray() {
        return new Object[]{
                getUserId(), getUserName(), getTotalScore(), getUserScore(),
                getUserGrade(), getTotalAmount(), getBalance(), getThreads(),
                getPosts(), getPostsReplies(), getNewsComments(), getImageComments(),
                getImageCommentReplies(), getLastModifiedTime()
        };
    }
}
