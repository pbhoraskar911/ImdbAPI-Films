package com.tmdb.ui.mvp.userreviews;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tmdb.R;
import com.tmdb.di.TmdbApplication;
import com.tmdb.di.component.NetworkComponent;
import com.tmdb.model.userreviews.UserReviewsResult;
import com.tmdb.ui.mvp.moviespage.RecyclerAdapter;
import com.tmdb.web.RetrofitInterface;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by Pranav Bhoraskar
 */

public class UserReviewFragment extends Fragment implements UserReviewsView {

    @BindView(R.id.recycler_view)
    RecyclerView userReviewRecyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar reviewProgressBar;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    @BindView(R.id.no_review_layout)
    ConstraintLayout noReviewLayout;
    @BindView(R.id.no_review_image)
    ImageView noReviewImage;
    @BindView(R.id.no_review_text)
    TextView noReviewText;

    private Unbinder unbinder;

    @Inject
    RetrofitInterface retrofitInterface;

    private String movieId;

    private UserReviewAdapter userReviewAdapter = new UserReviewAdapter();
    private LinearLayoutManager linearLayoutManager;
    private UserReviewPresenter userReviewPresenter;

    public static UserReviewFragment newInstance() {
        return new UserReviewFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getNetComponent().injectUserReviews(this);
        attachUserReviewPresenter();
    }

    private void attachUserReviewPresenter() {
        if (userReviewPresenter == null) {
            userReviewPresenter = new UserReviewPresenter();
        }
        userReviewPresenter.attachView(this);
    }

    private NetworkComponent getNetComponent() {
        return ((TmdbApplication) getActivity().getApplication()).getNetworkComponent();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        unbinder = ButterKnife.bind(this, view);
        swiperefresh.setEnabled(false);
        setHasOptionsMenu(false);
        getBundleArguments();
        return view;
    }

    private void getBundleArguments() {
        movieId = getArguments().getString(RecyclerAdapter.MOVIE_ID);
        userReviewPresenter.fetchUserReviews(movieId, retrofitInterface);
    }

    @Override
    public void showProgress() {
        reviewProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        reviewProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void setUpUserReviewRecyclerView(List<UserReviewsResult> userReviewsResults) {
        linearLayoutManager = new LinearLayoutManager(userReviewRecyclerView.getContext());
        userReviewAdapter = new UserReviewAdapter(getActivity(),
                new ArrayList<UserReviewsResult>());
        userReviewAdapter.setUpRecyclerView(userReviewsResults);
        userReviewRecyclerView.setLayoutManager(linearLayoutManager);
        userReviewRecyclerView.setAdapter(userReviewAdapter);
        userReviewRecyclerView.hasFixedSize();
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
    public void onDestroyView() {
        super.onDestroyView();
        userReviewPresenter.detachView();
        unbinder.unbind();
    }
}