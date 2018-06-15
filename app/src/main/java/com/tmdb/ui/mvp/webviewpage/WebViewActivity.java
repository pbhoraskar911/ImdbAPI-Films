package com.tmdb.ui.mvp.webviewpage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.tmdb.R;

import java.util.Objects;

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

    private String pageUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);

        if (getIntent() != null) {
            pageUrl = getIntent().getStringExtra(getString(R.string.url));
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
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl(pageUrl);
        }
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