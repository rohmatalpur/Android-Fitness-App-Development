package com.example.project.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.project.database.entity.UserPreferences;

@Dao
public interface UserPreferencesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(UserPreferences userPreferences);

    @Update
    void update(UserPreferences userPreferences);

    @Delete
    void delete(UserPreferences userPreferences);

    @Query("SELECT * FROM user_preferences LIMIT 1")
    LiveData<UserPreferences> getUserPreferences();

    @Query("SELECT * FROM user_preferences LIMIT 1")
    UserPreferences getUserPreferencesSync();

    @Query("UPDATE user_preferences SET current_streak = :streak WHERE id = (SELECT id FROM user_preferences LIMIT 1)")
    void updateStreak(int streak);

    @Query("UPDATE user_preferences SET total_workouts_completed = total_workouts_completed + 1 WHERE id = (SELECT id FROM user_preferences LIMIT 1)")
    void incrementTotalWorkouts();

    @Query("UPDATE user_preferences SET total_calories_burned = total_calories_burned + :calories WHERE id = (SELECT id FROM user_preferences LIMIT 1)")
    void addCaloriesBurned(int calories);

    @Query("UPDATE user_preferences SET total_workout_minutes = total_workout_minutes + :minutes WHERE id = (SELECT id FROM user_preferences LIMIT 1)")
    void addWorkoutMinutes(int minutes);

    @Query("UPDATE user_preferences SET notifications_enabled = :enabled WHERE id = (SELECT id FROM user_preferences LIMIT 1)")
    void updateNotificationsEnabled(boolean enabled);

    @Query("UPDATE user_preferences SET dark_mode_enabled = :enabled WHERE id = (SELECT id FROM user_preferences LIMIT 1)")
    void updateDarkMode(boolean enabled);

    @Query("UPDATE user_preferences SET updated_at = :timestamp WHERE id = (SELECT id FROM user_preferences LIMIT 1)")
    void updateTimestamp(long timestamp);

    @Query("DELETE FROM user_preferences")
    void deleteAll();

    @Query("SELECT COUNT(*) FROM user_preferences")
    int getPreferencesCount();
}
