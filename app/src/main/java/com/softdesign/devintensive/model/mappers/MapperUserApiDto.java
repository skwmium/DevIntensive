package com.softdesign.devintensive.model.mappers;

import com.softdesign.devintensive.model.dto.RepositoryDto;
import com.softdesign.devintensive.model.dto.UserDto;
import com.softdesign.devintensive.model.network.dto.Contacts;
import com.softdesign.devintensive.model.network.dto.ProfileValues;
import com.softdesign.devintensive.model.network.dto.PublicInfo;
import com.softdesign.devintensive.model.network.dto.Repo;
import com.softdesign.devintensive.model.network.dto.Repositories;
import com.softdesign.devintensive.model.network.dto.User;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by skwmium on 20.07.16.
 */
public class MapperUserApiDto implements Func1<User, UserDto> {
    @Inject
    MapperRepoApiDto mapperRepoApiDto;

    @Inject
    public MapperUserApiDto() {
    }

    @Override
    public UserDto call(User userApi) {
        return Observable.just(userApi)
                .map(user -> {
                    UserDto userDto = new UserDto();
                    userDto.setRemoteId(user.getId());
                    userDto.setFullName(user.getFirstName() + " " + user.getSecondName());

                    ProfileValues profileValues = user.getProfileValues();
                    if (profileValues != null) {
                        userDto.setRating(profileValues.getRating());
                        userDto.setCodeLinesCount(profileValues.getCodeLineCount());
                        userDto.setProjectCount(profileValues.getProjectCount());
                    }

                    Contacts contacts = user.getContacts();
                    if (contacts != null) {
                        userDto.setPhoneNumber(contacts.getPhone());
                        userDto.setEmail(contacts.getEmail());
                        userDto.setVkProfileUrl(contacts.getVk());
                    }

                    PublicInfo publicInfo = user.getPublicInfo();
                    if (publicInfo != null) {
                        userDto.setAvatarUrl(publicInfo.getAvatarUrl());
                        userDto.setPhotoUrl(publicInfo.getPhotoUrl());
                        userDto.setAbout(publicInfo.getBio());
                    }

                    Repositories repositories = user.getRepositories();
                    if (repositories != null && repositories.getRepositories() != null) {
                        List<RepositoryDto> repositoriesStrings = new ArrayList<>();
                        //noinspection Convert2streamapi
                        for (Repo repo : repositories.getRepositories()) {
                            RepositoryDto dto = new RepositoryDto();
                            dto.setId(repo.getId());
                            dto.setTitle(repo.getTitle());
                            dto.setGitUrl(repo.getGitUrl());
                            repositoriesStrings.add(dto);
                        }
                        userDto.setRepositories(repositoriesStrings);
                    }
                    return userDto;
                }).toBlocking()
                .first();
    }
}
