package com.example.project.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.project.database.entity.FavoriteWorkout;
import com.example.project.database.entity.Workout;

import java.util.List;

@Dao
public interface FavoriteWorkoutDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(FavoriteWorkout favoriteWorkout);

    @Update
    void update(FavoriteWorkout favoriteWorkout);

    @Delete
    void delete(FavoriteWorkout favoriteWorkout);

    @Query("DELETE FROM favorite_workouts WHERE workout_id = :workoutId")
    void deleteByWorkoutId(int workoutId);

    @Query("SELECT * FROM favorite_workouts ORDER BY added_at DESC")
    LiveData<List<FavoriteWorkout>> getAllFavorites();

    @Query("SELECT * FROM favorite_workouts WHERE workout_id = :workoutId LIMIT 1")
    LiveData<FavoriteWorkout> getFavoriteByWorkoutId(int workoutId);

    @Query("SELECT * FROM favorite_workouts WHERE workout_id = :workoutId LIMIT 1")
    FavoriteWorkout getFavoriteByWorkoutIdSync(int workoutId);

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_workouts WHERE workout_id = :workoutId)")
    LiveData<Boolean> isFavorite(int workoutId);

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_workouts WHERE workout_id = :workoutId)")
    boolean isFavoriteSync(int workoutId);

    @Query("SELECT w.* FROM workouts w INNER JOIN favorite_workouts f ON w.id = f.workout_id ORDER BY f.added_at DESC")
    LiveData<List<Workout>> getFavoriteWorkoutsWithDetails();

    // Get all favorite workouts directly (no join needed)
    @Query("SELECT * FROM favorite_workouts ORDER BY added_at DESC")
    LiveData<List<FavoriteWorkout>> getAllFavoriteWorkouts();

    @Query("SELECT COUNT(*) FROM favorite_workouts")
    LiveData<Integer> getFavoriteCount();

    @Query("DELETE FROM favorite_workouts")
    void deleteAllFavorites();
}
