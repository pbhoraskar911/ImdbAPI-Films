package com.imdb.MVP.login;

/**
 * Created by pranavbhoraskar on 8/4/16.
 */
public interface LoginSessionModel {
    boolean performLogin(String username, String password, OnLoginFinishedListener listener);

    void createUserSession(String username, String password, OnLoginFinishedListener listener);
}
