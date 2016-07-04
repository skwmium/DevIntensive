package com.softdesign.devintensive.data.network.restmodels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by skwmium on 27.06.16.
 */
@SuppressWarnings("unused")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileValues extends BaseValueObject {
    @JsonProperty("projects")
    private Integer mProjectCount;

    @JsonProperty("linesCode")
    private Integer mCodeLineCount;

    @JsonProperty("rait")
    private Integer mRating;

    @JsonProperty("updated")
    private Date mUpdated;

    public Integer getProjectCount() {
        return mProjectCount;
    }

    public Integer getCodeLineCount() {
        return mCodeLineCount;
    }

    public Integer getRating() {
        return mRating;
    }

    public Date getUpdated() {
        return mUpdated;
    }
}
