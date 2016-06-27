package com.softdesign.devintensive.ui.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;

import com.softdesign.devintensive.ui.activities.BaseActivity;

/**
 * Created by skwmium on 23.06.16.
 */
public abstract class BaseFragment extends Fragment {

    public <T extends View> T $(@IdRes int id) {
        //noinspection unchecked,ConstantConditions
        return (T) getView().findViewById(id);
    }

    @Nullable
    protected BaseActivity getBaseActivity() {
        Activity activity = getActivity();
        if (activity != null && activity instanceof BaseActivity) {
            return (BaseActivity) activity;
        }
        return null;
    }

    @Override
    public Context getContext() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            return super.getContext();
        else
            return getActivity();
    }
}
