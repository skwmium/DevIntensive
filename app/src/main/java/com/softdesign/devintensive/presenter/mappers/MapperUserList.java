package com.softdesign.devintensive.presenter.mappers;

import com.softdesign.devintensive.data.network.dto.User;
import com.softdesign.devintensive.ui.viewmodel.ProfileViewModel;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by skwmium on 14.07.16.
 */
public class MapperUserList implements Func1<List<User>, List<ProfileViewModel>> {
    @Inject
    MapperUser mapperUser;

    @Inject
    public MapperUserList() {
    }

    @Override
    public List<ProfileViewModel> call(List<User> users) {
        return Observable.from(users)
                .map(mapperUser)
                .toList()
                .toBlocking()
                .first();
    }
}
