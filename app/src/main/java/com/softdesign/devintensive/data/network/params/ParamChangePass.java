package com.softdesign.devintensive.data.network.params;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by skwmium on 08.07.16.
 */
public class ParamChangePass {
    @JsonProperty("pass3")
    private String mNewPassword;

    public ParamChangePass(String newPassword) {
        mNewPassword = newPassword;
    }

    public String getNewPassword() {
        return mNewPassword;
    }

    public void setNewPassword(String newPassword) {
        mNewPassword = newPassword;
    }
}
