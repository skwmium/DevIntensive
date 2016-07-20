package com.softdesign.devintensive.model.mappers;

import com.softdesign.devintensive.model.dto.UserDto;
import com.softdesign.devintensive.model.storage.entities.DbUser;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by skwmium on 20.07.16.
 */
public class MapperUserDtoDb implements Func1<UserDto, DbUser> {
    @Inject
    public MapperUserDtoDb() {
    }

    @Override
    public DbUser call(UserDto userDto) {
        return Observable.just(userDto)
                .map(dto -> {
                    DbUser dbUser = new DbUser();
                    dbUser.setRemoteId(dto.getRemoteId());
                    dbUser.setName(dto.getFullName());

                    dbUser.setRating(dto.getRating());
                    dbUser.setLinesCount(dto.getCodeLinesCount());
                    dbUser.setProjectCount(dto.getProjectCount());
                    dbUser.setMobilePhoneNumber(dto.getPhoneNumber());
                    dbUser.setEmail(dto.getEmail());
                    dbUser.setVkProfile(dto.getVkProfileUrl());
                    dbUser.setAvatarUrl(dto.getAvatarUrl());
                    dbUser.setPhotoUrl(dto.getPhotoUrl());
                    dbUser.setAbout(dto.getAbout());
                    return dbUser;
                })
                .toBlocking()
                .first();
    }
}
