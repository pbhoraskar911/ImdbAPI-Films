
package com.imdb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class InTheater {

    @SerializedName("openingThisWeek")
    @Expose
    private String openingThisWeek;
    @SerializedName("movies")
    @Expose
    private List<Movie> movies = new ArrayList<Movie>();
    @SerializedName("inTheatersNow")
    @Expose
    private String inTheatersNow;

    /**
     * @return The openingThisWeek
     */
    public String getOpeningThisWeek() {
        return openingThisWeek;
    }

    /**
     * @param openingThisWeek The openingThisWeek
     */
    public void setOpeningThisWeek(String openingThisWeek) {
        this.openingThisWeek = openingThisWeek;
    }

    /**
     * @return The movies
     */
    public List<Movie> getMovies() {
        return movies;
    }

    /**
     * @param movies The movies
     */
    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    /**
     * @return The inTheatersNow
     */
    public String getInTheatersNow() {
        return inTheatersNow;
    }

    /**
     * @param inTheatersNow The inTheatersNow
     */
    public void setInTheatersNow(String inTheatersNow) {
        this.inTheatersNow = inTheatersNow;
    }

}
