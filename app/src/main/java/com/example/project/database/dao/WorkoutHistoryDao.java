package com.example.project.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Embedded;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Relation;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.project.database.entity.Workout;
import com.example.project.database.entity.WorkoutHistory;

import java.util.List;

@Dao
public interface WorkoutHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(WorkoutHistory workoutHistory);

    @Update
    void update(WorkoutHistory workoutHistory);

    @Delete
    void delete(WorkoutHistory workoutHistory);

    @Query("SELECT * FROM workout_history ORDER BY completed_at DESC")
    LiveData<List<WorkoutHistory>> getAllHistory();

    @Query("SELECT * FROM workout_history ORDER BY completed_at DESC")
    List<WorkoutHistory> getAllHistorySync();

    @Query("SELECT * FROM workout_history WHERE workout_id = :workoutId ORDER BY completed_at DESC")
    LiveData<List<WorkoutHistory>> getHistoryByWorkoutId(int workoutId);

    @Query("SELECT * FROM workout_history WHERE workout_id = :workoutId ORDER BY completed_at DESC LIMIT 1")
    WorkoutHistory getLastHistoryForWorkoutSync(int workoutId);

    @Query("SELECT * FROM workout_history WHERE workout_id = :workoutId AND date = :date ORDER BY completed_at DESC LIMIT 1")
    WorkoutHistory getTodayHistoryForWorkoutSync(int workoutId, String date);

    @Query("SELECT * FROM workout_history WHERE date = :date ORDER BY completed_at DESC")
    LiveData<List<WorkoutHistory>> getHistoryByDate(String date);

    @Query("SELECT * FROM workout_history WHERE date BETWEEN :startDate AND :endDate ORDER BY completed_at DESC")
    LiveData<List<WorkoutHistory>> getHistoryByDateRange(String startDate, String endDate);

    @Query("SELECT COUNT(*) FROM workout_history")
    LiveData<Integer> getTotalWorkoutsCompleted();

    @Query("SELECT SUM(duration_minutes) FROM workout_history")
    LiveData<Integer> getTotalWorkoutMinutes();

    @Query("SELECT SUM(calories_burned) FROM workout_history")
    LiveData<Integer> getTotalCaloriesBurned();

    @Query("SELECT AVG(rating) FROM workout_history WHERE rating > 0")
    LiveData<Float> getAverageRating();

    @Query("SELECT * FROM workout_history WHERE completed_at >= :timestamp ORDER BY completed_at DESC")
    LiveData<List<WorkoutHistory>> getRecentHistory(long timestamp);

    @Query("SELECT date, COUNT(*) as count FROM workout_history GROUP BY date ORDER BY date DESC LIMIT 30")
    LiveData<List<DailyWorkoutCount>> getLast30DaysActivity();

    @Query("SELECT COUNT(DISTINCT date) FROM workout_history WHERE date >= :startDate")
    LiveData<Integer> getWorkoutDaysCount(String startDate);

    @Query("DELETE FROM workout_history")
    void deleteAllHistory();

    @Query("DELETE FROM workout_history WHERE completed_at < :timestamp")
    void deleteOldHistory(long timestamp);

    // Data class for daily workout count
    class DailyWorkoutCount {
        public String date;
        public int count;
    }

    // Data class for history with workout details
    class HistoryWithWorkout {
        @Embedded
        public WorkoutHistory history;

        @Relation(
            parentColumn = "workout_id",
            entityColumn = "id"
        )
        public Workout workout;
    }

    @Transaction
    @Query("SELECT * FROM workout_history ORDER BY completed_at DESC")
    LiveData<List<HistoryWithWorkout>> getAllHistoryWithWorkouts();

    @Transaction
    @Query("SELECT * FROM workout_history ORDER BY completed_at DESC LIMIT 1")
    HistoryWithWorkout getLastWorkoutWithDetails();
}
