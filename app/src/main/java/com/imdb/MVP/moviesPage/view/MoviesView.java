package com.imdb.MVP.moviesPage.view;

import android.content.Context;

import com.imdb.model.Movie;

import java.util.ArrayList;

/**
 * Created by pranavbhoraskar on 8/15/16.
 */
public interface MoviesView extends ViewInterface{

    void setUpRecyclerView(ArrayList<Movie> tempData);
    void showErrorDialog();

    Context getContext();
    void showProgress();
    void hideProgress();

}
