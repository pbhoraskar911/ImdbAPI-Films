package com.imdb.ui.mvp.moviespage;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.imdb.model.ImdbResponse;
import com.imdb.model.Movie;
import com.imdb.retrofit.RetrofitInterface;
import com.imdb.ui.mvp.Presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by pranavbhoraskar on 8/15/16.
 */
public class MoviesPresenter implements Presenter<MoviesView> {

    MoviesView moviesView;
    Retrofit retrofit;

    private String access_token = "159ab6ca-37ea-4351-ba42-9ef903beacc3";

    ArrayList<Movie> tempData;
    static boolean flag = false;

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
        queryParameters.put("token", access_token);
        queryParameters.put("format", "json");
        queryParameters.put("language", "en-us");

        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
        Call<ImdbResponse> serverResponse = retrofitInterface.queryImdbApi(queryParameters);

        serverResponse.enqueue(new Callback<ImdbResponse>() {
            @Override
            public void onResponse(Call<ImdbResponse> call, Response<ImdbResponse> response) {

                ImdbResponse imdbResponse;

                if (response.code() == 200) {
                    imdbResponse = response.body();

                    tempData = new ArrayList<>();
                    List<Movie> movies = imdbResponse.getData().getInTheaters().get(1).getMovies();
                    for (int i = 0; i < movies.size(); i++) {
                        tempData.add(new Movie(movies.get(i).getTitle()
                                , movies.get(i).getRuntime()
                                , movies.get(i).getRated()
                                , movies.get(i).getGenres()
                                , movies.get(i).getUrlPoster()
                                , movies.get(i).getDirectors()
                                , movies.get(i).getLanguages()
                                , movies.get(i).getPlot()
                        ));
                    }

                    // setting the Recycler View using Recycler Adapter
                    moviesView.setUpRecyclerView(tempData);
                }
                else {
                    Log.e(getClass().getSimpleName() + " --- ", response.message());
                }
                flag = true;
                moviesView.hideProgress();
            }

            @Override
            public void onFailure(Call<ImdbResponse> call, Throwable t) {
                Log.e(getClass().getSimpleName(), "" + t.getMessage());
                flag = false;
                moviesView.hideProgress();
            }
        });
    }

    /**
     * Checking the availability of the network for proper connection
     *
     * @return
     */
    public boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) moviesView.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
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