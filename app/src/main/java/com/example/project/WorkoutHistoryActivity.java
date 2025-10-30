package com.example.project;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.project.database.dao.WorkoutHistoryDao;
import com.example.project.database.entity.WorkoutHistory;
import com.example.project.repository.WorkoutHistoryRepository;

import java.util.List;

public class WorkoutHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView emptyTextView;
    private TextView statsTextView;
    private ProgressBar progressBar;
    private LottieAnimationView emptyAnimation;
    private LottieAnimationView loadingAnimation;
    private WorkoutHistoryRepository historyRepository;
    private WorkoutHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_history);

        // Initialize views
        recyclerView = findViewById(R.id.historyRecyclerView);
        emptyTextView = findViewById(R.id.emptyTextView);
        statsTextView = findViewById(R.id.statsTextView);
        progressBar = findViewById(R.id.progressBar);
        emptyAnimation = findViewById(R.id.emptyAnimation);
        loadingAnimation = findViewById(R.id.loadingAnimation);

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize repository
        historyRepository = new WorkoutHistoryRepository(getApplication());

        // Load history and stats
        loadHistory();
        loadStats();
    }

    private void loadHistory() {
        android.util.Log.d("WorkoutHistoryActivity", "loadHistory() called");
        showLoading();

        historyRepository.getAllHistory().observe(this, historyList -> {
            hideLoading();

            android.util.Log.d("WorkoutHistoryActivity", "History loaded: " +
                (historyList != null ? historyList.size() + " items" : "null"));

            if (historyList == null || historyList.isEmpty()) {
                android.util.Log.d("WorkoutHistoryActivity", "No history found, showing empty view");
                showEmptyState();
                recyclerView.setVisibility(View.GONE);
                statsTextView.setVisibility(View.GONE);
            } else {
                android.util.Log.d("WorkoutHistoryActivity", "Showing history list");
                for (WorkoutHistory item : historyList) {
                    android.util.Log.d("WorkoutHistoryActivity", "History item: " +
                        (item.getWorkoutType() != null ? item.getWorkoutType() : "Unknown") +
                        " | workoutId=" + item.getWorkoutId() +
                        ", duration=" + item.getDurationMinutes() + " min");
                }
                hideEmptyState();
                recyclerView.setVisibility(View.VISIBLE);
                statsTextView.setVisibility(View.VISIBLE);

                adapter = new WorkoutHistoryAdapter(WorkoutHistoryActivity.this, historyList);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    private void showLoading() {
        if (loadingAnimation != null) {
            loadingAnimation.setVisibility(View.VISIBLE);
            loadingAnimation.playAnimation();
        }
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    private void hideLoading() {
        if (loadingAnimation != null) {
            loadingAnimation.cancelAnimation();
            loadingAnimation.setVisibility(View.GONE);
        }
        progressBar.setVisibility(View.GONE);
    }

    private void showEmptyState() {
        if (emptyAnimation != null) {
            emptyAnimation.setVisibility(View.VISIBLE);
            emptyAnimation.playAnimation();
        }
        emptyTextView.setVisibility(View.VISIBLE);
    }

    private void hideEmptyState() {
        if (emptyAnimation != null) {
            emptyAnimation.cancelAnimation();
            emptyAnimation.setVisibility(View.GONE);
        }
        emptyTextView.setVisibility(View.GONE);
    }

    private void loadStats() {
        historyRepository.getTotalWorkoutsCompleted().observe(this, total -> {
            if (total != null && total > 0) {
                statsTextView.setVisibility(View.VISIBLE);

                // Get additional stats
                historyRepository.getTotalWorkoutMinutes().observe(this, minutes -> {
                    historyRepository.getTotalCaloriesBurned().observe(this, calories -> {
                        String stats = "📊 Total: " + total + " workouts";
                        if (minutes != null) {
                            stats += " | ⏱️ " + minutes + " min";
                        }
                        if (calories != null) {
                            stats += " | 🔥 " + calories + " cal";
                        }
                        statsTextView.setText(stats);
                    });
                });
            }
        });
    }
}
