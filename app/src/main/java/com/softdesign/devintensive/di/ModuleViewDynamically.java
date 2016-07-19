package com.softdesign.devintensive.di;

import com.softdesign.devintensive.presenter.PresenterAuth;
import com.softdesign.devintensive.presenter.PresenterMain;
import com.softdesign.devintensive.presenter.PresenterProfile;
import com.softdesign.devintensive.presenter.PresenterProfileList;
import com.softdesign.devintensive.presenter.PresenterRestorePassword;
import com.softdesign.devintensive.view.ViewAuth;
import com.softdesign.devintensive.view.ViewMain;
import com.softdesign.devintensive.view.ViewProfile;
import com.softdesign.devintensive.view.ViewProfileList;
import com.softdesign.devintensive.view.ViewRestorePassword;

import dagger.Module;
import dagger.Provides;

/**
 * Created by skwmium on 19.07.16.
 */
@Module
public class ModuleViewDynamically {
    private ViewMain mViewMain;
    private ViewProfile mViewProfile;
    private ViewProfileList mViewProfileList;
    private ViewAuth mViewAuth;
    private ViewRestorePassword mViewRestorePassword;

    public ModuleViewDynamically(ViewMain viewMain) {
        mViewMain = viewMain;
    }

    public ModuleViewDynamically(ViewProfile viewProfile) {
        mViewProfile = viewProfile;
    }

    public ModuleViewDynamically(ViewProfileList viewProfileList) {
        mViewProfileList = viewProfileList;
    }

    public ModuleViewDynamically(ViewAuth viewAuth) {
        mViewAuth = viewAuth;
    }

    public ModuleViewDynamically(ViewRestorePassword viewRestorePassword) {
        mViewRestorePassword = viewRestorePassword;
    }

    @Provides
    PresenterMain provideMainPresenter() {
        return new PresenterMain(mViewMain);
    }

    @Provides
    PresenterProfile provideProfilePresenter() {
        return new PresenterProfile(mViewProfile);
    }

    @Provides
    public PresenterProfileList provideProfileListPresenter() {
        return new PresenterProfileList(mViewProfileList);
    }

    @Provides
    public PresenterAuth provideAuthPresenter() {
        return new PresenterAuth(mViewAuth);
    }

    @Provides
    public PresenterRestorePassword proviewRestorePasswordPresenter() {
        return new PresenterRestorePassword(mViewRestorePassword);
    }
}
