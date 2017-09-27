package com.tmdb.ui.mvp.moviespage;

import android.content.Context;

import com.tmdb.model.Result;
import com.tmdb.ui.mvp.ViewInterface;

import java.util.ArrayList;

/**
 * Created by Pranav Bhoraskar
 */

public interface MoviesView extends ViewInterface {

    void setUpRecyclerView(ArrayList<Result> tempData);

    void showErrorDialog();

    Context getContext();

    void showProgress();

    void hideProgress();
}