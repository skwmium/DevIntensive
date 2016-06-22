package com.softdesign.devintensive.ui.activities;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by skwmium on 22.06.16.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public <T extends View> T $(@IdRes int id) {
        //noinspection unchecked
        return (T) findViewById(id);
    }
}
