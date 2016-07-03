package com.softdesign.devintensive.data;

import com.softdesign.devintensive.common.App;
import com.softdesign.devintensive.data.network.api.SoftdesignApiClient;
import com.softdesign.devintensive.data.network.dto.AuthResult;
import com.softdesign.devintensive.data.network.dto.BaseResponse;
import com.softdesign.devintensive.data.network.dto.EditProfileResult;
import com.softdesign.devintensive.data.network.dto.Profile;
import com.softdesign.devintensive.data.network.params.ParamAuth;
import com.softdesign.devintensive.data.network.params.ParamEdit;
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
                .compose(applySchedulers());
    }

    @Override
    public Observable<BaseResponse<EditProfileResult>> userEditProfile(ParamEdit editParam) {
        return null;
    }

    @Override
    public Observable<BaseResponse<Profile>> userGetProfile() {
        return mSoftdesignApiInterface
                .userGet(LocalUser.getInst().getAuthToken())
                .compose(applySchedulers());
    }

    @SuppressWarnings("unchecked")
    private <T> Observable.Transformer<T, T> applySchedulers() {
        return (Observable.Transformer<T, T>) mSchedulersTransformer;
    }
}
