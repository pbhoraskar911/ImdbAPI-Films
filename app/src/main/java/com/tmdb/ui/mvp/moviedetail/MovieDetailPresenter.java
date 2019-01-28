package com.tmdb.ui.mvp.moviedetail;

import android.util.Log;

import com.tmdb.BuildConfig;
import com.tmdb.model.ModelMapper;
import com.tmdb.model.MovieDetailResponse;
import com.tmdb.model.videos.VideoResponse;
import com.tmdb.ui.mvp.Presenter;
import com.tmdb.utils.ViewUtils;
import com.tmdb.web.RetrofitInterface;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import timber.log.Timber;

/**
 * Created by Pranav Bhoraskar
 */

public class MovieDetailPresenter implements Presenter<MovieDetailsView> {

    private String imdbPageUrl;
    private final String movieId;
    private final Retrofit retrofit;
    private VideoResponse videoResponse;
    private MovieDetailsView movieDetailsView;
    private MovieDetailResponse movieDetailResponse;

    Call<MovieDetailResponse> movieDetailResponseCall;
    Call<VideoResponse> movieVideoResponseCall;

    public MovieDetailPresenter(MovieDetailsView movieDetailsView, Retrofit retrofit,
                                String movieId) {
        this.movieDetailsView = movieDetailsView;
        this.retrofit = retrofit;
        this.movieId = movieId;
    }

    public void makeRetrofitcall() {
        movieDetailsView.showProgress();

        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
        movieDetailResponseCall =
                retrofitInterface.queryMovieDetails(movieId, getQueryParameters());

        fetchApiResponse(movieDetailResponseCall);
    }

    @NonNull
    private Map<String, String> getQueryParameters() {
        Map<String, String> queryParameters = new HashMap<String, String>();
        queryParameters.put("api_key", BuildConfig.API_KEY);
        queryParameters.put("language", "en-US");
        return queryParameters;
    }

    private void fetchApiResponse(Call<MovieDetailResponse> movieDetailResponseCall) {
        movieDetailResponseCall.enqueue(new Callback<MovieDetailResponse>() {
            @Override
            public void onResponse(Call<MovieDetailResponse> call,
                                   Response<MovieDetailResponse> response) {
                if (response.code() == 200) {
                    onSuccess(response.body());
                }
                movieDetailsView.hideProgress();
            }

            @Override
            public void onFailure(Call<MovieDetailResponse> call, Throwable t) {
                Timber.tag(getClass().getSimpleName()).e("" + t.getMessage());
                movieDetailsView.hideProgress();
            }
        });
    }

    private void onSuccess(MovieDetailResponse movieDetailResponse) {
        fetchMovieVideos();
        this.movieDetailResponse = ModelMapper.toMovieDetailModel(movieDetailResponse);

        movieDetailsView.hideProgress();
        movieDetailsView.setMovieDetails(movieDetailResponse);
    }

    private void fetchMovieVideos() {
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
        movieVideoResponseCall = retrofitInterface.getMovieVideos(movieId, getQueryParameters());

        movieVideoResponseCall.enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                onVideoSuccess(response.body());
            }

            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {
                Log.e(getClass().getSimpleName(), "" + t.getMessage());
            }
        });

    }

    private void onVideoSuccess(VideoResponse videoResponse) {
        this.videoResponse = ModelMapper.toMovieVideoModel(videoResponse);
        movieDetailsView.setMovieVideoDetails(this.videoResponse);
    }

    public void makeImdbCall(String movieImdbId) {
        // open in a webview
        imdbPageUrl = parseImdbId(movieImdbId);
        movieDetailsView.openImdbPage(imdbPageUrl);
    }

    public void makeMovieHomePageCall(String movieHomePage) {
        // open in a webview
        movieDetailsView.openMovieHomePage(movieHomePage);
    }

    private String parseImdbId(String movieImdbId) {
        return ViewUtils.IMDB_URL + movieImdbId;
    }

    @Override
    public void attachView(MovieDetailsView movieDetailsView) {
        this.movieDetailsView = movieDetailsView;
    }

    @Override
    public void detachView() {
        this.movieDetailsView = null;
    }
}
