package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.adapters.CategoryAdapter;
import com.example.project.model.WorkoutCategory;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategoryBrowserActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private RecyclerView recyclerView;
    private CategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_browser);

        // Initialize views
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tabLayout);
        recyclerView = findViewById(R.id.categoriesRecyclerView);

        // Setup toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Browse Categories");
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        // Setup RecyclerView
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new CategoryAdapter(new ArrayList<>(), this::onCategoryClick);
        recyclerView.setAdapter(adapter);

        // Setup tabs
        setupTabs();

        // Load initial category
        loadWorkoutTypes();
    }

    private void setupTabs() {
        tabLayout.addTab(tabLayout.newTab().setText("Workout Types"));
        tabLayout.addTab(tabLayout.newTab().setText("By Goal"));
        tabLayout.addTab(tabLayout.newTab().setText("By Duration"));
        tabLayout.addTab(tabLayout.newTab().setText("By Equipment"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        loadWorkoutTypes();
                        break;
                    case 1:
                        loadGoalCategories();
                        break;
                    case 2:
                        loadDurationCategories();
                        break;
                    case 3:
                        loadEquipmentCategories();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void loadWorkoutTypes() {
        List<WorkoutCategory> categories = Arrays.asList(
                WorkoutCategory.CARDIO,
                WorkoutCategory.STRENGTH,
                WorkoutCategory.YOGA,
                WorkoutCategory.PILATES,
                WorkoutCategory.DANCE,
                WorkoutCategory.MARTIAL_ARTS,
                WorkoutCategory.CROSSFIT,
                WorkoutCategory.CYCLING,
                WorkoutCategory.RUNNING,
                WorkoutCategory.MEDITATION,
                WorkoutCategory.HIIT
        );
        adapter.updateCategories(categories);
    }

    private void loadGoalCategories() {
        List<WorkoutCategory> categories = Arrays.asList(
                WorkoutCategory.WEIGHT_LOSS,
                WorkoutCategory.MUSCLE_BUILDING,
                WorkoutCategory.FLEXIBILITY,
                WorkoutCategory.ATHLETIC_PERFORMANCE,
                WorkoutCategory.INJURY_RECOVERY,
                WorkoutCategory.GENERAL_FITNESS,
                WorkoutCategory.ENDURANCE
        );
        adapter.updateCategories(categories);
    }

    private void loadDurationCategories() {
        List<WorkoutCategory> categories = Arrays.asList(
                WorkoutCategory.QUICK_5MIN,
                WorkoutCategory.EXPRESS_10MIN,
                WorkoutCategory.SESSION_30MIN,
                WorkoutCategory.HOUR_LONG
        );
        adapter.updateCategories(categories);
    }

    private void loadEquipmentCategories() {
        List<WorkoutCategory> categories = Arrays.asList(
                WorkoutCategory.NO_EQUIPMENT,
                WorkoutCategory.BODYWEIGHT,
                WorkoutCategory.DUMBBELLS,
                WorkoutCategory.RESISTANCE_BANDS,
                WorkoutCategory.KETTLEBELLS,
                WorkoutCategory.YOGA_MAT,
                WorkoutCategory.HOME_GYM,
                WorkoutCategory.FULL_GYM
        );
        adapter.updateCategories(categories);
    }

    private void onCategoryClick(WorkoutCategory category) {
        Intent intent = new Intent(this, WorkoutListActivity.class);
        intent.putExtra("category", category.name());
        intent.putExtra("categoryName", category.getDisplayName());
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
