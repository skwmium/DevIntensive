package com.softdesign.devintensive.model.managers;

import com.softdesign.devintensive.model.network.dto.AuthResult;
import com.softdesign.devintensive.model.storage.LocalUser;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by skwmium on 09.07.16.
 */
public class LocalUserAuthorizer implements Action1<AuthResult> {

    @Inject
    public LocalUserAuthorizer() {
    }

    @Override
    public void call(AuthResult authResult) {
        if (authResult != null && authResult.getUser() != null) {
            LocalUser.getInst().login(authResult.getToken(), authResult.getUser().getId());
        }
    }
}