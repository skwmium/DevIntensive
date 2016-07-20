package com.softdesign.devintensive.model.storage;

import android.support.annotation.Nullable;

import com.softdesign.devintensive.model.managers.PreferencesManager;
import com.softdesign.devintensive.utils.Const;
import com.softdesign.devintensive.utils.LogoutEvent;
import com.softdesign.devintensive.utils.Utils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by skwmium on 07.07.16.
 */
public final class LocalUser {
    private static final LocalUser INSTANCE = new LocalUser();

    public static LocalUser getInst() {
        return INSTANCE;
    }

    private LocalUser() {
    }

    public boolean login(@Nullable String authToken, @Nullable String userId) {
        if (Utils.isNullOrEmpty(authToken) || Utils.isNullOrEmpty(userId))
            return false;
        setAuthToken(authToken);
        setUserId(userId);
        return true;
    }

    public boolean logout() {
        setAuthToken(null);
        setUserId(null);
        EventBus.getDefault().post(new LogoutEvent());
        return true;
    }

    public boolean isLogined() {
        return !Utils.isNullOrEmpty(getAuthToken()) &&
                !Utils.isNullOrEmpty(getUserId());
    }

    @Nullable
    public String getAuthToken() {
        return PreferencesManager.getInst().get(Const.PREF_AUTH_TOKEN, null);
    }

    @Nullable
    public String getUserId() {
        return PreferencesManager.getInst().get(Const.PREF_USER_ID, null);
    }

    private void setAuthToken(@Nullable String authToken) {
        PreferencesManager.getInst().put(Const.PREF_AUTH_TOKEN, authToken);
    }

    private void setUserId(@Nullable String userId) {
        PreferencesManager.getInst().put(Const.PREF_USER_ID, userId);
    }
}
