package com.softdesign.devintensive.presenter;

import com.softdesign.devintensive.common.App;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by skwmium on 01.07.16.
 */
public abstract class BasePresenter {
    @Inject
    protected CompositeSubscription mCompositeSubscription;

    public BasePresenter() {
        App.getAppComponent().inject(this);
    }

    protected void addSubscription(Subscription subscription) {
        mCompositeSubscription.add(subscription);
    }

    public void onStop() {
        mCompositeSubscription.clear();
    }
}
