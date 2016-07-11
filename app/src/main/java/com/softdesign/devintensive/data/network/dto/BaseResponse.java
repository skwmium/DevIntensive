package com.softdesign.devintensive.data.network.dto;

import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by skwmium on 27.06.16.
 */
@SuppressWarnings("unused")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T extends BaseValueObject> {
    @JsonProperty("success")
    private boolean mSuccess;

    @Nullable
    @JsonProperty("data")
    private T mBody;

    @Nullable
    @JsonProperty("err")
    private String mError;

    public boolean isSuccess() {
        return mSuccess;
    }

    @Nullable
    public T getBody() {
        return mBody;
    }

    @Nullable
    public String getError() {
        return mError;
    }
}
