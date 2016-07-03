package com.softdesign.devintensive.presenter.mappers;

import com.softdesign.devintensive.data.network.dto.Contacts;
import com.softdesign.devintensive.data.network.dto.ProfileValues;
import com.softdesign.devintensive.data.network.dto.PublicInfo;
import com.softdesign.devintensive.data.network.dto.Repositories;
import com.softdesign.devintensive.data.network.dto.User;
import com.softdesign.devintensive.ui.viewmodel.ProfileViewModel;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by skwmium on 08.07.16.
 */
public class MapperUser implements Func1<User, ProfileViewModel> {

    @Inject
    public MapperUser() {
    }

    @Override
    public ProfileViewModel call(User userDto) {
        return Observable.just(userDto)
                .map(user -> {
                    ProfileViewModel profileViewModel = new ProfileViewModel();
                    profileViewModel.setName(user.getFirstName() + " " + user.getSecondName());

                    ProfileValues profileValues = user.getProfileValues();
                    if (profileValues != null) {
                        profileViewModel.setRating(profileValues.getRating());
                        profileViewModel.setLinesCount(profileValues.getCodeLineCount());
                        profileViewModel.setProjectCount(profileValues.getProjectCount());
                    }

                    Contacts contacts = user.getContacts();
                    if (contacts != null) {
                        profileViewModel.setMobilePhoneNumber(contacts.getPhone());
                        profileViewModel.setEmail(contacts.getEmail());
                        profileViewModel.setVkProfile(contacts.getVk());
                    }

                    PublicInfo publicInfo = user.getPublicInfo();
                    if (publicInfo != null) {
                        profileViewModel.setAvatarUrl(publicInfo.getAvatarUrl());
                        profileViewModel.setPhotoUrl(publicInfo.getPhotoUrl());
                        profileViewModel.setAbout(publicInfo.getBio());
                    }

                    Repositories repositories = user.getRepositories();
                    if (repositories != null && repositories.getRepositories() != null
                            && !repositories.getRepositories().isEmpty()) {
                        profileViewModel.setRepository(repositories.getRepositories().get(0).getGitUrl());
                    }
                    return profileViewModel;
                }).toBlocking()
                .first();
    }
}
