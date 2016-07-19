package com.softdesign.devintensive.ui.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

/**
 * Created by skwmium on 28.06.16.
 */
public class ActivitySplash extends BaseActivity {
    private static final long SPLASH_DELAY = 500L;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(this::startMainActivity, SPLASH_DELAY);
    }

    private void startMainActivity() {
        ActivityMain.start(this);
    }
}