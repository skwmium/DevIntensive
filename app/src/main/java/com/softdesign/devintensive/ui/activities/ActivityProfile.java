package com.softdesign.devintensive.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.softdesign.devintensive.R;
import com.softdesign.devintensive.data.managers.PreferencesManager;
import com.softdesign.devintensive.data.viewmodel.ProfileViewModel;
import com.softdesign.devintensive.databinding.AppBarProfileBinding;
import com.softdesign.devintensive.utils.Const;
import com.softdesign.devintensive.utils.JsonUtils;

public class ActivityProfile extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener
        , View.OnClickListener {
    public static void start(@NonNull Context context) {
        Intent intent = new Intent(context, ActivityProfile.class);
        context.startActivity(intent);
    }

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private FloatingActionButton mFloatingActionEdit;

    private ProfileViewModel mProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mToolbar = $(R.id.toolbar);
        mDrawerLayout = $(R.id.drawer_layout);
        mNavigationView = $(R.id.nav_view);
        mFloatingActionEdit = $(R.id.fab_edit_profile);

        mFloatingActionEdit.setOnClickListener(this);

        initToolbar();
        initProfile(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(Const.KEY_PROFILE, mProfile);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if (mDrawerLayout != null) mDrawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_edit_profile:
                fabEditClicked();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        mNavigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, 0, 0);
        //noinspection deprecation
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void initProfile(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mProfile = (ProfileViewModel) savedInstanceState.getSerializable(Const.KEY_PROFILE);
        } else {
            String profileJsonSerialized = PreferencesManager.getInst().get(Const.PREF_PROFILE, null);
            mProfile = JsonUtils.jsonToObject(profileJsonSerialized, ProfileViewModel.class);
        }
        if (mProfile == null) mProfile = ProfileViewModel.createTestProfile();
        //data binding
        View bindingProfileView = $(R.id.app_bar_profile);
        AppBarProfileBinding binding = DataBindingUtil.bind(bindingProfileView);
        binding.setProfile(mProfile);
    }

    private void fabEditClicked() {
        if (mProfile == null) return;
        if (mProfile.isEditable()) {
            mProfile.setEditable(false);
            mFloatingActionEdit.setImageResource(R.drawable.ic_edit_24dp);
            storeData();
        } else {
            mProfile.setEditable(true);
            mFloatingActionEdit.setImageResource(R.drawable.ic_done_24dp);
        }
    }

    private void storeData() {
        String profileJsonSerialized = JsonUtils.objectToJson(mProfile);
        PreferencesManager.getInst().put(Const.PREF_PROFILE, profileJsonSerialized);
    }
}
