package com.imdb.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.imdb.MVP.login.LoginActivity;
import com.imdb.MVP.moviesPage.MoviesListActivity;

/**
 * Created by pranavbhoraskar on 8/3/16.
 */
public class SessionManager {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Context context;

    public static final String LOGIN_PREFS = "imdb_login";
    public static final String USERNAME_KEY = "user_name";
    public static final String PASSWORD_KEY = "password_key";
    public static final String IS_LOGIN = "isLoggedIn";

    boolean isLoggedIn = false;

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(LOGIN_PREFS, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    public void createUserSession(String username, String password){
        isLoggedIn = true;
        editor.putBoolean(IS_LOGIN, isLoggedIn);
        editor.putString(USERNAME_KEY, username);
        editor.putString(PASSWORD_KEY,password);
        editor.commit();
    }

    public void checkLogin(){
        if(!this.isUserLoggedIn()){
            context.startActivity(new Intent(context, LoginActivity.class));
        }
        else{
            isLoggedIn = true;
            context.startActivity(new Intent(context, MoviesListActivity.class));
        }
    }

    public boolean isUserLoggedIn(){
        return sharedPreferences.getBoolean(IS_LOGIN,false);
    }


    public void logoutUser() {
        isLoggedIn = false;
        editor.putBoolean(IS_LOGIN, isLoggedIn);
        editor.commit();
        editor.clear();
        context.startActivity(new Intent(context, LoginActivity.class));
    }
}
