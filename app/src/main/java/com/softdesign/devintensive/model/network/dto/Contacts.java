package com.softdesign.devintensive.model.network.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by skwmium on 27.06.16.
 */
@SuppressWarnings("unused")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Contacts extends BaseValueObject {
    @JsonProperty("vk")
    private String mVk;

    @JsonProperty("phone")
    private String mPhone;

    @JsonProperty("email")
    private String mEmail;

    @JsonProperty("updated")
    private Date mUpdated;

    public String getVk() {
        return mVk;
    }

    public void setVk(String vk) {
        mVk = vk;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public Date getUpdated() {
        return mUpdated;
    }

    public void setUpdated(Date updated) {
        mUpdated = updated;
    }
}
