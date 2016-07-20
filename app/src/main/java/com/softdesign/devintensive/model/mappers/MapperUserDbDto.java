package com.softdesign.devintensive.model.mappers;

import com.softdesign.devintensive.model.dto.UserDto;
import com.softdesign.devintensive.model.storage.entities.DbUser;
import com.softdesign.devintensive.model.storage.entities.DbUserAttribute;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by skwmium on 20.07.16.
 */
public class MapperUserDbDto implements Func1<DbUser, UserDto> {
    @Inject
    public MapperUserDbDto() {
    }

    @Override
    public UserDto call(DbUser dbUser) {
        return Observable.just(dbUser)
                .map(user -> {
                    UserDto dto = new UserDto();
                    dto.setRemoteId(user.getRemoteId());
                    dto.setFullName(user.getName());
                    dto.setRating(user.getRating());
                    dto.setCodeLinesCount(user.getLinesCount());
                    dto.setProjectCount(user.getProjectCount());
                    dto.setPhoneNumber(user.getMobilePhoneNumber());
                    dto.setEmail(user.getEmail());
                    dto.setVkProfileUrl(user.getVkProfile());
                    dto.setAvatarUrl(user.getAvatarUrl());
                    dto.setPhotoUrl(user.getPhotoUrl());
                    dto.setAbout(user.getAbout());

                    DbUserAttribute attribute = user.getAttribute();
                    if (attribute != null) {
                        dto.setOrder(attribute.getOrder());
                    }
                    return dto;
                })
                .toBlocking()
                .first();
    }
}
