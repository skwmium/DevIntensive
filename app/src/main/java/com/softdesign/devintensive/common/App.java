package com.softdesign.devintensive.common;

import android.annotation.SuppressLint;
import android.app.Application;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Created by skwmium on 22.06.16.
 */
public class App extends Application {
    private static App sInstance;

    private Toast mToast;

    @SuppressLint("ShowToast")
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        mToast = Toast.makeText(this, null, Toast.LENGTH_SHORT);

        initServices();
    }

    public static App getInst() {
        return sInstance;
    }

    public void showToast(@StringRes int res) {
        showToast(getString(res));
    }

    public void showToast(@Nullable String s) {
        if (s == null || s.isEmpty()) return;
        mToast.setText(s);
        mToast.show();
    }

    private void initServices() {
        if (BuildConfiguration.CRASHLYTICS_ENABLED) Fabric.with(this, new Crashlytics());
    }
}
