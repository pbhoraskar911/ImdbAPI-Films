package com.imdb.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Movie implements Parcelable {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("directors")
    @Expose
    private List<Director> directors = new ArrayList<Director>();
    @SerializedName("runtime")
    @Expose
    private String runtime;
    @SerializedName("urlPoster")
    @Expose
    private String urlPoster;
    @SerializedName("languages")
    @Expose
    private List<String> languages = new ArrayList<String>();
    @SerializedName("genres")
    @Expose
    private List<String> genres = new ArrayList<String>();
    @SerializedName("plot")
    @Expose
    private String plot;
    @SerializedName("rated")
    @Expose
    private String rated;

    public Movie(String title, String runtime, String rated, List<String> genres, String urlPoster,
                 List<Director> directors, List<String> languages, String plot) {
        this.title = title;
        this.runtime = runtime;
        this.rated = rated;
        this.genres = genres;
        this.urlPoster = urlPoster;
        this.directors = directors;
        this.languages = languages;
        this.plot = plot;
    }

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The directors
     */
    public List<Director> getDirectors() {
        return directors;
    }

    /**
     * @param directors The directors
     */
    public void setDirectors(List<Director> directors) {
        this.directors = directors;
    }

    /**
     * @return The runtime
     */
    public String getRuntime() {
        return runtime;
    }

    /**
     * @param runtime The runtime
     */
    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    /**
     * @return The urlPoster
     */
    public String getUrlPoster() {
        return urlPoster;
    }

    /**
     * @param urlPoster The urlPoster
     */
    public void setUrlPoster(String urlPoster) {
        this.urlPoster = urlPoster;
    }

    /**
     * @return The languages
     */
    public List<String> getLanguages() {
        return languages;
    }

    /**
     * @param languages The languages
     */
    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    /**
     * @return The genres
     */
    public List<String> getGenres() {
        return genres;
    }

    /**
     * @param genres The genres
     */
    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    /**
     * @return The plot
     */
    public String getPlot() {
        return plot;
    }

    /**
     * @param plot The plot
     */
    public void setPlot(String plot) {
        this.plot = plot;
    }

    /**
     * @return The rated
     */
    public String getRated() {
        return rated;
    }

    /**
     * @param rated The rated
     */
    public void setRated(String rated) {
        this.rated = rated;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(rated);
        dest.writeString(runtime);
        dest.writeString(urlPoster);
        dest.writeStringList(languages);
        dest.writeStringList(genres);
        dest.writeString(plot);
    }

    protected Movie(Parcel in) {
        title = in.readString();
        rated = in.readString();
        runtime = in.readString();
        urlPoster = in.readString();
        languages = in.createStringArrayList();
        genres = in.createStringArrayList();
        plot = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}