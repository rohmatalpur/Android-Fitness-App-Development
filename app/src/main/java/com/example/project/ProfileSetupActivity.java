package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.database.FitnessDatabase;
import com.example.project.database.entity.UserPreferences;
import com.example.project.repository.UserPreferencesRepository;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileSetupActivity extends AppCompatActivity {

    private TextInputEditText nameEditText;
    private AutoCompleteTextView fitnessGoalDropdown;
    private AutoCompleteTextView fitnessLevelDropdown;
    private AutoCompleteTextView workoutDurationDropdown;
    private AutoCompleteTextView workoutTimeDropdown;
    private Button saveButton, skipButton;

    private UserPreferencesRepository userPreferencesRepository;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setup);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize Repository
        userPreferencesRepository = new UserPreferencesRepository(getApplication());

        // Initialize Views
        nameEditText = findViewById(R.id.nameEditText);
        fitnessGoalDropdown = findViewById(R.id.fitnessGoalDropdown);
        fitnessLevelDropdown = findViewById(R.id.fitnessLevelDropdown);
        workoutDurationDropdown = findViewById(R.id.workoutDurationDropdown);
        workoutTimeDropdown = findViewById(R.id.workoutTimeDropdown);
        saveButton = findViewById(R.id.saveButton);
        skipButton = findViewById(R.id.skipButton);

        // Pre-fill name from Firebase if available
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null && currentUser.getDisplayName() != null) {
            nameEditText.setText(currentUser.getDisplayName());
        }

        // Setup dropdowns
        setupDropdowns();

        // Set click listeners
        saveButton.setOnClickListener(v -> saveProfile());
        skipButton.setOnClickListener(v -> skipSetup());
    }

    private void setupDropdowns() {
        // Fitness Goals
        String[] goals = {"Weight Loss", "Muscle Gain", "Flexibility", "Endurance", "General Fitness"};
        ArrayAdapter<String> goalsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, goals);
        fitnessGoalDropdown.setAdapter(goalsAdapter);

        // Fitness Levels
        String[] levels = {"Beginner", "Intermediate", "Advanced"};
        ArrayAdapter<String> levelsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, levels);
        fitnessLevelDropdown.setAdapter(levelsAdapter);

        // Workout Duration
        String[] durations = {"15 minutes", "30 minutes", "45 minutes", "60 minutes", "90+ minutes"};
        ArrayAdapter<String> durationsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, durations);
        workoutDurationDropdown.setAdapter(durationsAdapter);

        // Workout Time
        String[] times = {"Morning", "Afternoon", "Evening", "Night", "Flexible"};
        ArrayAdapter<String> timesAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, times);
        workoutTimeDropdown.setAdapter(timesAdapter);
    }

    private void saveProfile() {
        String name = nameEditText.getText().toString().trim();
        String fitnessGoal = fitnessGoalDropdown.getText().toString().trim();
        String fitnessLevel = fitnessLevelDropdown.getText().toString().trim();
        String workoutDuration = workoutDurationDropdown.getText().toString().trim();
        String workoutTime = workoutTimeDropdown.getText().toString().trim();

        // Validate required fields
        if (TextUtils.isEmpty(name)) {
            nameEditText.setError("Name is required");
            return;
        }

        if (TextUtils.isEmpty(fitnessGoal)) {
            fitnessGoalDropdown.setError("Please select a fitness goal");
            return;
        }

        if (TextUtils.isEmpty(fitnessLevel)) {
            fitnessLevelDropdown.setError("Please select your fitness level");
            return;
        }

        // Create UserPreferences object
        UserPreferences preferences = new UserPreferences();
        preferences.setUserName(name);
        preferences.setFitnessGoal(fitnessGoal.toLowerCase().replace(" ", "_"));
        preferences.setFitnessLevel(fitnessLevel.toLowerCase());

        // Parse workout duration (extract number)
        int duration = 30; // default
        if (!TextUtils.isEmpty(workoutDuration)) {
            try {
                duration = Integer.parseInt(workoutDuration.split(" ")[0]);
            } catch (NumberFormatException e) {
                duration = 30;
            }
        }
        preferences.setPreferredWorkoutDuration(duration);

        preferences.setPreferredWorkoutTime(workoutTime.toLowerCase());
        preferences.setNotificationsEnabled(true);
        preferences.setDarkModeEnabled(false);
        preferences.setAutoPlayVideos(true);
        preferences.setDownloadOverWifiOnly(true);

        // Save to database
        FitnessDatabase.databaseWriteExecutor.execute(() -> {
            userPreferencesRepository.insert(preferences);

            runOnUiThread(() -> {
                Toast.makeText(this, "Profile created successfully!", Toast.LENGTH_SHORT).show();
                navigateToMainApp();
            });
        });
    }

    private void skipSetup() {
        // Create default preferences
        UserPreferences preferences = new UserPreferences();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null && currentUser.getDisplayName() != null) {
            preferences.setUserName(currentUser.getDisplayName());
        } else {
            preferences.setUserName("Fitness Enthusiast");
        }

        preferences.setFitnessGoal("general_fitness");
        preferences.setFitnessLevel("beginner");
        preferences.setPreferredWorkoutDuration(30);
        preferences.setPreferredWorkoutTime("flexible");

        FitnessDatabase.databaseWriteExecutor.execute(() -> {
            userPreferencesRepository.insert(preferences);

            runOnUiThread(() -> {
                Toast.makeText(this, "You can complete your profile later!", Toast.LENGTH_SHORT).show();
                navigateToMainApp();
            });
        });
    }

    private void navigateToMainApp() {
        Intent intent = new Intent(ProfileSetupActivity.this, MainActivity2.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
