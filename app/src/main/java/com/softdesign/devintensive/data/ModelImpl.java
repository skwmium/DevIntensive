package com.softdesign.devintensive.data;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.softdesign.devintensive.common.App;
import com.softdesign.devintensive.data.managers.LocalUserAuthorizer;
import com.softdesign.devintensive.data.network.api.SoftdesignApiClient;
import com.softdesign.devintensive.data.network.dto.AuthResult;
import com.softdesign.devintensive.data.network.dto.BaseResponse;
import com.softdesign.devintensive.data.network.dto.EditProfileResult;
import com.softdesign.devintensive.data.network.dto.PublicInfo;
import com.softdesign.devintensive.data.network.dto.UploadImageResult;
import com.softdesign.devintensive.data.network.dto.User;
import com.softdesign.devintensive.data.network.params.ParamAuth;
import com.softdesign.devintensive.data.network.params.ParamEdit;
import com.softdesign.devintensive.data.network.params.ParamForgotPassword;
import com.softdesign.devintensive.data.network.params.UploadPhotoType;
import com.softdesign.devintensive.data.storage.LocalUser;
import com.softdesign.devintensive.data.storage.PreferenceCache;
import com.softdesign.devintensive.data.storage.entities.DaoSession;
import com.softdesign.devintensive.utils.Const;
import com.softdesign.devintensive.utils.ContentUriUtils;
import com.softdesign.devintensive.utils.Utils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Scheduler;

import static com.softdesign.devintensive.data.network.params.UploadPhotoType.AVATAR;
import static com.softdesign.devintensive.data.network.params.UploadPhotoType.PHOTO;

/**
 * Created by skwmium on 01.07.16.
 */
@SuppressWarnings("Convert2MethodRef")
public class ModelImpl implements Model {
    private final Observable.Transformer mSchedulersTransformer;

    @Inject
    SoftdesignApiClient mSoftdesignApiInterface;

    @Inject
    DaoSession daoSession;

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
    public Observable<AuthResult> autUser(String email, String password) {
        ParamAuth authParam = new ParamAuth(email, password);
        return mSoftdesignApiInterface
                .userAuth(authParam)
                .map(authResultBaseResponse -> authResultBaseResponse.getBody())
                .doOnNext(localUserAuthoriser)//auth local user
                .compose(applySchedulers());
    }

    @Override
    public Observable<BaseResponse> userRestorePassword(String email) {
        ParamForgotPassword paramForgotPassword = new ParamForgotPassword(email);
        return mSoftdesignApiInterface
                .userRestorePassword(paramForgotPassword)
                .compose(applySchedulers());
    }

    @Override
    public Observable<EditProfileResult> userEditProfile(ParamEdit editParam) {
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        for (Map.Entry<String, String> entry : editParam.getParamsMap().entrySet()) {
            String value = entry.getValue();
            if (value == null) continue;
            requestBodyMap.put(entry.getKey(),
                    RequestBody.create(Const.MEDIA_TYPE_MULTIPART_FORM_DATA, value));
        }
        return mSoftdesignApiInterface
                .userEdit(requestBodyMap)
                .map(editProfileResultBaseResponse -> editProfileResultBaseResponse.getBody())
                .compose(applySchedulers());
    }

    @Override
    public Observable<UploadImageResult> updateProfilePhoto(Uri imageUri) {
        String userId = LocalUser.getInst().getUserId();
        PublicInfo publicInfo = getCachedUserPublicInfo();

        if (imageUri == null || publicInfo == null || Utils.isNullOrEmpty(userId) ||
                publicInfo.getPhotoUrl().equalsIgnoreCase(imageUri.toString())) {
            return Observable.empty();
        }

        MultipartBody.Part part = getMultipartFromPath(PHOTO, imageUri);
        if (part == null) {
            return Observable.error(new NullPointerException());
        }
        return mSoftdesignApiInterface
                .userUploadPhoto(userId, part)
                .map(response -> response.getBody())
                .compose(applySchedulers());
    }

    @Override
    public Observable<UploadImageResult> updateProfileAvatar(Uri imageUri) {
        String userId = LocalUser.getInst().getUserId();
        PublicInfo publicInfo = getCachedUserPublicInfo();
        if (imageUri == null || publicInfo == null || Utils.isNullOrEmpty(userId) ||
                publicInfo.getPhotoUrl().equalsIgnoreCase(imageUri.toString())) {
            return Observable.empty();
        }

        MultipartBody.Part part = getMultipartFromPath(AVATAR, imageUri);
        if (part == null) {
            return Observable.error(new NullPointerException());
        }
        return mSoftdesignApiInterface
                .userUploadAvatar(userId, part)
                .map(response -> response.getBody())
                .compose(applySchedulers());
    }

    @Override
    public Observable<User> userGetProfile(@NonNull String userid) {
        return mSoftdesignApiInterface
                .userGet(userid)
                .map(userBaseResponse -> userBaseResponse.getBody())
                .compose(applySchedulers());
    }

    @Override
    public Observable<User> userGetMe() {
        String userId = LocalUser.getInst().getUserId();
        if (Utils.isNullOrEmpty(userId)) {
            return Observable.empty();
        }
        return mSoftdesignApiInterface
                .userGet(userId)
                .map(userBaseResponse -> userBaseResponse.getBody())
                .doOnNext(user -> PreferenceCache.cacheUser(user))
                .onErrorReturn(throwable -> PreferenceCache.getUserFromCache())
                .compose(applySchedulers());
    }

    @Override
    public Observable<List<User>> getUserList() {
        return mSoftdesignApiInterface
                .userGetList()
                .map(listBaseResponse -> listBaseResponse.getBody())
                .compose(applySchedulers());
    }

    @Nullable
    private MultipartBody.Part getMultipartFromPath(@NonNull UploadPhotoType type, @NonNull Uri uri) {
        String path = ContentUriUtils.uriToPath(App.getInst(), uri);
        if (path == null) return null;
        File file = new File(path);
        if (!file.exists() || !file.isFile())
            return null;

        RequestBody requestFile = RequestBody
                .create(Const.MEDIA_TYPE_MULTIPART_FORM_DATA, file);
        return MultipartBody.Part.createFormData(type.toString(), file.getName(), requestFile);
    }

    @Nullable
    private PublicInfo getCachedUserPublicInfo() {
        User userFromCache = PreferenceCache.getUserFromCache();
        return userFromCache == null ? null : userFromCache.getPublicInfo();
    }

    @SuppressWarnings("unchecked")
    private <T> Observable.Transformer<T, T> applySchedulers() {
        return (Observable.Transformer<T, T>) mSchedulersTransformer;
    }
}
