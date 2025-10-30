package com.example.project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.project.database.FitnessDatabase;
import com.example.project.database.entity.UserPreferences;
import com.example.project.repository.UserPreferencesRepository;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "AppPreferences";
    private static final String KEY_DARK_MODE = "dark_mode_enabled";

    private BottomNavigationView bottomNavigation;
    private TextView usernameText, emailText;
    private TextView totalWorkoutsText, totalCaloriesText, totalMinutesText, currentStreakText;
    private TextView fitnessGoalText, fitnessLevelText, workoutDurationText, workoutTimeText;
    private MaterialCardView statsCard, editProfileCard, aboutCard;
    private SwitchMaterial notificationSwitch, darkModeSwitch, autoPlaySwitch, wifiOnlySwitch;
    private Button logoutButton;
    private CircleImageView profileImage;

    private FirebaseAuth mAuth;
    private UserPreferencesRepository userPreferencesRepository;
    private UserPreferences currentPreferences;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize Repository
        userPreferencesRepository = new UserPreferencesRepository(getApplication());

        // Initialize Views
        initializeViews();

        // Set Profile as selected in bottom navigation
        bottomNavigation.setSelectedItemId(R.id.navigation_profile);

        // Load user data
        loadUserData();

        // Setup click listeners
        setupClickListeners();

        // Setup bottom navigation
        setupBottomNavigation();
    }

    private void initializeViews() {
        bottomNavigation = findViewById(R.id.bottom_navigation);
        usernameText = findViewById(R.id.usernameText);
        emailText = findViewById(R.id.emailText);
        profileImage = findViewById(R.id.profileImage);

        // Quick Stats
        totalWorkoutsText = findViewById(R.id.totalWorkoutsText);
        totalCaloriesText = findViewById(R.id.totalCaloriesText);
        totalMinutesText = findViewById(R.id.totalMinutesText);
        currentStreakText = findViewById(R.id.currentStreakText);

        // Profile Info
        fitnessGoalText = findViewById(R.id.fitnessGoalText);
        fitnessLevelText = findViewById(R.id.fitnessLevelText);
        workoutDurationText = findViewById(R.id.workoutDurationText);
        workoutTimeText = findViewById(R.id.workoutTimeText);

        // Cards
        statsCard = findViewById(R.id.statsCard);
        editProfileCard = findViewById(R.id.editProfileCard);
        aboutCard = findViewById(R.id.aboutCard);

        // Switches
        notificationSwitch = findViewById(R.id.notificationSwitch);
        darkModeSwitch = findViewById(R.id.darkModeSwitch);
        autoPlaySwitch = findViewById(R.id.autoPlaySwitch);
        wifiOnlySwitch = findViewById(R.id.wifiOnlySwitch);

        // Logout Button
        logoutButton = findViewById(R.id.logoutButton);
    }

    private void loadUserData() {
        // Load Firebase user data
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String email = currentUser.getEmail();
            emailText.setText(email != null ? email : "No email");
        } else {
            emailText.setText("Guest User");
        }

        // Load saved profile image
        loadProfileImage();

        // Load user preferences from database
        FitnessDatabase.databaseWriteExecutor.execute(() -> {
            currentPreferences = userPreferencesRepository.getUserPreferencesSync();

            runOnUiThread(() -> {
                if (currentPreferences != null) {
                    displayUserPreferences();
                } else {
                    // No preferences found - show default values
                    usernameText.setText("Fitness Enthusiast");
                    Toast.makeText(this, "Please complete your profile", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void loadProfileImage() {
        // Check both SharedPreferences locations for backward compatibility
        SharedPreferences profilePrefs = getSharedPreferences("ProfilePreferences", MODE_PRIVATE);
        String imageUriString = profilePrefs.getString("profile_image_uri", null);

        // Fallback to AppPreferences if not found
        if (imageUriString == null) {
            imageUriString = sharedPreferences.getString("profile_image_uri", null);
        }

        if (imageUriString != null) {
            try {
                android.net.Uri imageUri = android.net.Uri.parse(imageUriString);

                // Use Glide to load the image - it handles permissions better and caches the image
                Glide.with(this)
                        .load(imageUri)
                        .diskCacheStrategy(DiskCacheStrategy.ALL) // Cache the image
                        .placeholder(android.R.drawable.ic_menu_myplaces) // Default while loading
                        .error(android.R.drawable.ic_menu_myplaces) // Default if error
                        .into(profileImage);

            } catch (Exception e) {
                e.printStackTrace();
                // If we can't load, just show default
                Glide.with(this)
                        .load(android.R.drawable.ic_menu_myplaces)
                        .into(profileImage);
            }
        } else {
            // No saved image - show default
            Glide.with(this)
                    .load(android.R.drawable.ic_menu_myplaces)
                    .into(profileImage);
        }
    }

    private void displayUserPreferences() {
        // Display user name
        String userName = currentPreferences.getUserName();
        usernameText.setText(userName != null ? userName : "Fitness Enthusiast");

        // Display stats
        totalWorkoutsText.setText(String.valueOf(currentPreferences.getTotalWorkoutsCompleted()));
        totalCaloriesText.setText(String.valueOf(currentPreferences.getTotalCaloriesBurned()));
        totalMinutesText.setText(String.valueOf(currentPreferences.getTotalWorkoutMinutes()));
        currentStreakText.setText(String.valueOf(currentPreferences.getCurrentStreak()));

        // Display profile info
        String goal = currentPreferences.getFitnessGoal();
        fitnessGoalText.setText(goal != null ? formatText(goal) : "Not set");

        String level = currentPreferences.getFitnessLevel();
        fitnessLevelText.setText(level != null ? formatText(level) : "Not set");

        workoutDurationText.setText(currentPreferences.getPreferredWorkoutDuration() + " minutes");

        String time = currentPreferences.getPreferredWorkoutTime();
        workoutTimeText.setText(time != null ? formatText(time) : "Not set");

        // Set switch states WITHOUT triggering listeners
        notificationSwitch.setOnCheckedChangeListener(null);
        darkModeSwitch.setOnCheckedChangeListener(null);
        autoPlaySwitch.setOnCheckedChangeListener(null);
        wifiOnlySwitch.setOnCheckedChangeListener(null);

        notificationSwitch.setChecked(currentPreferences.isNotificationsEnabled());
        darkModeSwitch.setChecked(currentPreferences.isDarkModeEnabled());
        autoPlaySwitch.setChecked(currentPreferences.isAutoPlayVideos());
        wifiOnlySwitch.setChecked(currentPreferences.isDownloadOverWifiOnly());

        // Re-attach listeners after setting states
        setupSwitchListeners();
    }

    private String formatText(String text) {
        if (text == null) return "";
        return text.replace("_", " ")
                   .substring(0, 1).toUpperCase() + text.replace("_", " ").substring(1);
    }

    private void setupClickListeners() {
        // Stats Card - View Full Statistics
        statsCard.setOnClickListener(v -> {
            startActivity(new Intent(this, FitnessDashboardActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });

        // Edit Profile Card
        editProfileCard.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditProfileActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });

        // About Card
        aboutCard.setOnClickListener(v -> showAboutDialog());

        // Setup switch listeners
        setupSwitchListeners();

        // Logout Button
        logoutButton.setOnClickListener(v -> showLogoutDialog());
    }

    private void setupSwitchListeners() {
        // Notification Switch
        notificationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (currentPreferences != null && buttonView.isPressed()) {
                userPreferencesRepository.updateNotificationsEnabled(isChecked);
                Toast.makeText(this, isChecked ? "Notifications enabled" : "Notifications disabled",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Dark Mode Switch
        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (currentPreferences != null && buttonView.isPressed()) {
                android.util.Log.d("UserProfile", "Dark mode toggled to: " + isChecked);

                // Save to SharedPreferences immediately (synchronously) for app startup
                sharedPreferences.edit().putBoolean(KEY_DARK_MODE, isChecked).commit();

                // Save to Room database
                userPreferencesRepository.updateDarkMode(isChecked);

                // Apply dark mode immediately - this will recreate all activities
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    Toast.makeText(this, "Dark mode enabled", Toast.LENGTH_SHORT).show();
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    Toast.makeText(this, "Dark mode disabled", Toast.LENGTH_SHORT).show();
                }

                // The activity will automatically recreate at this point
                // No need for toast as it won't be visible due to recreation
            }
        });

        // Auto Play Switch
        autoPlaySwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (currentPreferences != null && buttonView.isPressed()) {
                currentPreferences.setAutoPlayVideos(isChecked);
                userPreferencesRepository.update(currentPreferences);
                Toast.makeText(this, isChecked ? "Auto-play enabled" : "Auto-play disabled",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // WiFi Only Switch
        wifiOnlySwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (currentPreferences != null && buttonView.isPressed()) {
                currentPreferences.setDownloadOverWifiOnly(isChecked);
                userPreferencesRepository.update(currentPreferences);
                Toast.makeText(this, isChecked ? "Download over WiFi only" : "Download on any network",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void showAboutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("About FitnessPal")
                .setMessage("FitnessPal - Your Personal Fitness Companion\n\n" +
                        "Version: 1.0.0\n\n" +
                        "Track your workouts, monitor progress, and achieve your fitness goals!\n\n" +
                        "© 2024 FitnessPal. All rights reserved.")
                .setPositiveButton("OK", null)
                .show();
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Logout", (dialog, which) -> {
                    // Sign out from Firebase
                    mAuth.signOut();

                    // Clear user preferences (optional - comment out if you want to keep data)
                    // userPreferencesRepository.deleteAll();

                    Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();

                    // Redirect to login
                    Intent intent = new Intent(UserProfileActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void setupBottomNavigation() {
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.navigation_home) {
                startActivity(new Intent(this, MainActivity2.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            } else if (itemId == R.id.navigation_workout_info) {
                startActivity(new Intent(this, FitnessDashboardActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            } else if (itemId == R.id.navigation_discover) {
                startActivity(new Intent(this, DiscoverArticlesActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            } else if (itemId == R.id.navigation_profile) {
                return true; // Already here
            }

            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload user data when returning to this activity
        loadUserData();
    }
}
