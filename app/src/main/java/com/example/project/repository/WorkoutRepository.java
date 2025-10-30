package com.example.project.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.project.database.FitnessDatabase;
import com.example.project.database.dao.WorkoutDao;
import com.example.project.database.entity.Workout;

import java.util.List;

public class WorkoutRepository {

    private WorkoutDao workoutDao;
    private LiveData<List<Workout>> allWorkouts;

    public WorkoutRepository(Application application) {
        FitnessDatabase database = FitnessDatabase.getInstance(application);
        workoutDao = database.workoutDao();
        allWorkouts = workoutDao.getAllWorkouts();
    }

    // Insert workout
    public void insert(Workout workout) {
        FitnessDatabase.databaseWriteExecutor.execute(() -> {
            workoutDao.insert(workout);
        });
    }

    // Insert multiple workouts
    public void insertAll(List<Workout> workouts) {
        FitnessDatabase.databaseWriteExecutor.execute(() -> {
            workoutDao.insertAll(workouts);
        });
    }

    // Update workout
    public void update(Workout workout) {
        FitnessDatabase.databaseWriteExecutor.execute(() -> {
            workoutDao.update(workout);
        });
    }

    // Delete workout
    public void delete(Workout workout) {
        FitnessDatabase.databaseWriteExecutor.execute(() -> {
            workoutDao.delete(workout);
        });
    }

    // Delete all workouts
    public void deleteAllWorkouts() {
        FitnessDatabase.databaseWriteExecutor.execute(() -> {
            workoutDao.deleteAllWorkouts();
        });
    }

    // Get all workouts
    public LiveData<List<Workout>> getAllWorkouts() {
        return allWorkouts;
    }

    // Get workout by ID
    public LiveData<Workout> getWorkoutById(int workoutId) {
        return workoutDao.getWorkoutById(workoutId);
    }

    // Get workouts by category
    public LiveData<List<Workout>> getWorkoutsByCategory(String category) {
        return workoutDao.getWorkoutsByCategory(category);
    }

    // Get workouts by trainer
    public LiveData<List<Workout>> getWorkoutsByTrainer(String trainerCategory) {
        return workoutDao.getWorkoutsByTrainer(trainerCategory);
    }

    // Get workouts by difficulty
    public LiveData<List<Workout>> getWorkoutsByDifficulty(String level) {
        return workoutDao.getWorkoutsByDifficulty(level);
    }

    // Search workouts
    public LiveData<List<Workout>> searchWorkouts(String searchQuery) {
        return workoutDao.searchWorkouts(searchQuery);
    }

    // Get workout count
    public LiveData<Integer> getWorkoutCount() {
        return workoutDao.getWorkoutCount();
    }

    // Get all categories
    public LiveData<List<String>> getAllCategories() {
        return workoutDao.getAllCategories();
    }

    // Get all trainers
    public LiveData<List<String>> getAllTrainers() {
        return workoutDao.getAllTrainers();
    }
}
