package com.tmdb.di;

import android.app.Application;

import com.tmdb.di.component.DaggerNetworkComponent;
import com.tmdb.di.component.NetworkComponent;
import com.tmdb.di.module.AppModule;
import com.tmdb.di.module.NetworkModule;

import timber.log.Timber;

/**
 * Created by Pranav Bhoraskar
 */

public class TmdbApplication extends Application {

    public static final String BASE_URL = "https://api.themoviedb.org/";
    private NetworkComponent networkComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        networkComponent = DaggerNetworkComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule(BASE_URL))
                .build();

        Timber.plant(new Timber.DebugTree());
    }

    public NetworkComponent getNetworkComponent() {
        return networkComponent;
    }
}