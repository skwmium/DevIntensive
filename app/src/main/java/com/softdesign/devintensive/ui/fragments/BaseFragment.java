package com.softdesign.devintensive.ui.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;

import com.softdesign.devintensive.ui.activities.BaseActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by skwmium on 23.06.16.
 */
public abstract class BaseFragment extends Fragment {
    private Unbinder mUnbinder;

    public <T extends View> T $(@IdRes int id) {
        //noinspection unchecked,ConstantConditions
        return (T) getView().findViewById(id);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
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
