package com.softdesign.devintensive.model.mappers;

import com.softdesign.devintensive.model.dto.UserDto;
import com.softdesign.devintensive.model.network.dto.User;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by skwmium on 20.07.16.
 */
public class MapperListUserApiDto implements Func1<List<User>, List<UserDto>> {
    @Inject
    MapperUserApiDto mapperUserApiDto;

    @Inject
    public MapperListUserApiDto() {
    }

    @Override
    public List<UserDto> call(List<User> users) {
        return Observable.from(users)
                .map(mapperUserApiDto)
                .toList()
                .toBlocking()
                .first();
    }
}
