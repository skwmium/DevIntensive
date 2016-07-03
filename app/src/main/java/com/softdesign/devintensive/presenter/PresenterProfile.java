package com.softdesign.devintensive.presenter;

import android.os.Bundle;

import com.softdesign.devintensive.R;
import com.softdesign.devintensive.common.App;
import com.softdesign.devintensive.data.Model;
import com.softdesign.devintensive.presenter.mappers.MapperParamEdit;
import com.softdesign.devintensive.presenter.mappers.MapperUser;
import com.softdesign.devintensive.ui.viewmodel.ProfileViewModel;
import com.softdesign.devintensive.utils.Const;
import com.softdesign.devintensive.view.ViewProfile;

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

    public void editProfileClicked() {
        if (mProfileViewModel == null) return;
        if (mProfileViewModel.isEditable()) {
            mProfileViewModel.setEditable(false);
            saveProfile();
        } else {
            mProfileViewModel.setEditable(true);
        }
    }

    private void loadProfile() {
        mView.showProgress();
        Subscription subscription = mModel.userGetProfile()
                .map(profileBaseResponse -> profileBaseResponse.getBody())
                .map(profile -> profile.getUser())
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
