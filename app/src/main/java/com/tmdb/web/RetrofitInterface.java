package com.tmdb.web;

import com.tmdb.model.MovieDBResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Pranav Bhoraskar
 */

public interface RetrofitInterface {

    @GET("3/discover/movie")
    Call<MovieDBResponse> queryTmdbApi(@QueryMap Map<String, String> queryMap);

    @GET("3/movie/popular")
    Call<MovieDBResponse> queryPopularMovieApi(@QueryMap Map<String, String> queryMap);

    @GET("3/movie/top_rated")
    Call<MovieDBResponse> queryTopRatedMoviesApi(@QueryMap Map<String, String> queryMap);
}
