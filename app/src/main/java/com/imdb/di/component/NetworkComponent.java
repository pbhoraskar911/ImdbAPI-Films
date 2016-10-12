package com.imdb.di.component;

import com.imdb.di.module.AppModule;
import com.imdb.di.module.NetworkModule;
import com.imdb.MVP.moviesPage.MoviesFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by pranavbhoraskar on 8/10/16.
 */

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface NetworkComponent {

    void inject(MoviesFragment moviesFragment);

}
