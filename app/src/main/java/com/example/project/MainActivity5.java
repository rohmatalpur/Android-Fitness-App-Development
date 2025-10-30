package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.database.FitnessDatabase;
import com.example.project.database.entity.Workout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity5 extends AppCompatActivity {
    TextView textView;
    private RecyclerView recyclerView;
    private Adaptor adaptor;
    Intent intent;
    private FitnessDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        recyclerView=findViewById(R.id.rv);
        textView=findViewById(R.id.youtubers_video_activity_text);

        // Initialize database
        database = FitnessDatabase.getInstance(getApplicationContext());

        intent=getIntent();
        String condition=intent.getStringExtra("button");

        if(condition.equalsIgnoreCase("muktigautum")){
            textView.setText("MUKTI GAUTAM");
            loadWorkoutsByTrainer("mukti_gautam");
        }
        else if(condition.equalsIgnoreCase("mindwithmuscle")){
            textView.setText("MIND WITH MUSCLES");
            loadWorkoutsByTrainer("mind_with_muscles");
        }
        else if(condition.equalsIgnoreCase("madfit")){
            textView.setText("MADFIT");
            loadWorkoutsByTrainer("madfit");
        }
        else if(condition.equalsIgnoreCase("cultfit")){
            textView.setText("CULTFIT");
            loadWorkoutsByTrainer("cultfit");
        }
        else if(condition.equalsIgnoreCase("healthifyme")){
            textView.setText("HEALTHIFYME");
            loadWorkoutsByTrainer("healthifyme");
        }
        else if(condition.equalsIgnoreCase("thebodyproject")){
            textView.setText("THE BODY PROJECT");
            loadWorkoutsByTrainer("thebodyproject");
        }
    }

    private void loadWorkoutsByTrainer(String trainerCategory) {
        // Load workouts from database in a background thread
        FitnessDatabase.databaseWriteExecutor.execute(() -> {
            // Check total workout count
            int totalWorkouts = database.workoutDao().getWorkoutCountSync();
            android.util.Log.d("MainActivity5", "Total workouts in DB: " + totalWorkouts);

            List<Workout> workouts = database.workoutDao().getWorkoutsByTrainerSync(trainerCategory);
            android.util.Log.d("MainActivity5", "Workouts for trainer " + trainerCategory + ": " + workouts.size());

            // Convert to ArrayLists for Adaptor
            ArrayList<String> pictures = new ArrayList<>();
            ArrayList<String> videos = new ArrayList<>();
            ArrayList<String> titles = new ArrayList<>();
            ArrayList<String> trainers = new ArrayList<>();
            ArrayList<Integer> workoutIds = new ArrayList<>();

            for (Workout workout : workouts) {
                android.util.Log.d("MainActivity5", "Workout: " + workout.getTitle() + " (ID: " + workout.getId() + ")");
                pictures.add(workout.getThumbnailResource());
                videos.add(workout.getVideoUrl());
                titles.add(workout.getTitle());
                trainers.add(workout.getTrainerName());
                workoutIds.add(workout.getId());
            }

            // Update UI on main thread
            runOnUiThread(() -> {
                if (workouts.isEmpty()) {
                    android.widget.Toast.makeText(MainActivity5.this,
                        "No workouts found for trainer: " + trainerCategory,
                        android.widget.Toast.LENGTH_LONG).show();
                }
                adaptor = new Adaptor(MainActivity5.this, pictures, videos, titles, trainers, workoutIds);
                recyclerView.setLayoutManager(new GridLayoutManager(MainActivity5.this, 1));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adaptor);
            });
        });
    }
}
