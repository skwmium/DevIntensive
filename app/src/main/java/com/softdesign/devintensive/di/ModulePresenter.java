package com.softdesign.devintensive.di;

import com.softdesign.devintensive.model.Model;
import com.softdesign.devintensive.model.ModelImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by skwmium on 03.07.16.
 */
@Module
public class ModulePresenter {
    @Provides
    @Singleton
    Model provideModel() {
        return new ModelImpl();
    }

    @Provides
    CompositeSubscription provideCompositeSubscription() {
        return new CompositeSubscription();
    }
}
