package com.softdesign.devintensive.ui.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.softdesign.devintensive.R;
import com.softdesign.devintensive.ui.view.InflaterFactory;

import butterknife.ButterKnife;

/**
 * Created by skwmium on 22.06.16.
 */
@SuppressWarnings("unused")
public abstract class BaseActivity extends AppCompatActivity implements com.softdesign.devintensive.view.View {
    @Nullable
    private ProgressDialog mProgressDialog;

    // ---------- ACTIVITY LOGIC ----------
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getLayoutInflater().setFactory(new InflaterFactory(this));
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        ButterKnife.bind(this);
    }

    @Nullable
    protected View getRootView() {
        ViewGroup rootGroup = (ViewGroup) this.findViewById(android.R.id.content);
        if (rootGroup == null) return null;
        return rootGroup.getChildAt(0);
    }


    // ---------- PROGRESS DIALOG ----------
    @Override
    public void showProgress() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this, R.style.activity_base_progress_dialog);
            mProgressDialog.setCancelable(false);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        mProgressDialog.show();
        mProgressDialog.setContentView(R.layout.dialog_progress_activity);
    }

    @Override
    public void hideProgress() {
        if (mProgressDialog == null || !mProgressDialog.isShowing()) {
            return;
        }
        mProgressDialog.dismiss();
    }


    // ---------- MESSAGES ----------
    @Override
    public void showMessage(@StringRes int res) {
        showSnackbar(res);
    }

    public void showSnackbar(@StringRes int res) {
        showSnackbar(getString(res));
    }

    public void showSnackbar(@Nullable String s) {
        if (s == null || s.isEmpty()) return;
        View rootView = getRootView();
        if (rootView == null) return;
        Snackbar.make(rootView, s, Snackbar.LENGTH_SHORT).show();
    }


    // ---------- UTIL ----------
    public <T extends View> T $(@IdRes int id) {
        //noinspection unchecked
        return (T) findViewById(id);
    }

    @Nullable
    public <T extends BaseActivity> T as(Class<T> clazz) {
        //noinspection unchecked
        return (T) this;
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }
}
