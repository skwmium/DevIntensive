package com.softdesign.devintensive.data.network.restmodels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by skwmium on 27.06.16.
 */
@SuppressWarnings("unused")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PublicInfo extends BaseValueObject {
    @JsonProperty("bio")
    private String mBio;

    @JsonProperty("avatar")
    private String mAvatarUrl;

    @JsonProperty("photo")
    private String mPhotoUrl;

    @JsonProperty("updated")
    private Date mUpdated;

    public String getBio() {
        return mBio;
    }

    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }

    public Date getUpdated() {
        return mUpdated;
    }
}
