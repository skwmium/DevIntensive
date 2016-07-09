package com.softdesign.devintensive.data;

import android.support.annotation.NonNull;

import com.softdesign.devintensive.common.App;
import com.softdesign.devintensive.data.managers.LocalUserAuthorizer;
import com.softdesign.devintensive.data.network.api.SoftdesignApiClient;
import com.softdesign.devintensive.data.network.dto.AuthResult;
import com.softdesign.devintensive.data.network.dto.BaseResponse;
import com.softdesign.devintensive.data.network.dto.EditProfileResult;
import com.softdesign.devintensive.data.network.dto.User;
import com.softdesign.devintensive.data.network.params.ParamAuth;
import com.softdesign.devintensive.data.network.params.ParamEdit;
import com.softdesign.devintensive.data.network.params.ParamForgotPassword;
import com.softdesign.devintensive.data.storage.LocalUser;
import com.softdesign.devintensive.utils.Const;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by skwmium on 01.07.16.
 */
public class ModelImpl implements Model {
    private final Observable.Transformer mSchedulersTransformer;

    @Inject
    SoftdesignApiClient mSoftdesignApiInterface;

    @Inject
    @Named(Const.UI_THREAD)
    Scheduler mSchedulerUi;

    @Inject
    @Named(Const.IO_THREAD)
    Scheduler mSchedulerIo;

    @Inject
    LocalUserAuthorizer localUserAuthoriser;

    public ModelImpl() {
        App.getAppComponent().inject(this);
        mSchedulersTransformer = o -> ((Observable) o)
                .subscribeOn(mSchedulerIo)
                .observeOn(mSchedulerUi);
    }

    @Override
    public Observable<BaseResponse<AuthResult>> autUser(String email, String password) {
        ParamAuth authParam = new ParamAuth(email, password);
        return mSoftdesignApiInterface
                .userAuth(authParam)
                .compose(applySchedulers())
                .doOnNext(localUserAuthoriser);//auth local user
    }

    @Override
    public Observable<BaseResponse> userRestorePassword(String email) {
        ParamForgotPassword paramForgotPassword = new ParamForgotPassword(email);
        return mSoftdesignApiInterface
                .userRestorePassword(paramForgotPassword)
                .compose(applySchedulers());
    }

    @Override
    public Observable<BaseResponse<EditProfileResult>> userEditProfile(ParamEdit editParam) {
        return null;
    }

    @Override
    public Observable<BaseResponse<User>> userGetProfile(@NonNull String userid) {
        return mSoftdesignApiInterface
                .userGet(userid)
                .compose(applySchedulers());
    }

    @Override
    public Observable<BaseResponse<User>> userGetMe() {
        return mSoftdesignApiInterface
                .userGet(LocalUser.getInst().getUserId())
                .compose(applySchedulers());
    }

    @SuppressWarnings("unchecked")
    private <T> Observable.Transformer<T, T> applySchedulers() {
        return (Observable.Transformer<T, T>) mSchedulersTransformer;
    }
}
