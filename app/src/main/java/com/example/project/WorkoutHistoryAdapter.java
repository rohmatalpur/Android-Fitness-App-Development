package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project.database.FitnessDatabase;
import com.example.project.database.dao.WorkoutHistoryDao;
import com.example.project.database.entity.Workout;
import com.example.project.database.entity.WorkoutHistory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WorkoutHistoryAdapter extends RecyclerView.Adapter<WorkoutHistoryAdapter.ViewHolder> {

    private List<WorkoutHistory> historyList;
    private Context context;

    public WorkoutHistoryAdapter(Context context, List<WorkoutHistory> historyList) {
        this.context = context;
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_workout_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WorkoutHistory history = historyList.get(position);

        // Hide thumbnail for now (we don't have workout details)
        holder.thumbnailImageView.setVisibility(View.GONE);

        // Set workout title from workoutType field
        String title = history.getWorkoutType() != null ? history.getWorkoutType() : "Video Workout";
        holder.titleTextView.setText(title);

        // Set trainer from notes if available
        String notes = history.getNotes();
        if (notes != null && notes.startsWith("Trainer: ")) {
            holder.trainerTextView.setText(notes.substring(9)); // Remove "Trainer: " prefix
        } else {
            holder.trainerTextView.setText(notes != null ? notes : "");
        }

        // Set date
        holder.dateTextView.setText(history.getDate());

        // Set duration - show actual workout time if available
        int actualWorkoutMinutes = history.getActualWorkoutTime() / 60;
        if (actualWorkoutMinutes > 0) {
            holder.durationTextView.setText("Workout: " + actualWorkoutMinutes + " min");
        } else {
            holder.durationTextView.setText("Duration: " + history.getDurationMinutes() + " min");
        }

        // Set calories
        holder.caloriesTextView.setText(history.getCaloriesBurned() + " cal");

        // Hide progress text (not using resume feature anymore)
        holder.progressTextView.setVisibility(View.GONE);

        // Enable click to replay workout
        holder.cardView.setOnClickListener(v -> {
            // Check if video URL is stored in history
            String videoUrl = history.getVideoUrl();

            if (videoUrl != null && !videoUrl.isEmpty()) {
                // Use stored video URL directly
                Intent intent = new Intent(context, MainActivity4.class);
                intent.putExtra("workoutId", history.getWorkoutId());
                intent.putExtra("title", history.getWorkoutType());
                intent.putExtra("trainer", history.getTrainerName() != null ? history.getTrainerName() : "");
                intent.putExtra("video", videoUrl);
                intent.putExtra("picture", history.getThumbnailUrl() != null ? history.getThumbnailUrl() : "");
                intent.putExtra("useBrowserMode", true); // Use browser mode like Discover videos

                android.util.Log.d("WorkoutHistoryAdapter", "Playing video from history: " + history.getWorkoutType() + ", URL: " + videoUrl);
                context.startActivity(intent);
            } else {
                // Fallback: Try to fetch from workout database
                int workoutId = history.getWorkoutId();
                android.util.Log.d("WorkoutHistoryAdapter", "No video URL in history, looking up workout ID: " + workoutId);

                FitnessDatabase database = FitnessDatabase.getInstance(context);
                FitnessDatabase.databaseWriteExecutor.execute(() -> {
                    Workout workout = database.workoutDao().getWorkoutByIdSync(workoutId);

                    ((android.app.Activity) context).runOnUiThread(() -> {
                        if (workout != null && workout.getVideoUrl() != null) {
                            // Launch video player with workout details
                            Intent intent = new Intent(context, MainActivity4.class);
                            intent.putExtra("workoutId", workout.getId());
                            intent.putExtra("title", workout.getTitle());
                            intent.putExtra("trainer", workout.getTrainerName());
                            intent.putExtra("video", workout.getVideoUrl());
                            intent.putExtra("picture", workout.getThumbnailResource());
                            intent.putExtra("useBrowserMode", true); // Use browser mode

                            android.util.Log.d("WorkoutHistoryAdapter", "Found workout in database, playing: " + workout.getTitle());
                            context.startActivity(intent);
                        } else {
                            android.util.Log.d("WorkoutHistoryAdapter", "Workout not found in database");
                            Toast.makeText(context, "Video not available for this workout", Toast.LENGTH_SHORT).show();
                        }
                    });
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView thumbnailImageView;
        TextView titleTextView, trainerTextView, dateTextView, durationTextView, caloriesTextView, progressTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.historyCardView);
            thumbnailImageView = itemView.findViewById(R.id.thumbnailImageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            trainerTextView = itemView.findViewById(R.id.trainerTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            durationTextView = itemView.findViewById(R.id.durationTextView);
            caloriesTextView = itemView.findViewById(R.id.caloriesTextView);
            progressTextView = itemView.findViewById(R.id.progressTextView);
        }
    }
}
