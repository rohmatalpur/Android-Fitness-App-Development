package com.example.project.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.project.database.FitnessDatabase;
import com.example.project.database.dao.UserPreferencesDao;
import com.example.project.database.entity.UserPreferences;

public class UserPreferencesRepository {

    private UserPreferencesDao userPreferencesDao;
    private LiveData<UserPreferences> userPreferences;

    public UserPreferencesRepository(Application application) {
        FitnessDatabase database = FitnessDatabase.getInstance(application);
        userPreferencesDao = database.userPreferencesDao();
        userPreferences = userPreferencesDao.getUserPreferences();
    }

    // Insert preferences
    public void insert(UserPreferences preferences) {
        FitnessDatabase.databaseWriteExecutor.execute(() -> {
            userPreferencesDao.insert(preferences);
        });
    }

    // Update preferences
    public void update(UserPreferences preferences) {
        FitnessDatabase.databaseWriteExecutor.execute(() -> {
            preferences.setUpdatedAt(System.currentTimeMillis());
            userPreferencesDao.update(preferences);
        });
    }

    // Delete preferences
    public void delete(UserPreferences preferences) {
        FitnessDatabase.databaseWriteExecutor.execute(() -> {
            userPreferencesDao.delete(preferences);
        });
    }

    // Get user preferences
    public LiveData<UserPreferences> getUserPreferences() {
        return userPreferences;
    }

    // Get user preferences synchronously
    public UserPreferences getUserPreferencesSync() {
        return userPreferencesDao.getUserPreferencesSync();
    }

    // Update streak
    public void updateStreak(int streak) {
        FitnessDatabase.databaseWriteExecutor.execute(() -> {
            userPreferencesDao.updateStreak(streak);
            userPreferencesDao.updateTimestamp(System.currentTimeMillis());
        });
    }

    // Increment total workouts
    public void incrementTotalWorkouts() {
        FitnessDatabase.databaseWriteExecutor.execute(() -> {
            userPreferencesDao.incrementTotalWorkouts();
            userPreferencesDao.updateTimestamp(System.currentTimeMillis());
        });
    }

    // Add calories burned
    public void addCaloriesBurned(int calories) {
        FitnessDatabase.databaseWriteExecutor.execute(() -> {
            userPreferencesDao.addCaloriesBurned(calories);
            userPreferencesDao.updateTimestamp(System.currentTimeMillis());
        });
    }

    // Add workout minutes
    public void addWorkoutMinutes(int minutes) {
        FitnessDatabase.databaseWriteExecutor.execute(() -> {
            userPreferencesDao.addWorkoutMinutes(minutes);
            userPreferencesDao.updateTimestamp(System.currentTimeMillis());
        });
    }

    // Update notifications enabled
    public void updateNotificationsEnabled(boolean enabled) {
        FitnessDatabase.databaseWriteExecutor.execute(() -> {
            userPreferencesDao.updateNotificationsEnabled(enabled);
            userPreferencesDao.updateTimestamp(System.currentTimeMillis());
        });
    }

    // Update dark mode
    public void updateDarkMode(boolean enabled) {
        FitnessDatabase.databaseWriteExecutor.execute(() -> {
            userPreferencesDao.updateDarkMode(enabled);
            userPreferencesDao.updateTimestamp(System.currentTimeMillis());
        });
    }

    // Delete all preferences
    public void deleteAll() {
        FitnessDatabase.databaseWriteExecutor.execute(() -> {
            userPreferencesDao.deleteAll();
        });
    }
}
