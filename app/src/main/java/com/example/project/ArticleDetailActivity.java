package com.example.project;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

public class ArticleDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        TextView titleText = findViewById(R.id.articleTitle);
        TextView contentText = findViewById(R.id.articleContent);

        // Get article data from intent
        String title = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");

        android.util.Log.d("ArticleDetail", "Title: " + (title != null ? title : "NULL"));
        android.util.Log.d("ArticleDetail", "Content length: " + (content != null ? content.length() : "NULL"));

        // Set toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Article");
        }

        toolbar.setNavigationOnClickListener(v -> finish());

        // Set article content
        if (title != null && !title.isEmpty()) {
            titleText.setText(title);
        } else {
            titleText.setText("Article Title");
        }

        if (content != null && !content.isEmpty()) {
            contentText.setText(content);
            android.util.Log.d("ArticleDetail", "Content set successfully");
        } else {
            contentText.setText("Article content not available. Please try again.");
            android.util.Log.e("ArticleDetail", "Content is null or empty!");
        }
    }
}
