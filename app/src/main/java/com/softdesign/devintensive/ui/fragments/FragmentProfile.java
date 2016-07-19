package com.softdesign.devintensive.ui.fragments;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softdesign.devintensive.R;
import com.softdesign.devintensive.databinding.FragmentProfileBinding;
import com.softdesign.devintensive.di.DaggerComponentView;
import com.softdesign.devintensive.di.ModuleViewDynamically;
import com.softdesign.devintensive.presenter.BasePresenter;
import com.softdesign.devintensive.presenter.PresenterProfile;
import com.softdesign.devintensive.ui.dialogs.DialogChooseProfilePhoto;
import com.softdesign.devintensive.ui.viewmodel.ProfileViewModel;
import com.softdesign.devintensive.utils.Utils;
import com.softdesign.devintensive.view.ViewProfile;

import javax.inject.Inject;

import butterknife.BindView;

import static com.softdesign.devintensive.ui.dialogs.DialogChooseProfilePhoto.OnChooseItemListener.Type.CAMERA;
import static com.softdesign.devintensive.ui.dialogs.DialogChooseProfilePhoto.OnChooseItemListener.Type.GALLERY;

/**
 * Created by skwmium on 19.07.16.
 */
public class FragmentProfile extends BaseFragment implements ViewProfile, View.OnClickListener {
    @Nullable
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab_edit_profile)
    FloatingActionButton floatingActionEdit;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Inject
    PresenterProfile presenter;

    private FragmentProfileBinding mProfileBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerComponentView
                .builder()
                .moduleViewDynamically(new ModuleViewDynamically(this))
                .build()
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        activityCallback.setActionBar(toolbar);
        presenter.onCreate(savedInstanceState);
    }

    private void init() {
        mProfileBinding = DataBindingUtil.bind(getView());
        if (mProfileBinding != null) {
            mProfileBinding.contentProfile.imageActionPhone.setOnClickListener(this);
            mProfileBinding.contentProfile.imageActionEmail.setOnClickListener(this);
            mProfileBinding.contentProfile.imageActionRepo.setOnClickListener(this);
            mProfileBinding.contentProfile.imageActionVk.setOnClickListener(this);
            mProfileBinding.relativeProfilePlaceholder.setOnClickListener(this);
        }
        floatingActionEdit.setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        presenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Nullable
    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_edit_profile:
                presenter.editProfileClicked();
                break;
            case R.id.image_action_phone:
                presenter.dialPhoneClicked();
                break;
            case R.id.image_action_email:
                presenter.sendEmailClicked();
                break;
            case R.id.image_action_repo:
                presenter.watchRepoClicked();
                break;
            case R.id.image_action_vk:
                presenter.watchVkClicked();
                break;
            case R.id.relative_profile_placeholder:
                presenter.changeProfilePhotoClicked();
                break;
        }
    }

    @Override
    public void setEditMode(boolean editMode) {
        collapsingToolbarLayout.setExpandedTitleColor(editMode ? Color.TRANSPARENT : Color.WHITE);
    }

    @Override
    public boolean checkPermissionsAndRequestIfNotGranted(@NonNull String[] permissons, int requestCode) {
        return Utils.checkPermissionsAndRequestIfNotGranted(this, permissons, requestCode);
    }

    @Override
    public void showPhotoPicker(int requestCode) {
        Utils.showPhotoPickerDialog(this, requestCode);
    }

    @Override
    public void takePhoto(Uri photoFileUri) {
        Utils.takePhoto(this, photoFileUri);
    }

    @Override
    public void setProfileViewModel(ProfileViewModel profileViewModel) {
        mProfileBinding.setProfile(profileViewModel);
    }

    @Override
    public void showTakePhotoChooser() {
        new DialogChooseProfilePhoto()
                .setListener(type -> {
                    if (type == GALLERY)
                        presenter.openGalleryClicked();
                    else if (type == CAMERA)
                        presenter.takePhotoClicked();
                }).show(getFragmentManager(), null);
    }
}
