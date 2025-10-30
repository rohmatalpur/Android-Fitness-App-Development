package com.example.project.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_workouts",
        indices = {@Index("workout_id")})
public class FavoriteWorkout {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "workout_id")
    private int workoutId;

    @ColumnInfo(name = "added_at")
    private long addedAt;

    @ColumnInfo(name = "notes")
    private String notes;

    // Store workout details directly
    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "trainer")
    private String trainer;

    @ColumnInfo(name = "video_url")
    private String videoUrl;

    @ColumnInfo(name = "thumbnail")
    private String thumbnail;

    // Constructors
    public FavoriteWorkout() {
        this.addedAt = System.currentTimeMillis();
    }

    public FavoriteWorkout(int workoutId) {
        this.workoutId = workoutId;
        this.addedAt = System.currentTimeMillis();
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

    public long getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(long addedAt) {
        this.addedAt = addedAt;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
