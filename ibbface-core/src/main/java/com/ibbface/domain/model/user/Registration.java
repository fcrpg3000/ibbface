/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) Registration.java 2013-08-02 13:50
 */

package com.ibbface.domain.model.user;

import com.google.common.base.Objects;
import com.ibbface.domain.shared.AbstractValueObject;

import java.io.Serializable;

/**
 * @author Fuchun
 * @since 1.0
 */
public class Registration extends AbstractValueObject<Registration>
        implements Serializable {
    private static final long serialVersionUID = 1L;

    private String email;
    private String userName;
    private String password1;
    private String password2;
    private String mobileNo;
    private String gender;

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

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public int hashCode() {
        return 31 * Objects.hashCode(email, userName, password1, password2,
                mobileNo, gender);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Registration)) return false;

        Registration that = (Registration) o;

        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (gender != null ? !gender.equals(that.gender) : that.gender != null) return false;
        if (mobileNo != null ? !mobileNo.equals(that.mobileNo) : that.mobileNo != null) return false;
        if (password1 != null ? !password1.equals(that.password1) : that.password1 != null) return false;
        if (password2 != null ? !password2.equals(that.password2) : that.password2 != null) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;

        return true;
    }

    @Override
    public String toString() {
        return String.format("Registration{email='%s', userName='%s', password1='%s', " +
                "password2='%s', mobileNo='%s', gender='%s'}", email, userName, password1,
                password2, mobileNo, gender);
    }
}
