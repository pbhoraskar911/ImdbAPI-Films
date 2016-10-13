package com.imdb.ui.mvp.login;

import android.text.TextUtils;

import com.imdb.session.SessionManager;
import com.imdb.ui.mvp.Presenter;

import javax.inject.Inject;

/**
 * Created by pranavbhoraskar on 10/12/16.
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
        if (TextUtils.equals(username, "pml889") && TextUtils.equals(password, "pml889")) {
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
            loginViewInterface.openImdbHome();
        }
    }

    public void credentialsFailure() {
        if (loginViewInterface != null) {
            loginViewInterface.onCredentialsFailure();
            loginViewInterface.hideProgressBar();
        }
    }
}