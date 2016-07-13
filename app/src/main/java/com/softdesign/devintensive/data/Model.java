package com.softdesign.devintensive.data;

import android.support.annotation.NonNull;

import com.softdesign.devintensive.data.network.dto.AuthResult;
import com.softdesign.devintensive.data.network.dto.BaseResponse;
import com.softdesign.devintensive.data.network.dto.EditProfileResult;
import com.softdesign.devintensive.data.network.dto.UploadImageResult;
import com.softdesign.devintensive.data.network.dto.User;
import com.softdesign.devintensive.data.network.params.ParamEdit;

import rx.Observable;

/**
 * Created by skwmium on 01.07.16.
 */
public interface Model {
    Observable<AuthResult> autUser(String email, String password);

    Observable<BaseResponse> userRestorePassword(String email);

    Observable<EditProfileResult> userEditProfile(ParamEdit editParam);

    Observable<UploadImageResult> updateProfilePhoto(String path);

    Observable<UploadImageResult> updateProfileAvatar(String path);

    Observable<User> userGetProfile(@NonNull String userid);

    Observable<User> userGetMe();
}
