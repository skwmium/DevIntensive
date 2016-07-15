package com.softdesign.devintensive.di;

import com.softdesign.devintensive.presenter.PresenterProfileList;
import com.softdesign.devintensive.view.ViewProfileList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by skwmium on 14.07.16.
 */
@Module
public class ModuleProfileList {
    private ViewProfileList mView;

    public ModuleProfileList(ViewProfileList view) {
        mView = view;
    }

    @Provides
    public PresenterProfileList providePresenter() {
        return new PresenterProfileList(mView);
    }
}
