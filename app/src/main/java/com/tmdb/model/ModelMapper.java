package com.tmdb.model;

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
}