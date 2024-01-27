package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    Button discover, trending;
    TextView welcomeText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        welcomeText = findViewById(R.id.welcomeText);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(welcomeText, "alpha", 0f, 1f);
        fadeIn.setDuration(2000); // Animation duration in milliseconds (adjust as needed)
        fadeIn.start();


        discover=findViewById(R.id.discover);
        trending=findViewById(R.id.trending);

        discover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity2.this, Discover.class);
                startActivity(intent);
            }
        });

        trending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(MainActivity2.this, Discoveryoutubers.class);
                startActivity(intent1);
            }
        });




    }
}