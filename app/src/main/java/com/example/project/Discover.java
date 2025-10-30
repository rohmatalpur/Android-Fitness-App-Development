package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

public class Discover extends AppCompatActivity {

    MaterialCardView muscle, fullbody, legs, belly, chest, searchCard;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);

        backButton = findViewById(R.id.backButton);
        muscle = findViewById(R.id.muscle);
        fullbody = findViewById(R.id.fullbody);
        legs = findViewById(R.id.Legs);
        belly = findViewById(R.id.belly);
        chest = findViewById(R.id.chest);
        searchCard = findViewById(R.id.searchCard);

        // Setup back button
        backButton.setOnClickListener(v -> finish());

        // Setup search card click
        searchCard.setOnClickListener(v -> {
            Intent intent = new Intent(Discover.this, YoutubeSearchActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });

        muscle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Discover.this,MainActivity3.class);
                intent.putExtra("button","muscle");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        fullbody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Discover.this,MainActivity3.class);
                intent.putExtra("button","full_body");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        legs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Discover.this,MainActivity3.class);
                intent.putExtra("button","leg");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        belly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Discover.this,MainActivity3.class);
                intent.putExtra("button","belly");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        chest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Discover.this,MainActivity3.class);
                intent.putExtra("button","chest");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


    }
}