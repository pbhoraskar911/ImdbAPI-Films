package com.imdb.ui.mvp.login;

import com.imdb.ui.mvp.ViewInterface;

/**
 * Created by pranavbhoraskar on 8/4/16.
 */
public interface LoginViewInterface extends ViewInterface {

    void showProgressBar();

    void hideProgressBar();

    void openImdbHome();

    void onCredentialsFailure();
}