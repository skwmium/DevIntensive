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
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.softdesign.devintensive.R;
import com.softdesign.devintensive.presenter.BasePresenter;
import com.softdesign.devintensive.ui.view.InflaterFactory;
import com.softdesign.devintensive.utils.L;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by skwmium on 22.06.16.
 */
@SuppressWarnings("unused")
public abstract class BaseActivity extends AppCompatActivity implements com.softdesign.devintensive.view.View,
        NavigationView.OnNavigationItemSelectedListener {
    @Nullable
    private ProgressDialog mProgressDialog;

    @Nullable
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Nullable
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Nullable
    @BindView(R.id.nav_view)
    NavigationView navigationView;


    // ---------- ACTIVITY LOGIC ----------
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getLayoutInflater().setFactory(new InflaterFactory(this));
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (getPresenter() != null) {
            getPresenter().onStop();
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        initUi();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        ButterKnife.bind(this);
        initUi();
    }

    @Nullable
    protected View getRootView() {
        ViewGroup rootGroup = (ViewGroup) this.findViewById(android.R.id.content);
        if (rootGroup == null) return null;
        return rootGroup.getChildAt(0);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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


    // ---------- OTHER UI LOGIC ----------
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if (drawerLayout != null) drawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {
            case R.id.nav_item_contacts:
                ActivityProfileList.start(this);
                return true;
            case R.id.nav_item_profile:
                ActivityProfile.start(this);
                return true;
            default:
                return false;
        }
    }

    private void initUi() {
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }
        if (toolbar != null && drawerLayout != null) {
            setSupportActionBar(toolbar);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
            //noinspection deprecation
            drawerLayout.setDrawerListener(toggle);
            toggle.syncState();
        }
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

    @Nullable
    protected abstract BasePresenter getPresenter();
}
