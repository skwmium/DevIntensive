package com.softdesign.devintensive.di;

import com.softdesign.devintensive.ui.activities.ActivityMain;
import com.softdesign.devintensive.ui.fragments.FragmentAuth;
import com.softdesign.devintensive.ui.fragments.FragmentProfile;
import com.softdesign.devintensive.ui.fragments.FragmentProfileList;
import com.softdesign.devintensive.ui.fragments.FragmentRestorePassword;

import dagger.Component;

/**
 * Created by skwmium on 19.07.16.
 */
@Component(modules = {ModuleViewDynamically.class})
public interface ComponentView {
    void inject(ActivityMain activityMain);

    void inject(FragmentProfile fragmentProfile);

    void inject(FragmentProfileList fragmentProfileList);

    void inject(FragmentAuth fragmentAuth);

    void inject(FragmentRestorePassword fragmentRestorePassword);
}