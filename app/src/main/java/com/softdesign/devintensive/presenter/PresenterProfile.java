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
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by skwmium on 05.07.16.
 */
@SuppressWarnings("Convert2MethodRef")
public class PresenterProfile extends BasePresenter {
    private enum State {
        IDLE,
        UPLOADING_AVATAR,
        UPLOADING_PHOTO
    }

    @Inject
    Model mModel;

    @Inject
    MapperUser mMapperUser;

    @Inject
    MapperParamEdit mMapperParamEdit;

    @Inject
    @Named(Const.IO_THREAD)
    Scheduler schedulerIo;

    private ViewProfile mView;
    private ProfileViewModel mProfileViewModel;

    @Nullable
    private File mPhotoFile;
    @NonNull
    private State mCurrentState = State.IDLE;

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
            case Const.REQUEST_PHOTO_PICKER:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    updateImageByState(data.getData().toString());
                }
                changeState(State.IDLE);
                break;
            case Const.REQUEST_PHOTO_CAMERA:
                if (resultCode == Activity.RESULT_OK && mPhotoFile != null) {
                    updateImageByState(Uri.fromFile(mPhotoFile).toString());
                }
                changeState(State.IDLE);
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
        changeState(State.UPLOADING_PHOTO);
        mView.showTakePhotoChooser();
    }

    public void changeProfileAvatarClicked() {
        changeState(State.UPLOADING_AVATAR);
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
                .subscribeOn(schedulerIo)
                .map(mMapperParamEdit)
                .flatMap(paramEdit -> mModel.userEditProfile(paramEdit))
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
                        mProfileViewModel.updateValues(profileViewModel);
                    }
                });
        addSubscription(subscription);
    }

    private void updateImageByState(String imageUrl) {
        if (isState(State.UPLOADING_AVATAR)) {
            updateProfileAvatar(imageUrl);
            mProfileViewModel.setAvatarUrl(imageUrl);
        } else if (isState(State.UPLOADING_PHOTO)) {
            updateProfilePhoto(imageUrl);
            mProfileViewModel.setPhotoUrl(imageUrl);
        }
    }

    private void updateProfileAvatar(String imageUrl) {
        mModel.updateProfileAvatar(imageUrl)
                .doOnError(throwable -> mView.showMessage(R.string.error_upload_avatar))
                .doOnNext(uploadImageResult -> mView.showMessage(R.string.profile_avatar_was_changed))
                .subscribe();
    }

    private void updateProfilePhoto(String imageUrl) {
        mModel.updateProfilePhoto(imageUrl)
                .doOnError(throwable -> mView.showMessage(R.string.error_upload_photo))
                .doOnNext(uploadImageResult -> mView.showMessage(R.string.profile_photo_was_changed))
                .subscribe();
    }


    // ---------- STATE ----------
    private boolean isState(@NonNull State state) {
        return mCurrentState == state;
    }

    private void changeState(@NonNull State state) {
        mCurrentState = state;
    }
}
