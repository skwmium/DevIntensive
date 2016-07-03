package com.softdesign.devintensive.di;

import com.softdesign.devintensive.ui.activities.ActivityProfile;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by skwmium on 06.07.16.
 */
@Singleton
@Component(modules = {ModuleViewProfile.class})
public interface ComponentProfile {
    void inject(ActivityProfile activityProfile);
}
