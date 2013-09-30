/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) BaseChild.java 2013-09-29 16:39
 */

package com.ibbface.domain.model.user.base;

import com.google.common.base.Objects;
import com.ibbface.domain.model.user.Child;
import com.ibbface.domain.shared.AbstractEntity;

import java.util.Date;

/**
 * The user's child base information class.
 *
 * @author Fuchun
 * @since 1.0
 */
public abstract class BaseChild extends AbstractEntity<Long, Child> {
    private static final long serialVersionUID = 1L;

    public static final String PROP_PET_NAME = "petName";
    public static final String PROP_REAL_NAME = "realName";
    public static final String PROP_GENDER_CODE = "genderCode";
    public static final String PROP_BIRTHDAY = "birthday";
    public static final String PROP_LUNAR_BIRTH = "lunarBirth";
    public static final String PROP_CONSTELLATION_ID = "constellationId";
    public static final String PROP_ZODIAC_ID = "zodiacId";
    public static final String PROP_BLOOD_TYPE_ID = "bloodTypeId";
    public static final String PROP_AVATAR_URI = "avatarUri";
    public static final String PROP_SMALL_AVATAR_URI = "smallAvatarUri";
    public static final String PROP_THUMB_AVATAR_URI = "thumbAvatarUri";

    private Long userId;
    private String petName;
    private String realName;
    private Short genderCode;
    private Date birthday;
    private String lunarBirth;
    private Short constellationId;
    private Short zodiacId;
    private Short bloodTypeId;
    private String avatarUri;
    private String smallAvatarUri;
    private String thumbAvatarUri;
    private Date lastModifiedTime;
    private Date createdTime;

    protected BaseChild() {
        super();
    }

    protected BaseChild(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Short getGenderCode() {
        return genderCode;
    }

    public void setGenderCode(Short genderCode) {
        this.genderCode = genderCode;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getLunarBirth() {
        return lunarBirth;
    }

    public void setLunarBirth(String lunarBirth) {
        this.lunarBirth = lunarBirth;
    }

    public Short getConstellationId() {
        return constellationId;
    }

    public void setConstellationId(Short constellationId) {
        this.constellationId = constellationId;
    }

    public Short getZodiacId() {
        return zodiacId;
    }

    public void setZodiacId(Short zodiacId) {
        this.zodiacId = zodiacId;
    }

    public Short getBloodTypeId() {
        return bloodTypeId;
    }

    public void setBloodTypeId(Short bloodTypeId) {
        this.bloodTypeId = bloodTypeId;
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

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add(PROP_ID, getId())
                .add(PROP_USER_ID, getUserId())
                .add(PROP_PET_NAME, getPetName())
                .add(PROP_REAL_NAME, getRealName())
                .add(PROP_GENDER_CODE, getGenderCode())
                .add(PROP_BIRTHDAY, getBirthday())
                .add(PROP_LUNAR_BIRTH, getLunarBirth())
                .add(PROP_CONSTELLATION_ID, getConstellationId())
                .add(PROP_ZODIAC_ID, getZodiacId())
                .add(PROP_BLOOD_TYPE_ID, getBloodTypeId())
                .add(PROP_AVATAR_URI, getAvatarUri())
                .add(PROP_SMALL_AVATAR_URI, getSmallAvatarUri())
                .add(PROP_THUMB_AVATAR_URI, getThumbAvatarUri())
                .add(PROP_LAST_MODIFIED_TIME, getLastModifiedTime())
                .add(PROP_CREATED_TIME, getCreatedTime())
                .toString();
    }
}
