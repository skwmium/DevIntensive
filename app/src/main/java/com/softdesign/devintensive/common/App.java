package com.softdesign.devintensive.common;

import android.app.Application;

/**
 * Created by skwmium on 22.06.16.
 */
public class App extends Application {
    private static App sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public App getInst() {
        return sInstance;
    }
}
