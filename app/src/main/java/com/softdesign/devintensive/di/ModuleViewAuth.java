package com.softdesign.devintensive.di;

import com.softdesign.devintensive.presenter.PresenterAuth;
import com.softdesign.devintensive.view.ViewAuth;

import dagger.Module;
import dagger.Provides;

/**
 * Created by skwmium on 05.07.16.
 */
@Module
public class ModuleViewAuth {
    private ViewAuth mAuthView;

    public ModuleViewAuth(ViewAuth authView) {
        mAuthView = authView;
    }

    @Provides
    public PresenterAuth provideAuthPresenter() {
        return new PresenterAuth(mAuthView);
    }
}
