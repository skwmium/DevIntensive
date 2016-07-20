package com.softdesign.devintensive.model.network.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by skwmium on 27.06.16.
 */
@SuppressWarnings("unused")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User extends BaseValueObject {
    @JsonProperty("_id")
    private String mId;

    @JsonProperty("first_name")
    private String mFirstName;

    @JsonProperty("second_name")
    private String mSecondName;

    @JsonProperty("repositories")
    private Repositories mRepositories;

    @JsonProperty("contacts")
    private Contacts mContacts;

    @JsonProperty("profileValues")
    private ProfileValues mProfileValues;

    @JsonProperty("publicInfo")
    private PublicInfo mPublicInfo;

    @JsonProperty("specialization")
    private String mSpecialization;

    @JsonProperty("role")
    private String mRole;

    @JsonProperty("updated")
    private Date mUpdated;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getSecondName() {
        return mSecondName;
    }

    public void setSecondName(String secondName) {
        mSecondName = secondName;
    }

    public Repositories getRepositories() {
        return mRepositories;
    }

    public void setRepositories(Repositories repositories) {
        mRepositories = repositories;
    }

    public Contacts getContacts() {
        return mContacts;
    }

    public void setContacts(Contacts contacts) {
        mContacts = contacts;
    }

    public ProfileValues getProfileValues() {
        return mProfileValues;
    }

    public void setProfileValues(ProfileValues profileValues) {
        mProfileValues = profileValues;
    }

    public PublicInfo getPublicInfo() {
        return mPublicInfo;
    }

    public void setPublicInfo(PublicInfo publicInfo) {
        mPublicInfo = publicInfo;
    }

    public String getSpecialization() {
        return mSpecialization;
    }

    public void setSpecialization(String specialization) {
        mSpecialization = specialization;
    }

    public String getRole() {
        return mRole;
    }

    public void setRole(String role) {
        mRole = role;
    }

    public Date getUpdated() {
        return mUpdated;
    }

    public void setUpdated(Date updated) {
        mUpdated = updated;
    }
}
