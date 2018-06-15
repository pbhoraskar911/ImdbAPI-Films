package com.tmdb.model.userreviews;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Pranav Bhoraskar
 */

public class UserReviewResponse implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("results")
    @Expose
    private List<UserReviewsResult> userReviewsResults = null;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<UserReviewsResult> getUserReviewsResults() {
        return userReviewsResults;
    }

    public void setUserReviewsResults(List<UserReviewsResult> userReviewsResults) {
        this.userReviewsResults = userReviewsResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.page);
        dest.writeTypedList(this.userReviewsResults);
        dest.writeValue(this.totalPages);
        dest.writeValue(this.totalResults);
    }

    public UserReviewResponse() {
    }

    protected UserReviewResponse(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.page = (Integer) in.readValue(Integer.class.getClassLoader());
        this.userReviewsResults = in.createTypedArrayList(UserReviewsResult.CREATOR);
        this.totalPages = (Integer) in.readValue(Integer.class.getClassLoader());
        this.totalResults = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<UserReviewResponse> CREATOR = new Parcelable.Creator<UserReviewResponse>() {
        @Override
        public UserReviewResponse createFromParcel(Parcel source) {
            return new UserReviewResponse(source);
        }

        @Override
        public UserReviewResponse[] newArray(int size) {
            return new UserReviewResponse[size];
        }
    };
}