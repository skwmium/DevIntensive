package com.softdesign.devintensive.di;

import com.softdesign.devintensive.presenter.PresenterProfile;
import com.softdesign.devintensive.view.ViewProfile;

import dagger.Module;
import dagger.Provides;

/**
 * Created by skwmium on 06.07.16.
 */
@Module
public class ModuleViewProfile {
    private ViewProfile mProfileView;

    public ModuleViewProfile(ViewProfile profileView) {
        mProfileView = profileView;
    }

    @Provides
    PresenterProfile provideProfilePresenter() {
        return new PresenterProfile(mProfileView);
    }
}
