package com.tmdb.session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.tmdb.ui.mvp.login.LoginActivity;

import javax.inject.Inject;

/**
 * Created by Pranav Bhoraskar
 */

public class SessionManager {

    private static final String LOGIN_PREFS = "imdb_login";
    private static final String USERNAME_KEY = "user_name";
    private static final String PASSWORD_KEY = "password_key";
    private static final String IS_LOGIN = "isLoggedIn";

    private Context context;
    private boolean isLoggedIn = false;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Inject
    public SessionManager(@NonNull Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(LOGIN_PREFS, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void createUserSession(String username, String password) {
        isLoggedIn = true;
        editor.putBoolean(IS_LOGIN, isLoggedIn);
        editor.putString(USERNAME_KEY, username);
        editor.putString(PASSWORD_KEY, password);
        editor.apply();
    }

    public boolean checkLogin() {
        return isUserLoggedIn();
    }

    private boolean isUserLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGIN, isLoggedIn);
    }

    public void logoutUser() {
        isLoggedIn = false;
        editor.putBoolean(IS_LOGIN, isLoggedIn).remove(USERNAME_KEY).remove(PASSWORD_KEY).apply();
        context.startActivity(new Intent(context, LoginActivity.class));
    }
}