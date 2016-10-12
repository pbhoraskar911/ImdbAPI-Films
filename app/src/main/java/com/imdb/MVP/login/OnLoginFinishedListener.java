package com.imdb.MVP.login;

/**
 * Created by pranavbhoraskar on 8/4/16.
 */
public interface OnLoginFinishedListener {
    void onSuccess();


    void onUserNameError();

    void onPasswordError();

    void credentialsFailure();
}
