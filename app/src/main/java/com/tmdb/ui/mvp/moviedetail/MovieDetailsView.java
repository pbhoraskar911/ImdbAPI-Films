package com.tmdb.ui.mvp.moviedetail;

import android.content.Context;

import com.tmdb.model.MovieDetailResponse;
import com.tmdb.model.videos.VideoResponse;
import com.tmdb.ui.mvp.ViewInterface;

/**
 * Created by Pranav Bhoraskar
 */

public interface MovieDetailsView extends ViewInterface {

    void showErrorDialog();

    Context getContext();

    void showProgress();

    void hideProgress();

    void setMovieDetails(MovieDetailResponse movieDetailResponse);

    void openImdbPage(String movieUrl);

    void openMovieHomePage(String movieHomePageUrl);

    void setMovieVideoDetails(VideoResponse movieDetailResponse);
}