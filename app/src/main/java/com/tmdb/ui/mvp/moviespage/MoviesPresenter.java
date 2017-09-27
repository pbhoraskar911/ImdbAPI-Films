package com.tmdb.ui.mvp.moviespage;

import android.annotation.SuppressLint;
import android.util.Log;

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

/**
 * Created by Pranav Bhoraskar
 */

public class MoviesPresenter implements Presenter<MoviesView> {

    private Retrofit retrofit;
    private MoviesView moviesView;

    static boolean flag = false;

    private Call<MovieDBResponse> movieDBResponseCall;

    public MoviesPresenter(MoviesView moviesView, Retrofit retrofit) {
        this.moviesView = moviesView;
        this.retrofit = retrofit;
    }

    public void makeRetrofitCall() {
        fetchMovieDetails();
    }

    public void fetchMovieDetails() {
        moviesView.showProgress();

        Map<String, String> queryParameters = new HashMap<String, String>();
        queryParameters.put("api_key", "6a81b36fbcea5752b554e90dbdf643cd");
        queryParameters.put("sort_by", "popularity.desc");
        queryParameters.put("primary_release_date.gte", getDate(30));
        queryParameters.put("primary_release_date.lte", getDate(0));

        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
        movieDBResponseCall = retrofitInterface.queryTmdbApi(queryParameters);

        movieDBResponseCall.enqueue(new Callback<MovieDBResponse>() {
            @Override
            public void onResponse(Call<MovieDBResponse> call, Response<MovieDBResponse> response) {
                if (response.code() == 200) {
                    onSuccess(response);
                }
                flag = true;
                moviesView.hideProgress();
            }

            @Override
            public void onFailure(Call<MovieDBResponse> call, Throwable t) {
                Log.e(getClass().getSimpleName(), "" + t.getMessage());
                moviesView.hideProgress();
            }
        });
    }

    private void onSuccess(Response<MovieDBResponse> response) {
        ArrayList<Result> moviesList = new ArrayList<>();
        for (Result movieResult : response.body().getResults()) {
            moviesList.add(ModelMapper.toMovieResultModel(movieResult));
        }
        moviesView.setUpRecyclerView(moviesList);
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
    }
}