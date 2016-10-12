package com.imdb.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import com.imdb.MVP.moviesPage.MoviesListActivity;
import com.imdb.R;

public class LauncherActivity extends AppCompatActivity {

    private static final int SPLASH_TIME = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        View statusBarView = getWindow().getDecorView();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT) {
            statusBarView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_IMMERSIVE);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SessionManager sessionManager = new SessionManager(LauncherActivity.this);
                sessionManager.checkLogin();
//                startActivity(new Intent(LauncherActivity.this, MoviesListActivity.class));
                finish();
            }
        }, SPLASH_TIME);
    }
}
