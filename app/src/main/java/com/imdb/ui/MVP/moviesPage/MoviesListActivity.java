package com.imdb.ui.mvp.moviespage;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.imdb.R;

public class MoviesListActivity extends AppCompatActivity {

    public static final String MOVIES_FRAGMENT = "movies_fragment";
    public static final String MOVIE_DETAIL_FRAGMENT = "movie_detail_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_list);

        MoviesFragment savedMoviesFragment = (MoviesFragment) getSupportFragmentManager()
                .findFragmentByTag(MOVIES_FRAGMENT);

        if (savedMoviesFragment == null) {
            MoviesFragment moviesFragment = new MoviesFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                    .beginTransaction();
            fragmentTransaction.add(R.id.moviesFrameLayout, moviesFragment, MOVIES_FRAGMENT);
            fragmentTransaction.commit();
        }
    }
}