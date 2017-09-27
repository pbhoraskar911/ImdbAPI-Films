package com.tmdb.model;

/**
 * Created by Pranav Bhoraskar
 */

public abstract class ModelMapper {
    public static Result toMovieResultModel(Result movieResult) {

        Result result = new Result();

        result.setTitle(movieResult.getTitle());
        result.setPosterPath(movieResult.getPosterPath());
        result.setVoteAverage(movieResult.getVoteAverage());

        result.setOverview(movieResult.getOverview());
        result.setBackdropPath(movieResult.getBackdropPath());
        result.setReleaseDate(movieResult.getReleaseDate());

        return result;
    }
}