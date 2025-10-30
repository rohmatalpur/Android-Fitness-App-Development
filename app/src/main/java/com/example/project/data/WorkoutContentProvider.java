package com.example.project.data;

import com.example.project.model.WorkoutCategory;
import com.example.project.model.WorkoutContent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WorkoutContentProvider {

    public static List<WorkoutContent> getAllWorkouts() {
        List<WorkoutContent> workouts = new ArrayList<>();

        // Yoga & Stretching
        WorkoutContent yoga1 = new WorkoutContent("yoga1", "Morning Yoga Flow",
                "Start your day with energizing yoga poses", "Sarah Johnson",
                "v7AYKMP6rOE", 20, "Beginner");
        yoga1.addCategory(WorkoutCategory.YOGA);
        yoga1.addCategory(WorkoutCategory.FLEXIBILITY);
        yoga1.addCategory(WorkoutCategory.EXPRESS_10MIN);
        yoga1.addCategory(WorkoutCategory.NO_EQUIPMENT);
        yoga1.addCategory(WorkoutCategory.YOGA_MAT);
        yoga1.setCaloriesBurn(100);
        workouts.add(yoga1);

        // Martial Arts
        WorkoutContent martial1 = new WorkoutContent("martial1", "Kickboxing Cardio",
                "Punch and kick your way to fitness", "HASfit",
                "j64BBgBGNIU", 30, "Intermediate");
        martial1.addCategory(WorkoutCategory.MARTIAL_ARTS);
        martial1.addCategory(WorkoutCategory.CARDIO);
        martial1.addCategory(WorkoutCategory.WEIGHT_LOSS);
        martial1.addCategory(WorkoutCategory.SESSION_30MIN);
        martial1.addCategory(WorkoutCategory.NO_EQUIPMENT);
        martial1.setCaloriesBurn(350);
        workouts.add(martial1);

        // HIIT Workouts
        WorkoutContent hiit2 = new WorkoutContent("hiit2", "10-Minute Morning HIIT",
                "Energize your morning with quick HIIT", "Jessica Brown",
                "gC_L9qAHVJ8", 10, "Intermediate");
        hiit2.addCategory(WorkoutCategory.HIIT);
        hiit2.addCategory(WorkoutCategory.CARDIO);
        hiit2.addCategory(WorkoutCategory.GENERAL_FITNESS);
        hiit2.addCategory(WorkoutCategory.EXPRESS_10MIN);
        hiit2.addCategory(WorkoutCategory.NO_EQUIPMENT);
        hiit2.setCaloriesBurn(120);
        workouts.add(hiit2);

        // Meditation & Cooldown
        WorkoutContent meditation1 = new WorkoutContent("meditation1", "5-Min Meditation",
                "Quick mindfulness meditation for busy days", "Dr. Peace",
                "inpok4MKVLM", 5, "Beginner");
        meditation1.addCategory(WorkoutCategory.MEDITATION);
        meditation1.addCategory(WorkoutCategory.FLEXIBILITY);
        meditation1.addCategory(WorkoutCategory.QUICK_5MIN);
        meditation1.addCategory(WorkoutCategory.NO_EQUIPMENT);
        meditation1.addCategory(WorkoutCategory.INJURY_RECOVERY);
        meditation1.setCaloriesBurn(20);
        workouts.add(meditation1);

        // Bodyweight Workouts
        WorkoutContent bodyweight1 = new WorkoutContent("bodyweight1", "30-Day Pushup Challenge",
                "Build upper body strength progressively", "Chris Heria",
                "IODxDxX7oi4", 15, "Intermediate");
        bodyweight1.addCategory(WorkoutCategory.STRENGTH);
        bodyweight1.addCategory(WorkoutCategory.MUSCLE_BUILDING);
        bodyweight1.addCategory(WorkoutCategory.EXPRESS_10MIN);
        bodyweight1.addCategory(WorkoutCategory.BODYWEIGHT);
        bodyweight1.addCategory(WorkoutCategory.NO_EQUIPMENT);
        bodyweight1.setCaloriesBurn(150);
        workouts.add(bodyweight1);

        return workouts;
    }

    public static List<WorkoutContent> getWorkoutsByCategory(WorkoutCategory category) {
        List<WorkoutContent> allWorkouts = getAllWorkouts();
        List<WorkoutContent> filtered = new ArrayList<>();

        for (WorkoutContent workout : allWorkouts) {
            if (workout.getCategories().contains(category)) {
                filtered.add(workout);
            }
        }

        return filtered;
    }

    public static List<WorkoutContent> getWorkoutsByDuration(int minMinutes, int maxMinutes) {
        List<WorkoutContent> allWorkouts = getAllWorkouts();
        List<WorkoutContent> filtered = new ArrayList<>();

        for (WorkoutContent workout : allWorkouts) {
            int duration = workout.getDurationMinutes();
            if (duration >= minMinutes && duration <= maxMinutes) {
                filtered.add(workout);
            }
        }

        return filtered;
    }

    public static List<WorkoutContent> getWorkoutsByDifficulty(String difficulty) {
        List<WorkoutContent> allWorkouts = getAllWorkouts();
        List<WorkoutContent> filtered = new ArrayList<>();

        for (WorkoutContent workout : allWorkouts) {
            if (workout.getDifficulty().equalsIgnoreCase(difficulty)) {
                filtered.add(workout);
            }
        }

        return filtered;
    }
}
