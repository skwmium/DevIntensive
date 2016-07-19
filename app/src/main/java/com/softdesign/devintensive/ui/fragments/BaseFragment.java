package com.softdesign.devintensive.ui.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.View;

import com.softdesign.devintensive.presenter.BasePresenter;
import com.softdesign.devintensive.ui.activities.ActivityMainCallback;
import com.softdesign.devintensive.ui.activities.BaseActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by skwmium on 23.06.16.
 */
public abstract class BaseFragment extends Fragment {
    private Unbinder mUnbinder;
    protected ActivityMainCallback activityCallback;

    public <T extends View> T $(@IdRes int id) {
        //noinspection unchecked,ConstantConditions
        return (T) getView().findViewById(id);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
        activityCallback.setDrawerLocked(isDrawerLocked());
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof ActivityMainCallback)) {
            throw new ClassCastException(context.toString() +
                    " must implement callback interface!");
        }
        activityCallback = (ActivityMainCallback) context;
    }

    @Nullable
    protected BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    @Nullable
    protected abstract BasePresenter getPresenter();

    protected boolean isDrawerLocked() {
        return false;
    }

    public void showMessage(@StringRes int res) {
        BaseActivity baseActivity = getBaseActivity();
        if (baseActivity != null)
            baseActivity.showSnackbar(res);
    }

    public void showProgress() {
        BaseActivity baseActivity = getBaseActivity();
        if (baseActivity != null)
            getBaseActivity().showProgress();
    }

    public void hideProgress() {
        BaseActivity baseActivity = getBaseActivity();
        if (baseActivity != null)
            getBaseActivity().hideProgress();
    }
}
