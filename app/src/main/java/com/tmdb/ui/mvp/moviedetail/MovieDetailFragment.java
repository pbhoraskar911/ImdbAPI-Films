package com.tmdb.ui.mvp.moviedetail;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tmdb.R;
import com.tmdb.di.TmdbApplication;
import com.tmdb.di.component.NetworkComponent;
import com.tmdb.model.Genre;
import com.tmdb.model.MovieDetailResponse;
import com.tmdb.model.videos.VideoResponse;
import com.tmdb.model.videos.VideoResult;
import com.tmdb.ui.mvp.moviespage.RecyclerAdapter;
import com.tmdb.ui.mvp.userreviews.UserReviewActivity;
import com.tmdb.ui.mvp.webviewpage.WebViewActivity;
import com.tmdb.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;
import timber.log.Timber;

/**
 * Created by Pranav Bhoraskar
 */

public class MovieDetailFragment extends Fragment implements MovieDetailsView {

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getNetComponent().injectMovieDetails(this);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        detailMoviePlot.setMovementMethod(new ScrollingMovementMethod());

        setUpMovieDetails();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fetchMovieDetails(movieId);
    }

    private NetworkComponent getNetComponent() {
        return ((TmdbApplication) getActivity().getApplication()).getNetworkComponent();
    }

    private void fetchMovieDetails(String movieId) {
        movieDetailPresenter = new MovieDetailPresenter(this, retrofit, movieId);
        movieDetailPresenter.makeRetrofitcall();
    }

    private void setUpMovieDetails() {
        getBundleArguments();
        getActivity().setTitle(movieTitle);
    }

    private void getBundleArguments() {
        movieId = getArguments().getString(RecyclerAdapter.MOVIE_ID);
        movieTitle = getArguments().getString(RecyclerAdapter.MOVIE_TITLE);
    }

    @Override
    public void setMovieDetails(MovieDetailResponse movieDetailResponse) {
        movieTitle = movieDetailResponse.getOriginalTitle();
        moviePlot = movieDetailResponse.getOverview();
        movieRated = String.valueOf(movieDetailResponse.getVoteAverage());
        moviePoster = getActivity().getString(R.string.image_url_500) +
                movieDetailResponse.getBackdropPath();
        movieReleaseDate = movieDetailResponse.getReleaseDate();
        movieHomePage = movieDetailResponse.getHomepage();
        movieImdbId = movieDetailResponse.getImdbId();
        movieRuntime = String.valueOf(movieDetailResponse.getRuntime()) +
                getActivity().getString(R.string.minutes);

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
        Picasso.with(getActivity()).load(moviePoster)
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
        videoRecyclerAdapter = new VideoRecyclerAdapter(getActivity(), listOfVideos);
        linearLayoutManager = new LinearLayoutManager(getActivity());

        videoRecyclerView.setLayoutManager(linearLayoutManager);
        videoRecyclerView.setAdapter(videoRecyclerAdapter);
        videoRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_favorite, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_favorite) {
            if (item.getIcon().getConstantState().equals(
                    getResources().getDrawable(R.drawable.image_favorite).getConstantState())) {
                Timber.i("First if : " + String.valueOf(item.getIcon()));
                item.setIcon(R.drawable.image_unfavorite);
                Toast.makeText(getActivity(), "Movie set as Unfavorite.", Toast.LENGTH_SHORT)
                        .show();
            }
            else if (item.getIcon().getConstantState().equals(
                    getResources().getDrawable(R.drawable.image_unfavorite).getConstantState())) {
                Timber.i("Second if : " + String.valueOf(item.getIcon()));
                item.setIcon(R.drawable.image_favorite);
                Toast.makeText(getActivity(), "Movie set as Favorite.", Toast.LENGTH_SHORT)
                        .show();
                saveFavoriteMoviesDetails();
            }

        }
        return super.onOptionsItemSelected(item);
    }

    private void saveFavoriteMoviesDetails() {

    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().setTitle(getResources().getString(R.string.app_name));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setUpMovieDetails();
    }

    @Override
    public void showErrorDialog() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

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
//                if (ViewUtils.isInternetAvailable(getActivity())) {
//                    navigateToUserReviewActivity(movieId);
//                }
//                else {
//                    ViewUtils.createNoInternetDialog(getActivity(), R.string.network_error);
//                }
                break;
        }
    }

    private void navigateToUserReviewActivity(String movieId) {
        Intent intent = UserReviewActivity.newInstance(getContext());
        Bundle bundle = new Bundle();
        bundle.putString(RecyclerAdapter.MOVIE_ID, movieId);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}