package com.softdesign.devintensive.utils;

import android.support.annotation.NonNull;

/**
 * Created by skwmium on 19.07.16.
 */
public class LocalUserEvent {
    public enum Action {
        LOGIN,
        LOGOUT
    }

    @NonNull
    private Action mAction;

    public LocalUserEvent(@NonNull Action action) {
        mAction = action;
    }

    @NonNull
    public Action getAction() {
        return mAction;
    }
}
