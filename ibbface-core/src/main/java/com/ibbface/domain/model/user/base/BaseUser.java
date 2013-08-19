/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) BaseUser.java 2013-07-28 14:38
 */

package com.ibbface.domain.model.user.base;

import com.google.common.base.Objects;
import com.ibbface.domain.model.user.User;
import com.ibbface.domain.shared.AbstractEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * User entity base class.
 *
 * @author Fuchun
 * @since 1.0
 */
public abstract class BaseUser extends AbstractEntity<Long, User>
        implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String PROP_EMAIL = "email";
    public static final String PROP_USER_NAME = "userName";
    public static final String PROP_USER_HANDLE = "userHandle";
    public static final String PROP_HASH_PASSWORD = "hashPassword";
    public static final String PROP_GENDER_CODE = "genderCode";
    public static final String PROP_PASSWORD_SALT = "passwordSalt";
    public static final String PROP_MOBILE_NO = "mobileNo";
    public static final String PROP_MOBILE_VERIFIED = "mobileVerified";
    public static final String PROP_AVATAR_URI = "avatarUri";
    public static final String PROP_SMALL_AVATAR_URI = "smallAvatarUri";
    public static final String PROP_THUMB_AVATAR_URI = "thumbAvatarUri";
    public static final String PROP_SPARE_EMAIL = "spareEmail";
    public static final String PROP_IS_ACTIVATED = "activated";
    public static final String PROP_IS_DISABLED = "disabled";
    public static final String PROP_DISABLED_START = "disabledStart";
    public static final String PROP_DISABLED_END = "disabledEnd";
    public static final String PROP_ROLE_DATA = "roleData";

    private Long userId;
    private String email;
    private String userName;
    private String userHandle;
    private String hashPassword;
    private Short genderCode;
    private String passwordSalt;
    private String mobileNo;
    private boolean mobileVerified;
    private String avatarUri;
    private String smallAvatarUri;
    private String thumbAvatarUri;
    private String spareEmail;
    private String roleData;
    private boolean isActivated;
    private boolean isDisabled;
    private Date disabledStart;
    private Date disabledEnd;
    private Date createdTime;
    private Date lastModifiedTime;

    protected BaseUser() {
    }

    protected BaseUser(Long userId) {
        this.userId = userId;
    }

    protected BaseUser(String email, String userName, String userHandle,
                       String hashPassword, Short genderCode, String passwordSalt) {
        this.email = email;
        this.userName = userName;
        this.userHandle = userHandle;
        this.hashPassword = hashPassword;
        this.genderCode = genderCode;
        this.passwordSalt = passwordSalt;
    }

    public void preInsert() {
        final Date dateNow = new Date();
        this.createdTime = dateNow;
        this.lastModifiedTime = dateNow;
    }

    public void preUpdate() {
        this.lastModifiedTime = new Date();
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserHandle() {
        return userHandle;
    }

    public void setUserHandle(String userHandle) {
        this.userHandle = userHandle;
    }

    public String getHashPassword() {
        return hashPassword;
    }

    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }

    public Short getGenderCode() {
        return genderCode;
    }

    public void setGenderCode(Short genderCode) {
        this.genderCode = genderCode;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public boolean isMobileVerified() {
        return mobileVerified;
    }

    public void setMobileVerified(boolean mobileVerified) {
        this.mobileVerified = mobileVerified;
    }

    public String getAvatarUri() {
        return avatarUri;
    }

    public void setAvatarUri(String avatarUri) {
        this.avatarUri = avatarUri;
    }

    public String getSmallAvatarUri() {
        return smallAvatarUri;
    }

    public void setSmallAvatarUri(String smallAvatarUri) {
        this.smallAvatarUri = smallAvatarUri;
    }

    public String getThumbAvatarUri() {
        return thumbAvatarUri;
    }

    public void setThumbAvatarUri(String thumbAvatarUri) {
        this.thumbAvatarUri = thumbAvatarUri;
    }

    public String getSpareEmail() {
        return spareEmail;
    }

    public void setSpareEmail(String spareEmail) {
        this.spareEmail = spareEmail;
    }

    public String getRoleData() {
        return roleData;
    }

    public void setRoleData(String roleData) {
        this.roleData = roleData;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }

    public boolean isDisabled() {
        return isDisabled;
    }

    public void setDisabled(boolean disabled) {
        isDisabled = disabled;
    }

    public Date getDisabledStart() {
        return disabledStart;
    }

    public void setDisabledStart(Date disabledStart) {
        this.disabledStart = disabledStart;
    }

    public Date getDisabledEnd() {
        return disabledEnd;
    }

    public void setDisabledEnd(Date disabledEnd) {
        this.disabledEnd = disabledEnd;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(getClass()).add(PROP_USER_ID, getUserId())
                .add(PROP_EMAIL, getEmail()).add(PROP_USER_NAME, getUserName())
                .add(PROP_USER_HANDLE, getUserHandle())
                .add(PROP_HASH_PASSWORD, getHashPassword())
                .add(PROP_GENDER_CODE, getGenderCode())
                .add(PROP_PASSWORD_SALT, getPasswordSalt())
                .add(PROP_MOBILE_NO, getMobileNo())
                .add(PROP_MOBILE_VERIFIED, isMobileVerified())
                .add(PROP_AVATAR_URI, getAvatarUri())
                .add(PROP_SMALL_AVATAR_URI, getSmallAvatarUri())
                .add(PROP_THUMB_AVATAR_URI, getThumbAvatarUri())
                .add(PROP_SPARE_EMAIL, getSpareEmail())
                .add(PROP_ROLE_DATA, getRoleData())
                .add(PROP_IS_ACTIVATED, isActivated())
                .add(PROP_IS_DISABLED, isDisabled())
                .add(PROP_DISABLED_START, getDisabledStart())
                .add(PROP_DISABLED_END, getDisabledEnd())
                .add(PROP_CREATED_TIME, getCreatedTime())
                .add(PROP_LAST_MODIFIED_TIME, getLastModifiedTime())
                .toString();
    }
}