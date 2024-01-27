package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Discoveryoutubers extends AppCompatActivity {

    CardView muktigautam,mindwithmuscle,madfit,cultfit,healthifyme,thebodyproject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discoveryoutubers);

        muktigautam=findViewById(R.id.muktigautum);
        mindwithmuscle=findViewById(R.id.mindwithmuscle);
        madfit=findViewById(R.id.madfit);
        cultfit=findViewById(R.id.cultfit);
        healthifyme=findViewById(R.id.healthifyme);
        thebodyproject=findViewById(R.id.thebodyproject);


        muktigautam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Discoveryoutubers.this,MainActivity5.class);
                intent.putExtra("button","muktigautum");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        mindwithmuscle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Discoveryoutubers.this,MainActivity5.class);
                intent.putExtra("button","mindwithmuscle");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        madfit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Discoveryoutubers.this,MainActivity5.class);
                intent.putExtra("button","madfit");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        cultfit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Discoveryoutubers.this,MainActivity5.class);
                intent.putExtra("button","cultfit");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        healthifyme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Discoveryoutubers.this,MainActivity5.class);
                intent.putExtra("button","healthifyme");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        thebodyproject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Discoveryoutubers.this,MainActivity5.class);
                intent.putExtra("button","thebodyproject");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


    }
}