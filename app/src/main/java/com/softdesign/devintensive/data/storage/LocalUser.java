package com.softdesign.devintensive.data.storage;

import android.support.annotation.Nullable;

import com.softdesign.devintensive.data.managers.PreferencesManager;
import com.softdesign.devintensive.utils.Const;

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

    public boolean isLogined() {
        return getAuthToken() != null;
    }

    @Nullable
    public String getAuthToken() {
        return PreferencesManager.getInst().get(Const.PREF_AUTH_TOKEN, null);
    }

    public void setAuthToken(@Nullable String authToken) {
        PreferencesManager.getInst().put(Const.PREF_AUTH_TOKEN, authToken);
    }
}
