package com.softdesign.devintensive.di;

import com.softdesign.devintensive.ui.fragments.FragmentAuth;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by skwmium on 05.07.16.
 */
@Singleton
@Component(modules = {ModuleViewAuth.class})
public interface ComponentAuth {
    void inject(FragmentAuth fragmentAuth);
}
