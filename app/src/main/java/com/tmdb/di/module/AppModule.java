package com.tmdb.di.module;

import android.app.Application;

import com.tmdb.session.SessionManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Pranav Bhoraskar
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
