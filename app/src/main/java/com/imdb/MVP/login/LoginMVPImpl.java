package com.imdb.MVP.login;

/**
 * Created by pranavbhoraskar on 8/4/16.
 */
public class LoginMVPImpl implements LoginMVP, OnLoginFinishedListener {

    private LoginSessionModelImpl loginModel;
    private LoginViewInterface loginViewInterface;
//    SessionManager sessionManager = new SessionManager();

    public LoginMVPImpl(LoginViewInterface loginViewInterface) {
        this.loginViewInterface = loginViewInterface;
        this.loginModel = new LoginSessionModelImpl();
    }

    @Override
    public void checkCredentialsValidity(String username, String password) {
        if(loginViewInterface != null){
            loginViewInterface.showProgressBar();
        }


//        loginModel.performLogin(username,password,this);

        if(loginModel.performLogin(username,password,this)){
            //session management
                    loginModel.createUserSession(username, password,this);
        }
    }

    @Override
    public void onDestroy() {
        loginViewInterface = null;
    }

    @Override
    public void onSuccess() {
        if(loginViewInterface != null) {
            loginViewInterface.openImdbHome();
        }
    }

    @Override
    public void onUserNameError() {
        if(loginViewInterface!= null){
            loginViewInterface.setUsernameError();
            loginViewInterface.hideProgressBar();
        }
    }

    @Override
    public void onPasswordError() {
        if(loginViewInterface != null){
            loginViewInterface.setPasswordError();
            loginViewInterface.hideProgressBar();
        }
    }

    @Override
    public void credentialsFailure() {
        if(loginViewInterface != null){
            loginViewInterface.onCredentialsFailure();
            loginViewInterface.hideProgressBar();
        }
    }
}
