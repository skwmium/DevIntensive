package com.softdesign.devintensive.presenter.mappers;

import android.net.Uri;
import android.support.annotation.Nullable;

import com.softdesign.devintensive.data.network.dto.PublicInfo;
import com.softdesign.devintensive.data.network.dto.User;
import com.softdesign.devintensive.data.network.params.ParamEdit;
import com.softdesign.devintensive.data.storage.PreferenceCache;
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

                    PublicInfo publicInfo = getCachedUserPublicInfo();
                    if (publicInfo == null || !publicInfo.getAvatarUrl()
                            .equalsIgnoreCase(model.getAvatarUrl())) {
                        paramEdit.setAvatarUri(Uri.parse(model.getAvatarUrl()));
                    }
                    if (publicInfo == null || !publicInfo.getPhotoUrl()
                            .equalsIgnoreCase(model.getPhotoUrl())) {
                        paramEdit.setPhotoUri(Uri.parse(model.getPhotoUrl()));
                    }

                    paramEdit.setPhoneNumber(model.getMobilePhoneNumber());
                    paramEdit.setVkUrl(model.getVkProfile());
                    paramEdit.setGithubUrl(model.getRepository());
                    paramEdit.setBiography(model.getAbout());
                    return paramEdit;
                })
                .toBlocking()
                .first();
    }

    @Nullable
    private PublicInfo getCachedUserPublicInfo() {
        User userFromCache = PreferenceCache.getUserFromCache();
        return userFromCache == null ? null : userFromCache.getPublicInfo();
    }
}
