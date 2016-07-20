package com.softdesign.devintensive.model.mappers;

import com.softdesign.devintensive.model.network.params.ParamEdit;
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
                    paramEdit.setVkUrl(model.getVkProfileUrl());
                    paramEdit.setGithubUrl(model.getRepository());
                    paramEdit.setBiography(model.getAbout());
                    return paramEdit;
                })
                .toBlocking()
                .first();
    }
}
