package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import com.example.project.database.FitnessDatabase;
import com.example.project.database.dao.WorkoutHistoryDao;
import com.example.project.database.entity.WorkoutHistory;

public class MainActivity2 extends AppCompatActivity {

    MaterialCardView discoverCard, trendingCard, favoritesCard, historyCard;
    MaterialCardView upgradeCard, dashboardCard, startWorkoutCard;
    BottomNavigationView bottomNavigation;
    View searchBarContainer;

    private FitnessDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Dark mode is handled by FitnessApplication - don't override here

        setContentView(R.layout.activity_main2_uilover);

        // Initialize database
        database = FitnessDatabase.getInstance(this);

        // Ensure workouts are populated (force check on every launch for now)
        ensureWorkoutsPopulated();

        // Initialize views
        discoverCard = findViewById(R.id.discoverCardNew);
        trendingCard = findViewById(R.id.trainersCardNew);
        favoritesCard = findViewById(R.id.favoritesCardNew);
        historyCard = findViewById(R.id.historyCardNew);
        upgradeCard = findViewById(R.id.upgradeCard);
        dashboardCard = findViewById(R.id.dashboardCardNew);
        startWorkoutCard = findViewById(R.id.startWorkoutCardNew);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        searchBarContainer = findViewById(R.id.searchBarContainer);

        // Set Home as selected in bottom navigation
        bottomNavigation.setSelectedItemId(R.id.navigation_home);

        // Animate cards
        animateCards();

        // Setup search bar click listener
        searchBarContainer.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity2.this, YoutubeSearchActivity.class);
            startActivity(intent);
        });

        // Setup click listeners for cards
        discoverCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity2.this, CategoryBrowserActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });

        trendingCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity2.this, Discoveryoutubers.class);
            startActivity(intent);
        });

        favoritesCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity2.this, FavoritesActivity.class);
            startActivity(intent);
        });

        historyCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity2.this, WorkoutHistoryActivity.class);
            startActivity(intent);
        });

        // Dashboard Card
        dashboardCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity2.this, FitnessDashboardActivity.class);
            startActivity(intent);
        });

        // Start Workout Card - Resume latest workout
        startWorkoutCard.setOnClickListener(v -> {
            resumeLastWorkout();
        });

        // Upgrade Card
        upgradeCard.setOnClickListener(v -> {
            Toast.makeText(this, "🌟 Premium features coming soon!", Toast.LENGTH_SHORT).show();
        });

        // Bottom Navigation click handler
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.navigation_home) {
                // Already on home, do nothing or scroll to top
                return true;
            } else if (itemId == R.id.navigation_workout_info) {
                startActivity(new Intent(MainActivity2.this, FitnessDashboardActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            } else if (itemId == R.id.navigation_discover) {
                startActivity(new Intent(MainActivity2.this, DiscoverArticlesActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            } else if (itemId == R.id.navigation_profile) {
                startActivity(new Intent(MainActivity2.this, UserProfileActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            }

            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Ensure Home is selected when returning to this activity
        bottomNavigation.setSelectedItemId(R.id.navigation_home);
    }

    private void animateCards() {
        // Create array of all cards to animate
        MaterialCardView[] cards = {
            upgradeCard, discoverCard, favoritesCard,
            historyCard, trendingCard, dashboardCard,
            startWorkoutCard
        };

        // Set initial state
        for (MaterialCardView card : cards) {
            if (card != null) {
                card.setAlpha(0f);
                card.setTranslationY(50f);
                card.setScaleX(0.9f);
                card.setScaleY(0.9f);
            }
        }

        // Animate each card with stagger
        long delay = 0;
        for (MaterialCardView card : cards) {
            if (card != null) {
                card.animate()
                    .alpha(1f)
                    .translationY(0f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(400)
                    .setStartDelay(delay)
                    .setInterpolator(new android.view.animation.DecelerateInterpolator())
                    .start();
                delay += 60; // Stagger by 60ms
            }
        }
    }

    private void ensureWorkoutsPopulated() {
        android.util.Log.d("MainActivity2", "Checking if workouts need to be populated...");

        FitnessDatabase.databaseWriteExecutor.execute(() -> {
            int workoutCount = database.workoutDao().getWorkoutCountSync();
            android.util.Log.d("MainActivity2", "Current workout count in database: " + workoutCount);

            if (workoutCount == 0) {
                android.util.Log.d("MainActivity2", "No workouts found, populating 30 workouts now...");
                populateWorkouts();
                android.util.Log.d("MainActivity2", "Workouts populated successfully!");
            } else {
                android.util.Log.d("MainActivity2", "Workouts already exist, skipping population");
            }
        });
    }

    private void populateWorkouts() {
        com.example.project.database.dao.WorkoutDao workoutDao = database.workoutDao();

        // Belly workouts (6 items) - UPDATED WITH VERIFIED WORKING VIDEOS
        workoutDao.insert(createWorkout("10 Min Abs Workout", "Pamela Reif",
                "https://www.youtube.com/embed/DHD1-2P94DU",
                "mind_with_muscle_belly_workout", "belly", "pamela_reif"));

        workoutDao.insert(createWorkout("Intense Ab Workout", "Chloe Ting",
                "https://www.youtube.com/embed/2pLT-olgUJs",
                "mukti_gautam_belly_workout", "belly", "chloe_ting"));

        workoutDao.insert(createWorkout("Standing Abs Workout", "MadFit",
                "https://www.youtube.com/embed/eOMA70IgKxM",
                "madfit_belly_workout", "belly", "madfit"));

        workoutDao.insert(createWorkout("10 MIN FLAT BELLY", "Lilly Sabri",
                "https://www.youtube.com/embed/Eml2xnoLpYE",
                "cultfit_belly_workout", "belly", "lilly_sabri"));

        workoutDao.insert(createWorkout("AB WORKOUT", "Lucy Wyndham-Read",
                "https://www.youtube.com/embed/nC5tqiGqwuE",
                "thebodyproject_belly_workout", "belly", "lucy_wyndham"));

        workoutDao.insert(createWorkout("10 Min Abs Workout", "Pamela Reif",
                "https://www.youtube.com/embed/DHD1-2P94DU",
                "healthifyme_belly_workout", "belly", "pamela_reif"));

        // Full Body workouts (6 items) - UPDATED WITH VERIFIED WORKING VIDEOS
        workoutDao.insert(createWorkout("10 Min Full Body", "Koboko Fitness",
                "https://www.youtube.com/embed/gC_L9qAHVJ8",
                "mind_with_muscle_fat_loss_workout", "full_body", "koboko_fitness"));

        workoutDao.insert(createWorkout("15 MIN FULL BODY", "Pamela Reif",
                "https://www.youtube.com/embed/0rWJJr644A8",
                "mukti_gautam_fat_loss_workout", "full_body", "pamela_reif"));

        workoutDao.insert(createWorkout("Total Body Workout", "HASfit",
                "https://www.youtube.com/embed/j64BBgBGNIU",
                "madfit_full_body_workout", "full_body", "hasfit"));

        workoutDao.insert(createWorkout("30 MIN FULL BODY", "Heather Robertson",
                "https://www.youtube.com/embed/WgbRKJvqXcs",
                "cultfit_full_body_workout", "full_body", "heather_robertson"));

        workoutDao.insert(createWorkout("BEGINNER WORKOUT", "Move With Nicole",
                "https://www.youtube.com/embed/fPQx8sJPJDI",
                "thebodyproject_fullbody_workout", "full_body", "move_with_nicole"));

        workoutDao.insert(createWorkout("10 Min Full Body", "Koboko Fitness",
                "https://www.youtube.com/embed/gC_L9qAHVJ8",
                "healthifyme_fullbody_workout", "full_body", "koboko_fitness"));

        // Muscle workouts (7 items) - UPDATED WITH VERIFIED WORKING VIDEOS
        workoutDao.insert(createWorkout("Full Body Strength", "FitnessBlender",
                "https://www.youtube.com/embed/NcW0bqYJQE0",
                "mind_with_muscle_muscle_workout", "muscle", "fitnessblender"));

        workoutDao.insert(createWorkout("5 Minute Workout", "The Body Coach TV",
                "https://www.youtube.com/embed/ml6cT4AZdqI",
                "mukti_gautam_muscle_workout", "muscle", "body_coach"));

        workoutDao.insert(createWorkout("TOTAL BODY WORKOUT", "MrandMrsMuscle",
                "https://www.youtube.com/embed/UItWltVZZmE",
                "mukti_gautam_arm_workout", "muscle", "mrandmrsmuscle"));

        workoutDao.insert(createWorkout("Muscle Building Workout", "Jeff Cavaliere",
                "https://www.youtube.com/embed/3sEeVJEXTfY",
                "madfit_muscle_workout", "muscle", "jeff_cavaliere"));

        workoutDao.insert(createWorkout("Upper Body Strength", "Fitness Blender",
                "https://www.youtube.com/embed/yohMxv_k4Sc",
                "cultfit_muscle_workout", "muscle", "fitnessblender"));

        workoutDao.insert(createWorkout("Full Body Strength", "FitnessBlender",
                "https://www.youtube.com/embed/NcW0bqYJQE0",
                "thebodyproject_muscle_workout", "muscle", "fitnessblender"));

        workoutDao.insert(createWorkout("5 Minute Workout", "The Body Coach TV",
                "https://www.youtube.com/embed/ml6cT4AZdqI",
                "healthyfime_muscles_workout", "muscle", "body_coach"));

        // Leg workouts (6 items) - UPDATED WITH VERIFIED WORKING VIDEOS
        workoutDao.insert(createWorkout("Leg Workout at Home", "Jordan Yeoh",
                "https://www.youtube.com/embed/CCLKEoxh4Po",
                "mind_with_muscle_leg_workout", "leg", "jordan_yeoh"));

        workoutDao.insert(createWorkout("LEGS & BOOTY WORKOUT", "Pamela Reif",
                "https://www.youtube.com/embed/3qKwNQ4RLvg",
                "mukti_gautam_lower_body_workout", "leg", "pamela_reif"));

        workoutDao.insert(createWorkout("Leg Day Workout", "Heather Robertson",
                "https://www.youtube.com/embed/6K2qU2yX8qE",
                "madfit_leg_workout", "leg", "heather_robertson"));

        workoutDao.insert(createWorkout("Lower Body Workout", "Sydney Cummings",
                "https://www.youtube.com/embed/R1dW6pQcSxs",
                "cultfit_legs_workout", "leg", "sydney_cummings"));

        workoutDao.insert(createWorkout("LEG WORKOUT", "Caroline Girvan",
                "https://www.youtube.com/embed/zl3l_xV3TQE",
                "thebodyproject_leg_workout", "leg", "caroline_girvan"));

        workoutDao.insert(createWorkout("Leg Workout at Home", "Jordan Yeoh",
                "https://www.youtube.com/embed/CCLKEoxh4Po",
                "healthifyme_leg_workout", "leg", "jordan_yeoh"));

        // Chest workouts (5 items) - UPDATED WITH VERIFIED WORKING VIDEOS
        workoutDao.insert(createWorkout("Home Chest Workout", "Athlean-X",
                "https://www.youtube.com/embed/JhANNGWSL1s",
                "mind_with_muscle_chest_workout", "chest", "athlean_x"));

        workoutDao.insert(createWorkout("CHEST WORKOUT AT HOME", "Jordan Yeoh",
                "https://www.youtube.com/embed/svRQJcZKIcI",
                "madfit_chest_workout", "chest", "jordan_yeoh"));

        workoutDao.insert(createWorkout("Perfect Home Chest Workout", "Jeremy Ethier",
                "https://www.youtube.com/embed/Gl3hca3LD8Q",
                "cultfit_chest_workout", "chest", "jeremy_ethier"));

        workoutDao.insert(createWorkout("5 Minute Chest Workout", "HASfit",
                "https://www.youtube.com/embed/9pPW5A5rWLQ",
                "thebodyproject_chest_workout", "chest", "hasfit"));

        workoutDao.insert(createWorkout("Upper Body Workout", "Juice & Toya",
                "https://www.youtube.com/embed/Y5ggLl7z6lE",
                "healthyfime_chest_workout", "chest", "juice_and_toya"));
    }

    private com.example.project.database.entity.Workout createWorkout(String title, String trainerName, String videoUrl,
                                                                       String thumbnailResource, String category, String trainerCategory) {
        com.example.project.database.entity.Workout workout = new com.example.project.database.entity.Workout(
                title, trainerName, videoUrl, thumbnailResource, category, trainerCategory);
        workout.setDurationMinutes(20);
        workout.setDifficultyLevel("intermediate");
        return workout;
    }

    private void resumeLastWorkout() {
        android.util.Log.d("MainActivity2", "resumeLastWorkout() called - Start Workout button clicked");

        FitnessDatabase.databaseWriteExecutor.execute(() -> {
            // Get most recent history entry directly
            java.util.List<com.example.project.database.entity.WorkoutHistory> historyList =
                database.workoutHistoryDao().getAllHistorySync();

            android.util.Log.d("MainActivity2", "History entries found: " + (historyList != null ? historyList.size() : 0));

            runOnUiThread(() -> {
                if (historyList != null && !historyList.isEmpty()) {
                    // Get the most recent history entry (first in the list)
                    com.example.project.database.entity.WorkoutHistory lastHistory = historyList.get(0);

                    // Get video URL from history
                    String videoUrl = lastHistory.getVideoUrl();
                    String title = lastHistory.getWorkoutType();
                    String trainer = lastHistory.getTrainerName();
                    String thumbnailUrl = lastHistory.getThumbnailUrl();
                    int workoutId = lastHistory.getWorkoutId();

                    android.util.Log.d("MainActivity2", "Playing last video from history: " + title + ", URL: " + videoUrl);

                    if (videoUrl != null && !videoUrl.isEmpty()) {
                        // Create intent to MainActivity4 (video player)
                        Intent intent = new Intent(MainActivity2.this, MainActivity4.class);
                        intent.putExtra("workoutId", workoutId);
                        intent.putExtra("title", title != null ? title : "Workout");
                        intent.putExtra("trainer", trainer != null ? trainer : "");
                        intent.putExtra("video", videoUrl);
                        intent.putExtra("picture", thumbnailUrl != null ? thumbnailUrl : "");
                        intent.putExtra("useBrowserMode", true); // Use browser mode

                        Toast.makeText(this, "🏃 Resuming: " + title, Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    } else {
                        // History entry doesn't have video URL, fall back to first workout
                        android.util.Log.d("MainActivity2", "No video URL in history, falling back to first workout");
                        playFirstWorkout();
                    }
                } else {
                    android.util.Log.d("MainActivity2", "No workout history found, getting first workout from database");
                    playFirstWorkout();
                }
            });
        });
    }

    private void playFirstWorkout() {
        // Get first available workout from database
        FitnessDatabase.databaseWriteExecutor.execute(() -> {
            java.util.List<com.example.project.database.entity.Workout> allWorkouts =
                database.workoutDao().getAllWorkoutsSync();

            android.util.Log.d("MainActivity2", "All workouts count: " + (allWorkouts != null ? allWorkouts.size() : "NULL"));

            runOnUiThread(() -> {
                if (allWorkouts != null && !allWorkouts.isEmpty()) {
                    // Get first workout
                    com.example.project.database.entity.Workout firstWorkout = allWorkouts.get(0);

                    android.util.Log.d("MainActivity2", "Playing first workout: " + firstWorkout.getTitle() + ", Video: " + firstWorkout.getVideoUrl());

                    Intent intent = new Intent(MainActivity2.this, MainActivity4.class);
                    intent.putExtra("workoutId", firstWorkout.getId());
                    intent.putExtra("title", firstWorkout.getTitle());
                    intent.putExtra("trainer", firstWorkout.getTrainerName());
                    intent.putExtra("video", firstWorkout.getVideoUrl());
                    intent.putExtra("picture", firstWorkout.getThumbnailResource());
                    intent.putExtra("useBrowserMode", true); // Use browser mode

                    Toast.makeText(this, "🏃 Starting: " + firstWorkout.getTitle(), Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                } else {
                    android.util.Log.d("MainActivity2", "No workouts in database, going to Discover");

                    // No workouts in database at all, go to discover
                    Toast.makeText(this, "No workouts available. Let's discover one!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity2.this, Discover.class);
                    startActivity(intent);
                }
            });
        });
    }

}