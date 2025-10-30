package com.example.project;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.search.SearchBar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class YoutubeSearchActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private TextInputEditText searchEditText;
    private RecyclerView resultsRecyclerView;
    private ProgressBar progressBar;
    private com.google.android.material.button.MaterialButton searchButton;

    // TODO: Add your YouTube Data API v3 key here
    private static final String YOUTUBE_API_KEY = "YOUR_API_KEY_HERE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_search);

        // Initialize views
        toolbar = findViewById(R.id.toolbar);
        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        resultsRecyclerView = findViewById(R.id.resultsRecyclerView);
        progressBar = findViewById(R.id.progressBar);

        // Setup toolbar
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        // Setup RecyclerView
        resultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Search button click
        searchButton.setOnClickListener(v -> {
            String query = searchEditText.getText().toString().trim();
            if (query.isEmpty()) {
                Toast.makeText(this, "Please enter a search query", Toast.LENGTH_SHORT).show();
            } else {
                performSearch(query);
            }
        });

        // Show instructions
        showInstructions();
    }

    private void performSearch(String query) {
        if (YOUTUBE_API_KEY.equals("YOUR_API_KEY_HERE")) {
            showApiKeyInstructions();
            return;
        }

        // Show loading
        progressBar.setVisibility(View.VISIBLE);

        // TODO: Implement YouTube Data API search
        // For now, show a message
        Toast.makeText(this, "Searching for: " + query + "\n\nImplement YouTube Data API v3 to enable search", Toast.LENGTH_LONG).show();

        // Hide loading
        progressBar.setVisibility(View.GONE);

        /*
         * TO IMPLEMENT YOUTUBE SEARCH:
         *
         * 1. Add Retrofit dependencies to build.gradle.kts:
         *    implementation("com.squareup.retrofit2:retrofit:2.9.0")
         *    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
         *
         * 2. Get YouTube Data API v3 key from Google Cloud Console:
         *    - Go to https://console.cloud.google.com/
         *    - Enable YouTube Data API v3
         *    - Create API credentials
         *    - Copy the API key and paste it in YOUTUBE_API_KEY constant above
         *
         * 3. Create YouTube API service interface
         *
         * 4. Make API call:
         *    String url = "https://www.googleapis.com/youtube/v3/search" +
         *                 "?part=snippet" +
         *                 "&maxResults=25" +
         *                 "&q=" + query + " workout" +
         *                 "&type=video" +
         *                 "&key=" + YOUTUBE_API_KEY;
         *
         * 5. Parse results and display in RecyclerView
         *
         * 6. On video click, open MainActivity4 with video URL
         */
    }

    private void showInstructions() {
        Toast.makeText(this,
            "💡 Search for workout videos on YouTube!\n\n" +
            "Note: Requires YouTube Data API v3 key",
            Toast.LENGTH_LONG).show();
    }

    private void showApiKeyInstructions() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("YouTube API Key Required");
        builder.setMessage(
            "To enable YouTube search, you need to:\n\n" +
            "1. Go to Google Cloud Console\n" +
            "2. Enable YouTube Data API v3\n" +
            "3. Create API credentials\n" +
            "4. Add the API key to YoutubeSearchActivity.java\n\n" +
            "File location:\n" +
            "app/src/main/java/com/example/project/YoutubeSearchActivity.java\n\n" +
            "Line: private static final String YOUTUBE_API_KEY = \"YOUR_KEY\";"
        );
        builder.setPositiveButton("OK", null);
        builder.setNegativeButton("Open Google Cloud", (dialog, which) -> {
            android.content.Intent browserIntent = new android.content.Intent(
                android.content.Intent.ACTION_VIEW,
                android.net.Uri.parse("https://console.cloud.google.com/")
            );
            startActivity(browserIntent);
        });
        builder.show();
    }
}
