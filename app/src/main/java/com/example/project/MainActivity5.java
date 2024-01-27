package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class MainActivity5 extends AppCompatActivity {
        TextView textView;
        private ArrayList<String> muktigautumpic;
        private ArrayList<String> muktigautumvideo;
        private ArrayList<String> muktigautumtext1;
        private ArrayList<String> muktigautumtext2;
        private ArrayList<String> mindwithmusclepic;
        private ArrayList<String> mindwithmusclevideo;
        private ArrayList<String> mindwithmuscletext1;
        private ArrayList<String> mindwithmuscletext2;

        private ArrayList<String> madfitpic;
        private ArrayList<String> madfitvideo;
        private ArrayList<String> madfittext1;
        private ArrayList<String> madfittext2;
        private ArrayList<String> cultfitpic;
        private ArrayList<String> cultfitvideo;
        private ArrayList<String> cultfittext1;
        private ArrayList<String> cultfittext2;

        private ArrayList<String> healthifymepic;
        private ArrayList<String> healthifymevideo;
        private ArrayList<String> healthifymetext1;
        private ArrayList<String> healthifymetext2;
        private ArrayList<String> thebodyprojectpic;
        private ArrayList<String> thebodyprojectvideo;
        private ArrayList<String> thebodyprojecttext1;
        private ArrayList<String> thebodyprojecttext2;
        private RecyclerView recyclerView;
        private Adaptor adaptor;
        Intent intent;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main5);
            recyclerView=findViewById(R.id.rv);
            textView=findViewById(R.id.youtubers_video_activity_text);

            intent=getIntent();
            String condition=intent.getStringExtra("button");
            if(condition.equalsIgnoreCase("muktigautum")){
                textView.setText("MUKTI GAUTUM");
                muktigautum();
            }

            if(condition.equalsIgnoreCase("mindwithmuscle")){
                textView.setText("MIND WITH MUSCLES");
                mindwithmuscle();
            }

            if(condition.equalsIgnoreCase("madfit")){
                textView.setText("MADFIT");
                madfit();
            }

            if(condition.equalsIgnoreCase("cultfit")){
                textView.setText("CULTFIT");
                cultfit();
            }

            if(condition.equalsIgnoreCase("healthifyme")){
                textView.setText("HEALTHIFYME");
                healthifyme();
            }


            if(condition.equalsIgnoreCase("thebodyproject")){
                textView.setText("THE BODY PROJECT");
                thebodyproject();
            }
        }
        public void muktigautum(){

            muktigautumpic=new ArrayList<>();
            muktigautumpic.add("@drawable/mukti_gautum_belly_workout");
            muktigautumpic.add("@drawable/mukti_gautam_lower_body_workout");
            muktigautumpic.add("@drawable/mukti_gautam_arm_workout");
            muktigautumpic.add("@drawable/mukti_gautam_muscle_workout");
            muktigautumpic.add("@drawable/mukti_gautam_fat_loss_workout");

            muktigautumvideo=new ArrayList<>();
            muktigautumvideo.add("https://www.youtube.com/embed/Eq86UbZLrhk?si=hxGuxWewPJYAZLUv");
            muktigautumvideo.add("https://www.youtube.com/embed/rHL0v2NBPSg?si=d_UV7oojPZclpTJl" );
            muktigautumvideo.add("https://www.youtube.com/embed/crpjaOsjCbw?si=sEipZqBsOZNBdpvL");
            muktigautumvideo.add("https://www.youtube.com/embed/ig1O65s5Yws?si=g1PguFqdO5sQnMr3");
            muktigautumvideo.add("https://www.youtube.com/embed/gGBIFe_ver8?si=y1OHpVEy6JV518ei");

            muktigautumtext1=new ArrayList<>();
            muktigautumtext1.add("Belly workout");
            muktigautumtext1.add("Lower Body workout");
            muktigautumtext1.add("Arm workout");
            muktigautumtext1.add("Muscle workout");
            muktigautumtext1.add("Full body workout");

            muktigautumtext2=new ArrayList<>();
            muktigautumtext2.add("Mukti Gutum");
            muktigautumtext2.add("Mukti Gutum");
            muktigautumtext2.add("Mukti Gutum");
            muktigautumtext2.add("Mukti Gutum");
            muktigautumtext2.add("Mukti Gutum");




            adaptor=new Adaptor(MainActivity5.this,muktigautumpic,muktigautumvideo,muktigautumtext1,muktigautumtext2);
            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity5.this,1));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adaptor);
        }

        public void mindwithmuscle(){
            mindwithmusclepic=new ArrayList<>();
            mindwithmusclepic.add("@drawable/mind_with_muscle_belly_workout");
            mindwithmusclepic.add("@drawable/mind_with_muscle_chest_workout");
            mindwithmusclepic.add("@drawable/mind_with_muscle_leg_workout");
            mindwithmusclepic.add("@drawable/mind_with_muscle_muscle_workout");
            mindwithmusclepic.add("@drawable/mind_with_muscle_fat_loss_workout");


            mindwithmusclevideo=new ArrayList<>();
            mindwithmusclevideo.add("https://www.youtube.com/embed/IyRPOjlKGEw?si=-1zihkTLcE-AsTHz");
            mindwithmusclevideo.add("https://www.youtube.com/embed/l7ig59R_JU0?si=ghY6HQNsdMnwkNS5");
            mindwithmusclevideo.add("https://www.youtube.com/embed/HMu6O4bfsqw?si=SQgirzjDHxM4mIAc");
            mindwithmusclevideo.add("https://www.youtube.com/embed/_yo1kHbCB0Q?si=cvdqx0fYyzeWFRUD");
            mindwithmusclevideo.add("https://www.youtube.com/embed/HKYBegMGhQA?si=aKj71mo_5JZBDyyU");

            mindwithmuscletext1=new ArrayList<>();
            mindwithmuscletext1.add("Belly workout");
            mindwithmuscletext1.add("Leg workout");
            mindwithmuscletext1.add("Chest workout");
            mindwithmuscletext1.add("Muscle workout");
            mindwithmuscletext1.add("Full body workout");

            mindwithmuscletext2=new ArrayList<>();
            mindwithmuscletext2.add("Mind With Muscles");
            mindwithmuscletext2.add("Mind With Muscles");
            mindwithmuscletext2.add("Mind With Muscles");
            mindwithmuscletext2.add("Mind With Muscles");
            mindwithmuscletext2.add("Mind With Muscles");



            adaptor=new Adaptor(MainActivity5.this,mindwithmusclepic,mindwithmusclevideo, mindwithmuscletext1, mindwithmuscletext2);
            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity5.this,1));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adaptor);
        }

        public void madfit(){
            madfitpic=new ArrayList<>();
            madfitpic.add("@drawable/madfit_belly_workout");
            madfitpic.add("@drawable/madfit_chest_workout");
            madfitpic.add("@drawable/madfit_leg_workout");
            madfitpic.add("@drawable/madfit_muscle_workout");
            madfitpic.add("@drawable/madfit_full_body_workout");


            madfitvideo=new ArrayList<>();
            madfitvideo.add("https://www.youtube.com/embed/hJHkRHpdYTE?si=2vmfAlTub00JG-dz");
            madfitvideo.add("https://www.youtube.com/embed/ESkI_WR1qqc?si=SzMeLzWGmXwiWJIv");
            madfitvideo.add("https://www.youtube.com/embed/FJA3R7n_594?si=esuKV_9posD6JEDn");
            madfitvideo.add("https://www.youtube.com/embed/tUUFn2oK_E0?si=jCjFTXLPw1Q1pjNJ");
            madfitvideo.add("https://www.youtube.com/embed/4iy4yEKa7W8?si=iPxfRZREKrup8jUC");

            madfittext1=new ArrayList<>();
            madfittext1.add("Belly workout");
            madfittext1.add("Leg workout");
            madfittext1.add("Chest workout");
            madfittext1.add("Muscle workout");
            madfittext1.add("Full body workout");

            madfittext2=new ArrayList<>();
            madfittext2.add("Madfit");
            madfittext2.add("Madfit");
            madfittext2.add("Madfit");
            madfittext2.add("Madfit");
            madfittext2.add("Madfit");


            adaptor=new Adaptor(MainActivity5.this,madfitpic,madfitvideo, madfittext1, madfittext2);
            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity5.this,1));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adaptor);
        }

        public void cultfit(){
            cultfitpic=new ArrayList<>();
            cultfitpic.add("@drawable/cultfit_belly_workout");
            cultfitpic.add("@drawable/cultfit_legs_workout");
            cultfitpic.add("@drawable/cultfit_chest_workout");
            cultfitpic.add("@drawable/cultfit_muscle_workout");
            cultfitpic.add("@drawable/cultfit_full_body_workout");

            cultfitvideo=new ArrayList<>();
            cultfitvideo.add("https://www.youtube.com/embed/digpucxFbMo?si=75_Hbkfc8mL-7QWR");
            cultfitvideo.add("https://www.youtube.com/embed/hUwWYIz9f0Y?si=Or6bo925mWj3JLav");
            cultfitvideo.add("https://www.youtube.com/embed/P0skdqdirWE?si=arAdl4eK73eJvqlc");
            cultfitvideo.add("https://www.youtube.com/embed/v_0jdkgwPKE?si=AwCjHNC46K2PJEzC");
            cultfitvideo.add("https://www.youtube.com/embed/t9F9gZg42NM?si=kQe7-oNufsagBtHl");

            cultfittext1=new ArrayList<>();
            cultfittext1.add("Belly workout");
            cultfittext1.add("Leg workout");
            cultfittext1.add("Chest workout");
            cultfittext1.add("Muscle workout");
            cultfittext1.add("Full body workout");

            cultfittext2=new ArrayList<>();
            cultfittext2.add("Cultfit");
            cultfittext2.add("Cultfit");
            cultfittext2.add("Cultfit");
            cultfittext2.add("Cultfit");
            cultfittext2.add("Cultfit");


            adaptor=new Adaptor(MainActivity5.this,cultfitpic,cultfitvideo,cultfittext1,cultfittext2);
            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity5.this,1));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adaptor);
        }

        public void healthifyme(){
            healthifymepic=new ArrayList<>();
            healthifymepic.add("@drawable/healthifyme_belly_workout");
            healthifymepic.add("@drawable/healthifyme_leg_workout");
            healthifymepic.add("@drawable/healthyfime_chest_workout");
            healthifymepic.add("@drawable/healthyfime_muscles_workout");
            healthifymepic.add("@drawable/healthifyme_fullbody_workout");


            healthifymevideo=new ArrayList<>();
            healthifymevideo.add("https://www.youtube.com/embed/QXQ-pAEGcCk?si=XU9-Sikal4qbkEUA");
            healthifymevideo.add("https://www.youtube.com/embed/Cv1SJ5qKhJE?si=1e3Ag6Oqm7G8bk36");
            healthifymevideo.add("https://www.youtube.com/embed/YV-oiyU-kEo?si=ON6wRmWvEtUzE363");
            healthifymevideo.add("https://www.youtube.com/embed/dj03_VDetdw?si=kvqX5Ru587c84ZMs");
            healthifymevideo.add("https://www.youtube.com/embed/5ARgeR1rMTo?si=eHkkG_cUsFAulYat");

            healthifymetext1=new ArrayList<>();
            healthifymetext1.add("Belly workout");
            healthifymetext1.add("Leg workout");
            healthifymetext1.add("Chest workout");
            healthifymetext1.add("Muscle workout");
            healthifymetext1.add("Full body workout");

            healthifymetext2=new ArrayList<>();
            healthifymetext2.add("Healthifyme");
            healthifymetext2.add("Healthifyme");
            healthifymetext2.add("Healthifyme");
            healthifymetext2.add("Healthifyme");
            healthifymetext2.add("Healthifyme");



            adaptor=new Adaptor(MainActivity5.this,healthifymepic, healthifymevideo, healthifymetext1, healthifymetext2);
            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity5.this,1));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adaptor);
        }


        public void thebodyproject(){
            thebodyprojectpic=new ArrayList<>();
            thebodyprojectpic.add("@drawable/thebodyproject_belly_workout");
            thebodyprojectpic.add("@drawable/thebodyproject_leg_workout");
            thebodyprojectpic.add("@drawable/thebodyproject_chest_workout");
            thebodyprojectpic.add("@drawable/thebodyproject_muscle_workout");
            thebodyprojectpic.add("@drawable/thebodyproject_fullbody_workout");



            thebodyprojectvideo=new ArrayList<>();
            thebodyprojectvideo.add("https://www.youtube.com/embed/rVjSQ6NXAO8?si=CkQJRxntcxCWNHbr");
            thebodyprojectvideo.add("https://www.youtube.com/embed/xsqh63FaYz4?si=PdoZv7J0Ai-XDNg3");
            thebodyprojectvideo.add("https://www.youtube.com/embed/260e3JKxaNU?si=s0YXWRjITqqwe794");
            thebodyprojectvideo.add("https://www.youtube.com/embed/KY8jENkQpDQ?si=KrfJZ7q5geSvRin0");
            thebodyprojectvideo.add("https://www.youtube.com/embed/xq0Hz3250zI?si=G3p34QQ1pWHqj7WT");

            thebodyprojecttext1=new ArrayList<>();
            thebodyprojecttext1.add("Belly workout");
            thebodyprojecttext1.add("Leg workout");
            thebodyprojecttext1.add("Chest workout");
            thebodyprojecttext1.add("Muscle workout");
            thebodyprojecttext1.add("Full body workout");

            thebodyprojecttext2=new ArrayList<>();
            thebodyprojecttext2.add("The Body Project");
            thebodyprojecttext2.add("The Body Project");
            thebodyprojecttext2.add("The Body Project");
            thebodyprojecttext2.add("The Body Project");
            thebodyprojecttext2.add("The Body Project");



            adaptor=new Adaptor(MainActivity5.this,thebodyprojectpic,thebodyprojectvideo, thebodyprojecttext1, thebodyprojecttext2);
            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity5.this,1));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adaptor);
        }
    }