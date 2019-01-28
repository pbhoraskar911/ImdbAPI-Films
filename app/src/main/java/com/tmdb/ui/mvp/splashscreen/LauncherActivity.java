package com.tmdb.ui.mvp.splashscreen;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.tmdb.R;
import com.tmdb.session.SessionManager;
import com.tmdb.ui.mvp.login.LoginActivity;
import com.tmdb.ui.mvp.moviespage.MoviesListActivity;
import com.tmdb.utils.ViewUtils;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Pranav Bhoraskar
 */

public class LauncherActivity extends AppCompatActivity {

    private static final int SPLASH_TIME = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        if (ViewUtils.isInternetAvailable(getCurrentContext())) {
            getSessionDetails();
        }
        else {
            ViewUtils.createNoInternetDialog(getCurrentContext(), R.string.network_error);
        }
    }

    private void getSessionDetails() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SessionManager sessionManager = new SessionManager(getCurrentContext());

                if (sessionManager.checkLogin()) {
                    navigateToMoviesListActivity();
                }
                else {
                    navigateToLoginActivity();
                }
                finish();
                handler.removeCallbacks(this);
            }
        }, SPLASH_TIME);
    }

    private void navigateToLoginActivity() {
        startActivity(new Intent(getCurrentContext(), LoginActivity.class));
    }

    private void navigateToMoviesListActivity() {
        startActivity(new Intent(getCurrentContext(), MoviesListActivity.class));
    }

    private LauncherActivity getCurrentContext() {
        return LauncherActivity.this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        View statusBarView = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            statusBarView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_IMMERSIVE);
        }
        else {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }
}