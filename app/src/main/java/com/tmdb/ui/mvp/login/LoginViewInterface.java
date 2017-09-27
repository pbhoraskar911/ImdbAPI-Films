package com.tmdb.ui.mvp.login;

import com.tmdb.ui.mvp.ViewInterface;

/**
 * Created by Pranav Bhoraskar
 */

public interface LoginViewInterface extends ViewInterface {

    void showProgressBar();

    void hideProgressBar();

    void openTmdbHome();

    void onCredentialsFailure();
}