package com.imdb.MVP.login;

/**
 * Created by pranavbhoraskar on 8/4/16.
 */
public interface LoginViewInterface {

    void showProgressBar();
    void hideProgressBar();
    void openImdbHome();
    void setUsernameError();
    void setPasswordError();
    void onCredentialsFailure();
}
