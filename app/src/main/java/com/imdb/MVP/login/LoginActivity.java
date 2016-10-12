package com.imdb.MVP.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.imdb.MVP.moviesPage.MoviesListActivity;
import com.imdb.R;
import com.imdb.ui.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginViewInterface{

    @BindView(R.id.username)
    EditText usernameEditText;
    @BindView(R.id.password)
    EditText passwordEditText;
    @BindView(R.id.loginButton)
    Button loginButton;
    @BindView(R.id.progress)
    ProgressBar progressBar;

    SessionManager sessionManager;

    LoginMVP loginMVPPresenter;

    String username, password;
    public boolean loggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        loginMVPPresenter = new LoginMVPImpl(this);

//        sessionManager = new SessionManager(this);
    }

    @OnClick(R.id.loginButton)
    public void onClick() {
        checkCredentialsValidity();
    }

    private void checkCredentialsValidity() {
        username = usernameEditText.getText().toString();
        password = passwordEditText.getText().toString();

        loginMVPPresenter.checkCredentialsValidity(username,password);
    }

    @Override
    public void openImdbHome() {
        Toast.makeText(this, "Login Success...", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MoviesListActivity.class));
        finish();
    }

    @Override
    public void setUsernameError() {
        usernameEditText.setText("");

    }

    @Override
    public void setPasswordError() {
        passwordEditText.setText("");
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onCredentialsFailure() {
        Toast.makeText(this, "username and/or password are incorrect!!", Toast.LENGTH_SHORT).show();
        usernameEditText.setText("");
        passwordEditText.setText("");
    }

    @Override
    protected void onDestroy() {
        loginMVPPresenter.onDestroy();
        super.onDestroy();
    }
}
