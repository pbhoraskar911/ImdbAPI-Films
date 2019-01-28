package com.tmdb.ui.mvp.moviespage;

import android.annotation.SuppressLint;

import com.tmdb.BuildConfig;
import com.tmdb.model.ModelMapper;
import com.tmdb.model.MovieDBResponse;
import com.tmdb.model.Result;
import com.tmdb.ui.mvp.Presenter;
import com.tmdb.web.RetrofitInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import timber.log.Timber;

/**
 * Created by Pranav Bhoraskar
 */

public class MoviesPresenter implements Presenter<MoviesView> {

    private final String tag;
    private Retrofit retrofit;
    private MoviesView moviesView;

    static boolean flag = false;

    private Call<MovieDBResponse> movieDBResponseCall;

    public MoviesPresenter(MoviesView moviesView, Retrofit retrofit, String tag) {
        this.moviesView = moviesView;
        this.retrofit = retrofit;
        this.tag = tag;
    }

    public void makeRetrofitCall() {
        fetchMovieDetails();
    }

    public void fetchMovieDetails() {
        moviesView.showProgress();
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        Map<String, String> queryParameters = new HashMap<String, String>();
        queryParameters.put("api_key", BuildConfig.API_KEY);

        queryParameters.put("vote_count.gte", "250");
        queryParameters.put("include_adult", "false");


        if (tag.equals(MoviesListActivity.POPULAR_TAB)) {
            queryParameters.put("language", "en-US");
            queryParameters.put("sort_by", "popularity.desc");
            movieDBResponseCall = retrofitInterface.querySortMoviesByType(tag, queryParameters);
        }
        if (tag.equals(MoviesListActivity.RELEASE_DATE_TAB)) {
            queryParameters.put("with_original_language", "en");
            queryParameters.put("primary_release_year", "2017");
            queryParameters.put("sort_by", "release_date.desc");
            movieDBResponseCall = retrofitInterface.queryTmdbApi(queryParameters);
        }
        if (tag.equals(MoviesListActivity.VOTE_COUNT_TAB)) {
            queryParameters.put("language", "en-US");
            queryParameters.put("sort_by", "vote_count.desc");
            movieDBResponseCall = retrofitInterface.querySortMoviesByType(tag, queryParameters);
        }
        if (tag.equals(MoviesListActivity.FAVORITE_TAB)) {
            Timber.i("tag for favorite : " + tag);
            queryParameters.put("language", "en-US");
            queryParameters.put("sort_by", "popularity.desc");
            movieDBResponseCall = retrofitInterface.querySortMoviesByType(tag, queryParameters);
        }

        fetchApiResponse(movieDBResponseCall);
    }

    private void fetchApiResponse(Call<MovieDBResponse> movieDBResponseCall) {
        movieDBResponseCall.enqueue(new Callback<MovieDBResponse>() {
            @Override
            public void onResponse(Call<MovieDBResponse> call, Response<MovieDBResponse> response) {
                if (response.code() == 200) {
                    Timber.tag("movie detail url : ").i(String.valueOf(call.request().url()));
                    onSuccess(response);
                }
                flag = true;
                moviesView.hideProgress();
            }

            @Override
            public void onFailure(Call<MovieDBResponse> call, Throwable t) {
                Timber.tag(getClass().getSimpleName()).e("%s", t.getMessage());
                moviesView.hideProgress();
            }
        });
    }

    private void onSuccess(Response<MovieDBResponse> response) {
        ArrayList<Result> moviesList = new ArrayList<>();
        for (Result movieResult : response.body().getResults()) {
            moviesList.add(ModelMapper.toMovieResultModel(movieResult));
        }
        moviesView.setUpRecyclerView(moviesList, tag);
    }

    public static String getDate(int days) {
        Date date;
        Calendar cal = Calendar.getInstance();
        if (days == 30) {
            cal.add(Calendar.MONTH, -1);
            date = cal.getTime();
        }
        else {
            date = cal.getTime();
        }

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    @Override
    public void attachView(MoviesView moviesView) {
        this.moviesView = moviesView;
    }

    @Override
    public void detachView() {
        moviesView = null;
        if (!movieDBResponseCall.isCanceled() && movieDBResponseCall != null) {
            System.out.println("Cancelling movieDBResponseCall..");
            movieDBResponseCall.cancel();
        }
    }
}