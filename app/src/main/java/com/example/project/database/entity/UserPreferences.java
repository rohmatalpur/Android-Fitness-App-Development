package com.example.project.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_preferences")
public class UserPreferences {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "user_name")
    private String userName;

    @ColumnInfo(name = "fitness_goal")
    private String fitnessGoal; // weight_loss, muscle_gain, flexibility, endurance

    @ColumnInfo(name = "fitness_level")
    private String fitnessLevel; // beginner, intermediate, advanced

    @ColumnInfo(name = "preferred_workout_duration")
    private int preferredWorkoutDuration; // in minutes

    @ColumnInfo(name = "preferred_workout_time")
    private String preferredWorkoutTime; // morning, afternoon, evening

    @ColumnInfo(name = "notifications_enabled")
    private boolean notificationsEnabled;

    @ColumnInfo(name = "reminder_time")
    private String reminderTime; // HH:MM format

    @ColumnInfo(name = "dark_mode_enabled")
    private boolean darkModeEnabled;

    @ColumnInfo(name = "auto_play_videos")
    private boolean autoPlayVideos;

    @ColumnInfo(name = "download_over_wifi_only")
    private boolean downloadOverWifiOnly;

    @ColumnInfo(name = "current_streak")
    private int currentStreak; // consecutive workout days

    @ColumnInfo(name = "total_workouts_completed")
    private int totalWorkoutsCompleted;

    @ColumnInfo(name = "total_calories_burned")
    private int totalCaloriesBurned;

    @ColumnInfo(name = "total_workout_minutes")
    private int totalWorkoutMinutes;

    @ColumnInfo(name = "created_at")
    private long createdAt;

    @ColumnInfo(name = "updated_at")
    private long updatedAt;

    // Constructors
    public UserPreferences() {
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
        this.notificationsEnabled = true;
        this.autoPlayVideos = true;
        this.downloadOverWifiOnly = true;
        this.currentStreak = 0;
        this.totalWorkoutsCompleted = 0;
        this.totalCaloriesBurned = 0;
        this.totalWorkoutMinutes = 0;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFitnessGoal() {
        return fitnessGoal;
    }

    public void setFitnessGoal(String fitnessGoal) {
        this.fitnessGoal = fitnessGoal;
    }

    public String getFitnessLevel() {
        return fitnessLevel;
    }

    public void setFitnessLevel(String fitnessLevel) {
        this.fitnessLevel = fitnessLevel;
    }

    public int getPreferredWorkoutDuration() {
        return preferredWorkoutDuration;
    }

    public void setPreferredWorkoutDuration(int preferredWorkoutDuration) {
        this.preferredWorkoutDuration = preferredWorkoutDuration;
    }

    public String getPreferredWorkoutTime() {
        return preferredWorkoutTime;
    }

    public void setPreferredWorkoutTime(String preferredWorkoutTime) {
        this.preferredWorkoutTime = preferredWorkoutTime;
    }

    public boolean isNotificationsEnabled() {
        return notificationsEnabled;
    }

    public void setNotificationsEnabled(boolean notificationsEnabled) {
        this.notificationsEnabled = notificationsEnabled;
    }

    public String getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(String reminderTime) {
        this.reminderTime = reminderTime;
    }

    public boolean isDarkModeEnabled() {
        return darkModeEnabled;
    }

    public void setDarkModeEnabled(boolean darkModeEnabled) {
        this.darkModeEnabled = darkModeEnabled;
    }

    public boolean isAutoPlayVideos() {
        return autoPlayVideos;
    }

    public void setAutoPlayVideos(boolean autoPlayVideos) {
        this.autoPlayVideos = autoPlayVideos;
    }

    public boolean isDownloadOverWifiOnly() {
        return downloadOverWifiOnly;
    }

    public void setDownloadOverWifiOnly(boolean downloadOverWifiOnly) {
        this.downloadOverWifiOnly = downloadOverWifiOnly;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    }

    public int getTotalWorkoutsCompleted() {
        return totalWorkoutsCompleted;
    }

    public void setTotalWorkoutsCompleted(int totalWorkoutsCompleted) {
        this.totalWorkoutsCompleted = totalWorkoutsCompleted;
    }

    public int getTotalCaloriesBurned() {
        return totalCaloriesBurned;
    }

    public void setTotalCaloriesBurned(int totalCaloriesBurned) {
        this.totalCaloriesBurned = totalCaloriesBurned;
    }

    public int getTotalWorkoutMinutes() {
        return totalWorkoutMinutes;
    }

    public void setTotalWorkoutMinutes(int totalWorkoutMinutes) {
        this.totalWorkoutMinutes = totalWorkoutMinutes;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }
}
