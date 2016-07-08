package com.softdesign.devintensive.ui.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.View;

import com.softdesign.devintensive.presenter.BasePresenter;
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

    @Override
    public void onStop() {
        super.onStop();
        if (getPresenter() != null) {
            getPresenter().onStop();
        }
    }

    @Override
    public Context getContext() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            return super.getContext();
        else
            return getActivity();
    }

    @NonNull
    protected BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    @Nullable
    protected abstract BasePresenter getPresenter();

    public void showMessage(@StringRes int res) {
        getBaseActivity().showSnackbar(res);
    }

    public void showProgress() {
        getBaseActivity().showProgress();
    }

    public void hideProgress() {
        getBaseActivity().hideProgress();
    }
}
