package com.softdesign.devintensive.presenter;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.softdesign.devintensive.R;
import com.softdesign.devintensive.common.App;
import com.softdesign.devintensive.model.Model;
import com.softdesign.devintensive.model.mappers.MapperListUserDtoViewModel;
import com.softdesign.devintensive.ui.adapters.OnItemCLickListener;
import com.softdesign.devintensive.ui.adapters.OnItemChangedListener;
import com.softdesign.devintensive.ui.viewmodel.BaseViewModel;
import com.softdesign.devintensive.ui.viewmodel.ProfileViewModel;
import com.softdesign.devintensive.utils.Const;
import com.softdesign.devintensive.utils.L;
import com.softdesign.devintensive.utils.Utils;
import com.softdesign.devintensive.view.ViewProfileList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by skwmium on 14.07.16.
 */
public class PresenterProfileList extends BasePresenter implements OnItemCLickListener,
        OnItemChangedListener {
    @Inject
    Model model;

    @Inject
    MapperListUserDtoViewModel mapperListUserDtoViewModel;

    @Nullable
    private Subscription mPrevSubscriptionFilter;

    private ViewProfileList mView;
    private List<ProfileViewModel> mProfileList = new ArrayList<>();

    @Inject
    public PresenterProfileList() {
    }

    public PresenterProfileList(ViewProfileList view) {
        App.getAppComponent().inject(this);
        mView = view;
    }

    @Override
    public void onItemClick(BaseViewModel viewModel) {
        ProfileViewModel profileViewModel = (ProfileViewModel) viewModel;
        Bundle bundle = new Bundle();
        bundle.putParcelable(Const.KEY_PROFILE, profileViewModel);
        mView.startProfileView(bundle);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++)
                Collections.swap(mProfileList, i, i + 1);
        } else {
            for (int i = fromPosition; i > toPosition; i--)
                Collections.swap(mProfileList, i, i - 1);
        }
        mView.onItemMove(fromPosition, toPosition);
        model.changeUserOrder(fromPosition, toPosition).subscribe();
        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        ProfileViewModel viewModel = mProfileList.get(position);
        model.setUserRemoved(viewModel.getId()).subscribe();
        mProfileList.remove(position);
        mView.onItemDismiss(position);
    }

    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            ArrayList<ProfileViewModel> savedList = savedInstanceState.getParcelableArrayList(Const.KEY_PROFILE_LIST);
            if (savedList != null) {
                mProfileList = savedList;
            }
        }
    }

    public void onCreated() {
        if (!mProfileList.isEmpty()) {
            mView.showProfileList(mProfileList);
            return;
        }
        loadProfileList();
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(Const.KEY_PROFILE_LIST,
                (ArrayList<? extends Parcelable>) mProfileList);
    }

    public void filterProfiles(@NonNull String query) {
        L.e("filter query ", query);
        if (mPrevSubscriptionFilter != null && mPrevSubscriptionFilter.isUnsubscribed()) {
            mCompositeSubscription.remove(mPrevSubscriptionFilter);
            mPrevSubscriptionFilter.unsubscribe();
        }
        mPrevSubscriptionFilter = Observable.from(mProfileList)
                .filter(viewModel -> {
                    String model = viewModel.getName();
                    String pattern = query.toLowerCase();
                    //noinspection SimplifiableIfStatement
                    if (Utils.isNullOrEmpty(model) && !pattern.isEmpty())
                        return false;
                    return model.toLowerCase().startsWith(query);
                })
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(profileViewModels -> mView.showProfileList(profileViewModels));
        mCompositeSubscription.add(mPrevSubscriptionFilter);
    }

    private void loadProfileList() {
        mView.showProgress();
        Subscription subscription = model.getUserList()
                .map(mapperListUserDtoViewModel)
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
