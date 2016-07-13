package com.softdesign.devintensive.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.softdesign.devintensive.R;
import com.softdesign.devintensive.databinding.AppBarProfileBinding;
import com.softdesign.devintensive.databinding.NavHeaderMainBinding;
import com.softdesign.devintensive.di.DaggerComponentProfile;
import com.softdesign.devintensive.di.ModuleViewProfile;
import com.softdesign.devintensive.presenter.BasePresenter;
import com.softdesign.devintensive.presenter.PresenterProfile;
import com.softdesign.devintensive.ui.dialogs.DialogChooseProfilePhoto;
import com.softdesign.devintensive.ui.viewmodel.ProfileViewModel;
import com.softdesign.devintensive.view.ViewProfile;

import javax.inject.Inject;

import butterknife.BindView;

import static com.softdesign.devintensive.ui.dialogs.DialogChooseProfilePhoto.OnChooseItemListener.Type.CAMERA;
import static com.softdesign.devintensive.ui.dialogs.DialogChooseProfilePhoto.OnChooseItemListener.Type.GALLERY;

public class ActivityProfile extends BaseActivity implements ViewProfile, NavigationView.OnNavigationItemSelectedListener
        , View.OnClickListener {
    public static void start(@NonNull Context context) {
        Intent intent = new Intent(context, ActivityProfile.class);
        context.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.fab_edit_profile)
    FloatingActionButton floatingActionEdit;

    @BindView(R.id.app_bar_profile)
    View viewProfileBinding;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Inject
    PresenterProfile mPresenter;

    private AppBarProfileBinding mProfileBinding;
    private NavHeaderMainBinding mNavHeaderMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        DaggerComponentProfile
                .builder()
                .moduleViewProfile(new ModuleViewProfile(this))
                .build()
                .inject(this);

        init();
        mPresenter.onCreate(savedInstanceState);
    }

    private void init() {
        initToolbar();
        mProfileBinding = DataBindingUtil.bind(viewProfileBinding);
        mNavHeaderMainBinding = DataBindingUtil.bind(navigationView.getHeaderView(0));
        mNavHeaderMainBinding.imageAvatar.setOnClickListener(this);

        mProfileBinding.contentProfile.imageActionPhone.setOnClickListener(this);
        mProfileBinding.contentProfile.imageActionEmail.setOnClickListener(this);
        mProfileBinding.contentProfile.imageActionRepo.setOnClickListener(this);
        mProfileBinding.contentProfile.imageActionVk.setOnClickListener(this);
        mProfileBinding.relativeProfilePlaceholder.setOnClickListener(this);
        floatingActionEdit.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPresenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Nullable
    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mPresenter.onSaveInstanceState(outState);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if (drawerLayout != null) drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_edit_profile:
                mPresenter.editProfileClicked();
                break;
            case R.id.image_action_phone:
                mPresenter.dialPhoneClicked();
                break;
            case R.id.image_action_email:
                mPresenter.sendEmailClicked();
                break;
            case R.id.image_action_repo:
                mPresenter.watchRepoClicked();
                break;
            case R.id.image_action_vk:
                mPresenter.watchVkClicked();
                break;
            case R.id.relative_profile_placeholder:
                mPresenter.changeProfilePhotoClicked();
                break;
            case R.id.image_avatar:
                mPresenter.changeProfileAvatarClicked();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        //noinspection deprecation
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void showMessage(@StringRes int res) {
        showSnackbar(res);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public ActivityProfile getActivity() {
        return this;
    }

    @Override
    public void setEditMode(boolean editMode) {
        collapsingToolbarLayout.setExpandedTitleColor(editMode ? Color.TRANSPARENT : Color.WHITE);
    }

    @Override
    public void setProfileViewModel(ProfileViewModel profileViewModel) {
        mProfileBinding.setProfile(profileViewModel);
        mNavHeaderMainBinding.setProfile(profileViewModel);
    }

    @Override
    public void showTakePhotoChooser() {
        new DialogChooseProfilePhoto()
                .setListener(type -> {
                    if (type == GALLERY)
                        mPresenter.openGalleryClicked();
                    else if (type == CAMERA)
                        mPresenter.takePhotoClicked();
                })
                .show(getFragmentManager(), null);
    }
}
