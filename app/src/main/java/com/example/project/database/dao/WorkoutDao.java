package com.example.project.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.project.database.entity.Workout;

import java.util.List;

@Dao
public interface WorkoutDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Workout workout);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Workout> workouts);

    @Update
    void update(Workout workout);

    @Delete
    void delete(Workout workout);

    @Query("SELECT * FROM workouts ORDER BY created_at DESC")
    LiveData<List<Workout>> getAllWorkouts();

    @Query("SELECT * FROM workouts WHERE id = :workoutId")
    LiveData<Workout> getWorkoutById(int workoutId);

    @Query("SELECT * FROM workouts WHERE id = :workoutId")
    Workout getWorkoutByIdSync(int workoutId);

    @Query("SELECT * FROM workouts WHERE category = :category ORDER BY created_at DESC")
    LiveData<List<Workout>> getWorkoutsByCategory(String category);

    @Query("SELECT * FROM workouts WHERE category = :category ORDER BY id ASC")
    List<Workout> getWorkoutsByCategorySync(String category);

    @Query("SELECT * FROM workouts WHERE trainer_category = :trainerCategory ORDER BY created_at DESC")
    LiveData<List<Workout>> getWorkoutsByTrainer(String trainerCategory);

    @Query("SELECT * FROM workouts WHERE trainer_category = :trainerCategory ORDER BY id ASC")
    List<Workout> getWorkoutsByTrainerSync(String trainerCategory);

    @Query("SELECT * FROM workouts WHERE difficulty_level = :level ORDER BY created_at DESC")
    LiveData<List<Workout>> getWorkoutsByDifficulty(String level);

    @Query("SELECT * FROM workouts WHERE title LIKE '%' || :searchQuery || '%' OR trainer_name LIKE '%' || :searchQuery || '%'")
    LiveData<List<Workout>> searchWorkouts(String searchQuery);

    @Query("DELETE FROM workouts")
    void deleteAllWorkouts();

    @Query("SELECT COUNT(*) FROM workouts")
    LiveData<Integer> getWorkoutCount();

    @Query("SELECT COUNT(*) FROM workouts")
    int getWorkoutCountSync();

    @Query("SELECT DISTINCT category FROM workouts")
    LiveData<List<String>> getAllCategories();

    @Query("SELECT DISTINCT trainer_category FROM workouts")
    LiveData<List<String>> getAllTrainers();
@Query("SELECT * FROM workouts ORDER BY created_at DESC")    List<Workout> getAllWorkoutsSync();
}
