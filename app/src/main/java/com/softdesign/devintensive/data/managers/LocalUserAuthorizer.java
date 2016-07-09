package com.softdesign.devintensive.data.managers;

import com.softdesign.devintensive.data.network.dto.AuthResult;
import com.softdesign.devintensive.data.network.dto.BaseResponse;
import com.softdesign.devintensive.data.storage.LocalUser;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by skwmium on 09.07.16.
 */
public class LocalUserAuthorizer implements Action1<BaseResponse<AuthResult>> {

    @Inject
    public LocalUserAuthorizer() {
    }

    @Override
    public void call(BaseResponse<AuthResult> authResultBaseResponse) {
        AuthResult body = authResultBaseResponse.getBody();
        if (body != null && body.getUser() != null) {
            LocalUser.getInst().login(body.getToken(), body.getUser().getId());
        }
    }
}
