package com.softdesign.devintensive.model.network.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by skwmium on 12.07.16.
 */
@SuppressWarnings("unused")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UploadImageResult extends BaseValueObject {
    @JsonProperty("photo")
    private String mPhotoUrl;

    @JsonProperty("updated")
    private Date mUpdated;

    public String getPhotoUrl() {
        return mPhotoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        mPhotoUrl = photoUrl;
    }

    public Date getUpdated() {
        return mUpdated;
    }

    public void setUpdated(Date updated) {
        mUpdated = updated;
    }
}
