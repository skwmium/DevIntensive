package com.softdesign.devintensive.data.network.params;

/**
 * Created by skwmium on 10.07.16.
 */
public enum UploadPhotoType {
    AVATAR("avatar"),
    PHOTO("photo");

    private final String type;

    UploadPhotoType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
