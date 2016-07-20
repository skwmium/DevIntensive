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
import com.softdesign.devintensive.model.mappers.MapperUserDtoViewModel;
import com.softdesign.devintensive.ui.viewmodel.ProfileViewModel;
import com.softdesign.devintensive.utils.Const;
import com.softdesign.devintensive.utils.Utils;
import com.softdesign.devintensive.view.ViewMain;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.softdesign.devintensive.utils.Const.REQUEST_PERMISSION_CAMERA;
import static com.softdesign.devintensive.utils.Const.REQUEST_PERMISSION_READ_EXTERNAL_STORAGE;

/**
 * Created by skwmium on 20.07.16.
 */
public class PresenterMain extends BasePresenter {
    @Inject
    Model model;

    @Inject
    MapperUserDtoViewModel mapperUserDtoViewModel;

    private ViewMain mView;

    @Nullable
    private Uri mPhotoUri;
    private ProfileViewModel mProfileViewModel;

    @Inject
    public PresenterMain() {
    }

    public PresenterMain(ViewMain view) {
        App.getAppComponent().inject(this);
        mView = view;
    }

    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mProfileViewModel = savedInstanceState.getParcelable(Const.KEY_PROFILE);
        }
        if (mProfileViewModel == null) {
            loadProfile();
        } else {
            mView.setLocalUserProfileViewModel(mProfileViewModel);
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(Const.KEY_PROFILE, mProfileViewModel);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Const.REQUEST_PHOTO_PICKER:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    updateProfileAvatar(data.getData());
                }
                break;
            case Const.REQUEST_PHOTO_CAMERA:
                if (resultCode == Activity.RESULT_OK && mPhotoUri != null) {
                    updateProfileAvatar(mPhotoUri);
                }
                break;
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mView.showPhotoPicker(Const.REQUEST_PHOTO_PICKER);
                }
                break;
            case REQUEST_PERMISSION_CAMERA:
                if (grantResults.length == 2
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    takePhotoClicked();
                }
                break;
        }
    }

    public void changeProfileAvatarClicked() {
        mView.showTakePhotoChooser();
    }

    public void takePhotoClicked() {
        String[] permissions = new String[]{CAMERA, WRITE_EXTERNAL_STORAGE};
        if (!mView.checkPermissionsAndRequestIfNotGranted(permissions, REQUEST_PERMISSION_CAMERA)) {
            mPhotoUri = Utils.createFileForPhoto();
            if (mPhotoUri == null) {
                mView.showMessage(R.string.error_photo_file_creation);
                return;
            }
            mView.takePhoto(mPhotoUri);
        }
    }

    public void openGalleryClicked() {
        String[] permissions = new String[]{READ_EXTERNAL_STORAGE};
        if (!mView.checkPermissionsAndRequestIfNotGranted(permissions,
                REQUEST_PERMISSION_READ_EXTERNAL_STORAGE)) {
            mView.showPhotoPicker(Const.REQUEST_PHOTO_PICKER);
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
                        mView.setLocalUserProfileViewModel(profileViewModel);
                    }
                });
        addSubscription(subscription);
    }


    private void updateProfileAvatar(Uri imageUri) {
        model.updateProfileAvatar(imageUri)
                .doOnError(throwable -> mView.showMessage(R.string.error_upload_avatar))
                .doOnNext(uploadImageResult -> mView.showMessage(R.string.profile_avatar_was_changed))
                .subscribe();
    }
}
