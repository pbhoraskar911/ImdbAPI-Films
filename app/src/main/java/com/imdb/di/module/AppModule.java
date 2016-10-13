package com.imdb.di.module;

import android.app.Application;

import com.imdb.session.SessionManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by pranavbhoraskar on 8/10/16.
 */


@Module
public class AppModule {

    Application mApplication;

    public AppModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @Singleton
    @Provides
    public Application providesApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    SessionManager provideSession() {
        return new SessionManager(mApplication);
    }
}
