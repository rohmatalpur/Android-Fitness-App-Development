package com.example.project;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    TextView app_name;
    LottieAnimationView animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        animation = findViewById(R.id.animation_view);

        Runnable runnable = new Runnable() {
            public void run() {
                synchronized (this) {
                    try {
                        wait(2*1000);
                    } catch (Exception e) {}
                }

                // Check if user is logged in
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();

                Intent intent;
                if (currentUser != null) {
                    // User is logged in, go to main app
                    intent = new Intent(MainActivity.this, MainActivity2.class);
                } else {
                    // User not logged in, go to login
                    intent = new Intent(MainActivity.this, LoginActivity.class);
                }
                startActivity(intent);
                finish();
            }
        };
        Thread mythread = new Thread(runnable);
        mythread.start();
    }
}