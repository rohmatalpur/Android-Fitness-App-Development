package com.example.project.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.project.database.dao.FavoriteWorkoutDao;
import com.example.project.database.dao.UserPreferencesDao;
import com.example.project.database.dao.WorkoutDao;
import com.example.project.database.dao.WorkoutHistoryDao;
import com.example.project.database.entity.FavoriteWorkout;
import com.example.project.database.entity.UserPreferences;
import com.example.project.database.entity.Workout;
import com.example.project.database.entity.WorkoutHistory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
        entities = {
                Workout.class,
                FavoriteWorkout.class,
                WorkoutHistory.class,
                UserPreferences.class
        },
        version = 5,
        exportSchema = false
)
public abstract class FitnessDatabase extends RoomDatabase {

    // DAOs
    public abstract WorkoutDao workoutDao();
    public abstract FavoriteWorkoutDao favoriteWorkoutDao();
    public abstract WorkoutHistoryDao workoutHistoryDao();
    public abstract UserPreferencesDao userPreferencesDao();

    // Singleton instance
    private static volatile FitnessDatabase INSTANCE;

    // Thread pool for database operations
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // Get database instance (Singleton pattern)
    public static FitnessDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (FitnessDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            FitnessDatabase.class,
                            "fitness_database"
                    )
                    .fallbackToDestructiveMigration() // For development - removes this in production
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(androidx.sqlite.db.SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            // Prepopulate database on creation
                            databaseWriteExecutor.execute(() -> {
                                // Initialize default user preferences
                                UserPreferencesDao preferencesDao = INSTANCE.userPreferencesDao();
                                if (preferencesDao.getPreferencesCount() == 0) {
                                    UserPreferences defaultPrefs = new UserPreferences();
                                    defaultPrefs.setUserName("Fitness Enthusiast");
                                    defaultPrefs.setFitnessLevel("beginner");
                                    defaultPrefs.setPreferredWorkoutDuration(20);
                                    defaultPrefs.setNotificationsEnabled(true);
                                    preferencesDao.insert(defaultPrefs);
                                }

                                // Prepopulate workouts
                                WorkoutDao workoutDao = INSTANCE.workoutDao();
                                if (workoutDao.getWorkoutCountSync() == 0) {
                                    prepopulateWorkouts(workoutDao);
                                }
                            });
                        }
                    })
                    .build();
                }
            }
        }
        return INSTANCE;
    }

    // Close database instance (useful for testing)
    public static void closeInstance() {
        if (INSTANCE != null) {
            INSTANCE.close();
            INSTANCE = null;
        }
    }

    // Prepopulate workouts with all the videos from the app
    private static void prepopulateWorkouts(WorkoutDao workoutDao) {
        // Belly workouts (6 items)
        workoutDao.insert(createWorkout("Six-Pack Symphony", "Mind with Muscles",
                "https://www.youtube.com/embed/IyRPOjlKGEw?si=-1zihkTLcE-AsTHz",
                "mind_with_muscle_belly_workout", "belly", "mind_with_muscles"));

        workoutDao.insert(createWorkout("Stellar Abs Strive", "Mukti Gautam",
                "https://www.youtube.com/embed/Eq86UbZLrhk?si=hxGuxWewPJYAZLUv",
                "mukti_gautam_belly_workout", "belly", "mukti_gautam"));

        workoutDao.insert(createWorkout("Belly Blast Bonanza", "MadFit",
                "https://www.youtube.com/embed/hJHkRHpdYTE?si=2vmfAlTub00JG-dz",
                "madfit_belly_workout", "belly", "madfit"));

        workoutDao.insert(createWorkout("Ultimate Belly Burnout", "CulFit",
                "https://www.youtube.com/embed/digpucxFbMo?si=75_Hbkfc8mL-7QWR",
                "cultfit_belly_workout", "belly", "cultfit"));

        workoutDao.insert(createWorkout("Belly Bliss Burn", "The body Project",
                "https://www.youtube.com/embed/rVjSQ6NXAO8?si=CkQJRxntcxCWNHbr",
                "thebodyproject_belly_workout", "belly", "thebodyproject"));

        workoutDao.insert(createWorkout("Midsection Marve", "Healthi Fyme",
                "https://www.youtube.com/embed/QXQ-pAEGcCk?si=XU9-Sikal4qbkEUA",
                "healthifyme_belly_workout", "belly", "healthifyme"));

        // Full Body workouts (6 items)
        workoutDao.insert(createWorkout("Full Body Fury", "Mind with Muscles",
                "https://www.youtube.com/embed/HKYBegMGhQA?si=aKj71mo_5JZBDyyU",
                "mind_with_muscle_fat_loss_workout", "full_body", "mind_with_muscles"));

        workoutDao.insert(createWorkout("Whole Body Whirlwind", "Mukti Gautam",
                "https://www.youtube.com/embed/gGBIFe_ver8?si=y1OHpVEy6JV518ei",
                "mukti_gautam_fat_loss_workout", "full_body", "mukti_gautam"));

        workoutDao.insert(createWorkout("Body Blast Bonanza", "MadFit",
                "https://www.youtube.com/embed/4iy4yEKa7W8?si=iPxfRZREKrup8jUC",
                "madfit_full_body_workout", "full_body", "madfit"));

        workoutDao.insert(createWorkout("Total Transformation Tango", "CulFit",
                "https://www.youtube.com/embed/t9F9gZg42NM?si=kQe7-oNufsagBtHl",
                "cultfit_full_body_workout", "full_body", "cultfit"));

        workoutDao.insert(createWorkout("Intense Integrated Ignition", "The body Project",
                "https://www.youtube.com/embed/xq0Hz3250zI?si=G3p34QQ1pWHqj7WT",
                "thebodyproject_fullbody_workout", "full_body", "thebodyproject"));

        workoutDao.insert(createWorkout("Total Torso Takeover", "Healthi Fyme",
                "https://www.youtube.com/embed/5ARgeR1rMTo?si=eHkkG_cUsFAulYat",
                "healthifyme_fullbody_workout", "full_body", "healthifyme"));

        // Muscle workouts (7 items)
        workoutDao.insert(createWorkout("Muscle Maximization Marathon", "Mind with Muscles",
                "https://www.youtube.com/embed/_yo1kHbCB0Q?si=cvdqx0fYyzeWFRUD",
                "mind_with_muscle_muscle_workout", "muscle", "mind_with_muscles"));

        workoutDao.insert(createWorkout("Iron Grip Inferno", "Mukti Gautam",
                "https://www.youtube.com/embed/ig1O65s5Yws?si=g1PguFqdO5sQnMr3",
                "mukti_gautam_muscle_workout", "muscle", "mukti_gautam"));

        workoutDao.insert(createWorkout("Mighty Muscle Mania", "Mukti Gautam",
                "https://www.youtube.com/embed/crpjaOsjCbw?si=sEipZqBsOZNBdpvL",
                "mukti_gautam_arm_workout", "muscle", "mukti_gautam"));

        workoutDao.insert(createWorkout("Dynamic Muscle Drive", "MadFit",
                "https://www.youtube.com/embed/tUUFn2oK_E0?si=jCjFTXLPw1Q1pjNJ",
                "madfit_muscle_workout", "muscle", "madfit"));

        workoutDao.insert(createWorkout("Mega Muscle Medley", "CulFit",
                "https://www.youtube.com/embed/v_0jdkgwPKE?si=AwCjHNC46K2PJEzC",
                "cultfit_muscle_workout", "muscle", "cultfit"));

        workoutDao.insert(createWorkout("Herculean Hypertrophy", "The body Project",
                "https://www.youtube.com/embed/KY8jENkQpDQ?si=KrfJZ7q5geSvRin0",
                "thebodyproject_muscle_workout", "muscle", "thebodyproject"));

        workoutDao.insert(createWorkout("Anabolic Ascendancy", "Healthi Fyme",
                "https://www.youtube.com/embed/dj03_VDetdw?si=kvqX5Ru587c84ZMs",
                "healthyfime_muscles_workout", "muscle", "healthifyme"));

        // Leg workouts (6 items)
        workoutDao.insert(createWorkout("Legs of Steel Saga", "Mind with Muscles",
                "https://www.youtube.com/embed/HMu6O4bfsqw?si=SQgirzjDHxM4mIAc",
                "mind_with_muscle_leg_workout", "leg", "mind_with_muscles"));

        workoutDao.insert(createWorkout("Thigh Thrust Throwdown", "Mukti Gautam",
                "https://www.youtube.com/embed/rHL0v2NBPSg?si=d_UV7oojPZclpTJl",
                "mukti_gautam_lower_body_workout", "leg", "mukti_gautam"));

        workoutDao.insert(createWorkout("Legs On Fire Frenzy", "MadFit",
                "https://www.youtube.com/embed/FJA3R7n_594?si=esuKV_9posD6JEDn",
                "madfit_leg_workout", "leg", "madfit"));

        workoutDao.insert(createWorkout("Calf Carnage Crusade", "CulFit",
                "https://www.youtube.com/embed/hUwWYIz9f0Y?si=Or6bo925mWj3JLav",
                "cultfit_legs_workout", "leg", "cultfit"));

        workoutDao.insert(createWorkout("Quad Quake Quest", "The body Project",
                "https://www.youtube.com/embed/xsqh63FaYz4?si=PdoZv7J0Ai-XDNg3",
                "thebodyproject_leg_workout", "leg", "thebodyproject"));

        workoutDao.insert(createWorkout("Lower Limb Labyrinth", "Healthi Fyme",
                "https://www.youtube.com/embed/Cv1SJ5qKhJE?si=1e3Ag6Oqm7G8bk36",
                "healthifyme_leg_workout", "leg", "healthifyme"));

        // Chest workouts (5 items)
        workoutDao.insert(createWorkout("Chest Conquest Chronicles", "Mind with Muscles",
                "https://www.youtube.com/embed/l7ig59R_JU0?si=ghY6HQNsdMnwkNS5",
                "mind_with_muscle_chest_workout", "chest", "mind_with_muscles"));

        workoutDao.insert(createWorkout("Upper Body Uprising", "MadFit",
                "https://www.youtube.com/embed/ESkI_WR1qqc?si=SzMeLzWGmXwiWJIv",
                "madfit_chest_workout", "chest", "madfit"));

        workoutDao.insert(createWorkout("Pec Power Play", "CulFit",
                "https://www.youtube.com/embed/P0skdqdirWE?si=arAdl4eK73eJvqlc",
                "cultfit_chest_workout", "chest", "cultfit"));

        workoutDao.insert(createWorkout("Fly & Flex Frenzy", "The body Project",
                "https://www.youtube.com/embed/260e3JKxaNU?si=s0YXWRjITqqwe794",
                "thebodyproject_chest_workout", "chest", "thebodyproject"));

        workoutDao.insert(createWorkout("Chest Comet Cascade", "Healthi Fyme",
                "https://www.youtube.com/embed/YV-oiyU-kEo?si=ON6wRmWvEtUzE363",
                "healthyfime_chest_workout", "chest", "healthifyme"));
    }

    private static Workout createWorkout(String title, String trainerName, String videoUrl,
                                          String thumbnailResource, String category, String trainerCategory) {
        Workout workout = new Workout(title, trainerName, videoUrl, thumbnailResource, category, trainerCategory);
        workout.setDurationMinutes(20); // Default duration
        workout.setDifficultyLevel("intermediate"); // Default difficulty
        return workout;
    }
}
