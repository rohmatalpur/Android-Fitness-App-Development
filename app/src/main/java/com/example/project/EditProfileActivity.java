package com.example.project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.project.database.FitnessDatabase;
import com.example.project.database.entity.UserPreferences;
import com.example.project.repository.UserPreferencesRepository;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "ProfilePreferences";
    private static final String KEY_PROFILE_IMAGE_URI = "profile_image_uri";

    private CircleImageView profileImageView;
    private MaterialButton changePhotoButton;
    private TextInputEditText nameEditText;
    private AutoCompleteTextView fitnessGoalDropdown;
    private AutoCompleteTextView fitnessLevelDropdown;
    private AutoCompleteTextView workoutDurationDropdown;
    private AutoCompleteTextView workoutTimeDropdown;
    private Button saveButton;
    private Toolbar toolbar;

    private UserPreferencesRepository userPreferencesRepository;
    private UserPreferences currentPreferences;
    private SharedPreferences sharedPreferences;
    private Uri selectedImageUri;

    // Activity Result Launcher for picking images
    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();

                    if (selectedImageUri != null) {
                        // Take persistent URI permission to keep access to this image
                        try {
                            getContentResolver().takePersistableUriPermission(
                                    selectedImageUri,
                                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                            );
                        } catch (SecurityException e) {
                            // Some URIs don't support persistent permissions
                            e.printStackTrace();
                        }

                        // Use Glide to load and cache the image
                        Glide.with(this)
                                .load(selectedImageUri)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(profileImageView);

                        // Save the URI to SharedPreferences
                        sharedPreferences.edit()
                                .putString(KEY_PROFILE_IMAGE_URI, selectedImageUri.toString())
                                .apply();

                        Toast.makeText(this, "Profile picture updated", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Initialize Repository and SharedPreferences
        userPreferencesRepository = new UserPreferencesRepository(getApplication());
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Initialize Views
        initializeViews();

        // Setup Toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Edit Profile");
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        // Load saved profile image
        loadProfileImage();

        // Setup dropdowns
        setupDropdowns();

        // Load current user data
        loadCurrentUserData();

        // Set click listeners
        changePhotoButton.setOnClickListener(v -> openImagePicker());
        saveButton.setOnClickListener(v -> saveProfile());
    }

    private void initializeViews() {
        toolbar = findViewById(R.id.toolbar);
        profileImageView = findViewById(R.id.profileImageView);
        changePhotoButton = findViewById(R.id.changePhotoButton);
        nameEditText = findViewById(R.id.nameEditText);
        fitnessGoalDropdown = findViewById(R.id.fitnessGoalDropdown);
        fitnessLevelDropdown = findViewById(R.id.fitnessLevelDropdown);
        workoutDurationDropdown = findViewById(R.id.workoutDurationDropdown);
        workoutTimeDropdown = findViewById(R.id.workoutTimeDropdown);
        saveButton = findViewById(R.id.saveButton);
    }

    private void loadProfileImage() {
        String imageUriString = sharedPreferences.getString(KEY_PROFILE_IMAGE_URI, null);
        if (imageUriString != null) {
            try {
                selectedImageUri = Uri.parse(imageUriString);

                // Use Glide to load the image - it caches it so it persists
                Glide.with(this)
                        .load(selectedImageUri)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(android.R.drawable.ic_menu_myplaces)
                        .error(android.R.drawable.ic_menu_myplaces)
                        .into(profileImageView);

            } catch (Exception e) {
                e.printStackTrace();
                selectedImageUri = null;
            }
        }
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        imagePickerLauncher.launch(intent);
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

    private void loadCurrentUserData() {
        FitnessDatabase.databaseWriteExecutor.execute(() -> {
            try {
                currentPreferences = userPreferencesRepository.getUserPreferencesSync();

                runOnUiThread(() -> {
                    if (currentPreferences != null) {
                        // Set current values
                        nameEditText.setText(currentPreferences.getUserName());

                        String goal = currentPreferences.getFitnessGoal();
                        if (goal != null) {
                            fitnessGoalDropdown.setText(formatTextForDisplay(goal), false);
                        }

                        String level = currentPreferences.getFitnessLevel();
                        if (level != null) {
                            fitnessLevelDropdown.setText(formatTextForDisplay(level), false);
                        }

                        int duration = currentPreferences.getPreferredWorkoutDuration();
                        if (duration > 0) {
                            if (duration >= 90) {
                                workoutDurationDropdown.setText("90+ minutes", false);
                            } else {
                                workoutDurationDropdown.setText(duration + " minutes", false);
                            }
                        }

                        String time = currentPreferences.getPreferredWorkoutTime();
                        if (time != null) {
                            workoutTimeDropdown.setText(formatTextForDisplay(time), false);
                        }
                    } else {
                        // No preferences exist yet, create default ones
                        android.util.Log.d("EditProfileActivity", "No user preferences found, using defaults");
                        nameEditText.setText("");
                    }
                });
            } catch (Exception e) {
                android.util.Log.e("EditProfileActivity", "Error loading profile data", e);
                runOnUiThread(() -> {
                    // Don't show error toast, just use empty defaults
                    nameEditText.setText("");
                });
            }
        });
    }

    private String formatTextForDisplay(String text) {
        if (text == null) return "";
        String formatted = text.replace("_", " ");
        return formatted.substring(0, 1).toUpperCase() + formatted.substring(1);
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

        // If no preferences exist, create new ones
        if (currentPreferences == null) {
            android.util.Log.d("EditProfileActivity", "Creating new user preferences");
            currentPreferences = new com.example.project.database.entity.UserPreferences();
            currentPreferences.setNotificationsEnabled(true);
            currentPreferences.setDarkModeEnabled(false);
            currentPreferences.setAutoPlayVideos(true);
            currentPreferences.setDownloadOverWifiOnly(true);
        }

        // Update preferences object
        currentPreferences.setUserName(name);

        if (!TextUtils.isEmpty(fitnessGoal)) {
            currentPreferences.setFitnessGoal(fitnessGoal.toLowerCase().replace(" ", "_"));
        }

        if (!TextUtils.isEmpty(fitnessLevel)) {
            currentPreferences.setFitnessLevel(fitnessLevel.toLowerCase());
        }

        // Parse workout duration
        if (!TextUtils.isEmpty(workoutDuration)) {
            try {
                int duration = Integer.parseInt(workoutDuration.split(" ")[0]);
                currentPreferences.setPreferredWorkoutDuration(duration);
            } catch (NumberFormatException e) {
                // Keep existing value if parse fails
            }
        }

        if (!TextUtils.isEmpty(workoutTime)) {
            currentPreferences.setPreferredWorkoutTime(workoutTime.toLowerCase());
        }

        // Save to database (insert if new, update if exists)
        FitnessDatabase.databaseWriteExecutor.execute(() -> {
            if (currentPreferences.getId() == 0) {
                // New preferences - insert
                userPreferencesRepository.insert(currentPreferences);
            } else {
                // Existing preferences - update
                userPreferencesRepository.update(currentPreferences);
            }

            runOnUiThread(() -> {
                Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
                finish();
            });
        });
    }
}
