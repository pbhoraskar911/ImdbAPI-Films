package com.tmdb.ui.mvp.moviedetail;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tmdb.R;
import com.tmdb.ui.mvp.moviespage.RecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Pranav Bhoraskar
 */

public class MovieDetailFragment extends Fragment {

    private String moviePlot;
    private String movieTitle;
    private String movieRated;
    private String moviePoster;
    private String movieReleaseDate;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(false);

        detailMoviePlot.setMovementMethod(new ScrollingMovementMethod());

        setUpMovieDetails();

        return view;
    }

    private void setUpMovieDetails() {
        getBundleArguments();
        getActivity().setTitle(movieTitle);

    }

    private void getBundleArguments() {
        movieTitle = getArguments().getString(RecyclerAdapter.MOVIE_TITLE);
        movieRated = getArguments().getString(RecyclerAdapter.MOVIE_RATED);
        moviePoster = getActivity().getString(R.string.image_url_500)
                + getArguments().getString(RecyclerAdapter.MOVIE_POSTER_URL);
        moviePlot = getArguments().getString(RecyclerAdapter.MOVIE_PLOT);
        movieReleaseDate = getArguments().getString(RecyclerAdapter.MOVIE_RELEASE_DATE);
        updateDisplay();
    }

    private void updateDisplay() {

        detailMoviePlot.setText(moviePlot);
        detailMovieRating.setText(movieRated);
        detailMovieTitle.setText(movieTitle);
        detailMovieReleaseDate.setText(movieReleaseDate);
        Picasso.with(getActivity()).load(moviePoster).placeholder(R.mipmap.no_image)
                .fit()
                .into(detailMoviePoster);
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
}