/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) Child.java 2013-09-29 16:39
 */

package com.ibbface.domain.model.user;

import com.google.common.base.Objects;
import com.ibbface.domain.model.common.BloodType;
import com.ibbface.domain.model.common.Constellation;
import com.ibbface.domain.model.common.Zodiac;
import com.ibbface.domain.model.user.base.BaseChild;
import com.ibbface.domain.shared.QueryValue;
import org.joda.time.DateTime;

import java.util.Date;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * The user's child entity class.
 *
 * @author Fuchun
 * @since 1.0
 */
public class Child extends BaseChild implements QueryValue {
    private static final long serialVersionUID = 1L;

    private Gender gender;
    private Constellation constellation;
    private BloodType bloodType;
    private Zodiac zodiac;

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

    @Override
    public void setBloodTypeId(Short bloodTypeId) {
        super.setBloodTypeId(bloodTypeId);
        if (bloodTypeId != null && bloodTypeId > 0) {
            bloodType = BloodType.get(bloodTypeId);
        }
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
        super.setBloodTypeId(bloodType == null ? null : bloodType.getId());
    }

    @Override
    public void setZodiacId(Short zodiacId) {
        super.setZodiacId(zodiacId);
        if (zodiacId != null && zodiacId > 0) {
            zodiac = Zodiac.get(zodiacId);
        }
    }

    public Zodiac getZodiac() {
        return zodiac;
    }

    public void setZodiac(Zodiac zodiac) {
        this.zodiac = zodiac;
        super.setZodiacId(zodiac == null ? null : zodiac.getId());
    }

    @Override
    public Object[] toArray() {
        return new Object[]{
                getId(), getUserId(), getPetName(), getRealName(), getGenderCode(),
                getBirthday(), getLunarBirth(), getConstellationId(), getZodiacId(),
                getBloodTypeId(), getAvatarUri(), getSmallAvatarUri(), getThumbAvatarUri()
        };
    }

    // logic
    // --------------------------------------------------------------------------------

    public Child birthday(Date birthday) {
        checkArgument(birthday != null, "The given `birthday` must be not null.");
        boolean changed = false;
        if (nullOrNotSameDay(birthday)) {
            setBirthday(birthday);
            changed = true;
        }
        if (changed) {
            // Auto set constellation property base on birthday.
            Constellation c = Constellation.fromBirthday(getBirthday());
            setConstellation(c);
        }

        return this;
    }

    /**
     * Returns {@code true} if current birthday is {@code null}, or
     * given birthday and this birthday is not same day, otherwise {@code false}.
     *
     * @param birthday the given new birthday.
     */
    private boolean nullOrNotSameDay(final Date birthday) {
        if (getBirthday() == null) {
            return true;
        }
        DateTime dtOld = new DateTime(getBirthday());
        DateTime dtNew = new DateTime(birthday);
        return dtOld.getEra() != dtNew.getEra() ||
                dtOld.getYear() != dtNew.getYear() ||
                dtOld.getDayOfYear() != dtNew.getDayOfYear();
    }
}
