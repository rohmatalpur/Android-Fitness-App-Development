package com.example.project;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.project.repository.UserPreferencesRepository;
import com.example.project.repository.WorkoutHistoryRepository;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class WorkoutInfoActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_info);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        bottomNavigation = findViewById(R.id.bottom_navigation);

        // Set Workout Info as selected
        bottomNavigation.setSelectedItemId(R.id.navigation_workout_info);

        // Setup ViewPager with tabs
        WorkoutInfoPagerAdapter adapter = new WorkoutInfoPagerAdapter(this);
        viewPager.setAdapter(adapter);

        // Connect TabLayout with ViewPager2
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("📊 Stats");
                            break;
                        case 1:
                            tab.setText("📅 History");
                            break;
                        case 2:
                            tab.setText("❤️ Favorites");
                            break;
                    }
                }
        ).attach();

        // Bottom Navigation
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.navigation_home) {
                startActivity(new Intent(this, MainActivity2.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            } else if (itemId == R.id.navigation_workout_info) {
                return true; // Already here
            } else if (itemId == R.id.navigation_discover) {
                startActivity(new Intent(this, DiscoverArticlesActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            } else if (itemId == R.id.navigation_profile) {
                startActivity(new Intent(this, UserProfileActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            }

            return false;
        });
    }

    // ViewPager Adapter
    private class WorkoutInfoPagerAdapter extends FragmentStateAdapter {

        public WorkoutInfoPagerAdapter(@NonNull AppCompatActivity activity) {
            super(activity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new StatsFragment();
                case 1:
                    return new HistoryFragmentWrapper();
                case 2:
                    return new FavoritesFragmentWrapper();
                default:
                    return new StatsFragment();
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }

    // Stats Fragment
    public static class StatsFragment extends Fragment {
        private WorkoutHistoryRepository historyRepository;
        private UserPreferencesRepository preferencesRepository;

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_workout_stats, container, false);

            historyRepository = new WorkoutHistoryRepository(requireActivity().getApplication());
            preferencesRepository = new UserPreferencesRepository(requireActivity().getApplication());

            TextView totalWorkoutsText = view.findViewById(R.id.totalWorkoutsText);
            TextView totalCaloriesText = view.findViewById(R.id.totalCaloriesText);
            TextView totalMinutesText = view.findViewById(R.id.totalMinutesText);
            TextView streakText = view.findViewById(R.id.streakText);
            TextView recentActivityText = view.findViewById(R.id.recentActivityText);

            // Progress Rings
            CircularProgressBar caloriesProgressRing = view.findViewById(R.id.caloriesProgressRing);
            CircularProgressBar timeProgressRing = view.findViewById(R.id.timeProgressRing);
            CircularProgressBar workoutsProgressRing = view.findViewById(R.id.workoutsProgressRing);

            TextView caloriesProgressText = view.findViewById(R.id.caloriesProgressText);
            TextView timeProgressText = view.findViewById(R.id.timeProgressText);
            TextView workoutsProgressText = view.findViewById(R.id.workoutsProgressText);

            // Load stats
            historyRepository.getTotalWorkoutsCompleted().observe(getViewLifecycleOwner(), total -> {
                if (total != null) {
                    totalWorkoutsText.setText(String.valueOf(total));
                    // Animate workouts progress ring (goal: 3 workouts per day)
                    animateProgressRing(workoutsProgressRing, total % 3, workoutsProgressText);
                }
            });

            historyRepository.getTotalCaloriesBurned().observe(getViewLifecycleOwner(), calories -> {
                if (calories != null) {
                    totalCaloriesText.setText(String.valueOf(calories));
                    // Animate calories progress ring (goal: 500 calories per day)
                    int todayCalories = calories % 500;
                    animateProgressRing(caloriesProgressRing, todayCalories, caloriesProgressText);
                }
            });

            historyRepository.getTotalWorkoutMinutes().observe(getViewLifecycleOwner(), minutes -> {
                if (minutes != null) {
                    totalMinutesText.setText(String.valueOf(minutes));
                    // Animate time progress ring (goal: 60 minutes per day)
                    int todayMinutes = minutes % 60;
                    animateProgressRing(timeProgressRing, todayMinutes, timeProgressText);
                }
            });

            preferencesRepository.getUserPreferences().observe(getViewLifecycleOwner(), prefs -> {
                if (prefs != null) {
                    streakText.setText(String.valueOf(prefs.getCurrentStreak()));
                }
            });

            // Load recent activity
            historyRepository.getAllHistoryWithWorkouts().observe(getViewLifecycleOwner(), historyList -> {
                if (historyList != null && !historyList.isEmpty()) {
                    StringBuilder recentActivity = new StringBuilder();
                    int count = Math.min(5, historyList.size());

                    for (int i = 0; i < count; i++) {
                        com.example.project.database.dao.WorkoutHistoryDao.HistoryWithWorkout item = historyList.get(i);

                        // Format date
                        String dateStr = new java.text.SimpleDateFormat("MMM dd", java.util.Locale.getDefault())
                            .format(new java.util.Date(item.history.getCompletedAt()));

                        recentActivity.append("📅 ")
                            .append(dateStr)
                            .append(" - ")
                            .append(item.workout.getTitle())
                            .append("\n")
                            .append("   ⏱️ ")
                            .append(item.history.getDurationMinutes())
                            .append(" min • 🔥 ")
                            .append(item.history.getCaloriesBurned())
                            .append(" cal");

                        if (i < count - 1) {
                            recentActivity.append("\n\n");
                        }
                    }
                    recentActivityText.setText(recentActivity.toString());
                } else {
                    recentActivityText.setText("No recent activity yet.\nStart your first workout!");
                }
            });

            return view;
        }

        private void animateProgressRing(CircularProgressBar progressBar, float targetValue, TextView textView) {
            ValueAnimator animator = ValueAnimator.ofFloat(0, targetValue);
            animator.setDuration(1500); // 1.5 seconds animation
            animator.setInterpolator(new DecelerateInterpolator());

            animator.addUpdateListener(animation -> {
                float value = (float) animation.getAnimatedValue();
                progressBar.setProgress(value);
                textView.setText(String.valueOf((int) value));
            });

            animator.start();
        }
    }

    // History Fragment
    public static class HistoryFragmentWrapper extends Fragment {
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_workout_history, container, false);

            RecyclerView recyclerView = view.findViewById(R.id.historyRecyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            WorkoutHistoryRepository historyRepository = new WorkoutHistoryRepository(requireActivity().getApplication());

            historyRepository.getAllHistory().observe(getViewLifecycleOwner(), historyList -> {
                if (historyList != null && !historyList.isEmpty()) {
                    WorkoutHistoryAdapter adapter = new WorkoutHistoryAdapter(getContext(), historyList);
                    recyclerView.setAdapter(adapter);
                }
            });

            return view;
        }
    }

    // Favorites Fragment
    public static class FavoritesFragmentWrapper extends Fragment {
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_favorites, container, false);

            RecyclerView recyclerView = view.findViewById(R.id.favoritesRecyclerView);
            TextView emptyText = view.findViewById(R.id.emptyText);

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            com.example.project.repository.FavoriteRepository favoriteRepository =
                new com.example.project.repository.FavoriteRepository(requireActivity().getApplication());

            favoriteRepository.getFavoriteWorkoutsWithDetails().observe(getViewLifecycleOwner(), workouts -> {
                if (workouts == null || workouts.isEmpty()) {
                    emptyText.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    emptyText.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                    // Convert workouts to ArrayLists for Adaptor
                    java.util.ArrayList<String> pictures = new java.util.ArrayList<>();
                    java.util.ArrayList<String> videos = new java.util.ArrayList<>();
                    java.util.ArrayList<String> titles = new java.util.ArrayList<>();
                    java.util.ArrayList<String> trainers = new java.util.ArrayList<>();
                    java.util.ArrayList<Integer> workoutIds = new java.util.ArrayList<>();

                    for (com.example.project.database.entity.Workout workout : workouts) {
                        pictures.add(workout.getThumbnailResource());
                        videos.add(workout.getVideoUrl());
                        titles.add(workout.getTitle());
                        trainers.add(workout.getTrainerName());
                        workoutIds.add(workout.getId());
                    }

                    Adaptor adapter = new Adaptor(getContext(), pictures, videos, titles, trainers, workoutIds);
                    recyclerView.setAdapter(adapter);
                }
            });

            return view;
        }
    }
}
