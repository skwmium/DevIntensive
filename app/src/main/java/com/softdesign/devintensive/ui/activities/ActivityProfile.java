package com.softdesign.devintensive.ui.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.softdesign.devintensive.R;
import com.softdesign.devintensive.data.viewmodel.ProfileViewModel;
import com.softdesign.devintensive.databinding.AppBarProfileBinding;
import com.softdesign.devintensive.utils.CropCircleTransformation;
import com.softdesign.devintensive.utils.L;
import com.squareup.picasso.Picasso;

public class ActivityProfile extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        L.d("onCreate", savedInstanceState == null ?
                " (is first start)" : " (isn't first start)");

        mToolbar = $(R.id.toolbar);
        mDrawerLayout = $(R.id.drawer_layout);
        mNavigationView = $(R.id.nav_view);


        setSupportActionBar(mToolbar);
        mNavigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, 0, 0);
        //noinspection deprecation
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        //data binding
        View bindingProfileView = $(R.id.app_bar_profile);
        AppBarProfileBinding binding = DataBindingUtil.bind(bindingProfileView);
        binding.setProfile(ProfileViewModel.createTestProfile());

        //test circle transformation
        ImageView imageAvatar = (ImageView) mNavigationView.getHeaderView(0).findViewById(R.id.image_avatar);
        Picasso.with(this)
                .load("https://new.vk.com/images/camera_400.png")
                .placeholder(R.drawable.nav_avatar_default)
                .transform(new CropCircleTransformation())
                .into(imageAvatar);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        L.d("onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        L.d("onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        L.d("onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        L.d("onStop");
    }

    @Override
    protected void onResume() {
        super.onResume();
        L.d("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        L.d("onPause");
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if (mDrawerLayout != null) mDrawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
