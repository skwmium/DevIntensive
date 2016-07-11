package com.softdesign.devintensive.data.network.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by skwmium on 27.06.16.
 */
@SuppressWarnings("unused")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResult extends BaseValueObject {
    @JsonProperty("user")
    private User mUser;

    @JsonProperty("token")
    private String mToken;

    public User getUser() {
        return mUser;
    }

    public String getToken() {
        return mToken;
    }
}
