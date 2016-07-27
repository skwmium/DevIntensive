package com.softdesign.devintensive.di;

import com.softdesign.devintensive.model.ModelImpl;
import com.softdesign.devintensive.presenter.BasePresenter;
import com.softdesign.devintensive.presenter.PresenterAuth;
import com.softdesign.devintensive.presenter.PresenterMain;
import com.softdesign.devintensive.presenter.PresenterProfile;
import com.softdesign.devintensive.presenter.PresenterProfileList;
import com.softdesign.devintensive.presenter.PresenterRestorePassword;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by skwmium on 01.07.16.
 */
@Singleton
@Component(modules = {ModelModule.class, ModulePresenter.class})
public interface ComponentApp {
    void inject(ModelImpl model);

    void inject(BasePresenter basePresenter);

    void inject(PresenterMain presenterMain);

    void inject(PresenterAuth presenterAuth);

    void inject(PresenterProfile presenterProfile);

    void inject(PresenterRestorePassword presenterRestorePassword);

    void inject(PresenterProfileList presenterProfileList);
}
