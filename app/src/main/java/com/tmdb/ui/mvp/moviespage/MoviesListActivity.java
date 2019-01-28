package com.tmdb.ui.mvp.moviespage;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

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
//        Fragment savedMoviesFragment = getSupportFragmentManager()
//                .findFragmentByTag(MOVIES_FRAGMENT_TAG);
        final Bundle bundle = new Bundle();
//
//        if (savedMoviesFragment == null) {
//
        fragment = new MoviesFragment();
        bundle.putString("tag", RELEASE_DATE_TAB);
        fragment.setArguments(bundle);
//
//            FragmentTransaction fragmentTransaction = getSupportFragmentManager()
//                    .beginTransaction();
//            fragmentTransaction.add(R.id.moviesFrameLayout, fragment, RELEASE_DATE_TAB).commit();
//        }

        replaceFragment(R.id.moviesFrameLayout, fragment, MOVIES_FRAGMENT_TAG);


        bottomNavigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.bn_movie_latest:
//                                fragment = getSupportFragmentManager()
//                                        .findFragmentByTag(RELEASE_DATE_TAB);
//                                if (fragment == null) {
//                                    fragment = MoviesFragment.newInstance();
//                                    bundle.putString("tag", RELEASE_DATE_TAB);
//                                    fragment.setArguments(bundle);
//                                }
//                                FragmentTransaction transaction = getSupportFragmentManager()
//                                        .beginTransaction();
//                                transaction
//                              .replace(R.id.moviesFrameLayout, fragment, RELEASE_DATE_TAB)
//                                        .commit();

                                if (fragment != null) {
                                    getSupportFragmentManager().popBackStack(null,
                                            FragmentManager.POP_BACK_STACK_INCLUSIVE);
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
//                                fragment = getSupportFragmentManager()
//                                        .findFragmentByTag(POPULAR_TAB);
//                                if (fragment == null) {
//
//                                    fragment = MoviesFragment.newInstance();
//                                    bundle.putString("tag", POPULAR_TAB);
//                                    fragment.setArguments(bundle);
//                                }
//                                getSupportFragmentManager().beginTransaction()
//                                        .replace(R.id.moviesFrameLayout, fragment, POPULAR_TAB)
//                                        .commit();

                                if (fragment != null) {
                                    getSupportFragmentManager().popBackStack(null,
                                            FragmentManager.POP_BACK_STACK_INCLUSIVE);
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
//                                fragment = getSupportFragmentManager()
//                                        .findFragmentByTag(VOTE_COUNT_TAB);
//                                if (fragment == null) {
//                                    fragment = MoviesFragment.newInstance();
//                                    bundle.putString("tag", VOTE_COUNT_TAB);
//                                    fragment.setArguments(bundle);
//                                }
//                                getSupportFragmentManager().beginTransaction()
//                                        .replace(R.id.moviesFrameLayout, fragment, VOTE_COUNT_TAB)
//                                        .commit();

                                if (fragment != null) {
                                    getSupportFragmentManager().popBackStack(null,
                                            FragmentManager.POP_BACK_STACK_INCLUSIVE);
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
//                                fragment = getSupportFragmentManager()
//                                        .findFragmentByTag(FAVORITE_TAB);
//                                if (fragment == null) {
//                                    fragment = MoviesFragment.newInstance();
//                                    bundle.putString("tag", FAVORITE_TAB);
//                                    fragment.setArguments(bundle);
//
//                                }
//                                getSupportFragmentManager().beginTransaction()
//                                        .replace(R.id.moviesFrameLayout, fragment, FAVORITE_TAB)
//                                        .commit();

                                if (fragment != null) {
                                    getSupportFragmentManager().popBackStack(null,
                                            FragmentManager.POP_BACK_STACK_INCLUSIVE);
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
                    }
                });
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

    private void clearBackStack() {
        selectedTab = "";
        getSupportFragmentManager().popBackStack(MOVIES_FRAGMENT_TAG,
                FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void onBackPressed() {
        try {
//            super.onBackPressed();
//            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
//                System.out.println("Popping backstack on backpressed... " +
//                        getSupportFragmentManager().getBackStackEntryCount());
//                getSupportFragmentManager().popBackStack();
//            }
//            else {
//                System.out.println("in onbackpressed... empty backstack... " +
//                        getSupportFragmentManager().getBackStackEntryCount());
//            }


            int count = getSupportFragmentManager().getBackStackEntryCount();
            if (count == 0) {
                super.onBackPressed();
                createCloseAppDialog(getCurrentContext(), getString(R.string.dialog_exit_title),
                        getString(R.string.dialog_exit_app));
            }
            else {
                clearBackStack();
            }

        }
        catch (IllegalStateException e) {
            supportFinishAfterTransition();
        }
    }

    private Context getCurrentContext() {
        return MoviesListActivity.this;
    }

    public static void createCloseAppDialog(final Context context, String title,
                                            final String content) {
        new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .cancelable(false)
                .positiveText(R.string.exit)
                .negativeText(R.string.dismiss)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog,
                                        @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog,
                                        @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}