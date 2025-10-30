package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * WebView fallback for YouTube videos that can't be embedded
 * Opens the video in a browser view within the app
 */
public class WebViewVideoActivity extends AppCompatActivity {

    private WebView webView;
    private ProgressBar progressBar;
    private FloatingActionButton backButton;
    private String videoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_video);

        webView = findViewById(R.id.webview);
        progressBar = findViewById(R.id.progress_bar);
        backButton = findViewById(R.id.back_button);

        // Get video ID from intent
        Intent intent = getIntent();
        videoId = intent.getStringExtra("videoId");
        String title = intent.getStringExtra("title");

        if (videoId == null || videoId.isEmpty()) {
            Toast.makeText(this, "Invalid video", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Setup back button
        backButton.setOnClickListener(v -> finish());

        // Setup WebView
        setupWebView();

        // Load YouTube video in browser mode
        String url = "https://www.youtube.com/watch?v=" + videoId;
        webView.loadUrl(url);

        Toast.makeText(this, "Loading in browser mode...", Toast.LENGTH_SHORT).show();
    }

    private void setupWebView() {
        WebSettings webSettings = webView.getSettings();

        // Enable JavaScript (required for YouTube)
        webSettings.setJavaScriptEnabled(true);

        // Enable video playback
        webSettings.setMediaPlaybackRequiresUserGesture(false);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);

        // Enable zooming
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);

        // Set user agent to desktop mode for better playback
        webSettings.setUserAgentString("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");

        // Handle page loading
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                Toast.makeText(WebViewVideoActivity.this,
                    "Error loading video: " + description,
                    Toast.LENGTH_LONG).show();
            }
        });

        // Handle fullscreen
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (webView != null) {
            webView.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (webView != null) {
            webView.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.destroy();
        }
    }
}
