/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) BaseUserDigital.java 2013-07-28 15:13
 */

package com.ibbface.domain.model.user.base;

import com.google.common.base.Objects;
import com.ibbface.domain.model.user.UserDigital;
import com.ibbface.domain.shared.AbstractEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * The user digital information base entity.
 *
 * @author Fuchun
 * @since 1.0
 */
public abstract class BaseUserDigital extends AbstractEntity<Long, UserDigital>
        implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String PROP_TOTAL_SCORE = "totalScore";
    public static final String PROP_USER_SCORE = "userScore";
    public static final String PROP_USER_GRADE = "userGrade";
    public static final String PROP_TOTAL_AMOUNT = "totalAmount";
    public static final String PROP_BALANCE = "balance";
    public static final String PROP_THREADS = "threads";
    public static final String PROP_POSTS = "posts";
    public static final String PROP_POSTS_REPLIES = "postsReplies";
    public static final String PROP_NEWS_COMMENTS = "newsComments";
    public static final String PROP_IMAGE_COMMENTS = "imageComments";
    public static final String PROP_IMAGE_COMMENT_REPLIES = "imageCommentReplies";

    private Long userId;
    private String userName;
    private Long totalScore;
    private Long userScore;
    private Integer userGrade;
    private Long totalAmount;
    private Long balance;
    private Integer threads;
    private Integer posts;
    private Integer postsReplies;
    private Integer newsComments;
    private Integer imageComments;
    private Integer imageCommentReplies;
    private Date lastModifiedTime;

    protected BaseUserDigital() {
        initialize();
    }

    protected BaseUserDigital(Long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
        initialize();
    }

    protected void initialize() {
        totalScore = 0L;
        userScore = 0L;
        userGrade = 1;
        totalAmount = 0L;
        balance = 0L;
        threads = 0;
        posts = 0;
        postsReplies = 0;
        newsComments = 0;
        imageComments = 0;
        imageCommentReplies = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long getId() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setId(Long id) {
        setUserId(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isNew() {
        return getLastModifiedTime() == null;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Long totalScore) {
        this.totalScore = totalScore;
    }

    public Long getUserScore() {
        return userScore;
    }

    public void setUserScore(Long userScore) {
        this.userScore = userScore;
    }

    public Integer getUserGrade() {
        return userGrade;
    }

    public void setUserGrade(Integer userGrade) {
        this.userGrade = userGrade;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Integer getThreads() {
        return threads;
    }

    public void setThreads(Integer threads) {
        this.threads = threads;
    }

    public Integer getPosts() {
        return posts;
    }

    public void setPosts(Integer posts) {
        this.posts = posts;
    }

    public Integer getPostsReplies() {
        return postsReplies;
    }

    public void setPostsReplies(Integer postsReplies) {
        this.postsReplies = postsReplies;
    }

    public Integer getNewsComments() {
        return newsComments;
    }

    public void setNewsComments(Integer newsComments) {
        this.newsComments = newsComments;
    }

    public Integer getImageComments() {
        return imageComments;
    }

    public void setImageComments(Integer imageComments) {
        this.imageComments = imageComments;
    }

    public Integer getImageCommentReplies() {
        return imageCommentReplies;
    }

    public void setImageCommentReplies(Integer imageCommentReplies) {
        this.imageCommentReplies = imageCommentReplies;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(getClass())
                .add(PROP_USER_ID, getUserId())
                .add(PROP_USER_NAME, getUserName())
                .add(PROP_TOTAL_SCORE, getTotalScore())
                .add(PROP_USER_SCORE, getUserScore())
                .add(PROP_USER_GRADE, getUserGrade())
                .add(PROP_TOTAL_AMOUNT, getTotalAmount())
                .add(PROP_BALANCE, getBalance())
                .add(PROP_THREADS, getThreads())
                .add(PROP_POSTS, getPosts())
                .add(PROP_POSTS_REPLIES, getPostsReplies())
                .add(PROP_NEWS_COMMENTS, getNewsComments())
                .add(PROP_IMAGE_COMMENTS, getImageComments())
                .add(PROP_IMAGE_COMMENT_REPLIES, getImageCommentReplies())
                .add(PROP_LAST_MODIFIED_TIME, getLastModifiedTime())
                .toString();
    }
}
