package com.example.project.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.project.database.FitnessDatabase;
import com.example.project.database.dao.FavoriteWorkoutDao;
import com.example.project.database.entity.FavoriteWorkout;
import com.example.project.database.entity.Workout;

import java.util.List;

public class FavoriteRepository {

    private FavoriteWorkoutDao favoriteWorkoutDao;

    public FavoriteRepository(Application application) {
        FitnessDatabase database = FitnessDatabase.getInstance(application);
        favoriteWorkoutDao = database.favoriteWorkoutDao();
    }

    // Insert favorite
    public void insert(FavoriteWorkout favoriteWorkout) {
        FitnessDatabase.databaseWriteExecutor.execute(() -> {
            favoriteWorkoutDao.insert(favoriteWorkout);
        });
    }

    // Delete favorite
    public void delete(FavoriteWorkout favoriteWorkout) {
        FitnessDatabase.databaseWriteExecutor.execute(() -> {
            favoriteWorkoutDao.delete(favoriteWorkout);
        });
    }

    // Delete by workout ID
    public void deleteByWorkoutId(int workoutId) {
        FitnessDatabase.databaseWriteExecutor.execute(() -> {
            favoriteWorkoutDao.deleteByWorkoutId(workoutId);
        });
    }

    // Toggle favorite (add if not exists, remove if exists)
    public void toggleFavorite(int workoutId) {
        FitnessDatabase.databaseWriteExecutor.execute(() -> {
            FavoriteWorkout existingFavorite = favoriteWorkoutDao.getFavoriteByWorkoutIdSync(workoutId);
            if (existingFavorite != null) {
                favoriteWorkoutDao.delete(existingFavorite);
            } else {
                FavoriteWorkout newFavorite = new FavoriteWorkout(workoutId);
                favoriteWorkoutDao.insert(newFavorite);
            }
        });
    }

    // Get all favorites
    public LiveData<List<FavoriteWorkout>> getAllFavorites() {
        return favoriteWorkoutDao.getAllFavorites();
    }

    // Get favorite by workout ID
    public LiveData<FavoriteWorkout> getFavoriteByWorkoutId(int workoutId) {
        return favoriteWorkoutDao.getFavoriteByWorkoutId(workoutId);
    }

    // Check if workout is favorite
    public LiveData<Boolean> isFavorite(int workoutId) {
        return favoriteWorkoutDao.isFavorite(workoutId);
    }

    // Get favorite workouts with details
    public LiveData<List<Workout>> getFavoriteWorkoutsWithDetails() {
        return favoriteWorkoutDao.getFavoriteWorkoutsWithDetails();
    }

    // Get favorite count
    public LiveData<Integer> getFavoriteCount() {
        return favoriteWorkoutDao.getFavoriteCount();
    }

    // Delete all favorites
    public void deleteAllFavorites() {
        FitnessDatabase.databaseWriteExecutor.execute(() -> {
            favoriteWorkoutDao.deleteAllFavorites();
        });
    }
}
