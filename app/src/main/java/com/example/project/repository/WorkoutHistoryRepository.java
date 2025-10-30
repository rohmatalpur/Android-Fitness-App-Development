package com.example.project.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.project.database.FitnessDatabase;
import com.example.project.database.dao.WorkoutHistoryDao;
import com.example.project.database.entity.WorkoutHistory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WorkoutHistoryRepository {

    private WorkoutHistoryDao workoutHistoryDao;

    public WorkoutHistoryRepository(Application application) {
        FitnessDatabase database = FitnessDatabase.getInstance(application);
        workoutHistoryDao = database.workoutHistoryDao();
    }

    // Insert workout history
    public void insert(WorkoutHistory workoutHistory) {
        FitnessDatabase.databaseWriteExecutor.execute(() -> {
            // Set date in YYYY-MM-DD format if not already set
            if (workoutHistory.getDate() == null || workoutHistory.getDate().isEmpty()) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                workoutHistory.setDate(dateFormat.format(new Date()));
            }
            workoutHistoryDao.insert(workoutHistory);
        });
    }

    // Update workout history
    public void update(WorkoutHistory workoutHistory) {
        FitnessDatabase.databaseWriteExecutor.execute(() -> {
            workoutHistoryDao.update(workoutHistory);
        });
    }

    // Delete workout history
    public void delete(WorkoutHistory workoutHistory) {
        FitnessDatabase.databaseWriteExecutor.execute(() -> {
            workoutHistoryDao.delete(workoutHistory);
        });
    }

    // Get all history
    public LiveData<List<WorkoutHistory>> getAllHistory() {
        return workoutHistoryDao.getAllHistory();
    }

    // Get all history with workout details
    public LiveData<List<WorkoutHistoryDao.HistoryWithWorkout>> getAllHistoryWithWorkouts() {
        return workoutHistoryDao.getAllHistoryWithWorkouts();
    }

    // Get history by workout ID
    public LiveData<List<WorkoutHistory>> getHistoryByWorkoutId(int workoutId) {
        return workoutHistoryDao.getHistoryByWorkoutId(workoutId);
    }

    // Get history by date
    public LiveData<List<WorkoutHistory>> getHistoryByDate(String date) {
        return workoutHistoryDao.getHistoryByDate(date);
    }

    // Get history by date range
    public LiveData<List<WorkoutHistory>> getHistoryByDateRange(String startDate, String endDate) {
        return workoutHistoryDao.getHistoryByDateRange(startDate, endDate);
    }

    // Get total workouts completed
    public LiveData<Integer> getTotalWorkoutsCompleted() {
        return workoutHistoryDao.getTotalWorkoutsCompleted();
    }

    // Get total workout minutes
    public LiveData<Integer> getTotalWorkoutMinutes() {
        return workoutHistoryDao.getTotalWorkoutMinutes();
    }

    // Get total calories burned
    public LiveData<Integer> getTotalCaloriesBurned() {
        return workoutHistoryDao.getTotalCaloriesBurned();
    }

    // Get average rating
    public LiveData<Float> getAverageRating() {
        return workoutHistoryDao.getAverageRating();
    }

    // Get recent history (last 7 days)
    public LiveData<List<WorkoutHistory>> getRecentHistory() {
        long sevenDaysAgo = System.currentTimeMillis() - (7L * 24 * 60 * 60 * 1000);
        return workoutHistoryDao.getRecentHistory(sevenDaysAgo);
    }

    // Get last 30 days activity
    public LiveData<List<WorkoutHistoryDao.DailyWorkoutCount>> getLast30DaysActivity() {
        return workoutHistoryDao.getLast30DaysActivity();
    }

    // Get workout days count
    public LiveData<Integer> getWorkoutDaysCount(String startDate) {
        return workoutHistoryDao.getWorkoutDaysCount(startDate);
    }

    // Delete all history
    public void deleteAllHistory() {
        FitnessDatabase.databaseWriteExecutor.execute(() -> {
            workoutHistoryDao.deleteAllHistory();
        });
    }

    // Delete old history (older than specified days)
    public void deleteOldHistory(int daysToKeep) {
        FitnessDatabase.databaseWriteExecutor.execute(() -> {
            long cutoffTime = System.currentTimeMillis() - ((long) daysToKeep * 24 * 60 * 60 * 1000);
            workoutHistoryDao.deleteOldHistory(cutoffTime);
        });
    }
}
