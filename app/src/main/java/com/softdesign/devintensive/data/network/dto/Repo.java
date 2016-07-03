package com.softdesign.devintensive.data.network.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by skwmium on 27.06.16.
 */
@SuppressWarnings("unused")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Repo extends BaseValueObject {
    @JsonProperty("_id")
    private String mId;

    @JsonProperty("git")
    private String mGitUrl;

    @JsonProperty("title")
    private String mTitle;

    public String getId() {
        return mId;
    }

    public String getGitUrl() {
        return mGitUrl;
    }

    public String getTitle() {
        return mTitle;
    }
}
