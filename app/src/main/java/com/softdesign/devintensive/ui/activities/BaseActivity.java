package com.softdesign.devintensive.ui.activities;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.softdesign.devintensive.utils.InflaterFactory;

/**
 * Created by skwmium on 22.06.16.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public <T extends View> T $(@IdRes int id) {
        //noinspection unchecked
        return (T) findViewById(id);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getLayoutInflater().setFactory(new InflaterFactory(this));
        super.onCreate(savedInstanceState);
    }
}
