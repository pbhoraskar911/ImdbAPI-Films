package com.imdb.MVP.login;

import android.os.Handler;
import android.text.TextUtils;

import com.imdb.ui.SessionManager;

import javax.inject.Inject;

/**
 * Created by pranavbhoraskar on 8/4/16.
 */
public class LoginSessionModelImpl implements LoginSessionModel {

    @Inject
    SessionManager sessionManager;

    boolean loggedIn;

    @Override
    public boolean performLogin(final String username, final String password, final OnLoginFinishedListener listener) {

//        final SessionManager sessionManager = new SessionManager(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loggedIn = false;
                if (TextUtils.equals(username, "pml889") && TextUtils.equals(password, "pml889")) {
                    listener.onSuccess();
                    loggedIn = true;
                    sessionManager.createUserSession(username, password);
                }
                else if (TextUtils.isEmpty(username)) {
                    listener.onUserNameError();
                    loggedIn = false;
                }
                else if (TextUtils.isEmpty(password)) {
                    listener.onPasswordError();
                    loggedIn = false;
                }
                else{
                    listener.credentialsFailure();
                    loggedIn = false;
                }
            }
        }, 1000);

        return loggedIn;

    }

    @Override
    public void createUserSession(String username, String password, OnLoginFinishedListener listener) {
        System.out.println("creating user session");
    }

}
