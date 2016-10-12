package com.imdb.MVP.login;

/**
 * Created by pranavbhoraskar on 8/4/16.
 */
public interface LoginMVP {

    void checkCredentialsValidity(String username, String password);

    void onDestroy();

}
