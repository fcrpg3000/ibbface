/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) Child.java 2013-09-29 16:39
 */

package com.ibbface.domain.model.user;

import com.google.common.base.Objects;
import com.ibbface.domain.model.common.Constellation;
import com.ibbface.domain.model.user.base.BaseChild;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * The user's child entity class.
 *
 * @author Fuchun
 * @since 1.0
 */
public class Child extends BaseChild {
    private static final long serialVersionUID = 1L;

    private Gender gender;
    private Constellation constellation;

    public Child() {
        super();
    }

    public Child(Long userId) {
        super(userId);
    }

    /**
     * 使用给定的实体，更新当前实体的信息。通常是已改变的属性的变更。
     *
     * @param other 新的实体。
     * @return 返回当前实体对象。
     * @throws IllegalStateException if {@code !other.getId().equals(getId())}.
     */
    @Override
    public Child update(Child other) {
        checkArgument(other != null, "The given `other` child must not be null!");
        assert other != null; // just cancel IDE warning
        if (!Objects.equal(getId(), other.getId())) {
            throw new IllegalStateException("The given `other` child's id not " +
                    "equals this child's id, cannot be updated.");
        }
        if (other.getPetName() != null) {
            setPetName(other.getPetName());
        }
        if (other.getRealName() != null) {
            setRealName(other.getRealName());
        }
        if (other.getBirthday() != null) {
            setBirthday(other.getBirthday());
            setZodiacId(other.getZodiacId());
        }
        if (other.getBloodTypeId() != null) {
            setBloodTypeId(other.getBloodTypeId());
        }
        if (other.getLunarBirth() != null) {
            setLunarBirth(other.getLunarBirth());
        }
        if (other.getConstellationId() != null) {
            setConstellationId(other.getConstellationId());
        }
        if (other.getAvatarUri() != null) {
            setAvatarUri(other.getAvatarUri());
            setSmallAvatarUri(other.getSmallAvatarUri());
            setThumbAvatarUri(other.getThumbAvatarUri());
        }
        return this;
    }

    @Override
    public void setGenderCode(Short genderCode) {
        super.setGenderCode(genderCode);
        gender = Gender.of(genderCode);
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
        super.setGenderCode(gender == null ? Gender.UNKNOWN.getCode() :
                gender.getCode());
    }

    @Override
    public void setConstellationId(Short constellationId) {
        super.setConstellationId(constellationId);
        if (constellationId != null && constellationId > 0) {
            constellation = Constellation.get(constellationId);
        }
    }

    public Constellation getConstellation() {
        return constellation;
    }

    public void setConstellation(Constellation constellation) {
        this.constellation = constellation;
        super.setConstellationId(constellation == null ? null : constellation.getId());
    }

    public Object[] toArray() {
        return new Object[]{
                getId(), getUserId(), getPetName(), getRealName(), getGenderCode(),
                getBirthday(), getLunarBirth(), getConstellationId(), getZodiacId(),
                getBloodTypeId(), getAvatarUri(), getSmallAvatarUri(), getThumbAvatarUri()
        };
    }
}
