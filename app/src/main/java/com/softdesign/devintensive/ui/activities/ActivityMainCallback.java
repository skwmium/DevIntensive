package com.softdesign.devintensive.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

/**
 * Created by skwmium on 19.07.16.
 */
public interface ActivityMainCallback {
    void setActionBar(Toolbar toolbar);

    void setDrawerLocked(boolean locked);

    void startProfileFragment(@Nullable Bundle arg);

    void startProfileListFragment();

    void startAuthFragment();

    void startRestorePasswordFragment();

    void onBackPressed();
}
