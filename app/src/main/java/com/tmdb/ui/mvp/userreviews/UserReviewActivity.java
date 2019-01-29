package com.tmdb.ui.mvp.userreviews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.tmdb.R;
import com.tmdb.di.TmdbApplication;
import com.tmdb.di.component.NetworkComponent;
import com.tmdb.model.userreviews.UserReviewsResult;
import com.tmdb.ui.mvp.moviespage.RecyclerAdapter;
import com.tmdb.web.RetrofitInterface;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by Pranav Bhoraskar
 */
public class UserReviewActivity extends AppCompatActivity implements UserReviewsView {

    @BindView(R.id.recycler_view)
    RecyclerView userReviewRecyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.no_review_image)
    ImageView noReviewImage;
    @BindView(R.id.no_review_text)
    TextView noReviewText;
    @BindView(R.id.no_review_layout)
    ConstraintLayout noReviewLayout;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;

    private UserReviewPresenter userReviewPresenter;
    private MaterialDialog userReviewsMaterialDialog;

    private String movieId;
    private String movieTitle;
    private Unbinder unbinder;

    private LinearLayoutManager linearLayoutManager;
    private UserReviewAdapter userReviewAdapter = new UserReviewAdapter(getContext(), movieTitle, new ArrayList<UserReviewsResult>());

    @Inject
    RetrofitInterface retrofitInterface;

    public static Intent newInstance(Context context) {
        return new Intent(context, UserReviewActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_review);
        unbinder = ButterKnife.bind(this);
        swiperefresh.setEnabled(false);
        getNetComponent().injectUserReviewsActivity(this);
        attachUserReviewPresenter();
        getIntentDetails();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle(movieTitle);
    }

    private void attachUserReviewPresenter() {
        if (userReviewPresenter == null) {
            userReviewPresenter = new UserReviewPresenter();
        }
        userReviewPresenter.attachView(this);
    }

    private NetworkComponent getNetComponent() {
        return ((TmdbApplication) getApplication()).getNetworkComponent();
    }

    private void getIntentDetails() {
        if (getIntent().getExtras() != null) {
            movieTitle = getIntent().getStringExtra(RecyclerAdapter.MOVIE_TITLE);
            movieId = getIntent().getStringExtra(RecyclerAdapter.MOVIE_ID);
            userReviewPresenter.fetchUserReviews(movieId, retrofitInterface);
        }
        else {
            showNoReviewAvailable();
        }
    }

    @Override
    public void setUpUserReviewRecyclerView(List<UserReviewsResult> userReviewsResults) {
        linearLayoutManager = new LinearLayoutManager(userReviewRecyclerView.getContext());
        userReviewAdapter = new UserReviewAdapter(getContext(), movieTitle,
                new ArrayList<UserReviewsResult>());
        userReviewAdapter.setUpRecyclerView(userReviewsResults);
        userReviewRecyclerView.setLayoutManager(linearLayoutManager);
        userReviewRecyclerView.setAdapter(userReviewAdapter);
        userReviewRecyclerView.hasFixedSize();
    }

    @Override
    public void showProgress() {
//        userReviewsMaterialDialog = new MaterialDialog.Builder(getContext())
//                .title("Loading...")
//                .content("Fetching User Reviews...")
//                .cancelable(false)
//                .progress(true, 0)
//                .progressIndeterminateStyle(true)
//                .show();

        if (progressBar != null && progressBar.getVisibility() != View.VISIBLE) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgress() {
//        if (userReviewsMaterialDialog != null && userReviewsMaterialDialog.isShowing()) {
//            userReviewsMaterialDialog.dismiss();
//        }

        if (progressBar != null && progressBar.getVisibility() != View.GONE) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showNoReviewAvailable() {
        if (noReviewLayout != null) {
            noReviewLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideNoReviewAvailable() {
        if (noReviewLayout != null) {
            noReviewLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public Context getContext() {
        return UserReviewActivity.this;
    }

    @Override
    protected void onDestroy() {
        userReviewPresenter.detachView();
        unbinder.unbind();
        super.onDestroy();
    }
}
