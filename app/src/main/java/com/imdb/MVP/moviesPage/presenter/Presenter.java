package com.imdb.MVP.moviesPage.presenter;

/**
 * Created by pranavbhoraskar on 8/15/16.
 */
public interface Presenter<V> {

    boolean isNetworkAvailable();

    void detachView();

    void makeRetrofitCall();

    void fetchMovieDetails();
}
