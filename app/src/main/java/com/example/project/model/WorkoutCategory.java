package com.example.project.model;

public enum WorkoutCategory {
    // Workout Types
    CARDIO("Cardio", "🏃", WorkoutCategoryType.WORKOUT_TYPE),
    STRENGTH("Strength Training", "💪", WorkoutCategoryType.WORKOUT_TYPE),
    YOGA("Yoga & Stretching", "🧘", WorkoutCategoryType.WORKOUT_TYPE),
    PILATES("Pilates", "🤸", WorkoutCategoryType.WORKOUT_TYPE),
    DANCE("Dance Fitness", "💃", WorkoutCategoryType.WORKOUT_TYPE),
    MARTIAL_ARTS("Martial Arts", "🥋", WorkoutCategoryType.WORKOUT_TYPE),
    CROSSFIT("CrossFit", "🏋️", WorkoutCategoryType.WORKOUT_TYPE),
    CYCLING("Cycling/Spinning", "🚴", WorkoutCategoryType.WORKOUT_TYPE),
    RUNNING("Running/Walking", "🏃‍♀️", WorkoutCategoryType.WORKOUT_TYPE),
    MEDITATION("Meditation & Cooldown", "🧘‍♂️", WorkoutCategoryType.WORKOUT_TYPE),
    HIIT("HIIT", "⚡", WorkoutCategoryType.WORKOUT_TYPE),

    // Programs by Goal
    WEIGHT_LOSS("Weight Loss", "⚖️", WorkoutCategoryType.GOAL),
    MUSCLE_BUILDING("Muscle Building", "💪", WorkoutCategoryType.GOAL),
    FLEXIBILITY("Flexibility", "🤸‍♀️", WorkoutCategoryType.GOAL),
    ATHLETIC_PERFORMANCE("Athletic Performance", "🏆", WorkoutCategoryType.GOAL),
    INJURY_RECOVERY("Injury Recovery", "🩹", WorkoutCategoryType.GOAL),
    GENERAL_FITNESS("General Fitness", "🎯", WorkoutCategoryType.GOAL),
    ENDURANCE("Endurance", "🔋", WorkoutCategoryType.GOAL),

    // Duration-Based
    QUICK_5MIN("5-Minute Quick", "⏱️", WorkoutCategoryType.DURATION),
    EXPRESS_10MIN("10-Minute Express", "⏰", WorkoutCategoryType.DURATION),
    SESSION_30MIN("30-Minute Session", "🕐", WorkoutCategoryType.DURATION),
    HOUR_LONG("Hour-Long Endurance", "🕒", WorkoutCategoryType.DURATION),
    CUSTOM_DURATION("Custom Duration", "⏲️", WorkoutCategoryType.DURATION),

    // Equipment Categories
    NO_EQUIPMENT("No Equipment", "🙌", WorkoutCategoryType.EQUIPMENT),
    DUMBBELLS("Dumbbells", "🏋️", WorkoutCategoryType.EQUIPMENT),
    RESISTANCE_BANDS("Resistance Bands", "🎀", WorkoutCategoryType.EQUIPMENT),
    KETTLEBELLS("Kettlebells", "⚙️", WorkoutCategoryType.EQUIPMENT),
    HOME_GYM("Home Gym Equipment", "🏠", WorkoutCategoryType.EQUIPMENT),
    BODYWEIGHT("Bodyweight Only", "🧍", WorkoutCategoryType.EQUIPMENT),
    YOGA_MAT("Yoga Mat", "🧘", WorkoutCategoryType.EQUIPMENT),
    FULL_GYM("Full Gym Access", "🏢", WorkoutCategoryType.EQUIPMENT);

    private final String displayName;
    private final String emoji;
    private final WorkoutCategoryType type;

    WorkoutCategory(String displayName, String emoji, WorkoutCategoryType type) {
        this.displayName = displayName;
        this.emoji = emoji;
        this.type = type;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmoji() {
        return emoji;
    }

    public WorkoutCategoryType getType() {
        return type;
    }

    public String getDisplayWithEmoji() {
        return emoji + " " + displayName;
    }

    public enum WorkoutCategoryType {
        WORKOUT_TYPE("Workout Type"),
        GOAL("By Goal"),
        DURATION("By Duration"),
        EQUIPMENT("By Equipment");

        private final String displayName;

        WorkoutCategoryType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}
