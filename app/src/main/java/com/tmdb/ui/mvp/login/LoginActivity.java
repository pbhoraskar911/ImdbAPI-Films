package com.tmdb.ui.mvp.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.tmdb.R;
import com.tmdb.di.TmdbApplication;
import com.tmdb.di.component.NetworkComponent;
import com.tmdb.ui.mvp.moviespage.MoviesListActivity;
import com.tmdb.utils.ViewUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Pranav Bhoraskar
 */

public class LoginActivity extends AppCompatActivity implements LoginViewInterface {

    @BindView(R.id.username)
    MaterialEditText usernameEditText;
    @BindView(R.id.password)
    MaterialEditText passwordEditText;
    @BindView(R.id.loginButton)
    ActionProcessButton loginButton;
    @BindView(R.id.progress)
    ProgressBar progressBar;

    @Inject
    LoginPresenter loginPresenter;

    String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginButton.setMode(ActionProcessButton.Mode.ENDLESS);
        getNetComponent().injectLauncher(this);
        initialize();
    }

    private NetworkComponent getNetComponent() {
        return ((TmdbApplication) getApplication()).getNetworkComponent();
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
        if (ViewUtils.isInternetAvailable(this)) {
            loginButton.setProgress(1);
            checkCredentialsValidity();
        }
        else {
            ViewUtils.createNoInternetDialog(this, R.string.network_error);
        }
    }

    private void checkCredentialsValidity() {
        username = usernameEditText.getText().toString();
        password = passwordEditText.getText().toString();

        loginPresenter.checkCredentialsValidity(username, password);
    }

    @Override
    public void openTmdbHome() {
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
        loginButton.setProgress(0);
        Toast.makeText(this, R.string.loginCredentialsFailure, Toast.LENGTH_SHORT).show();
        usernameEditText.setText("");
        passwordEditText.setText("");
    }

    @Override
    protected void onStop() {
        super.onStop();
        loginPresenter.detachView();
    }
}