package com.tmdb.ui.mvp.webviewpage;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tmdb.R;

import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Pranav Bhoraskar
 */

public class WebViewActivity extends AppCompatActivity {
    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.no_web_page)
    TextView noPageFoundTextView;
    @BindView(R.id.progress_bar_webview)
    ProgressBar progressBarWebview;

    private String pageUrl;
    private String pageTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_webview);
        getWindow().setFeatureInt(Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);
        ButterKnife.bind(this);

        if (getIntent() != null) {
            pageUrl = getIntent().getStringExtra(getString(R.string.url));
            pageTitle = getIntent().getStringExtra(getString(R.string.movie_title));
            openWebPage(pageUrl);
        }
    }

    private void openWebPage(String pageUrl) {

        if (Objects.equals(pageUrl, "")) {
            noPageFoundTextView.setVisibility(View.VISIBLE);
        }
        else {
            noPageFoundTextView.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);

            webView.setWebViewClient(new AppWebViewClient(progressBarWebview));

            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webView.loadUrl(pageUrl);
        }
    }

    private Context getCurrentContext() {
        return WebViewActivity.this;
    }

    @Override
    public boolean onKeyDown(final int keyCode, final KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}