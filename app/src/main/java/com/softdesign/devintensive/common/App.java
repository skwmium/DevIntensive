package com.softdesign.devintensive.common;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Created by skwmium on 22.06.16.
 */
public class App extends Application {
    private static App sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        initServices();
    }

    public App getInst() {
        return sInstance;
    }

    private void initServices() {
        if (BuildConfiguration.CRASHLYTICS_ENABLED) Fabric.with(this, new Crashlytics());
    }
}
