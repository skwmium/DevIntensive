package com.softdesign.devintensive.presenter;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.softdesign.devintensive.R;
import com.softdesign.devintensive.common.App;
import com.softdesign.devintensive.model.Model;
import com.softdesign.devintensive.model.mappers.MapperParamEdit;
import com.softdesign.devintensive.model.mappers.MapperUserDtoViewModel;
import com.softdesign.devintensive.ui.viewmodel.ProfileViewModel;
import com.softdesign.devintensive.utils.Const;
import com.softdesign.devintensive.utils.Utils;
import com.softdesign.devintensive.view.ViewProfile;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.softdesign.devintensive.utils.Const.REQUEST_PROFILE_PERMISSION_CAMERA;
import static com.softdesign.devintensive.utils.Const.REQUEST_PROFILE_PERMISSION_READ_EXTERNAL_STORAGE;

/**
 * Created by skwmium on 05.07.16.
 */
@SuppressWarnings("Convert2MethodRef")
public class PresenterProfile extends BasePresenter {
    @Inject
    Model model;

    @Inject
    MapperUserDtoViewModel mapperUserDtoViewModel;

    @Inject
    MapperParamEdit mapperParamEdit;

    @Inject
    @Named(Const.IO_THREAD)
    Scheduler schedulerIo;

    private ViewProfile mView;
    private ProfileViewModel mProfileViewModel;

    @Nullable
    private File mPhotoFile;

    @Inject
    public PresenterProfile() {
    }

    public PresenterProfile(ViewProfile profileView) {
        App.getAppComponent().inject(this);
        mView = profileView;
    }

    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mProfileViewModel = savedInstanceState.getParcelable(Const.KEY_PROFILE);
        }
        if (mProfileViewModel == null && mView.getArguments() != null) {
            mProfileViewModel = mView.getArguments().getParcelable(Const.KEY_PROFILE);
        }
        if (mProfileViewModel == null) {
            loadProfile();
        } else {
            mView.setProfileViewModel(mProfileViewModel);
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(Const.KEY_PROFILE, mProfileViewModel);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Const.REQUEST_PROFILE_PHOTO_PICKER:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    updateProfilePhoto(data.getData().toString());
                }
                break;
            case Const.REQUEST_PROFILE_PHOTO_CAMERA:
                if (resultCode == Activity.RESULT_OK && mPhotoFile != null) {
                    updateProfilePhoto(Uri.fromFile(mPhotoFile).toString());
                }
                break;
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PROFILE_PERMISSION_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mView.showPhotoPicker(Const.REQUEST_PROFILE_PHOTO_PICKER);
                }
                break;
            case REQUEST_PROFILE_PERMISSION_CAMERA:
                if (grantResults.length == 2
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    takePhotoClicked();
                }
                break;
        }
    }

    public void editProfileClicked() {
        if (mProfileViewModel == null) return;
        if (mProfileViewModel.isEditable()) {
            mProfileViewModel.setEditable(false);
            saveProfile();
        } else {
            mProfileViewModel.setEditable(true);
        }
        mView.setEditMode(mProfileViewModel.isEditable());
    }

    public void dialPhoneClicked() {
        Utils.dialPhoneNumber(mView.getContext(), mProfileViewModel.getMobilePhoneNumber());
    }

    public void sendEmailClicked() {
        Utils.sendEmail(mView.getContext(), null, mProfileViewModel.getEmail());
    }

    public void watchVkClicked() {
        Utils.openWebPage(mView.getContext(), mProfileViewModel.getVkProfileUrl());
    }

    public void changeProfilePhotoClicked() {
        mView.showTakePhotoChooser();
    }

    public void takePhotoClicked() {
        String[] permissions = new String[]{CAMERA, WRITE_EXTERNAL_STORAGE};
        if (mView.checkPermissionsAndRequestIfNotGranted(permissions, REQUEST_PROFILE_PERMISSION_CAMERA)) {
            mPhotoFile = Utils.createFileForPhoto();
            if (mPhotoFile == null) {
                mView.showMessage(R.string.error_photo_file_creation);
                return;
            }
            mView.takePhoto(mPhotoFile, Const.REQUEST_PROFILE_PHOTO_CAMERA);
        }
    }

    public void openGalleryClicked() {
        String[] permissions = new String[]{READ_EXTERNAL_STORAGE};
        if (mView.checkPermissionsAndRequestIfNotGranted(permissions,
                REQUEST_PROFILE_PERMISSION_READ_EXTERNAL_STORAGE)) {
            mView.showPhotoPicker(Const.REQUEST_PROFILE_PHOTO_PICKER);
        }
    }

    private void loadProfile() {
        mView.showProgress();
        Subscription subscription = model.userGetMe()
                .map(mapperUserDtoViewModel)
                .subscribe(new Subscriber<ProfileViewModel>() {
                    @Override
                    public void onCompleted() {
                        mView.hideProgress();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mView.hideProgress();
                        mView.showMessage(R.string.error_loading_data);
                    }

                    @Override
                    public void onNext(ProfileViewModel profileViewModel) {
                        mProfileViewModel = profileViewModel;
                        mView.setProfileViewModel(profileViewModel);
                    }
                });
        addSubscription(subscription);
    }

    private void saveProfile() {
        mView.showProgress();
        Subscription subscription = Observable.just(mProfileViewModel)
                .subscribeOn(schedulerIo)
                .map(mapperParamEdit)
                .flatMap(paramEdit -> model.userEditProfile(paramEdit))
                .map(mapperUserDtoViewModel)
                .subscribe(new Subscriber<ProfileViewModel>() {
                    @Override
                    public void onCompleted() {
                        mView.hideProgress();
                        mView.showMessage(R.string.profile_data_saved);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.hideProgress();
                        mView.showMessage(R.string.error_loading_data);
                    }

                    @Override
                    public void onNext(ProfileViewModel profileViewModel) {
                        mProfileViewModel.updateValues(profileViewModel);
                    }
                });
        addSubscription(subscription);
    }

    private void updateProfilePhoto(String path) {
        mProfileViewModel.setPhotoUrl(path);
        model.updateProfilePhoto(path)
                .doOnError(throwable -> mView.showMessage(R.string.error_upload_photo))
                .doOnNext(uploadImageResult -> mView.showMessage(R.string.profile_photo_was_changed))
                .subscribe();
    }
}
