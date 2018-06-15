package com.tmdb.model;

import com.tmdb.model.videos.VideoResponse;
import com.tmdb.model.videos.VideoResult;

/**
 * Created by Pranav Bhoraskar
 */

public abstract class ModelMapper {
    public static Result toMovieResultModel(Result movieResult) {

        Result result = new Result();

        result.setId(movieResult.getId());
        result.setTitle(movieResult.getTitle());
        result.setOverview(movieResult.getOverview());
        result.setVoteCount(movieResult.getVoteCount());
        result.setPopularity(movieResult.getPopularity());
        result.setPosterPath(movieResult.getPosterPath());
        result.setVoteAverage(movieResult.getVoteAverage());
        result.setReleaseDate(movieResult.getReleaseDate());
        result.setBackdropPath(movieResult.getBackdropPath());

        return result;
    }

    public static MovieDetailResponse toMovieDetailModel(MovieDetailResponse response) {

        MovieDetailResponse movieDetailResponse = new MovieDetailResponse();

        movieDetailResponse.setBackdropPath(response.getBackdropPath());
        movieDetailResponse.setHomepage(response.getHomepage());
        movieDetailResponse.setImdbId(response.getImdbId());
        movieDetailResponse.setOriginalLanguage(response.getOriginalTitle());
        movieDetailResponse.setReleaseDate(response.getReleaseDate());
        movieDetailResponse.setOverview(response.getOverview());
        movieDetailResponse.setVoteAverage(response.getVoteAverage());
        movieDetailResponse.setRuntime(response.getRuntime());
        movieDetailResponse.setGenres(response.getGenres());

        return movieDetailResponse;
    }

    public static VideoResponse toMovieVideoModel(VideoResponse response) {
        VideoResponse videoResponse = new VideoResponse();

        videoResponse.setId(response.getId());
        videoResponse.setResults(response.getResults());
        return videoResponse;
    }

//    public static VideoResult toMovieVideoModel(VideoResponse response) {
//        VideoResult videoResult = new VideoResult();
//
////        videoResult.setId(response.getId());
////        videoResult.setResults(response.getResults());
//
//        videoResult.setName(response.getResults().ge);
//        return videoResult;
//    }
}