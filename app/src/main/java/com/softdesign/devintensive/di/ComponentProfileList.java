package com.softdesign.devintensive.di;

import com.softdesign.devintensive.ui.fragments.FragmentProfileList;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by skwmium on 14.07.16.
 */
@Singleton
@Component(modules = {ModuleProfileList.class})
public interface ComponentProfileList {
    void inject(FragmentProfileList fragmentProfileList);
}
