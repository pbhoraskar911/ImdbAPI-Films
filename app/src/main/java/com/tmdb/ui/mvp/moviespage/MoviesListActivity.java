package com.tmdb.ui.mvp.moviespage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.tmdb.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Pranav Bhoraskar
 */

public class MoviesListActivity extends AppCompatActivity {

    private Fragment fragment;

    public static final String POPULAR_TAG = "popular";
    public static final String RELEASE_DATE_TAG = "release_date";
    public static final String VOTE_COUNT_TAG = "top_rated";
    public static final String MOVIES_FRAGMENT = "movies_fragment";
    public static final String MOVIE_DETAIL_FRAGMENT = "movie_detail_fragment";

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_list);
        ButterKnife.bind(this);

        setUpMoviesFragment();
    }

    private void setUpMoviesFragment() {
        Fragment savedMoviesFragment = getSupportFragmentManager()
                .findFragmentByTag(MOVIES_FRAGMENT);
        final Bundle bundle = new Bundle();

        if (savedMoviesFragment == null) {

            fragment = new MoviesFragment();
            bundle.putString("tag", RELEASE_DATE_TAG);
            fragment.setArguments(bundle);

            FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                    .beginTransaction();
            fragmentTransaction.add(R.id.moviesFrameLayout, fragment, MOVIES_FRAGMENT).commit();

            bottomNavigation.setOnNavigationItemSelectedListener(
                    new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.bn_movie_latest:
                                    fragment = new MoviesFragment();
                                    bundle.putString("tag", RELEASE_DATE_TAG);
                                    fragment.setArguments(bundle);
                                    break;
                                case R.id.bn_movie_popular:
                                    fragment = new MoviesFragment();
                                    bundle.putString("tag", POPULAR_TAG);
                                    fragment.setArguments(bundle);
                                    break;
                                case R.id.bn_movie_vote_count:
                                    fragment = new MoviesFragment();
                                    bundle.putString("tag", VOTE_COUNT_TAG);
                                    fragment.setArguments(bundle);
                                    break;
                            }
                            final FragmentTransaction transaction = getSupportFragmentManager()
                                    .beginTransaction();
                            transaction.replace(R.id.moviesFrameLayout, fragment).commit();
                            return true;
                        }
                    });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}