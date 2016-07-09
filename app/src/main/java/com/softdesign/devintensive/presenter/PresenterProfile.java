package com.softdesign.devintensive.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.softdesign.devintensive.R;
import com.softdesign.devintensive.common.App;
import com.softdesign.devintensive.data.Model;
import com.softdesign.devintensive.presenter.mappers.MapperParamEdit;
import com.softdesign.devintensive.presenter.mappers.MapperUser;
import com.softdesign.devintensive.ui.viewmodel.ProfileViewModel;
import com.softdesign.devintensive.utils.Const;
import com.softdesign.devintensive.utils.Utils;
import com.softdesign.devintensive.view.ViewProfile;

import java.io.File;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by skwmium on 05.07.16.
 */
@SuppressWarnings("Convert2MethodRef")
public class PresenterProfile extends BasePresenter {
    @Inject
    Model mModel;

    @Inject
    MapperUser mMapperUser;

    @Inject
    MapperParamEdit mMapperParamEdit;

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
            mProfileViewModel = (ProfileViewModel) savedInstanceState.getSerializable(Const.KEY_PROFILE);
        }
        if (mProfileViewModel == null) {
            loadProfile();
        } else {
            mView.setProfileViewModel(mProfileViewModel);
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(Const.KEY_PROFILE, mProfileViewModel);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Const.REQUEST_PHOTO_PICKER:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    mProfileViewModel.setPhotoUrl(data.getData().toString());
                }
                break;
            case Const.REQUEST_PHOTO_CAMERA:
                if (resultCode == Activity.RESULT_OK && mPhotoFile != null) {
                    mProfileViewModel.setPhotoUrl(Uri.fromFile(mPhotoFile).toString());
                }
                break;
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Const.REQUEST_PERMISSION_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Utils.showPhotoPickerDialog(mView.getActivity(), Const.REQUEST_PHOTO_PICKER);
                }
                break;
            case Const.REQUEST_PERMISSION_CAMERA:
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

    public void watchRepoClicked() {
        Utils.openWebPage(mView.getContext(), mProfileViewModel.getRepository());
    }

    public void watchVkClicked() {
        Utils.openWebPage(mView.getContext(), mProfileViewModel.getVkProfile());
    }

    public void changeProfilePhotoClicked() {
        mView.showTakePhotoChooser();
    }

    public void takePhotoClicked() {
        if (Utils.checkPermissionAndRequestIfNotGranted(mView.getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, Const.REQUEST_PERMISSION_CAMERA)) {
            mPhotoFile = Utils.takePhoto(mView.getActivity());
        }
    }

    public void openGalleryClicked() {
        if (Utils.checkPermissionAndRequestIfNotGranted(mView.getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE, Const.REQUEST_PERMISSION_READ_EXTERNAL_STORAGE))
            Utils.showPhotoPickerDialog(mView.getActivity(), Const.REQUEST_PHOTO_PICKER);
    }

    private void loadProfile() {
        mView.showProgress();
        Subscription subscription = mModel.userGetMe()
                .map(profileBaseResponse -> profileBaseResponse.getBody())
                .map(mMapperUser)
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
                .map(mMapperParamEdit)
                .flatMap(paramEdit -> mModel.userEditProfile(paramEdit))
                .map(editProfileResponse -> editProfileResponse.getBody())
                .map(editProfileResult -> editProfileResult.getUser())
                .map(mMapperUser)
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
                    }
                });
        addSubscription(subscription);
    }
}
