package com.softdesign.devintensive.model.network.dto;

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

    public void setProjectCount(Integer projectCount) {
        mProjectCount = projectCount;
    }

    public Integer getCodeLineCount() {
        return mCodeLineCount;
    }

    public void setCodeLineCount(Integer codeLineCount) {
        mCodeLineCount = codeLineCount;
    }

    public Integer getRating() {
        return mRating;
    }

    public void setRating(Integer rating) {
        mRating = rating;
    }

    public Date getUpdated() {
        return mUpdated;
    }

    public void setUpdated(Date updated) {
        mUpdated = updated;
    }
}
