package com.softdesign.devintensive.data;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.softdesign.devintensive.data.network.dto.AuthResult;
import com.softdesign.devintensive.data.network.dto.BaseResponse;
import com.softdesign.devintensive.data.network.dto.EditProfileResult;
import com.softdesign.devintensive.data.network.dto.UploadImageResult;
import com.softdesign.devintensive.data.network.dto.User;
import com.softdesign.devintensive.data.network.params.ParamEdit;

import java.util.List;

import rx.Observable;

/**
 * Created by skwmium on 01.07.16.
 */
public interface Model {
    Observable<AuthResult> autUser(String email, String password);

    Observable<BaseResponse> userRestorePassword(String email);

    Observable<EditProfileResult> userEditProfile(ParamEdit editParam);

    Observable<UploadImageResult> updateProfilePhoto(Uri imageUri);

    Observable<UploadImageResult> updateProfileAvatar(Uri imageUri);

    Observable<User> userGetProfile(@NonNull String userid);

    Observable<User> userGetMe();

    Observable<List<User>> getUserList();
}
