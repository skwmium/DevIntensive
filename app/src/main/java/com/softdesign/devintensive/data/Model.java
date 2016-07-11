package com.softdesign.devintensive.data;

import com.softdesign.devintensive.data.network.dto.AuthResult;
import com.softdesign.devintensive.data.network.dto.BaseResponse;
import com.softdesign.devintensive.data.network.dto.EditProfileResult;
import com.softdesign.devintensive.data.network.dto.Profile;
import com.softdesign.devintensive.data.network.params.ParamEdit;

import rx.Observable;

/**
 * Created by skwmium on 01.07.16.
 */
public interface Model {
    Observable<BaseResponse<AuthResult>> autUser(String email, String password);

    Observable<BaseResponse> userRestorePassword(String email);

    Observable<BaseResponse<EditProfileResult>> userEditProfile(ParamEdit editParam);

    Observable<BaseResponse<Profile>> userGetProfile();
}
