package com.tmdb.ui.mvp.userreviews;

import android.support.annotation.NonNull;

import com.tmdb.BuildConfig;
import com.tmdb.R;
import com.tmdb.model.userreviews.UserReviewResponse;
import com.tmdb.ui.mvp.Presenter;
import com.tmdb.web.RetrofitInterface;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Pranav Bhoraskar
 */

public class UserReviewPresenter implements Presenter<UserReviewsView> {

    private UserReviewsView userReviewsView;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    public void fetchUserReviews(String movieId, RetrofitInterface retrofitInterface) {
        hideNoReview();
        userReviewsView.showProgress();

        retrofitInterface.getUserReviews(movieId, getQueryParameters())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<UserReviewResponse>() {
                    @Override
                    public void onNext(UserReviewResponse userReviewResponse) {

                        if (userReviewResponse.getTotalResults() > 0) {
                            userReviewsView.setUpUserReviewRecyclerView(
                                    userReviewResponse.getUserReviewsResults());
                        }

                        else {
                            showNoReview();
                            Timber.e(userReviewsView.getContext()
                                    .getString(R.string.timber_no_results));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        userReviewsView.hideProgress();
                        Timber.e(e);
                    }

                    @Override
                    public void onComplete() {
                        userReviewsView.hideProgress();
                        Timber.i(userReviewsView.getContext().
                                getString(R.string.timber_reviews_fetched));
                    }
                });
    }

    @NonNull
    private Map<String, String> getQueryParameters() {
        Map<String, String> queryParameters = new HashMap<String, String>();
        queryParameters.put("api_key", BuildConfig.API_KEY);
        queryParameters.put("language", "en-US");
        return queryParameters;
    }

    private void showNoReview() {
        userReviewsView.showNoReviewAvailable();
    }

    private void hideNoReview() {
        userReviewsView.hideNoReviewAvailable();
    }

    /**
     * Method to attach the view
     *
     * @param userReviewsView
     */
    @Override
    public void attachView(UserReviewsView userReviewsView) {
        this.userReviewsView = userReviewsView;
    }

    /**
     * Method to detach the current view and dispose the Composite Disposables
     */
    @Override
    public void detachView() {
        this.userReviewsView = null;
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
}