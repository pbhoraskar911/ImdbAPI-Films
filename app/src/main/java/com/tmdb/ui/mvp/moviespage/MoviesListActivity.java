package com.tmdb.ui.mvp.moviespage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
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
    public static final String FAVORITE_TAG = "popular";
//    public static final String FAVORITE_TAG = "favorite_fragment";

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
            fragmentTransaction.add(R.id.moviesFrameLayout, fragment, RELEASE_DATE_TAG).commit();
        }

        bottomNavigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.bn_movie_latest:
                                fragment = getSupportFragmentManager().findFragmentByTag(RELEASE_DATE_TAG);
                                if (fragment == null) {
                                    fragment = MoviesFragment.newInstance();
                                    bundle.putString("tag", RELEASE_DATE_TAG);
                                    fragment.setArguments(bundle);
                                }
                                FragmentTransaction transaction = getSupportFragmentManager()
                                        .beginTransaction();
                                transaction.replace(R.id.moviesFrameLayout, fragment, RELEASE_DATE_TAG).commit();
                                break;
                            case R.id.bn_movie_popular:
//                                if (fragment != null && fragment.isAdded()) {
//                                    System.out.println("popping fragment movie_popular : " + getSupportFragmentManager().getBackStackEntryCount());
////                                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
////                                    if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
////                                        getSupportFragmentManager().popBackStack();
////                                    }
//
//                                    if (fragment.isAdded()) {
//                                        System.out.println("Fragment is already added...");
//                                    }
//                                    else {
//                                        System.out.println("Fragment not added...");
//                                    }
//                                }
                                fragment = getSupportFragmentManager().findFragmentByTag(POPULAR_TAG);
                                if (fragment == null) {

                                    fragment = MoviesFragment.newInstance();
                                    bundle.putString("tag", POPULAR_TAG);
                                    fragment.setArguments(bundle);
                                }
                                getSupportFragmentManager().beginTransaction().replace(R.id.moviesFrameLayout, fragment, POPULAR_TAG).commit();
                                break;
                            case R.id.bn_movie_vote_count:
//                                if (fragment != null) {
//                                    System.out.println("popping fragment movie_vote_count : " + getSupportFragmentManager().getBackStackEntryCount());
//                                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                                    if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
//                                        getSupportFragmentManager().popBackStack();
//                                    }
//
//                                    if (fragment.isAdded()) {
//                                        System.out.println("Fragment is already added...");
//                                    }
//                                    else {
//                                        System.out.println("Fragment not added...");
//                                    }
//                                }

                                fragment = getSupportFragmentManager().findFragmentByTag(VOTE_COUNT_TAG);
                                if (fragment == null) {
                                    fragment = MoviesFragment.newInstance();
                                    bundle.putString("tag", VOTE_COUNT_TAG);
                                    fragment.setArguments(bundle);
                                }
                                getSupportFragmentManager().beginTransaction().replace(R.id.moviesFrameLayout, fragment, VOTE_COUNT_TAG).commit();
                                break;
                            case R.id.bn_movie_favorites:
//                                if (fragment != null && fragment.isAdded()) {
//                                    System.out.println("popping fragment movie_favorites.. : " + getSupportFragmentManager().getBackStackEntryCount());
////                                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
////                                    if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
////                                        getSupportFragmentManager().popBackStack();
////                                    }
//
//                                    if (fragment.isAdded()) {
//                                        System.out.println("Fragment is already added...");
//                                    }
//                                    else {
//                                        System.out.println("Fragment not added...");
//                                    }
//                                }


                                fragment = getSupportFragmentManager().findFragmentByTag(FAVORITE_TAG);
                                if (fragment == null) {
                                    fragment = MoviesFragment.newInstance();
                                    bundle.putString("tag", FAVORITE_TAG);
                                    fragment.setArguments(bundle);

                                }
                                getSupportFragmentManager().beginTransaction().replace(R.id.moviesFrameLayout, fragment, FAVORITE_TAG).commit();
                        }
//                        final FragmentTransaction transaction = getSupportFragmentManager()
//                                .beginTransaction();
//                        transaction.replace(R.id.moviesFrameLayout, fragment).commit();
                        return true;
                    }
                });
    }

    @Override
    public void onBackPressed() {
        try {
            super.onBackPressed();
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                System.out.println("Popping backstack on backpressed... " + getSupportFragmentManager().getBackStackEntryCount());
                getSupportFragmentManager().popBackStack();
            }
            else {
                System.out.println("in onbackpressed... empty backstack... " + getSupportFragmentManager().getBackStackEntryCount());
            }
        }
        catch (IllegalStateException e) {
            supportFinishAfterTransition();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}


//package com.tmdb.ui.mvp.moviespage;
//
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.design.widget.BottomNavigationView;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v7.app.AppCompatActivity;
//import android.view.KeyEvent;
//import android.view.MenuItem;
//
//import com.tmdb.R;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
///**
// * Created by Pranav Bhoraskar
// */
//
//public class MoviesListActivity extends AppCompatActivity {
//
//    private Fragment fragment;
//
//    public static final String POPULAR_TAG = "popular";
//    public static final String RELEASE_DATE_TAG = "release_date";
//    public static final String VOTE_COUNT_TAG = "top_rated";
//    public static final String MOVIES_FRAGMENT = "movies_fragment";
//    public static final String MOVIE_DETAIL_FRAGMENT = "movie_detail_fragment";
//    public static final String FAVORITE_TAG = "popular";
////    public static final String FAVORITE_TAG = "favorite_fragment";
//
//    @BindView(R.id.bottom_navigation)
//    BottomNavigationView bottomNavigation;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_movies_list);
//        ButterKnife.bind(this);
//
//        setUpMoviesFragment();
//    }
//
//    private void setUpMoviesFragment() {
//        Fragment savedMoviesFragment = getSupportFragmentManager()
//                .findFragmentByTag(MOVIES_FRAGMENT);
//        final Bundle bundle = new Bundle();
//
//        if (savedMoviesFragment == null) {
//
//            fragment = new MoviesFragment();
//            bundle.putString("tag", RELEASE_DATE_TAG);
//            fragment.setArguments(bundle);
//
//            FragmentTransaction fragmentTransaction = getSupportFragmentManager()
//                    .beginTransaction();
//            fragmentTransaction.add(R.id.moviesFrameLayout, fragment, MOVIES_FRAGMENT).commit();
//        }
//
//        bottomNavigation.setOnNavigationItemSelectedListener(
//                new BottomNavigationView.OnNavigationItemSelectedListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                        switch (item.getItemId()) {
//                            case R.id.bn_movie_latest:
//                                if (fragment != null) {
//                                    System.out.println("popping fragment movie_latest : " + getSupportFragmentManager().getBackStackEntryCount());
////                                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
////                                    if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
////                                        getSupportFragmentManager().popBackStack();
////                                    }
//                                }
//                                fragment = MoviesFragment.newInstance();
//                                bundle.putString("tag", RELEASE_DATE_TAG);
//                                fragment.setArguments(bundle);
//                                break;
//                            case R.id.bn_movie_popular:
//                                if (fragment != null) {
//                                    System.out.println("popping fragment movie_popular : " + getSupportFragmentManager().getBackStackEntryCount());
////                                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
////                                    if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
////                                        getSupportFragmentManager().popBackStack();
////                                    }
//                                }
//                                fragment = MoviesFragment.newInstance();
//                                bundle.putString("tag", POPULAR_TAG);
//                                fragment.setArguments(bundle);
//                                break;
//                            case R.id.bn_movie_vote_count:
//                                if (fragment != null) {
//                                    System.out.println("popping fragment movie_vote_count : " + getSupportFragmentManager().getBackStackEntryCount());
////                                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
////                                    if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
////                                        getSupportFragmentManager().popBackStack();
////                                    }
//                                }
//                                fragment = MoviesFragment.newInstance();
//                                bundle.putString("tag", VOTE_COUNT_TAG);
//                                fragment.setArguments(bundle);
//                                break;
//                            case R.id.bn_movie_favorites:
//                                if (fragment != null) {
//                                    System.out.println("popping fragment movie_favorites.. : " + getSupportFragmentManager().getBackStackEntryCount());
////                                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
////                                    if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
////                                        getSupportFragmentManager().popBackStack();
////                                    }
//                                }
//                                fragment = MoviesFragment.newInstance();
//                                bundle.putString("tag", FAVORITE_TAG);
//                                fragment.setArguments(bundle);
//                        }
//                        final FragmentTransaction transaction = getSupportFragmentManager()
//                                .beginTransaction();
//                        transaction.replace(R.id.moviesFrameLayout, fragment).commit();
//                        return true;
//                    }
//                });
//    }
//
//    @Override
//    public void onBackPressed() {
//        try {
//            super.onBackPressed();
//            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
//                System.out.println("Popping backstack on backpressed... " + getSupportFragmentManager().getBackStackEntryCount());
//                getSupportFragmentManager().popBackStack();
//            }
//            else {
//                System.out.println("in onbackpressed... empty backstack... " + getSupportFragmentManager().getBackStackEntryCount());
//            }
//        }
//        catch (IllegalStateException e) {
//            supportFinishAfterTransition();
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//    }
//}