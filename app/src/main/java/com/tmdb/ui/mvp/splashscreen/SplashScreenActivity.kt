package com.tmdb.ui.mvp.splashscreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.tmdb.R
import com.tmdb.session.SessionManager
import com.tmdb.ui.mvp.login.LoginActivity
import com.tmdb.ui.mvp.moviespage.MoviesListActivity
import com.tmdb.utils.ViewUtils

/**
 * Created by Pranav Bhoraskar
 */
class SplashScreenActivity : AppCompatActivity() {

    private val SPLASH_TIME = 1000L

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        if (ViewUtils.isInternetAvailable(getCurrentContext())) {
            getSessionDetails()
        } else {
            ViewUtils.createNoInternetDialog(getCurrentContext(), R.string.network_error)
        }
    }

    private fun getSessionDetails() {

        val handler = Handler()
        handler.postDelayed({

        }, SPLASH_TIME)


        // anonymous class using "object" - for passing listeners
        handler.postDelayed(object : Runnable {
            override fun run() {
                val sessionManager = SessionManager(getCurrentContext())

                if (sessionManager.checkLogin()) {
                    navigateToMoviesListActivity()
                } else {
                    navigateToLoginActivity()
                }
                finish()
                handler.removeCallbacks(this)
            }
        }, SPLASH_TIME)
    }

    private fun navigateToLoginActivity() {
        startActivity(Intent(getCurrentContext(), LoginActivity::class.java))
    }

    private fun navigateToMoviesListActivity() {
        startActivity(Intent(getCurrentContext(), MoviesListActivity::class.java))
    }

    private fun getCurrentContext(): Context {
        return this@SplashScreenActivity
    }
}