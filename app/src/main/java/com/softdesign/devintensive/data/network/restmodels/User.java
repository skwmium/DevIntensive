package com.softdesign.devintensive.data.network.restmodels;

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

    @JsonProperty("repositiries")
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

    public String getFirstName() {
        return mFirstName;
    }

    public String getSecondName() {
        return mSecondName;
    }

    public Repositories getRepositories() {
        return mRepositories;
    }

    public Contacts getContacts() {
        return mContacts;
    }

    public ProfileValues getProfileValues() {
        return mProfileValues;
    }

    public PublicInfo getPublicInfo() {
        return mPublicInfo;
    }

    public String getSpecialization() {
        return mSpecialization;
    }

    public String getRole() {
        return mRole;
    }

    public Date getUpdated() {
        return mUpdated;
    }
}
