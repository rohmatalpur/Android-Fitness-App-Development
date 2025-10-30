package com.example.project.adapters;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project.R;
import com.example.project.database.FitnessDatabase;
import com.example.project.database.entity.FavoriteWorkout;
import com.example.project.model.WorkoutContent;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class WorkoutContentAdapter extends RecyclerView.Adapter<WorkoutContentAdapter.WorkoutViewHolder> {

    private List<WorkoutContent> workouts;
    private OnWorkoutClickListener listener;
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public interface OnWorkoutClickListener {
        void onWorkoutClick(WorkoutContent workout);
    }

    public WorkoutContentAdapter(List<WorkoutContent> workouts, OnWorkoutClickListener listener) {
        this.workouts = workouts;
        this.listener = listener;
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_workout_content, parent, false);
        return new WorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        WorkoutContent workout = workouts.get(position);
        holder.bind(workout, listener, position, this);
    }

    @Override
    public int getItemCount() {
        return workouts.size();
    }

    private void toggleFavorite(int position, ImageButton favoriteButton, View itemView) {
        WorkoutContent workout = workouts.get(position);
        FitnessDatabase database = FitnessDatabase.getInstance(itemView.getContext());

        FitnessDatabase.databaseWriteExecutor.execute(() -> {
            // Use hash of workout ID as unique identifier
            int workoutId = workout.getId().hashCode();

            FavoriteWorkout existingFavorite = database.favoriteWorkoutDao().getFavoriteByWorkoutIdSync(workoutId);

            if (existingFavorite != null) {
                // Remove from favorites
                database.favoriteWorkoutDao().delete(existingFavorite);

                mainHandler.post(() -> {
                    favoriteButton.setImageResource(android.R.drawable.btn_star_big_off);
                    Toast.makeText(itemView.getContext(), "Removed from favorites", Toast.LENGTH_SHORT).show();
                });
            } else {
                // Add to favorites
                FavoriteWorkout newFavorite = new FavoriteWorkout(workoutId);
                newFavorite.setNotes(workout.getTitle() + " by " + workout.getTrainer());

                // Store full workout details
                newFavorite.setTitle(workout.getTitle());
                newFavorite.setTrainer(workout.getTrainer());
                newFavorite.setVideoUrl("https://www.youtube.com/embed/" + workout.getVideoId());
                // Store full YouTube thumbnail URL so it can be loaded in favorites
                newFavorite.setThumbnail("https://img.youtube.com/vi/" + workout.getVideoId() + "/hqdefault.jpg");

                database.favoriteWorkoutDao().insert(newFavorite);

                mainHandler.post(() -> {
                    favoriteButton.setImageResource(android.R.drawable.btn_star_big_on);
                    Toast.makeText(itemView.getContext(), "Added to favorites", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private void checkFavoriteStatus(int position, ImageButton favoriteButton, View itemView) {
        WorkoutContent workout = workouts.get(position);
        FitnessDatabase database = FitnessDatabase.getInstance(itemView.getContext());

        FitnessDatabase.databaseWriteExecutor.execute(() -> {
            int workoutId = workout.getId().hashCode();
            boolean isFavorite = database.favoriteWorkoutDao().isFavoriteSync(workoutId);

            mainHandler.post(() -> {
                if (isFavorite) {
                    favoriteButton.setImageResource(android.R.drawable.btn_star_big_on);
                } else {
                    favoriteButton.setImageResource(android.R.drawable.btn_star_big_off);
                }
            });
        });
    }

    class WorkoutViewHolder extends RecyclerView.ViewHolder {
        private MaterialCardView cardView;
        private ImageView thumbnail;
        private TextView title;
        private TextView trainer;
        private TextView duration;
        private TextView difficulty;
        private TextView calories;
        private ImageButton favoriteButton;

        public WorkoutViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.workoutCard);
            thumbnail = itemView.findViewById(R.id.workoutThumbnail);
            title = itemView.findViewById(R.id.workoutTitle);
            trainer = itemView.findViewById(R.id.workoutTrainer);
            duration = itemView.findViewById(R.id.workoutDuration);
            difficulty = itemView.findViewById(R.id.workoutDifficulty);
            calories = itemView.findViewById(R.id.workoutCalories);
            favoriteButton = itemView.findViewById(R.id.favoriteButton);
        }

        public void bind(WorkoutContent workout, OnWorkoutClickListener listener, int position, WorkoutContentAdapter adapter) {
            title.setText(workout.getTitle());
            trainer.setText("👤 " + workout.getTrainer());
            duration.setText("⏱️ " + workout.getDurationDisplay());
            difficulty.setText("📊 " + workout.getDifficulty());
            calories.setText("🔥 " + workout.getCaloriesBurn() + " cal");

            // Load thumbnail from YouTube
            String thumbnailUrl = "https://img.youtube.com/vi/" + workout.getVideoId() + "/hqdefault.jpg";
            Glide.with(itemView.getContext())
                    .load(thumbnailUrl)
                    .placeholder(R.drawable.picture)
                    .into(thumbnail);

            cardView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onWorkoutClick(workout);
                }
            });

            // Set up favorite button
            favoriteButton.setOnClickListener(v -> {
                adapter.toggleFavorite(position, favoriteButton, itemView);
            });

            // Check favorite status
            adapter.checkFavoriteStatus(position, favoriteButton, itemView);
        }
    }
}
