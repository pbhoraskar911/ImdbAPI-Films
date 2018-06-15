package com.tmdb.web;

import com.tmdb.model.MovieDBResponse;
import com.tmdb.model.MovieDetailResponse;
import com.tmdb.model.userreviews.UserReviewResponse;
import com.tmdb.model.videos.VideoResponse;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by Pranav Bhoraskar
 */

public interface RetrofitInterface {

    @GET("3/discover/movie")
    Call<MovieDBResponse> queryTmdbApi(@QueryMap Map<String, String> queryMap);

    @GET("3/movie/{sort_type}")
    Call<MovieDBResponse> querySortMoviesByType(@Path("sort_type") String sortType,
                                                @QueryMap Map<String, String> queryMap);

    @GET("3/movie/{movie_id}")
    Call<MovieDetailResponse> queryMovieDetails(@Path("movie_id") String movieId,
                                                @QueryMap Map<String, String> queryMap);

    @GET("3/movie/{movie_id}/videos")
    Call<VideoResponse> getMovieVideos(@Path("movie_id") String movieId,
                                          @QueryMap Map<String, String> queryMap);

    @GET("3/movie/{movie_id}/reviews")
    Observable<UserReviewResponse> getUserReviews(@Path("movie_id") String movieId,
                                                  @QueryMap Map<String, String> queryMap);
}
