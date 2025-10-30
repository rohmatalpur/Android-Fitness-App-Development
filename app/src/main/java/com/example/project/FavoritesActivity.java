package com.example.project;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.database.entity.Workout;
import com.example.project.database.entity.FavoriteWorkout;
import com.example.project.database.FitnessDatabase;
import com.example.project.repository.FavoriteRepository;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView emptyTextView;
    private ProgressBar progressBar;
    private FavoriteRepository favoriteRepository;
    private Adaptor adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        // Initialize views
        recyclerView = findViewById(R.id.favoritesRecyclerView);
        emptyTextView = findViewById(R.id.emptyTextView);
        progressBar = findViewById(R.id.progressBar);

        // Setup RecyclerView
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        // Initialize repository
        favoriteRepository = new FavoriteRepository(getApplication());

        // Load favorites
        loadFavorites();
    }

    private void loadFavorites() {
        progressBar.setVisibility(View.VISIBLE);

        // Use direct query to get all favorite workouts (no join needed)
        FitnessDatabase database = FitnessDatabase.getInstance(getApplication());
        database.favoriteWorkoutDao().getAllFavoriteWorkouts().observe(this, new Observer<List<FavoriteWorkout>>() {
            @Override
            public void onChanged(List<FavoriteWorkout> favorites) {
                progressBar.setVisibility(View.GONE);

                if (favorites == null || favorites.isEmpty()) {
                    emptyTextView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    emptyTextView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                    // Convert favorites to ArrayLists for Adaptor
                    ArrayList<String> pictures = new ArrayList<>();
                    ArrayList<String> videos = new ArrayList<>();
                    ArrayList<String> titles = new ArrayList<>();
                    ArrayList<String> trainers = new ArrayList<>();
                    ArrayList<Integer> workoutIds = new ArrayList<>();

                    for (FavoriteWorkout favorite : favorites) {
                        pictures.add(favorite.getThumbnail()); // Thumbnail stores video ID
                        videos.add(favorite.getVideoUrl());
                        titles.add(favorite.getTitle());
                        trainers.add(favorite.getTrainer());
                        workoutIds.add(favorite.getWorkoutId());
                    }

                    adapter = new Adaptor(FavoritesActivity.this, pictures, videos, titles, trainers, workoutIds);
                    recyclerView.setAdapter(adapter);
                }
            }
        });
    }
}
