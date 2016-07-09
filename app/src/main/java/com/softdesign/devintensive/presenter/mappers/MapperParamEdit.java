package com.softdesign.devintensive.presenter.mappers;

import android.net.Uri;

import com.softdesign.devintensive.data.network.params.ParamEdit;
import com.softdesign.devintensive.ui.viewmodel.ProfileViewModel;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by skwmium on 08.07.16.
 */
public class MapperParamEdit implements Func1<ProfileViewModel, ParamEdit> {

    @Inject
    public MapperParamEdit() {
    }

    @Override
    public ParamEdit call(ProfileViewModel profileViewModel) {
        return Observable.just(profileViewModel)
                .map(model -> {
                    ParamEdit paramEdit = new ParamEdit();
                    paramEdit.setPhoneNumber(model.getMobilePhoneNumber());
                    paramEdit.setVkUrl(model.getVkProfile());
                    paramEdit.setGithubUrl(model.getRepository());
                    paramEdit.setBiography(model.getAbout());
                    paramEdit.setAvatarUri(Uri.parse(model.getAvatarUrl()));
                    paramEdit.setPhotoUri(Uri.parse(model.getPhotoUrl()));
                    return paramEdit;
                })
                .toBlocking()
                .first();
    }
}
