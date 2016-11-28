package com.softdesign.devintensive.model.mappers;

import com.softdesign.devintensive.model.dto.UserDto;
import com.softdesign.devintensive.ui.viewmodel.ProfileViewModel;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by skwmium on 14.07.16.
 */
public class MapperListUserDtoViewModel implements Func1<List<UserDto>, List<ProfileViewModel>> {
    @Inject
    MapperUserDtoViewModel mapperUser;

    @Inject
    public MapperListUserDtoViewModel() {
    }

    @Override
    public List<ProfileViewModel> call(List<UserDto> userDtos) {
        return Observable.from(userDtos)
                .map(mapperUser)
                .toList()
                .toBlocking()
                .first();
    }
}
