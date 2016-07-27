package com.softdesign.devintensive.model;

import com.softdesign.devintensive.model.dto.UserDto;
import com.softdesign.devintensive.model.network.dto.AuthResult;
import com.softdesign.devintensive.model.network.dto.BaseResponse;
import com.softdesign.devintensive.model.network.dto.UploadImageResult;
import com.softdesign.devintensive.model.network.params.ParamEdit;

import java.util.List;

import rx.Observable;

/**
 * Created by skwmium on 01.07.16.
 */
public interface Model {
    Observable<AuthResult> autUser(String email, String password);

    Observable<BaseResponse> userRestorePassword(String email);

    Observable<UserDto> userEditProfile(ParamEdit editParam);

    Observable<UploadImageResult> updateProfilePhoto(String path);

    Observable<UploadImageResult> updateProfileAvatar(String path);

    Observable<UserDto> userGetMe();

    Observable<List<UserDto>> getUserList();

    Observable setUserRemoved(String objectId);

    Observable changeUserOrder(int from, int to);
}
