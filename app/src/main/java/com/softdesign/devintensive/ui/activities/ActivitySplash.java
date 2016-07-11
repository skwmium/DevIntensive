package com.softdesign.devintensive.ui.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.softdesign.devintensive.data.storage.LocalUser;
import com.softdesign.devintensive.presenter.BasePresenter;

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

    @Nullable
    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    private void startMainActivity() {
        if (LocalUser.getInst().isLogined()) {
            ActivityProfile.start(this);
        } else {
            ActivityAuth.start(this);
        }
    }
}