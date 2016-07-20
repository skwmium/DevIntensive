package com.softdesign.devintensive.di;

import com.softdesign.devintensive.common.App;
import com.softdesign.devintensive.model.network.api.ServiceGenerator;
import com.softdesign.devintensive.model.network.api.SoftdesignApiClient;
import com.softdesign.devintensive.model.storage.entities.DaoMaster;
import com.softdesign.devintensive.model.storage.entities.DaoMaster.DevOpenHelper;
import com.softdesign.devintensive.model.storage.entities.DaoSession;
import com.softdesign.devintensive.utils.Const;

import org.greenrobot.greendao.database.Database;

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

    @Provides
    @Singleton
    DaoSession provideDaoSession() {
        DevOpenHelper helper = new DevOpenHelper(App.getInst(), Const.DATABASE_NAME);
        Database db = helper.getWritableDb();
        return new DaoMaster(db).newSession();
    }
}
