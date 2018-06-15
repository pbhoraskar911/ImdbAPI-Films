package com.tmdb.model.videos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pranav Bhoraskar
 */

public class VideoResponse implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<VideoResult> videoResults = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<VideoResult> getResults() {
        return videoResults;
    }

    public void setResults(List<VideoResult> videoResults) {
        this.videoResults = videoResults;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeList(this.videoResults);
    }

    public VideoResponse() {
    }

    protected VideoResponse(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.videoResults = new ArrayList<VideoResult>();
        in.readList(this.videoResults, VideoResult.class.getClassLoader());
    }

    public static final Parcelable.Creator<VideoResponse> CREATOR = new Parcelable.Creator<VideoResponse>() {
        @Override
        public VideoResponse createFromParcel(Parcel source) {
            return new VideoResponse(source);
        }

        @Override
        public VideoResponse[] newArray(int size) {
            return new VideoResponse[size];
        }
    };
}