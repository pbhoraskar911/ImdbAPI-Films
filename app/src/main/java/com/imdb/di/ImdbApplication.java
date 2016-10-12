package com.imdb.di;

import android.app.Application;

import com.imdb.di.component.DaggerNetworkComponent;
import com.imdb.di.component.NetworkComponent;
import com.imdb.di.module.AppModule;
import com.imdb.di.module.NetworkModule;

/**
 * Created by pranavbhoraskar on 8/10/16.
 */
public class ImdbApplication extends Application {

    public static final String BASE_URL = "http://api.myapifilms.com/";
    private NetworkComponent networkComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        networkComponent = DaggerNetworkComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule(BASE_URL))
                .build();
    }

    public NetworkComponent getNetworkComponent() {
        return networkComponent;
    }
}