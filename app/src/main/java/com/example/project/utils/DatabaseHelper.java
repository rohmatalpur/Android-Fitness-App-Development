package com.example.project.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.project.database.FitnessDatabase;
import com.example.project.database.entity.Workout;

import java.util.List;

/**
 * Helper class to diagnose and fix database initialization issues
 */
public class DatabaseHelper {
    private static final String TAG = "DatabaseHelper";

    /**
     * Check if database is properly populated with workouts
     */
    public static boolean isDatabasePopulated(Context context, boolean showToast) {
        FitnessDatabase database = FitnessDatabase.getInstance(context);

        final boolean[] hasWorkouts = {false};
        final int[] workoutCount = {0};

        Thread checkThread = new Thread(() -> {
            try {
                workoutCount[0] = database.workoutDao().getWorkoutCountSync();
                hasWorkouts[0] = workoutCount[0] > 0;

                Log.d(TAG, "Database workout count: " + workoutCount[0]);

                if (showToast) {
                    android.os.Handler mainHandler = new android.os.Handler(context.getMainLooper());
                    mainHandler.post(() -> {
                        String message = hasWorkouts[0] ?
                            "Database OK: " + workoutCount[0] + " workouts found" :
                            "Database empty: Initializing workouts...";
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                    });
                }
            } catch (Exception e) {
                Log.e(TAG, "Error checking database", e);
            }
        });

        checkThread.start();
        try {
            checkThread.join();
        } catch (InterruptedException e) {
            Log.e(TAG, "Thread interrupted", e);
        }

        return hasWorkouts[0];
    }

    /**
     * Log all workouts by category for debugging
     */
    public static void logWorkoutsByCategory(Context context, String category) {
        FitnessDatabase database = FitnessDatabase.getInstance(context);

        FitnessDatabase.databaseWriteExecutor.execute(() -> {
            List<Workout> workouts = database.workoutDao().getWorkoutsByCategorySync(category);

            Log.d(TAG, "=== Workouts for category: " + category + " ===");
            Log.d(TAG, "Total count: " + workouts.size());

            for (Workout workout : workouts) {
                Log.d(TAG, String.format("ID=%d | Title=%s | Trainer=%s | Video=%s",
                    workout.getId(),
                    workout.getTitle(),
                    workout.getTrainerName(),
                    workout.getVideoUrl()));
            }
            Log.d(TAG, "=================================");
        });
    }
}
