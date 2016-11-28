package com.softdesign.devintensive.model.mappers;

import com.softdesign.devintensive.model.dto.RepositoryDto;
import com.softdesign.devintensive.model.dto.UserDto;
import com.softdesign.devintensive.model.storage.entities.DbRepository;
import com.softdesign.devintensive.model.storage.entities.DbUser;
import com.softdesign.devintensive.model.storage.entities.DbUserAttribute;

import java.util.ArrayList;
import java.util.List;

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

                    List<DbRepository> repositories = user.getRepositories();
                    if (repositories != null) {
                        List<RepositoryDto> repositoryDtoList = new ArrayList<>();
                        for (DbRepository dbRepository : repositories) {
                            RepositoryDto repositoryDto = new RepositoryDto();
                            repositoryDto.setId(dbRepository.getRemoteId());
                            repositoryDto.setGitUrl(dbRepository.getGitUrl());
                            repositoryDtoList.add(repositoryDto);
                        }
                        dto.setRepositories(repositoryDtoList);
                    }

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
