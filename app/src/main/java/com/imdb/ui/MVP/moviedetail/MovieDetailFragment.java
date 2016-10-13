package com.imdb.ui.mvp.moviedetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.imdb.R;
import com.imdb.ui.mvp.moviespage.RecyclerAdapter;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by prana on 7/1/2016.
 */
public class MovieDetailFragment extends Fragment {

    String movieTitle;
    String movieRated;
    String movieDuration;
    String movieGenre;
    String moviePoster;
    String moviePlot;
    String movieDirector;
    String movieLanguages;

    @BindView(R.id.detail_movie_duration)
    TextView detailMovieDuration;
    @BindView(R.id.detail_movie_rated)
    TextView detailMovieRated;
    @BindView(R.id.detail_movie_genre)
    TextView detailMovieGenre;
    @BindView(R.id.detail_movie_director)
    TextView detailMovieDirector;
    @BindView(R.id.detail_movie_language)
    TextView detailMovieLanguage;
    @BindView(R.id.detail_movie_plot)
    TextView detailMoviePlot;
    @BindView(R.id.detail_movie_poster)
    ImageView detailMoviePoster;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(false);

        detailMoviePlot.setMovementMethod(new ScrollingMovementMethod());

        getBundleArguments();

        getActivity().setTitle(movieTitle);
        updateDisplay();

        return view;
    }

    private void getBundleArguments() {
        movieTitle = getArguments().getString(RecyclerAdapter.MOVIE_TITLE);
        movieRated = getArguments().getString(RecyclerAdapter.MOVIE_RATED);
        movieDuration = getArguments().getString(RecyclerAdapter.MOVIE_DURATION);
        movieGenre = getArguments().getString(RecyclerAdapter.MOVIE_GENRE);
        moviePoster = getArguments().getString(RecyclerAdapter.MOVIE_POSTER_URL);
        moviePlot = getArguments().getString(RecyclerAdapter.MOVIE_PLOT);
        movieDirector = getArguments().getString(RecyclerAdapter.MOVIE_DIRECTOR);
        movieLanguages = getArguments().getString(RecyclerAdapter.MOVIE_LANGUAGE);
    }

    private void updateDisplay() {
        detailMovieDuration.setText(movieDuration);
        detailMovieRated.setText(movieRated);
        detailMovieGenre.setText(movieGenre);
        detailMovieDirector.setText(movieDirector);
        detailMovieLanguage.setText(movieLanguages);
        detailMoviePlot.setText(moviePlot);

        Picasso.with(getActivity())
                .load(moviePoster)
                .placeholder(R.mipmap.no_image)
                .into(detailMoviePoster);
    }


    @Override
    public void onStop() {
        super.onStop();
        getActivity().setTitle(getResources().getString(R.string.app_name));
    }
}