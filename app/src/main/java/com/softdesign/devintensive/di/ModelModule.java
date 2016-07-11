package com.softdesign.devintensive.di;

import com.softdesign.devintensive.data.network.api.ServiceGenerator;
import com.softdesign.devintensive.data.network.api.SoftdesignApiClient;
import com.softdesign.devintensive.utils.Const;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by skwmium on 01.07.16.
 */
@Module
public class ModelModule {

    @Provides
    @Singleton
    SoftdesignApiClient provideApiInterface() {
        return ServiceGenerator.createSoftdesignApiInterface();
    }

    @Provides
    @Singleton
    @Named(Const.UI_THREAD)
    Scheduler provideSchedulerUi() {
        return AndroidSchedulers.mainThread();
    }

    @Provides
    @Singleton
    @Named(Const.IO_THREAD)
    Scheduler provideSchedulersIo() {
        return Schedulers.io();
    }
}
