package com.example.project.worker;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.project.database.FitnessDatabase;
import com.example.project.database.dao.UserPreferencesDao;
import com.example.project.database.dao.WorkoutHistoryDao;
import com.example.project.database.entity.UserPreferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Worker class to update user's workout streak
 * Runs daily to check if user completed a workout yesterday
 * If not, resets the streak to 0
 */
public class StreakUpdateWorker extends Worker {

    private static final String TAG = "StreakUpdateWorker";

    public StreakUpdateWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            Log.d(TAG, "Starting streak update...");

            FitnessDatabase database = FitnessDatabase.getInstance(getApplicationContext());
            UserPreferencesDao preferencesDao = database.userPreferencesDao();
            WorkoutHistoryDao historyDao = database.workoutHistoryDao();

            UserPreferences preferences = preferencesDao.getUserPreferencesSync();

            if (preferences != null) {
                // Check if user worked out yesterday
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_YEAR, -1);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String yesterdayDate = dateFormat.format(calendar.getTime());

                // This is a simplified check - you'd need to implement proper streak logic
                // For now, we'll just log the check
                Log.d(TAG, "Checking workout for date: " + yesterdayDate);

                // TODO: Implement proper streak calculation
                // Check if there's a workout in history for yesterday
                // If yes, keep or increment streak
                // If no, reset streak to 0

                Log.d(TAG, "Streak update completed");
            }

            return Result.success();

        } catch (Exception e) {
            Log.e(TAG, "Error updating streak: " + e.getMessage(), e);
            return Result.failure();
        }
    }
}
