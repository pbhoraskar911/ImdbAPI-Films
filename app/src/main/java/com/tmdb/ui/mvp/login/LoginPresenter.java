package com.tmdb.ui.mvp.login;

import android.text.TextUtils;

import com.tmdb.session.SessionManager;
import com.tmdb.ui.mvp.Presenter;

import javax.inject.Inject;

/**
 * Created by Pranav Bhoraskar
 */

public class LoginPresenter implements Presenter<LoginViewInterface> {

    private SessionManager sessionManager;
    private LoginViewInterface loginViewInterface;

    private boolean loggedIn;

    @Inject
    LoginPresenter(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    void checkCredentialsValidity(String username, String password) {
        if (loginViewInterface != null) {
            loginViewInterface.showProgressBar();
        }

        if (performLogin(username, password)) {
            onSuccess();
        }
    }

    private boolean performLogin(final String username, final String password) {
        loggedIn = false;
        if (TextUtils.equals(username, "admin") && TextUtils.equals(password, "admin")) {
            loggedIn = true;
            createUserSession(username, password);
        }
        else {
            credentialsFailure();
            loggedIn = false;
        }
        return loggedIn;
    }

    private void createUserSession(String username, String password) {
        sessionManager.createUserSession(username, password);
    }

    @Override
    public void attachView(LoginViewInterface loginViewInterface) {
        this.loginViewInterface = loginViewInterface;
    }

    @Override
    public void detachView() {
        this.loginViewInterface = null;
    }


    public void onSuccess() {
        if (loginViewInterface != null) {
            loginViewInterface.openTmdbHome();
        }
    }

    public void credentialsFailure() {
        if (loginViewInterface != null) {
            loginViewInterface.onCredentialsFailure();
            loginViewInterface.hideProgressBar();
        }
    }
}