package com.softdesign.devintensive.model.network.params;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by skwmium on 08.07.16.
 */
public class ParamForgotPassword {
    @JsonProperty("email")
    private String mEmail;

    public ParamForgotPassword(String email) {
        mEmail = email;
    }

    public String getEmail() {
        return mEmail;
    }
}
