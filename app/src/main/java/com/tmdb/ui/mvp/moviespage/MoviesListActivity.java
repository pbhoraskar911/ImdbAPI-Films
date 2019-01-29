package com.tmdb.ui.mvp.moviespage;

import android.content.Context;
import android.os.Bundle;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tmdb.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Pranav Bhoraskar
 */

public class MoviesListActivity extends AppCompatActivity {

    private Fragment fragment;

    public static final String MOVIES_FRAGMENT_TAG = "movies_fragment";
    public static final String MOVIE_DETAIL_FRAGMENT_TAG = "movie_detail_fragment";

    public static final String POPULAR_TAB = "popular";
    public static final String RELEASE_DATE_TAB = "release_date";
    public static final String VOTE_COUNT_TAB = "top_rated";
    public static final String FAVORITE_TAB = "popular";

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;

    private String selectedTab = RELEASE_DATE_TAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_list);
        ButterKnife.bind(this);

        setUpMoviesFragment();
    }

    private void setUpMoviesFragment() {
        final Bundle bundle = new Bundle();
        if (getSupportFragmentManager().findFragmentByTag(MOVIES_FRAGMENT_TAG) == null) {
            fragment = new MoviesFragment();
            bundle.putString("tag", RELEASE_DATE_TAB);
            fragment.setArguments(bundle);
        }
        replaceFragment(R.id.moviesFrameLayout, fragment, MOVIES_FRAGMENT_TAG);


        bottomNavigation.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.bn_movie_latest:
                            if (fragment != null) {
                                clearBackStack(null);
                            }
                            fragment = MoviesFragment.newInstance();
                            bundle.putString("tag", RELEASE_DATE_TAB);
                            fragment.setArguments(bundle);

                            if (selectedTab != RELEASE_DATE_TAB) {
                                selectedTab = RELEASE_DATE_TAB;
                                changeFragment(fragment, MOVIES_FRAGMENT_TAG);
                            }
                            break;
                        case R.id.bn_movie_popular:
                            if (fragment != null) {
                                clearBackStack(null);
                            }

                            fragment = MoviesFragment.newInstance();
                            bundle.putString("tag", POPULAR_TAB);
                            fragment.setArguments(bundle);

                            if (selectedTab != POPULAR_TAB) {
                                selectedTab = POPULAR_TAB;
                                replaceFragment(R.id.moviesFrameLayout, fragment);
                            }
                            break;
                        case R.id.bn_movie_vote_count:
                            if (fragment != null) {
                                clearBackStack(null);
                            }

                            fragment = MoviesFragment.newInstance();
                            bundle.putString("tag", VOTE_COUNT_TAB);
                            fragment.setArguments(bundle);

                            if (selectedTab != VOTE_COUNT_TAB) {
                                selectedTab = VOTE_COUNT_TAB;
                                replaceFragment(R.id.moviesFrameLayout, fragment);
                            }
                            break;
                        case R.id.bn_movie_favorites:
                            if (fragment != null) {
                                clearBackStack(null);
                            }
                            fragment = MoviesFragment.newInstance();
                            bundle.putString("tag", FAVORITE_TAB);
                            fragment.setArguments(bundle);

                            if (selectedTab != FAVORITE_TAB) {
                                selectedTab = FAVORITE_TAB;
                                replaceFragment(R.id.moviesFrameLayout, fragment);
                            }
                    }
                    return true;
                });
    }

    private void clearBackStack(String str) {
        getSupportFragmentManager().popBackStack(str,
                FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    private void changeFragment(Fragment fragment, String tag) {
        replaceFragment(R.id.moviesFrameLayout, fragment, tag);
    }

    private void replaceFragment(int where, Fragment fragment, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        transaction.replace(where, fragment, tag)
                .addToBackStack(tag)
                .commit();
    }

    private void replaceFragment(int where, Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        transaction.replace(where, fragment)
                .commit();
    }

    private Fragment getFragment(String tag) {
        return getSupportFragmentManager().findFragmentByTag(tag);
    }

    private void clearBackStackWithTag() {
        selectedTab = "";
        clearBackStack(MOVIES_FRAGMENT_TAG);
    }

    @Override
    public void onBackPressed() {
        try {
//            super.onBackPressed();

            int count = getSupportFragmentManager().getBackStackEntryCount();
            if (count == 0) {
                createCloseAppDialog(getCurrentContext(), getString(R.string.dialog_exit_title),
                        getString(R.string.dialog_exit_app));
            }
            else {
                clearBackStackWithTag();
            }
        }
        catch (IllegalStateException e) {
            supportFinishAfterTransition();
        }
    }

    private Context getCurrentContext() {
        return MoviesListActivity.this;
    }

    public void createCloseAppDialog(final Context context, String title,
                                     final String content) {
        new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .cancelable(false)
                .positiveText(R.string.exit)
                .negativeText(R.string.dismiss)
                .onPositive((dialog, which) -> {
                    dialog.dismiss();
                    finish();
                })
                .onNegative((dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}