package com.example.project;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

public class DiscoverArticlesActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_articles);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);

        // Setup article click listeners
        setupArticleClicks();

        // Set Discover as selected
        bottomNavigation.setSelectedItemId(R.id.navigation_discover);

        // Setup pull-to-refresh
        swipeRefreshLayout.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        );
        swipeRefreshLayout.setOnRefreshListener(() -> {
            // Simulate refresh
            swipeRefreshLayout.postDelayed(() -> {
                swipeRefreshLayout.setRefreshing(false);
                android.widget.Toast.makeText(this, "Articles refreshed!", android.widget.Toast.LENGTH_SHORT).show();
            }, 1000);
        });

        // Bottom Navigation
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.navigation_home) {
                startActivity(new Intent(this, MainActivity2.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            } else if (itemId == R.id.navigation_workout_info) {
                startActivity(new Intent(this, FitnessDashboardActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            } else if (itemId == R.id.navigation_discover) {
                return true; // Already here
            } else if (itemId == R.id.navigation_profile) {
                startActivity(new Intent(this, UserProfileActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            }

            return false;
        });
    }

    private void setupArticleClicks() {
        MaterialCardView article1 = findViewById(R.id.article1Card);
        MaterialCardView article2 = findViewById(R.id.article2Card);
        MaterialCardView article3 = findViewById(R.id.article3Card);
        MaterialCardView article4 = findViewById(R.id.article4Card);

        article1.setOnClickListener(v -> openArticle(
            "🏃 10 Tips for Better Cardio Workouts",
            "Cardiovascular exercise is essential for heart health and overall fitness. Here are 10 proven tips to maximize your cardio workouts:\n\n" +
            "1. Start with a proper warm-up: Begin with 5-10 minutes of light cardio to prepare your body and prevent injuries.\n\n" +
            "2. Mix up your intensity: Alternate between high and low intensity intervals (HIIT) to burn more calories and improve endurance.\n\n" +
            "3. Monitor your heart rate: Stay within your target heart rate zone (typically 50-85% of your maximum heart rate) for optimal results.\n\n" +
            "4. Stay hydrated: Drink water before, during, and after your workout to maintain performance and recovery.\n\n" +
            "5. Use proper form: Whether running, cycling, or using machines, maintain correct posture to prevent strain and maximize efficiency.\n\n" +
            "6. Vary your exercises: Don't stick to just one type of cardio - mix running, cycling, swimming, and rowing to work different muscles.\n\n" +
            "7. Set realistic goals: Start with manageable targets and gradually increase duration and intensity as your fitness improves.\n\n" +
            "8. Track your progress: Keep a workout log to monitor improvements in distance, time, and heart rate recovery.\n\n" +
            "9. Include a cool-down: End with 5-10 minutes of light activity and stretching to aid recovery and prevent soreness.\n\n" +
            "10. Listen to your body: Rest when needed and don't push through pain - recovery is crucial for long-term progress.\n\n" +
            "Remember: Consistency is key! Aim for at least 150 minutes of moderate cardio or 75 minutes of vigorous cardio per week for optimal health benefits."
        ));

        article2.setOnClickListener(v -> openArticle(
            "💪 Building Muscle: Complete Guide",
            "Building lean muscle mass requires a combination of proper training, nutrition, and recovery. Follow this comprehensive guide to maximize your muscle growth:\n\n" +
            "TRAINING PRINCIPLES:\n\n" +
            "• Progressive Overload: Gradually increase weight, reps, or sets over time to continually challenge your muscles.\n\n" +
            "• Compound Exercises: Focus on multi-joint movements like squats, deadlifts, bench press, and rows that work multiple muscle groups.\n\n" +
            "• Training Frequency: Train each muscle group 2-3 times per week for optimal growth stimulus.\n\n" +
            "• Rep Ranges: Use 8-12 reps for hypertrophy (muscle growth), with 3-4 sets per exercise.\n\n" +
            "• Rest Periods: Take 60-90 seconds between sets for muscle building workouts.\n\n\n" +
            "NUTRITION FOR MUSCLE GROWTH:\n\n" +
            "• Caloric Surplus: Consume 300-500 calories above maintenance to support muscle growth.\n\n" +
            "• Protein Intake: Aim for 1.6-2.2 grams of protein per kilogram of body weight daily.\n\n" +
            "• Carbohydrates: Eat adequate carbs (4-7g per kg) to fuel intense training sessions.\n\n" +
            "• Healthy Fats: Include 0.5-1g per kg for hormone production and overall health.\n\n" +
            "• Meal Timing: Distribute protein evenly across 4-6 meals throughout the day.\n\n\n" +
            "RECOVERY STRATEGIES:\n\n" +
            "• Sleep: Get 7-9 hours of quality sleep for muscle repair and growth hormone release.\n\n" +
            "• Rest Days: Allow 1-2 complete rest days per week for full recovery.\n\n" +
            "• Active Recovery: Light cardio or stretching on off days to promote blood flow.\n\n" +
            "• Hydration: Drink at least 3-4 liters of water daily for optimal muscle function.\n\n\n" +
            "Remember: Muscle building is a marathon, not a sprint. Stay consistent with your training and nutrition, and you'll see results over time!"
        ));

        article3.setOnClickListener(v -> openArticle(
            "🥗 Nutrition Basics for Fitness",
            "Proper nutrition is the foundation of any successful fitness program. Understanding these fundamentals will help you fuel your workouts and achieve your goals:\n\n" +
            "MACRONUTRIENTS EXPLAINED:\n\n" +
            "PROTEIN (4 calories per gram):\n" +
            "• Essential for muscle repair and growth\n" +
            "• Sources: Chicken, fish, eggs, lean beef, tofu, legumes, Greek yogurt\n" +
            "• Target: 1.6-2.2g per kg of body weight for active individuals\n\n" +
            "CARBOHYDRATES (4 calories per gram):\n" +
            "• Primary energy source for high-intensity exercise\n" +
            "• Sources: Oats, rice, potatoes, quinoa, fruits, whole grain bread\n" +
            "• Target: 3-7g per kg depending on activity level\n\n" +
            "FATS (9 calories per gram):\n" +
            "• Important for hormone production and vitamin absorption\n" +
            "• Sources: Avocado, nuts, olive oil, fatty fish, seeds\n" +
            "• Target: 0.5-1g per kg of body weight\n\n\n" +
            "MICRONUTRIENTS & HYDRATION:\n\n" +
            "• Vitamins & Minerals: Eat a variety of colorful fruits and vegetables daily\n" +
            "• Water: Drink half your body weight in ounces (minimum 8 glasses/day)\n" +
            "• Electrolytes: Replenish sodium, potassium, and magnesium after intense workouts\n\n\n" +
            "MEAL TIMING:\n\n" +
            "• Pre-Workout (1-2 hours before): Carbs + moderate protein, low fat\n" +
            "• Post-Workout (within 2 hours): Protein + carbs for recovery\n" +
            "• Throughout Day: Spread protein intake across 4-6 meals\n\n\n" +
            "PRACTICAL TIPS:\n\n" +
            "• Meal Prep: Prepare meals in advance to stay on track\n" +
            "• Track Your Food: Use an app to monitor calories and macros initially\n" +
            "• Eat Whole Foods: Choose minimally processed options 80-90% of the time\n" +
            "• Flexible Dieting: Allow room for treats (10-20% of calories) to stay sane\n" +
            "• Consistency > Perfection: Focus on long-term habits, not short-term restrictions\n\n" +
            "Remember: Nutrition should support your training and lifestyle, not control it. Find a sustainable approach that works for you!"
        ));

        article4.setOnClickListener(v -> openArticle(
            "🧘 Recovery and Rest Days",
            "Rest and recovery are just as important as your training sessions. Proper recovery allows your body to adapt, grow stronger, and prevent injuries:\n\n" +
            "WHY RECOVERY MATTERS:\n\n" +
            "• Muscle Repair: Exercise creates micro-tears in muscle fibers - rest allows them to heal stronger\n" +
            "• Nervous System Recovery: Intense training stresses your central nervous system\n" +
            "• Glycogen Replenishment: Rest days allow muscles to restore energy reserves\n" +
            "• Injury Prevention: Overtraining increases injury risk - recovery reduces this\n" +
            "• Mental Refreshment: Time off prevents burnout and maintains motivation\n\n\n" +
            "TYPES OF RECOVERY:\n\n" +
            "ACTIVE RECOVERY:\n" +
            "• Light cardio (walking, easy cycling) at 30-50% max effort\n" +
            "• Yoga or stretching sessions\n" +
            "• Swimming or water activities\n" +
            "• Benefits: Promotes blood flow without adding stress\n\n" +
            "PASSIVE RECOVERY:\n" +
            "• Complete rest days with no structured exercise\n" +
            "• Focus on sleep, nutrition, and relaxation\n" +
            "• Benefits: Full physical and mental recovery\n\n\n" +
            "RECOVERY STRATEGIES:\n\n" +
            "SLEEP:\n" +
            "• Aim for 7-9 hours per night\n" +
            "• Maintain consistent sleep schedule\n" +
            "• Create dark, cool, quiet environment\n" +
            "• Avoid screens 1 hour before bed\n\n" +
            "NUTRITION:\n" +
            "• Adequate protein (1.6-2.2g per kg) for muscle repair\n" +
            "• Sufficient carbs to replenish glycogen\n" +
            "• Anti-inflammatory foods (berries, fatty fish, leafy greens)\n" +
            "• Stay hydrated throughout the day\n\n" +
            "MOBILITY & STRETCHING:\n" +
            "• Dynamic stretching before workouts\n" +
            "• Static stretching after workouts or on rest days\n" +
            "• Foam rolling to reduce muscle tension\n" +
            "• Mobility work for joint health\n\n" +
            "STRESS MANAGEMENT:\n" +
            "• Practice meditation or deep breathing\n" +
            "• Engage in hobbies outside of fitness\n" +
            "• Spend time with friends and family\n" +
            "• Limit caffeine and alcohol\n\n\n" +
            "HOW MANY REST DAYS?\n\n" +
            "• Beginners: 3-4 rest days per week\n" +
            "• Intermediate: 2-3 rest days per week\n" +
            "• Advanced: 1-2 rest days per week (with deload weeks monthly)\n\n" +
            "SIGNS YOU NEED MORE REST:\n\n" +
            "• Persistent muscle soreness\n" +
            "• Decreased performance\n" +
            "• Elevated resting heart rate\n" +
            "• Poor sleep quality\n" +
            "• Increased irritability or mood changes\n" +
            "• Frequent illness or infections\n" +
            "• Loss of motivation\n\n" +
            "Remember: More training isn't always better - smart training with adequate recovery produces the best results. Listen to your body and don't feel guilty about taking rest days!"
        ));
    }

    private void openArticle(String title, String content) {
        Intent intent = new Intent(this, ArticleDetailActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
