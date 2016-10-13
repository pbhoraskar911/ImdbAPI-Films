package com.imdb.ui.mvp.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.imdb.R;
import com.imdb.di.ImdbApplication;
import com.imdb.di.component.NetworkComponent;
import com.imdb.ui.mvp.moviespage.MoviesListActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LoginActivity extends AppCompatActivity implements LoginViewInterface {

    @BindView(R.id.username)
    EditText usernameEditText;
    @BindView(R.id.password)
    EditText passwordEditText;
    @BindView(R.id.loginButton)
    Button loginButton;
    @BindView(R.id.progress)
    ProgressBar progressBar;

    @Inject
    LoginPresenter loginPresenter;

    String username, password;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        unbinder = ButterKnife.bind(this);
        getNetComponent().injectLauncher(this);
        initialize();
    }

    private NetworkComponent getNetComponent() {
        return ((ImdbApplication) getApplication()).getNetworkComponent();
    }

    private void initialize() {
        loginPresenter.attachView(this);
    }

    @Override
    public Context getContext() {
        return getBaseContext();
    }

    @OnClick(R.id.loginButton)
    public void onClick() {
        checkCredentialsValidity();
    }

    private void checkCredentialsValidity() {
        username = usernameEditText.getText().toString();
        password = passwordEditText.getText().toString();

        loginPresenter.checkCredentialsValidity(username, password);
    }

    @Override
    public void openImdbHome() {
        Toast.makeText(this, R.string.loginSuccess, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MoviesListActivity.class));
        finish();
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
        Toast.makeText(this, R.string.loginCredentialsFailure, Toast.LENGTH_SHORT).show();
        usernameEditText.setText("");
        passwordEditText.setText("");
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbinder.unbind();
        loginPresenter.detachView();
    }
}