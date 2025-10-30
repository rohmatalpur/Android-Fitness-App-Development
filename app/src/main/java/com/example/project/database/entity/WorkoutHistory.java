package com.example.project.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "workout_history",
        indices = {@Index("workout_id")})
public class WorkoutHistory {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "workout_id")
    private int workoutId;

    @ColumnInfo(name = "completed_at")
    private long completedAt;

    @ColumnInfo(name = "duration_minutes")
    private int durationMinutes;

    @ColumnInfo(name = "calories_burned")
    private int caloriesBurned;

    @ColumnInfo(name = "completion_percentage")
    private int completionPercentage;

    @ColumnInfo(name = "rating")
    private float rating;

    @ColumnInfo(name = "notes")
    private String notes;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "video_position")
    private int videoPosition;

    @ColumnInfo(name = "actual_workout_time")
    private int actualWorkoutTime;

    @ColumnInfo(name = "workout_started_at")
    private long workoutStartedAt;

    @ColumnInfo(name = "workout_type")
    private String workoutType;

    @ColumnInfo(name = "video_url")
    private String videoUrl;

    @ColumnInfo(name = "trainer_name")
    private String trainerName;

    @ColumnInfo(name = "thumbnail_url")
    private String thumbnailUrl;

    // Constructors
    public WorkoutHistory() {
        this.completedAt = System.currentTimeMillis();
    }

    public WorkoutHistory(int workoutId, int durationMinutes) {
        this.workoutId = workoutId;
        this.durationMinutes = durationMinutes;
        this.completedAt = System.currentTimeMillis();
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(int workoutId) {
        this.workoutId = workoutId;
    }

    public long getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(long completedAt) {
        this.completedAt = completedAt;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(int caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public int getCompletionPercentage() {
        return completionPercentage;
    }

    public void setCompletionPercentage(int completionPercentage) {
        this.completionPercentage = completionPercentage;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getVideoPosition() {
        return videoPosition;
    }

    public void setVideoPosition(int videoPosition) {
        this.videoPosition = videoPosition;
    }

    public int getActualWorkoutTime() {
        return actualWorkoutTime;
    }

    public void setActualWorkoutTime(int actualWorkoutTime) {
        this.actualWorkoutTime = actualWorkoutTime;
    }

    public long getWorkoutStartedAt() {
        return workoutStartedAt;
    }

    public void setWorkoutStartedAt(long workoutStartedAt) {
        this.workoutStartedAt = workoutStartedAt;
    }

    public String getWorkoutType() {
        return workoutType;
    }

    public void setWorkoutType(String workoutType) {
        this.workoutType = workoutType;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
