package com.tmdb.ui.mvp.moviedetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;
import com.tmdb.R;
import com.tmdb.di.TmdbApplication;
import com.tmdb.di.component.NetworkComponent;
import com.tmdb.model.Genre;
import com.tmdb.model.MovieDetailResponse;
import com.tmdb.model.videos.VideoResponse;
import com.tmdb.model.videos.VideoResult;
import com.tmdb.ui.mvp.moviespage.RecyclerAdapter;
import com.tmdb.ui.mvp.userreviews.UserReviewFragment;
import com.tmdb.ui.mvp.webviewpage.WebViewActivity;
import com.tmdb.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;


/**
 * Created by Pranav Bhoraskar
 */
public class MovieDetailActivity extends AppCompatActivity implements MovieDetailsView {

    @BindView(R.id.detail_movie_title)
    TextView detailMovieTitle;
    @BindView(R.id.rating_value)
    TextView detailMovieRating;
    @BindView(R.id.release_date_value)
    TextView detailMovieReleaseDate;
    @BindView(R.id.detail_movie_plot)
    TextView detailMoviePlot;
    @BindView(R.id.detail_movie_poster)
    ImageView detailMoviePoster;
    @BindView(R.id.movie_runtime)
    TextView detailMovieRuntime;
    @BindView(R.id.movie_genres)
    TextView detailMovieGenres;
    @BindView(R.id.movie_imdb_link)
    Button detailMovieImdbLink;
    @BindView(R.id.movie_home_page_link)
    Button detailMovieHomePageLink;
    @BindView(R.id.video_recycler_view)
    RecyclerView videoRecyclerView;
    @BindView(R.id.button_reviews)
    Button buttonReviews;

    @Inject
    Retrofit retrofit;

    private String movieId;
    private String moviePlot;
    private String movieTitle;
    private String movieRated;
    private String moviePoster;
    private String movieReleaseDate;
    private String movieHomePage;
    private String movieImdbId;
    private String movieRuntime;
    private String movieGenreNames;

    private List<Genre> movieGenresList;
    private List<VideoResult> listOfVideos;

    private VideoRecyclerAdapter videoRecyclerAdapter;
    private LinearLayoutManager linearLayoutManager;
    private MovieDetailPresenter movieDetailPresenter;
    private MaterialDialog materialDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_movie_detail);
        ButterKnife.bind(this);
        getNetComponent().injectMovieDetailsActivity(this);

        getBundleArguments();

        detailMoviePlot.setMovementMethod(new ScrollingMovementMethod());
    }

    private NetworkComponent getNetComponent() {
        return ((TmdbApplication) getApplication()).getNetworkComponent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle(movieTitle);
        fetchMovieDetails(movieId);
    }

    private void getBundleArguments() {
        movieId = getIntent().getStringExtra(RecyclerAdapter.MOVIE_ID);
        movieTitle = getIntent().getStringExtra(RecyclerAdapter.MOVIE_TITLE);
    }

    private void fetchMovieDetails(String movieId) {
        movieDetailPresenter = new MovieDetailPresenter(this, retrofit, movieId);
        movieDetailPresenter.makeRetrofitcall();
    }

    @Override
    public void setMovieDetails(MovieDetailResponse movieDetailResponse) {
        movieTitle = movieDetailResponse.getOriginalTitle();
        moviePlot = movieDetailResponse.getOverview();
        movieRated = String.valueOf(movieDetailResponse.getVoteAverage());
        moviePoster = getContext().getString(R.string.image_url_500) +
                movieDetailResponse.getBackdropPath();
        movieReleaseDate = movieDetailResponse.getReleaseDate();
        movieHomePage = movieDetailResponse.getHomepage();
        movieImdbId = movieDetailResponse.getImdbId();
        movieRuntime = String.valueOf(movieDetailResponse.getRuntime()) +
                getContext().getString(R.string.minutes);

        movieGenresList = movieDetailResponse.getGenres();
        List<String> joinedGenre = new ArrayList<>();
        for (Genre genre : movieGenresList) {
            joinedGenre.add(genre.getName());
        }
        movieGenreNames = concatStringsWSep(joinedGenre, ", ");

        updateDisplay();
    }

    public static String concatStringsWSep(List<String> strings, String separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strings.size(); i++) {
            sb.append(strings.get(i));
            if (i < strings.size() - 1) {
                sb.append(separator);
            }
        }
        return sb.toString();
    }

    private void updateDisplay() {
        detailMoviePlot.setText(moviePlot);
        detailMovieRating.setText(movieRated);
        detailMovieTitle.setText(movieTitle);
        detailMovieReleaseDate.setText(movieReleaseDate);
        Picasso.with(getContext()).load(moviePoster)
                .placeholder(R.mipmap.no_image)
                .fit()
                .into(detailMoviePoster);
        detailMovieRuntime.setText(movieRuntime);
        detailMovieGenres.setText(movieGenreNames);
    }

    @Override
    public void openImdbPage(String imdbPageUrl) {
        Intent intent = new Intent(getContext(), WebViewActivity.class);
        intent.putExtra(getString(R.string.url), imdbPageUrl);
        startActivity(intent);
    }

    @Override
    public void openMovieHomePage(String movieHomePageUrl) {
        Intent intent = new Intent(getContext(), WebViewActivity.class);
        intent.putExtra(getString(R.string.url), movieHomePageUrl);
        startActivity(intent);
    }

    @Override
    public void setMovieVideoDetails(VideoResponse videoResponse) {
        listOfVideos = videoResponse.getResults();
        setUpVideoRecyclerView(listOfVideos);
    }

    private void setUpVideoRecyclerView(List<VideoResult> listOfVideos) {
        videoRecyclerAdapter = new VideoRecyclerAdapter(getContext(), listOfVideos);
        linearLayoutManager = new LinearLayoutManager(getContext());

        videoRecyclerView.setLayoutManager(linearLayoutManager);
        videoRecyclerView.setAdapter(videoRecyclerAdapter);
        videoRecyclerView.setHasFixedSize(true);
    }

    @OnClick({R.id.movie_imdb_link, R.id.movie_home_page_link, R.id.button_reviews})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.movie_imdb_link:
                movieDetailPresenter.makeImdbCall(movieImdbId);
                break;
            case R.id.movie_home_page_link:
                movieDetailPresenter.makeMovieHomePageCall(movieHomePage);
                break;
            case R.id.button_reviews:
                if (ViewUtils.isInternetAvailable(getContext())) {
                    navigateToUserFragment(movieId);
                }
                else {
                    ViewUtils.createNoInternetDialog(getContext(), R.string.network_error);
                }
                break;
        }
    }

    private void navigateToUserFragment(String movieId) {
        UserReviewFragment userReviewFragment = UserReviewFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.moviesFrameLayout, userReviewFragment)
                .addToBackStack(null)
                .commit();

        Bundle bundle = new Bundle();
        bundle.putString(RecyclerAdapter.MOVIE_ID, movieId);
        userReviewFragment.setArguments(bundle);
    }

    @Override
    public Context getContext() {
        return MovieDetailActivity.this;
    }

    @Override
    public void showProgress() {
        materialDialog = new MaterialDialog.Builder(getContext())
                .title("Loading...")
                .content("Fetching Movie Details...")
                .cancelable(false)
                .progress(true, 0)
                .progressIndeterminateStyle(true)
                .show();
    }

    @Override
    public void hideProgress() {
        if (materialDialog != null && materialDialog.isShowing()) {
            materialDialog.dismiss();
        }
    }

    @Override
    public void showErrorDialog() {

    }
}