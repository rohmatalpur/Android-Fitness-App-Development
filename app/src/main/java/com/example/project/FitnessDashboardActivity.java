package com.example.project;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import com.example.project.database.FitnessDatabase;
import com.example.project.database.entity.WorkoutHistory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Fitness Dashboard Activity
 * Inspired by JetLagged's sleep tracking dashboard
 * Displays workout statistics, graphs, and wellness metrics
 */
public class FitnessDashboardActivity extends AppCompatActivity {

    private WorkoutGraphView workoutGraph;
    private MaterialButton graphTypeButton;
    private TextView avgTimeValue;
    private TextView caloriesValue;
    private TextView workoutsValue;
    private TextView streakValue;
    private TextView wellnessScore;
    private LinearProgressIndicator wellnessProgress;
    private TextView heartRateValue;

    // Stats cards for animation
    private MaterialCardView graphCard;
    private MaterialCardView avgTimeCard;
    private MaterialCardView caloriesCard;
    private MaterialCardView workoutsCard;
    private MaterialCardView streakCard;
    private MaterialCardView wellnessCard;
    private MaterialCardView heartRateCard;

    private boolean isLineGraph = true;
    private FitnessDatabase database;
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_dashboard);

        // Initialize database
        database = FitnessDatabase.getInstance(this);

        initViews();
        setupGraph();
        setupBottomNavigation();
        loadWorkoutData();
        animateCardsIn();
        setupGraphTypeToggle();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Ensure Stats is selected when returning to this activity
        if (bottomNavigation != null) {
            bottomNavigation.setSelectedItemId(R.id.navigation_workout_info);
        }
    }

    private void initViews() {
        workoutGraph = findViewById(R.id.workoutGraph);
        graphTypeButton = findViewById(R.id.graphTypeButton);
        avgTimeValue = findViewById(R.id.avgTimeValue);
        caloriesValue = findViewById(R.id.caloriesValue);
        workoutsValue = findViewById(R.id.workoutsValue);
        streakValue = findViewById(R.id.streakValue);
        wellnessScore = findViewById(R.id.wellnessScore);
        wellnessProgress = findViewById(R.id.wellnessProgress);
        heartRateValue = findViewById(R.id.heartRateValue);
        bottomNavigation = findViewById(R.id.bottom_navigation);

        // Cards for animation
        graphCard = findViewById(R.id.graphCard);
        avgTimeCard = findViewById(R.id.avgTimeCard);
        caloriesCard = findViewById(R.id.caloriesCard);
        workoutsCard = findViewById(R.id.workoutsCard);
        streakCard = findViewById(R.id.streakCard);
        wellnessCard = findViewById(R.id.wellnessCard);
        heartRateCard = findViewById(R.id.heartRateCard);
    }

    private void setupBottomNavigation() {
        // Set Stats as selected
        bottomNavigation.setSelectedItemId(R.id.navigation_workout_info);

        // Bottom Navigation click handler
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.navigation_home) {
                startActivity(new Intent(FitnessDashboardActivity.this, MainActivity2.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            } else if (itemId == R.id.navigation_workout_info) {
                // Already on stats, do nothing
                return true;
            } else if (itemId == R.id.navigation_discover) {
                startActivity(new Intent(FitnessDashboardActivity.this, DiscoverArticlesActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            } else if (itemId == R.id.navigation_profile) {
                startActivity(new Intent(FitnessDashboardActivity.this, UserProfileActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            }

            return false;
        });
    }

    private void setupGraph() {
        // Set custom colors for the graph
        int primaryColor = ContextCompat.getColor(this, R.color.purple);
        int accentColor = ContextCompat.getColor(this, R.color.purple_light);
        workoutGraph.setColors(primaryColor, accentColor);
    }

    private void loadWorkoutData() {
        // Fetch real workout data from database
        FitnessDatabase.databaseWriteExecutor.execute(() -> {
            // Get all workout history from the last 7 days
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -7);
            long sevenDaysAgo = calendar.getTimeInMillis();

            List<WorkoutHistory> allHistory = database.workoutHistoryDao().getAllHistorySync();

            // Filter history from last 7 days
            List<WorkoutHistory> recentHistory = new ArrayList<>();
            for (WorkoutHistory history : allHistory) {
                if (history.getCompletedAt() >= sevenDaysAgo) {
                    recentHistory.add(history);
                }
            }

            // Calculate statistics
            List<Float> workoutDurations = new ArrayList<>();
            int totalDuration = 0;
            int totalCalories = 0;

            for (WorkoutHistory history : recentHistory) {
                int duration = history.getDurationMinutes();
                totalDuration += duration;
                totalCalories += history.getCaloriesBurned();
                workoutDurations.add((float) duration);
            }

            // If no data, use default values
            if (workoutDurations.isEmpty()) {
                for (int i = 0; i < 7; i++) {
                    workoutDurations.add(0f);
                }
            }

            // Ensure we have exactly 7 data points (for the week)
            while (workoutDurations.size() < 7) {
                workoutDurations.add(0f);
            }
            if (workoutDurations.size() > 7) {
                workoutDurations = workoutDurations.subList(workoutDurations.size() - 7, workoutDurations.size());
            }

            List<String> labels = new ArrayList<>();
            labels.add("Mon");
            labels.add("Tue");
            labels.add("Wed");
            labels.add("Thu");
            labels.add("Fri");
            labels.add("Sat");
            labels.add("Sun");

            // Calculate final statistics
            final float avgTime = recentHistory.isEmpty() ? 0 : totalDuration / (float) recentHistory.size();
            final int finalTotalCalories = totalCalories;
            final int workoutCount = recentHistory.size();
            final int streak = calculateStreak(allHistory);
            final int wellness = calculateWellnessScore(workoutDurations);
            final int avgHeartRate = 142; // Default heart rate (can be enhanced later)

            // Update UI on main thread
            final List<Float> finalDurations = new ArrayList<>(workoutDurations);
            runOnUiThread(() -> {
                workoutGraph.setDataPoints(finalDurations);
                workoutGraph.setLabels(labels);

                // Animate stat values
                animateStatValue(avgTimeValue, 0, (int) avgTime, " min");
                animateStatValue(caloriesValue, 0, finalTotalCalories, "");
                animateStatValue(workoutsValue, 0, workoutCount, "");
                animateStatValue(streakValue, 0, streak, " Days");
                animateStatValue(wellnessScore, 0, wellness, "");
                animateStatValue(heartRateValue, 0, avgHeartRate, "");

                // Set wellness progress (if visible)
                if (wellnessProgress != null && wellnessProgress.getVisibility() == View.VISIBLE) {
                    wellnessProgress.setProgress(wellness);
                }
            });
        });
    }

    private int calculateStreak(List<WorkoutHistory> allHistory) {
        if (allHistory.isEmpty()) return 0;

        // Sort by date descending
        allHistory.sort((a, b) -> Long.compare(b.getCompletedAt(), a.getCompletedAt()));

        int streak = 0;
        Calendar calendar = Calendar.getInstance();
        long today = calendar.getTimeInMillis();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        long currentDay = calendar.getTimeInMillis();

        for (WorkoutHistory history : allHistory) {
            Calendar historyCalendar = Calendar.getInstance();
            historyCalendar.setTimeInMillis(history.getCompletedAt());
            historyCalendar.set(Calendar.HOUR_OF_DAY, 0);
            historyCalendar.set(Calendar.MINUTE, 0);
            historyCalendar.set(Calendar.SECOND, 0);
            historyCalendar.set(Calendar.MILLISECOND, 0);

            long workoutDay = historyCalendar.getTimeInMillis();

            if (workoutDay == currentDay || workoutDay == currentDay - (24 * 60 * 60 * 1000L * streak)) {
                streak++;
                currentDay = workoutDay;
            } else if (workoutDay < currentDay - (24 * 60 * 60 * 1000L * streak)) {
                break;
            }
        }

        return streak;
    }

    private float calculateAverage(List<Float> values) {
        if (values.isEmpty()) return 0;
        float sum = 0;
        for (Float value : values) {
            sum += value;
        }
        return sum / values.size();
    }

    private int calculateTotalCalories(List<Float> workoutDurations) {
        // Simple calculation: ~35 calories per minute
        float totalMinutes = 0;
        for (Float duration : workoutDurations) {
            totalMinutes += duration;
        }
        return (int) (totalMinutes * 35);
    }

    private int calculateWellnessScore(List<Float> workoutDurations) {
        // Simple wellness score based on consistency and duration
        float avgDuration = calculateAverage(workoutDurations);
        int consistency = workoutDurations.size();

        // Score out of 100
        int score = (int) Math.min(100, (avgDuration / 60 * 50) + (consistency * 7));
        return score;
    }

    private void setupGraphTypeToggle() {
        graphTypeButton.setOnClickListener(v -> {
            isLineGraph = !isLineGraph;

            if (isLineGraph) {
                workoutGraph.setGraphType(WorkoutGraphView.GRAPH_TYPE_LINE);
                graphTypeButton.setText("📊");
            } else {
                workoutGraph.setGraphType(WorkoutGraphView.GRAPH_TYPE_BAR);
                graphTypeButton.setText("📈");
            }

            // Animate the transition
            workoutGraph.animate()
                .alpha(0.5f)
                .setDuration(150)
                .withEndAction(() -> {
                    workoutGraph.animate()
                        .alpha(1f)
                        .setDuration(150)
                        .start();
                })
                .start();
        });
    }

    private void animateCardsIn() {
        // Initial state - make cards invisible
        View[] cards = {graphCard, avgTimeCard, caloriesCard, workoutsCard,
                       streakCard, wellnessCard, heartRateCard};

        for (View card : cards) {
            card.setAlpha(0f);
            card.setTranslationY(100f);
            card.setScaleX(0.95f);
            card.setScaleY(0.95f);
        }

        // Animate cards in sequence with stagger
        long baseDelay = 100;
        long staggerDelay = 80;

        for (int i = 0; i < cards.length; i++) {
            final View card = cards[i];
            long delay = baseDelay + (i * staggerDelay);

            card.animate()
                .alpha(1f)
                .translationY(0f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(500)
                .setStartDelay(delay)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();
        }
    }

    private void animateStatValue(TextView textView, int start, int end, String suffix) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.setDuration(1500);
        animator.setStartDelay(300);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());

        animator.addUpdateListener(animation -> {
            int value = (int) animation.getAnimatedValue();
            // Handle heart rate value (it's the heartRateValue TextView)
            if (textView == heartRateValue) {
                textView.setText(String.format(Locale.getDefault(), "%d BPM", value));
            } else if (suffix.isEmpty()) {
                textView.setText(String.format(Locale.getDefault(), "%d", value));
            } else if (suffix.equals(" min")) {
                textView.setText(String.format(Locale.getDefault(), "%d%s", value, suffix));
            } else if (suffix.equals(" Days")) {
                textView.setText(String.format(Locale.getDefault(), "%d%s", value, suffix));
            } else {
                textView.setText(String.format(Locale.getDefault(), "%,d%s", value, suffix));
            }
        });

        animator.start();
    }

    /**
     * Public method to update graph with real data
     * Can be called from fragments or other activities
     */
    public void updateWorkoutData(List<Float> durations, List<String> labels) {
        if (workoutGraph != null) {
            workoutGraph.setDataPoints(durations);
            workoutGraph.setLabels(labels);
        }
    }

    /**
     * Update statistics with real-time data
     */
    public void updateStats(int avgMinutes, int calories, int workouts, int streakDays,
                           int wellnessValue, int heartRate) {
        if (avgTimeValue != null) {
            avgTimeValue.setText(String.format(Locale.getDefault(), "%d min", avgMinutes));
        }
        if (caloriesValue != null) {
            caloriesValue.setText(String.format(Locale.getDefault(), "%,d", calories));
        }
        if (workoutsValue != null) {
            workoutsValue.setText(String.format(Locale.getDefault(), "%d", workouts));
        }
        if (streakValue != null) {
            streakValue.setText(String.format(Locale.getDefault(), "%d Days", streakDays));
        }
        if (wellnessScore != null) {
            wellnessScore.setText(String.format(Locale.getDefault(), "%d", wellnessValue));
        }
        if (wellnessProgress != null) {
            wellnessProgress.setProgress(wellnessValue);
        }
        if (heartRateValue != null) {
            heartRateValue.setText(String.format(Locale.getDefault(), "%d", heartRate));
        }
    }
}
