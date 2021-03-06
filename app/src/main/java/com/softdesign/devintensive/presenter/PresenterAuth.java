package com.softdesign.devintensive.presenter;

import android.support.annotation.Nullable;

import com.softdesign.devintensive.R;
import com.softdesign.devintensive.common.App;
import com.softdesign.devintensive.model.Model;
import com.softdesign.devintensive.model.network.api.RetrofitException;
import com.softdesign.devintensive.model.network.dto.AuthResult;
import com.softdesign.devintensive.model.network.dto.BaseResponse;
import com.softdesign.devintensive.model.storage.LocalUser;
import com.softdesign.devintensive.view.ViewAuth;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by skwmium on 01.07.16.
 */
@SuppressWarnings("Convert2MethodRef")
public class PresenterAuth extends BasePresenter {
    @Inject
    Model model;

    private ViewAuth mView;

    @Inject
    public PresenterAuth() {
    }

    public PresenterAuth(ViewAuth authView) {
        App.getAppComponent().inject(this);
        mView = authView;
    }

    public void loginClicked() {
        String email = mView.getEmail();
        String password = mView.getPassword();

        if (!checkAuthData(email, password)) return;
        mView.showProgress();
        Subscription subscription = model
                .autUser(email, password)
                .subscribe(new Subscriber<AuthResult>() {
                    @Override
                    public void onCompleted() {
                        mView.hideProgress();
                        if (!LocalUser.getInst().isLogined()) {
                            mView.showMessage(R.string.auth_error_indefinite);
                        } else {
                            mView.startProfileView();
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mView.hideProgress();

                        RetrofitException error = (RetrofitException) throwable;
                        BaseResponse errorBodyAs = error.getErrorBodyAs(BaseResponse.class);
                        if (errorBodyAs != null) {
                            mView.showMessage(R.string.auth_error_invalid_pass_or_email);
                        } else {
                            mView.showMessage(R.string.auth_error_indefinite);
                        }
                    }

                    @Override
                    public void onNext(AuthResult authResult) {
                    }
                });
        addSubscription(subscription);
    }

    public void forgotPasswordClicked() {
        mView.startRestorePasswordView();
    }

    private boolean checkAuthData(@Nullable String email, @Nullable String password) {
        if (email == null || email.isEmpty() || !email.contains("@")) {
            mView.showMessage(R.string.auth_error_empty_email);
            return false;
        }
        if (password == null || password.isEmpty()) {
            mView.showMessage(R.string.auth_error_empty_password);
            return false;
        }
        return true;
    }
}