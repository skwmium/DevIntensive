package com.softdesign.devintensive.data.storage;

import android.support.annotation.Nullable;

import com.softdesign.devintensive.data.managers.PreferencesManager;
import com.softdesign.devintensive.data.network.dto.User;
import com.softdesign.devintensive.utils.Const;
import com.softdesign.devintensive.utils.JsonUtils;
import com.softdesign.devintensive.utils.L;

/**
 * Created by skwmium on 10.07.16.
 */
public class PreferenceCache {
    public static void cacheUser(@Nullable User user) {
        L.d("put user into pref cache");
        String jsonString = JsonUtils.objectToJson(user);
        PreferencesManager.getInst().put(Const.PREF_USER, jsonString);
    }

    @Nullable
    public static User getUserFromCache() {
        L.d("get user from pref cache");
        String jsonString = PreferencesManager.getInst().get(Const.PREF_USER, null);
        return JsonUtils.jsonToObject(jsonString, User.class);
    }
}
