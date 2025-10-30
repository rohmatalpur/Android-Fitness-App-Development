package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.adapters.WorkoutContentAdapter;
import com.example.project.data.WorkoutContentProvider;
import com.example.project.model.WorkoutCategory;
import com.example.project.model.WorkoutContent;

import java.util.List;

public class WorkoutListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private TextView emptyView;
    private WorkoutContentAdapter adapter;
    private String categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_list);

        // Get category from intent
        String categoryString = getIntent().getStringExtra("category");
        categoryName = getIntent().getStringExtra("categoryName");

        // Initialize views
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.workoutsRecyclerView);
        emptyView = findViewById(R.id.emptyView);

        // Setup toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(categoryName);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load workouts for category
        if (categoryString != null) {
            WorkoutCategory category = WorkoutCategory.valueOf(categoryString);
            List<WorkoutContent> workouts = WorkoutContentProvider.getWorkoutsByCategory(category);

            if (workouts.isEmpty()) {
                recyclerView.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                emptyView.setVisibility(View.GONE);
                adapter = new WorkoutContentAdapter(workouts, this::onWorkoutClick);
                recyclerView.setAdapter(adapter);
            }
        }
    }

    private void onWorkoutClick(WorkoutContent workout) {
        Intent intent = new Intent(this, MainActivity4.class);

        // Convert video ID to embed URL
        String videoUrl = "https://www.youtube.com/embed/" + workout.getVideoId();

        intent.putExtra("video", videoUrl);
        intent.putExtra("title", workout.getTitle());
        intent.putExtra("trainer", workout.getTrainer());
        intent.putExtra("useBrowserMode", true); // Use browser embedding directly
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
