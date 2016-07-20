package com.softdesign.devintensive.model.mappers;

import android.support.annotation.Nullable;

import com.softdesign.devintensive.model.dto.RepositoryDto;
import com.softdesign.devintensive.model.dto.UserDto;
import com.softdesign.devintensive.model.storage.LocalUser;
import com.softdesign.devintensive.ui.viewmodel.ProfileViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by skwmium on 08.07.16.
 */
public class MapperUserDtoViewModel implements Func1<UserDto, ProfileViewModel> {
    @Nullable
    private String mLocalUserid;

    @Inject
    public MapperUserDtoViewModel() {
        mLocalUserid = LocalUser.getInst().getUserId();
    }

    private boolean isLocalUser(String id) {
        return mLocalUserid != null && id != null && mLocalUserid.equals(id);
    }

    @Override
    public ProfileViewModel call(UserDto userDto) {
        return Observable.just(userDto)
                .map(user -> {
                    ProfileViewModel model = new ProfileViewModel();
                    model.setCanBeEditable(isLocalUser(user.getRemoteId()));
                    model.setId(user.getRemoteId());

                    model.setName(user.getFullName());
                    model.setRating(user.getRating());
                    model.setCodeLinesCount(user.getCodeLinesCount());
                    model.setProjectCount(user.getProjectCount());
                    model.setMobilePhoneNumber(user.getPhoneNumber());
                    model.setEmail(user.getEmail());
                    model.setVkProfileUrl(user.getVkProfileUrl());
                    model.setAvatarUrl(user.getAvatarUrl());
                    model.setPhotoUrl(user.getPhotoUrl());
                    model.setAbout(user.getAbout());

                    if (user.getRepositories() != null) {
                        List<String> stringList = new ArrayList<>();
                        for (RepositoryDto dto : user.getRepositories()) {
                            stringList.add(dto.getGitUrl());
                        }
                        model.setRepositories(stringList);
                    }
                    return model;
                }).toBlocking()
                .first();
    }
}
