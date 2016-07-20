package com.softdesign.devintensive.presenter;

import com.softdesign.devintensive.R;
import com.softdesign.devintensive.common.App;
import com.softdesign.devintensive.model.Model;
import com.softdesign.devintensive.model.network.api.RetrofitException;
import com.softdesign.devintensive.model.network.dto.BaseResponse;
import com.softdesign.devintensive.view.ViewRestorePassword;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by skwmium on 08.07.16.
 */
public class PresenterRestorePassword extends BasePresenter {
    @Inject
    protected Model model;

    private ViewRestorePassword mView;

    @Inject
    public PresenterRestorePassword() {
    }

    public PresenterRestorePassword(ViewRestorePassword view) {
        App.getAppComponent().inject(this);
        mView = view;
    }

    public void restoreClicked() {
        mView.showProgress();
        Subscription subscription = model
                .userRestorePassword(mView.getEmail())
                .subscribe(new Subscriber<BaseResponse>() {
                    @Override
                    public void onCompleted() {
                        mView.hideProgress();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mView.hideProgress();
                        RetrofitException error = (RetrofitException) throwable;
                        BaseResponse errorBodyAs = error.getErrorBodyAs(BaseResponse.class);
                        if (errorBodyAs != null) {
                            mView.showMessage(R.string.auth_error_restore_password);
                        } else {
                            mView.showMessage(R.string.error_indefinite);
                        }
                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        mView.showMessage(R.string.auth_password_was_restored);
                        mView.goBack();
                    }
                });
        addSubscription(subscription);
    }
}
