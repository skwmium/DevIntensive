package com.softdesign.devintensive.di;

import com.softdesign.devintensive.presenter.PresenterRestorePassword;
import com.softdesign.devintensive.view.ViewRestorePassword;

import dagger.Module;
import dagger.Provides;

/**
 * Created by skwmium on 08.07.16.
 */
@Module
public class ModuleRestorePassword {
    private ViewRestorePassword mView;

    public ModuleRestorePassword(ViewRestorePassword view) {
        mView = view;
    }

    @Provides
    public PresenterRestorePassword proviewRestorePasswordPresenter() {
        return new PresenterRestorePassword(mView);
    }
}
