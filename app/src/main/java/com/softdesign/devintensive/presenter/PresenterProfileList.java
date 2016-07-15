package com.softdesign.devintensive.presenter;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.softdesign.devintensive.R;
import com.softdesign.devintensive.common.App;
import com.softdesign.devintensive.data.Model;
import com.softdesign.devintensive.presenter.mappers.MapperUserList;
import com.softdesign.devintensive.ui.viewmodel.ProfileViewModel;
import com.softdesign.devintensive.utils.Const;
import com.softdesign.devintensive.view.ViewProfileList;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by skwmium on 14.07.16.
 */
public class PresenterProfileList extends BasePresenter {
    @Inject
    Model mModel;

    @Inject
    MapperUserList mMapperUserList;

    private ViewProfileList mView;
    private List<ProfileViewModel> mProfileList = new ArrayList<>();

    @Inject
    public PresenterProfileList() {
    }

    public PresenterProfileList(ViewProfileList view) {
        App.getAppComponent().inject(this);
        mView = view;
    }

    public void onViewCreated(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            ArrayList<ProfileViewModel> savedList = savedInstanceState.getParcelableArrayList(Const.KEY_PROFILE_LIST);
            if (savedList != null) {
                mProfileList = savedList;
                mView.showProfileList(mProfileList);
                return;
            }
        }
        loadProfileList();
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(Const.KEY_PROFILE_LIST, (ArrayList<? extends Parcelable>) mProfileList);
    }

    private void loadProfileList() {
        mView.showProgress();
        Subscription subscription = mModel.getUserList()
                .map(mMapperUserList)
                .subscribe(new Subscriber<List<ProfileViewModel>>() {
                    @Override
                    public void onCompleted() {
                        mView.hideProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showMessage(R.string.error_loading_data);
                        mView.hideProgress();
                    }

                    @Override
                    public void onNext(List<ProfileViewModel> profileViewModels) {
                        mProfileList = profileViewModels;
                        mView.showProfileList(profileViewModels);
                    }
                });
        addSubscription(subscription);
    }
}
