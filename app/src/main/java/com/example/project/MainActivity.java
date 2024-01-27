package com.example.project;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

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
                        wait(5*1000);
                    } catch (Exception e) {}
                }
                startActivity(new Intent(MainActivity.this,MainActivity2.class));
            }
        };
        Thread mythread = new Thread(runnable);
        mythread.start();
    }
}