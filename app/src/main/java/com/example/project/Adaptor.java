package com.example.project;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
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
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project.database.FitnessDatabase;
import com.example.project.database.entity.FavoriteWorkout;

import java.util.ArrayList;

public class Adaptor extends RecyclerView.Adapter<Adaptor.ViewHolder>{


    Context context;
    ArrayList<String> picture;
    ArrayList<String> video;
    ArrayList<String> text1;
    ArrayList<String> text2;
    ArrayList<Integer> workoutIds; // Track actual workout IDs
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public Adaptor(Context context, ArrayList<String> picture, ArrayList<String> video, ArrayList<String> text1, ArrayList<String> text2) {
        this.context = context;
        this.picture = picture;
        this.video = video;
        this.text1=text1;
        this.text2=text2;
        this.workoutIds = new ArrayList<>();
        // Initialize with position-based IDs if not provided
        for (int i = 0; i < text1.size(); i++) {
            workoutIds.add(i + 1);
        }
    }

    // Constructor that accepts workout IDs
    public Adaptor(Context context, ArrayList<String> picture, ArrayList<String> video, ArrayList<String> text1, ArrayList<String> text2, ArrayList<Integer> workoutIds) {
        this.context = context;
        this.picture = picture;
        this.video = video;
        this.text1=text1;
        this.text2=text2;
        this.workoutIds = workoutIds != null ? workoutIds : new ArrayList<>();
        // Initialize with position-based IDs if workoutIds is empty
        if (this.workoutIds.isEmpty()) {
            for (int i = 0; i < text1.size(); i++) {
                this.workoutIds.add(i + 1);
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new ViewHolder(view);
    }
    public int getImage(String imageName) {
        // Handle null image name
        if (imageName == null || imageName.isEmpty()) {
            return 0;
        }

        int drawableResourceId = this.context.getResources().getIdentifier(imageName, "drawable", this.context.getPackageName());

        return drawableResourceId;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //note
        String pictureName = picture.get(position);

        // Check if it's a URL (from Discover) or a drawable resource name (from Trainers)
        if (pictureName != null && (pictureName.startsWith("http://") || pictureName.startsWith("https://"))) {
            // It's a URL - load directly with Glide
            Glide.with(context)
                    .load(pictureName)
                    .error(R.drawable.picture)
                    .placeholder(R.drawable.picture)
                    .into(holder.imageView);
        } else {
            // It's a drawable resource name - try to load from resources
            int imageResource = getImage(pictureName);
            if (imageResource != 0) {
                Glide.with(context)
                        .load(imageResource)
                        .error(R.drawable.picture)
                        .placeholder(R.drawable.picture)
                        .into(holder.imageView);
            } else {
                // Use default image if resource not found or name is null
                holder.imageView.setImageResource(R.drawable.picture);
            }
        }
        holder.txt1.setText(text1.get(position));
        holder.txt2.setText("👤 " + text2.get(position));

        // Set up click listener for video playback with hero animation
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,MainActivity4.class);
                intent.putExtra("video",video.get(position));
                intent.putExtra("title",text1.get(position));
                intent.putExtra("trainer",text2.get(position));
                intent.putExtra("picture",picture.get(position)); // Pass thumbnail for history
                intent.putExtra("workoutId", workoutIds.get(position)); // Pass workout ID
                intent.putExtra("useBrowserMode", true); // Use browser embedding directly

                // Create shared element transition if context is an Activity
                if (context instanceof Activity) {
                    Activity activity = (Activity) context;

                    // Create pairs for shared elements
                    Pair<View, String> imagePair = Pair.create((View) holder.imageView, "workout_thumbnail");
                    Pair<View, String> titlePair = Pair.create((View) holder.txt1, "workout_title");

                    // Create ActivityOptions with shared elements
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity,
                        imagePair,
                        titlePair
                    );

                    activity.startActivity(intent, options.toBundle());
                } else {
                    // Fallback without animation
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });

        // Set up favorite button click listener
        holder.favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFavorite(position, holder.favoriteButton);
            }
        });

        // Check if this workout is already favorited and update button state
        checkFavoriteStatus(position, holder.favoriteButton);
    }

    private void toggleFavorite(int position, ImageButton favoriteButton) {
        FitnessDatabase database = FitnessDatabase.getInstance(context);

        FitnessDatabase.databaseWriteExecutor.execute(() -> {
            // Use actual workout ID from the list
            int workoutId = workoutIds.get(position);

            FavoriteWorkout existingFavorite = database.favoriteWorkoutDao().getFavoriteByWorkoutIdSync(workoutId);

            if (existingFavorite != null) {
                // Remove from favorites
                database.favoriteWorkoutDao().delete(existingFavorite);

                // Update UI on main thread
                mainHandler.post(() -> {
                    favoriteButton.setImageResource(android.R.drawable.btn_star_big_off);
                    Toast.makeText(context, "Removed from favorites", Toast.LENGTH_SHORT).show();
                });
            } else {
                // Add to favorites
                FavoriteWorkout newFavorite = new FavoriteWorkout(workoutId);

                // Store all workout details for display and playback
                newFavorite.setTitle(text1.get(position));
                newFavorite.setTrainer(text2.get(position));
                newFavorite.setVideoUrl(video.get(position));
                newFavorite.setThumbnail(picture.get(position));
                newFavorite.setNotes(text1.get(position) + " by " + text2.get(position));

                database.favoriteWorkoutDao().insert(newFavorite);

                // Update UI on main thread
                mainHandler.post(() -> {
                    favoriteButton.setImageResource(android.R.drawable.btn_star_big_on);
                    Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private void checkFavoriteStatus(int position, ImageButton favoriteButton) {
        FitnessDatabase database = FitnessDatabase.getInstance(context);

        FitnessDatabase.databaseWriteExecutor.execute(() -> {
            int workoutId = workoutIds.get(position);
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

    @Override
    public int getItemCount() {
        return text1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        ImageView imageView;
        TextView txt1;
        TextView txt2;
        ImageButton favoriteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cv = (CardView)itemView.findViewById(R.id.cv);
            imageView=(ImageView) itemView.findViewById(R.id.image);
            txt1=(TextView) itemView.findViewById(R.id.t1);
            txt2=(TextView) itemView.findViewById(R.id.t2);
            favoriteButton=(ImageButton) itemView.findViewById(R.id.favoriteButton);
        }
    }
}
