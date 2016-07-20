package com.softdesign.devintensive.model.dto;

import java.util.List;

/**
 * Created by skwmium on 20.07.16.
 */
public class UserDto {
    private String mRemoteId;
    private String mFullName;
    private String mEmail;
    private String mPhoneNumber;
    private String mVkProfileUrl;
    private String mAvatarUrl;
    private String mPhotoUrl;
    private String mAbout;
    private int mRating;
    private int mCodeLinesCount;
    private int mProjectCount;
    private List<RepositoryDto> mRepositories;
    private long mOrder;

    public String getRemoteId() {
        return mRemoteId;
    }

    public void setRemoteId(String remoteId) {
        mRemoteId = remoteId;
    }

    public String getFullName() {
        return mFullName;
    }

    public void setFullName(String fullName) {
        mFullName = fullName;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public String getVkProfileUrl() {
        return mVkProfileUrl;
    }

    public void setVkProfileUrl(String vkProfileUrl) {
        mVkProfileUrl = vkProfileUrl;
    }

    public String getAbout() {
        return mAbout;
    }

    public void setAbout(String about) {
        mAbout = about;
    }

    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        mAvatarUrl = avatarUrl;
    }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        mPhotoUrl = photoUrl;
    }

    public int getRating() {
        return mRating;
    }

    public void setRating(int rating) {
        mRating = rating;
    }

    public int getCodeLinesCount() {
        return mCodeLinesCount;
    }

    public void setCodeLinesCount(int codeLinesCount) {
        mCodeLinesCount = codeLinesCount;
    }

    public int getProjectCount() {
        return mProjectCount;
    }

    public void setProjectCount(int projectCount) {
        mProjectCount = projectCount;
    }

    public List<RepositoryDto> getRepositories() {
        return mRepositories;
    }

    public void setRepositories(List<RepositoryDto> repositories) {
        mRepositories = repositories;
    }

    public long getOrder() {
        return mOrder;
    }

    public void setOrder(long order) {
        mOrder = order;
    }
}
