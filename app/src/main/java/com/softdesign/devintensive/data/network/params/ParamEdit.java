package com.softdesign.devintensive.data.network.params;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by skwmium on 27.06.16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParamEdit {
    @JsonProperty("phone")
    private String mPhoneNumber;

    @JsonProperty("vk")
    private String mVkUrl;

    @JsonProperty("name")
    private String mFirstname;

    @JsonProperty("surname")
    private String mLastName;

    @JsonProperty("github")
    private String mGithubUrl;

    @JsonProperty("bio")
    private String mBiography;

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public String getVkUrl() {
        return mVkUrl;
    }

    public void setVkUrl(String vkUrl) {
        mVkUrl = vkUrl;
    }

    public String getFirstname() {
        return mFirstname;
    }

    public void setFirstname(String firstname) {
        mFirstname = firstname;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getGithubUrl() {
        return mGithubUrl;
    }

    public void setGithubUrl(String githubUrl) {
        mGithubUrl = githubUrl;
    }

    public String getBiography() {
        return mBiography;
    }

    public void setBiography(String biography) {
        mBiography = biography;
    }
}
