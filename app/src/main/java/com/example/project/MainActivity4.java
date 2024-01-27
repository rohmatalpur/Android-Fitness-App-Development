package com.example.project;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity4 extends AppCompatActivity {
    Intent intent;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        webView=findViewById(R.id.webview);

        intent=getIntent();
//        String videoName = intent.getStringExtra("video");
//        int rawId = getResources().getIdentifier(videoName,  "raw", getPackageName());
//        String videoUrl= "android.resource://" + getPackageName() + "/" + rawId;
//        VideoView videoView = findViewById(R.id.videoview);
//        Uri uri = Uri.parse(videoUrl);
//        videoView.setVideoURI(uri);
//
//        MediaController mediaController = new MediaController(this);
//        mediaController.setAnchorView(videoView);
//        mediaController.setMediaPlayer(videoView);
//        videoView.setMediaController(mediaController);
//        videoView.start();

        String videourl="<iframe width=\"100%\" height=\"100%\" src=\"" +
                intent.getStringExtra("video") +
                "frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
        webView.loadData(videourl,"text/html","utf-8");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
    }
}