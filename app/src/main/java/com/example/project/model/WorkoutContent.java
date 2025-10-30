package com.example.project.model;

import java.util.ArrayList;
import java.util.List;

public class WorkoutContent {
    private String id;
    private String title;
    private String description;
    private String trainer;
    private String thumbnailUrl;
    private String videoId;
    private int durationMinutes;
    private String difficulty; // Beginner, Intermediate, Advanced
    private List<WorkoutCategory> categories;
    private int caloriesBurn;

    public WorkoutContent() {
        this.categories = new ArrayList<>();
    }

    public WorkoutContent(String id, String title, String description, String trainer,
                          String videoId, int durationMinutes, String difficulty) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.trainer = trainer;
        this.videoId = videoId;
        this.durationMinutes = durationMinutes;
        this.difficulty = difficulty;
        this.categories = new ArrayList<>();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getTrainer() { return trainer; }
    public void setTrainer(String trainer) { this.trainer = trainer; }

    public String getThumbnailUrl() { return thumbnailUrl; }
    public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }

    public String getVideoId() { return videoId; }
    public void setVideoId(String videoId) { this.videoId = videoId; }

    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public List<WorkoutCategory> getCategories() { return categories; }
    public void setCategories(List<WorkoutCategory> categories) { this.categories = categories; }

    public void addCategory(WorkoutCategory category) {
        if (!this.categories.contains(category)) {
            this.categories.add(category);
        }
    }

    public int getCaloriesBurn() { return caloriesBurn; }
    public void setCaloriesBurn(int caloriesBurn) { this.caloriesBurn = caloriesBurn; }

    public String getDurationDisplay() {
        if (durationMinutes < 60) {
            return durationMinutes + " min";
        } else {
            int hours = durationMinutes / 60;
            int mins = durationMinutes % 60;
            return hours + "h " + (mins > 0 ? mins + "m" : "");
        }
    }

    public String getCategoriesString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < categories.size(); i++) {
            sb.append(categories.get(i).getDisplayName());
            if (i < categories.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}
