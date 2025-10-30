package com.example.project;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.button.MaterialButton;
import com.example.project.database.FitnessDatabase;
import com.example.project.database.dao.WorkoutHistoryDao;
import com.example.project.database.entity.WorkoutHistory;

public class MainActivity4 extends AppCompatActivity {
    private static final String TAG = "MainActivity4";
    Intent intent;
    WebView webView;
    ProgressBar progressBar;
    FloatingActionButton backButton;
    private boolean useDirectUrl = false;
    private boolean hasShownDialog = false;
    private String currentVideoUrl;

    // Workout tracking fields
    private MaterialButton startWorkoutButton;
    private MaterialButton endWorkoutButton;
    private String workoutTitle;
    private String workoutTrainer;
    private int workoutId;
    private long workoutStartTime;
    private boolean workoutInProgress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        Log.d(TAG, "MainActivity4 started");

        webView = findViewById(R.id.webview);
        progressBar = findViewById(R.id.progress_bar);
        backButton = findViewById(R.id.back_button);
        startWorkoutButton = findViewById(R.id.startWorkoutButton);
        endWorkoutButton = findViewById(R.id.endWorkoutButton);

        intent = getIntent();
        currentVideoUrl = intent.getStringExtra("video");
        workoutTitle = intent.getStringExtra("title");
        workoutTrainer = intent.getStringExtra("trainer");
        workoutId = intent.getIntExtra("workoutId", 0);

        // Check if we should use browser mode directly
        boolean useBrowserMode = intent.getBooleanExtra("useBrowserMode", false);
        if (useBrowserMode) {
            useDirectUrl = true;
            Log.d(TAG, "Browser mode requested - skipping iframe");
        }

        Log.d(TAG, "Video URL received: " + currentVideoUrl);

        if (currentVideoUrl == null || currentVideoUrl.isEmpty()) {
            Log.e(TAG, "No video URL provided!");
            Toast.makeText(this, "Error: No video URL provided", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // Setup back button
        if (backButton != null) {
            backButton.setOnClickListener(v -> {
                if (workoutInProgress) {
                    showExitConfirmation();
                } else {
                    finish();
                }
            });
        }

        // Setup workout controls
        setupWorkoutControls();

        // Setup WebView
        setupWebView();

        // Load video
        loadVideo();

        // Automatically save video to history when opened
        saveVideoToHistory();
    }

    private void setupWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setMediaPlaybackRequiresUserGesture(false);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (progressBar != null) progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);

                // Only show dialog for main frame errors and if we haven't shown it yet
                if (request.isForMainFrame() && !useDirectUrl && !hasShownDialog) {
                    hasShownDialog = true;
                    showVideoErrorDialog();
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);

                // Fallback for older Android versions
                if (!useDirectUrl && !hasShownDialog) {
                    hasShownDialog = true;
                    showVideoErrorDialog();
                }
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (progressBar != null) {
                    progressBar.setProgress(newProgress);
                    if (newProgress == 100) {
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    private void showVideoErrorDialog() {
        runOnUiThread(() -> {
            if (progressBar != null) progressBar.setVisibility(View.GONE);

            new AlertDialog.Builder(MainActivity4.this)
                .setTitle("Video Playback Issue")
                .setMessage("This video cannot be played in the app. Would you like to open it in YouTube?")
                .setPositiveButton("Open in YouTube", (dialog, which) -> {
                    openInYouTube();
                })
                .setNegativeButton("Try in Browser Mode", (dialog, which) -> {
                    useDirectUrl = true;
                    loadVideo();
                })
                .setNeutralButton("Cancel", (dialog, which) -> {
                    finish();
                })
                .setCancelable(false)
                .show();
        });
    }

    private void openInYouTube() {
        String videoId = extractVideoId(currentVideoUrl);

        // Try to open in YouTube app first
        Intent youtubeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoId));
        youtubeIntent.putExtra("VIDEO_ID", videoId);

        // Check if YouTube app is available
        if (youtubeIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(youtubeIntent);
        } else {
            // Fallback to browser
            Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.youtube.com/watch?v=" + videoId));
            startActivity(browserIntent);
        }

        finish();
    }

    private void loadVideo() {
        if (currentVideoUrl == null) {
            finish();
            return;
        }

        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);

        if (!useDirectUrl) {
            // Try iframe embedding first
            String html = "<!DOCTYPE html>" +
                    "<html>" +
                    "<head>" +
                    "<style>" +
                    "body { margin: 0; padding: 0; background: #000; }" +
                    "iframe { position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: 0; }" +
                    "</style>" +
                    "</head>" +
                    "<body>" +
                    "<iframe src='" + currentVideoUrl + "' " +
                    "frameborder='0' " +
                    "allow='accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture' " +
                    "allowfullscreen>" +
                    "</iframe>" +
                    "</body>" +
                    "</html>";

            webView.loadDataWithBaseURL("https://www.youtube.com", html, "text/html", "utf-8", null);
        } else {
            // Load direct YouTube URL in WebView
            String videoId = extractVideoId(currentVideoUrl);
            webView.loadUrl("https://m.youtube.com/watch?v=" + videoId);
        }
    }

    private String extractVideoId(String url) {
        try {
            if (url.contains("/embed/")) {
                String afterEmbed = url.split("/embed/")[1];
                String[] parts = afterEmbed.split("[?&]");
                return parts[0];
            }
            if (url.contains("v=")) {
                String afterV = url.split("v=")[1];
                String[] parts = afterV.split("&");
                return parts[0];
            }
            if (url.contains("youtu.be/")) {
                String afterBe = url.split("youtu.be/")[1];
                String[] parts = afterBe.split("[?&]");
                return parts[0];
            }
            return url;
        } catch (Exception e) {
            return url;
        }
    }

    private void setupWorkoutControls() {
        if (startWorkoutButton != null) {
            startWorkoutButton.setOnClickListener(v -> startWorkout());
        }
        if (endWorkoutButton != null) {
            endWorkoutButton.setOnClickListener(v -> endWorkout());
        }
    }

    private void startWorkout() {
        workoutInProgress = true;
        workoutStartTime = System.currentTimeMillis();

        if (startWorkoutButton != null) {
            startWorkoutButton.setVisibility(View.GONE);
        }
        if (endWorkoutButton != null) {
            endWorkoutButton.setVisibility(View.VISIBLE);
        }

        Toast.makeText(this, "Workout started!", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Workout started");
    }

    private void endWorkout() {
        if (!workoutInProgress) return;

        long workoutEndTime = System.currentTimeMillis();
        int durationMinutes = (int) ((workoutEndTime - workoutStartTime) / 60000);

        if (durationMinutes < 1) durationMinutes = 1; // Minimum 1 minute

        saveWorkoutToHistory(durationMinutes);

        workoutInProgress = false;
        if (startWorkoutButton != null) {
            startWorkoutButton.setVisibility(View.VISIBLE);
        }
        if (endWorkoutButton != null) {
            endWorkoutButton.setVisibility(View.GONE);
        }

        showWorkoutCompletionDialog(durationMinutes);
    }

    private void saveVideoToHistory() {
        FitnessDatabase database = FitnessDatabase.getInstance(getApplicationContext());

        FitnessDatabase.databaseWriteExecutor.execute(() -> {
            WorkoutHistory history = new WorkoutHistory();

            // Use workoutId if available, otherwise use video URL hash
            int historyWorkoutId = workoutId != 0 ? workoutId : currentVideoUrl.hashCode();
            history.setWorkoutId(historyWorkoutId);

            // Set workout type/title
            String workoutTypeStr = workoutTitle != null ? workoutTitle : "Video Workout";
            history.setWorkoutType(workoutTypeStr);

            // Default to 0 duration since we're auto-saving on open
            history.setDurationMinutes(0);
            history.setCaloriesBurned(0);
            history.setCompletionPercentage(0);

            // Store video details for replay
            history.setVideoUrl(currentVideoUrl);
            history.setTrainerName(workoutTrainer);
            history.setThumbnailUrl(intent.getStringExtra("picture"));

            // Format date string
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
            history.setDate(sdf.format(new java.util.Date()));

            // Add trainer info to notes if available
            if (workoutTrainer != null && !workoutTrainer.isEmpty()) {
                history.setNotes("Trainer: " + workoutTrainer);
            }

            database.workoutHistoryDao().insert(history);

            Log.d(TAG, "Video saved to history: " + workoutTypeStr);
        });
    }

    private void saveWorkoutToHistory(int durationMinutes) {
        FitnessDatabase database = FitnessDatabase.getInstance(getApplicationContext());

        FitnessDatabase.databaseWriteExecutor.execute(() -> {
            WorkoutHistory history = new WorkoutHistory();

            // Use workoutId if available, otherwise use video URL hash
            int historyWorkoutId = workoutId != 0 ? workoutId : currentVideoUrl.hashCode();
            history.setWorkoutId(historyWorkoutId);

            // Set workout type/title
            String workoutTypeStr = workoutTitle != null ? workoutTitle : "Video Workout";
            history.setWorkoutType(workoutTypeStr);

            history.setDurationMinutes(durationMinutes);
            // More realistic calorie calculation: Average intensity workout burns 7-10 cal/min
            // Using 8 cal/min as a reasonable average for fitness workouts
            history.setCaloriesBurned(durationMinutes * 8);
            history.setCompletionPercentage(100);

            // Store video details for replay
            history.setVideoUrl(currentVideoUrl);
            history.setTrainerName(workoutTrainer);
            history.setThumbnailUrl(intent.getStringExtra("picture"));

            // Format date string
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
            history.setDate(sdf.format(new java.util.Date()));

            // Add trainer info to notes if available
            if (workoutTrainer != null && !workoutTrainer.isEmpty()) {
                history.setNotes("Trainer: " + workoutTrainer);
            }

            database.workoutHistoryDao().insert(history);

            runOnUiThread(() -> {
                Toast.makeText(this, "Workout saved to history!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Workout saved: " + durationMinutes + " minutes");
            });
        });
    }

    private void showWorkoutCompletionDialog(int durationMinutes) {
        int estimatedCalories = durationMinutes * 8;

        new AlertDialog.Builder(this)
            .setTitle("Workout Complete!")
            .setMessage("Great job!\n\n" +
                "Duration: " + durationMinutes + " min\n" +
                "Calories: ~" + estimatedCalories + " cal\n\n" +
                "Saved to history!")
            .setPositiveButton("Awesome!", null)
            .setNegativeButton("View History", (dialog, which) -> {
                Intent historyIntent = new Intent(this, WorkoutHistoryActivity.class);
                startActivity(historyIntent);
            })
            .show();
    }

    private void showExitConfirmation() {
        new AlertDialog.Builder(this)
            .setTitle("Workout in Progress")
            .setMessage("Do you want to end your workout?")
            .setPositiveButton("End Workout", (dialog, which) -> {
                endWorkout();
                finish();
            })
            .setNegativeButton("Cancel Workout", (dialog, which) -> {
                workoutInProgress = false;
                finish();
            })
            .setNeutralButton("Continue", null)
            .show();
    }

    @Override
    public void onBackPressed() {
        if (workoutInProgress) {
            showExitConfirmation();
        } else if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        if (workoutInProgress) {
            // Auto-save if user closes without ending
            long workoutEndTime = System.currentTimeMillis();
            int durationMinutes = (int) ((workoutEndTime - workoutStartTime) / 60000);
            if (durationMinutes > 0) {
                saveWorkoutToHistory(durationMinutes);
            }
        }
        super.onDestroy();
    }
}
