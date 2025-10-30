package com.example.project.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "workouts")
public class Workout {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "trainer_name")
    private String trainerName;

    @ColumnInfo(name = "video_url")
    private String videoUrl;

    @ColumnInfo(name = "thumbnail_resource")
    private String thumbnailResource;

    @ColumnInfo(name = "category")
    private String category; // belly, chest, full_body, legs, muscle

    @ColumnInfo(name = "trainer_category")
    private String trainerCategory; // mukti_gautam, mind_with_muscles, etc.

    @ColumnInfo(name = "duration_minutes")
    private int durationMinutes;

    @ColumnInfo(name = "difficulty_level")
    private String difficultyLevel; // beginner, intermediate, advanced

    @ColumnInfo(name = "created_at")
    private long createdAt;

    // Constructors
    public Workout() {
        this.createdAt = System.currentTimeMillis();
    }

    public Workout(String title, String trainerName, String videoUrl, String thumbnailResource,
                   String category, String trainerCategory) {
        this.title = title;
        this.trainerName = trainerName;
        this.videoUrl = videoUrl;
        this.thumbnailResource = thumbnailResource;
        this.category = category;
        this.trainerCategory = trainerCategory;
        this.createdAt = System.currentTimeMillis();
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getThumbnailResource() {
        return thumbnailResource;
    }

    public void setThumbnailResource(String thumbnailResource) {
        this.thumbnailResource = thumbnailResource;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTrainerCategory() {
        return trainerCategory;
    }

    public void setTrainerCategory(String trainerCategory) {
        this.trainerCategory = trainerCategory;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
