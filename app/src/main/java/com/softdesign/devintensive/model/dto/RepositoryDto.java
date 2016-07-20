package com.softdesign.devintensive.model.dto;

/**
 * Created by skwmium on 20.07.16.
 */
public class RepositoryDto {
    private String mId;
    private String mGitUrl;
    private String mTitle;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getGitUrl() {
        return mGitUrl;
    }

    public void setGitUrl(String gitUrl) {
        mGitUrl = gitUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
