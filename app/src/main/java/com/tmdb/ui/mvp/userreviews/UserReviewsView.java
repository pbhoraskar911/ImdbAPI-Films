package com.tmdb.ui.mvp.userreviews;

import com.tmdb.model.userreviews.UserReviewsResult;
import com.tmdb.ui.mvp.ViewInterface;

import java.util.List;

/**
 * Created by Pranav Bhoraskar
 */

public interface UserReviewsView extends ViewInterface {

    void showProgress();

    void hideProgress();

    void setUpUserReviewRecyclerView(List<UserReviewsResult> userReviewsResults);

    void showNoReviewAvailable();

    void hideNoReviewAvailable();
}
