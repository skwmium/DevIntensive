package com.softdesign.devintensive.model.network.params;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by skwmium on 27.06.16.
 */
public class ParamEdit {
    @NonNull
    private Map<String, String> mParamsMap = new HashMap<>();

    public void setPhoneNumber(String phoneNumber) {
        mParamsMap.put("phone", phoneNumber);
    }

    public void setVkUrl(String vkUrl) {
        mParamsMap.put("vk", vkUrl);
    }

    public void setFirstname(String firstname) {
        mParamsMap.put("name", firstname);
    }

    public void setLastName(String lastName) {
        mParamsMap.put("surname", lastName);
    }

    public void setGithubUrl(String githubUrl) {
        mParamsMap.put("github", githubUrl);
    }

    public void setBiography(String biography) {
        mParamsMap.put("bio", biography);
    }

    @NonNull
    public Map<String, String> getParamsMap() {
        return mParamsMap;
    }
}
