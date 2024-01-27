package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity3 extends AppCompatActivity {
    TextView textView;
    private ArrayList<String> bellypic;
    private ArrayList<String> bellyvideo;
    private ArrayList<String> bellytext1;
    private ArrayList<String> bellytext2;

    private ArrayList<String> chestpic;
    private ArrayList<String> chestvideo;
    private ArrayList<String> chesttext1;
    private ArrayList<String> chesttext2;

    private ArrayList<String> fullbodypic;
    private ArrayList<String> fullbodyvideo;
    private ArrayList<String> fullbodytext1;
    private ArrayList<String> fullbodytext2;

    private ArrayList<String> legpic;
    private ArrayList<String> legvideo;
    private ArrayList<String> legtext1;
    private ArrayList<String> legtext2;

    private ArrayList<String> musclepic;
    private ArrayList<String> musclevideo;
    private ArrayList<String> muscletext1;
    private ArrayList<String> muscletext2;
    private RecyclerView recyclerView;
    private Adaptor adaptor;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        recyclerView=findViewById(R.id.rv);
        textView=findViewById(R.id.body_parts_video_activity_text);

        intent=getIntent();
        String condition=intent.getStringExtra("button");
        if(condition.equalsIgnoreCase("belly")){
            textView.setText("BELLY");
            Belly();
        }

        if(condition.equalsIgnoreCase("full_body")){
            textView.setText("FULL BODY");
            fullBody();
        }

        if(condition.equalsIgnoreCase("chest")){
            textView.setText("CHEST");
            chest();
        }

        if(condition.equalsIgnoreCase("muscle")){
            textView.setText("MUSCLE");
            muscle();
        }

        if(condition.equalsIgnoreCase("leg")){
            textView.setText("LEG");
            leg();
        }
    }
    public void Belly(){

        bellypic=new ArrayList<>();
        bellypic.add("@drawable/mind_with_muscle_belly_workout");
        bellypic.add("@drawable/mukti_gautam_belly_workout");
        bellypic.add("@drawable/madfit_belly_workout");
        bellypic.add("@drawable/cultfit_belly_workout");
        bellypic.add("@drawable/thebodyproject_belly_workout");
        bellypic.add("@drawable/healthifyme_belly_workout");

        bellyvideo=new ArrayList<>();
        bellyvideo.add("https://www.youtube.com/embed/IyRPOjlKGEw?si=-1zihkTLcE-AsTHz");
        bellyvideo.add("https://www.youtube.com/embed/Eq86UbZLrhk?si=hxGuxWewPJYAZLUv");
        bellyvideo.add("https://www.youtube.com/embed/hJHkRHpdYTE?si=2vmfAlTub00JG-dz");
        bellyvideo.add("https://www.youtube.com/embed/digpucxFbMo?si=75_Hbkfc8mL-7QWR");
        bellyvideo.add("https://www.youtube.com/embed/rVjSQ6NXAO8?si=CkQJRxntcxCWNHbr");
        bellyvideo.add("https://www.youtube.com/embed/QXQ-pAEGcCk?si=XU9-Sikal4qbkEUA");

        bellytext1=new ArrayList<>();
        bellytext1.add("Six-Pack Symphony");
        bellytext1.add("Stellar Abs Strive");
        bellytext1.add("Belly Blast Bonanza");
        bellytext1.add("Ultimate Belly Burnout");
        bellytext1.add("Belly Bliss Burn");
        bellytext1.add("Midsection Marve");

        bellytext2=new ArrayList<>();
        bellytext2.add("Mind with Muscles");
        bellytext2.add("Mukti Gautam");
        bellytext2.add("MadFit");
        bellytext2.add("CulFit");
        bellytext2.add("The body Project");
        bellytext2.add("Healthi Fyme");

        adaptor=new Adaptor(MainActivity3.this,bellypic,bellyvideo,bellytext1,bellytext2);
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity3.this,1));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adaptor);
    }

    public void fullBody(){
        fullbodypic=new ArrayList<>();
        fullbodypic.add("@drawable/mind_with_muscle_fat_loss_workout");
        fullbodypic.add("@drawable/mukti_gautam_fat_loss_workout");
        fullbodypic.add("@drawable/madfit_full_body_workout");
        fullbodypic.add("@drawable/cultfit_full_body_workout");
        fullbodypic.add("@drawable/thebodyproject_fullbody_workout");
        fullbodypic.add("@drawable/healthifyme_fullbody_workout");

        fullbodyvideo=new ArrayList<>();
        fullbodyvideo.add("https://www.youtube.com/embed/HKYBegMGhQA?si=aKj71mo_5JZBDyyU");
        fullbodyvideo.add("https://www.youtube.com/embed/gGBIFe_ver8?si=y1OHpVEy6JV518ei");
        fullbodyvideo.add("https://www.youtube.com/embed/4iy4yEKa7W8?si=iPxfRZREKrup8jUC");
        fullbodyvideo.add("https://www.youtube.com/embed/t9F9gZg42NM?si=kQe7-oNufsagBtHl");
        fullbodyvideo.add("https://www.youtube.com/embed/xq0Hz3250zI?si=G3p34QQ1pWHqj7WT");
        fullbodyvideo.add("https://www.youtube.com/embed/5ARgeR1rMTo?si=eHkkG_cUsFAulYat");

        fullbodytext1=new ArrayList<>();
        fullbodytext1.add("Full Body Fury");
        fullbodytext1.add("Whole Body Whirlwind");
        fullbodytext1.add("Body Blast Bonanza");
        fullbodytext1.add("Total Transformation Tango");
        fullbodytext1.add("Intense Integrated Ignition");
        fullbodytext1.add("Total Torso Takeover");

        fullbodytext2=new ArrayList<>();
        fullbodytext2.add("Mind with Muscles");
        fullbodytext2.add("Mukti Gautam");
        fullbodytext2.add("MadFit");
        fullbodytext2.add("CulFit");
        fullbodytext2.add("The body Project");
        fullbodytext2.add("Healthi Fyme");

        adaptor=new Adaptor(MainActivity3.this,fullbodypic,fullbodyvideo,fullbodytext1,fullbodytext2);
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity3.this,1));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adaptor);
    }

    public void muscle(){
        musclepic=new ArrayList<>();
        musclepic.add("@drawable/mind_with_muscle_muscle_workout");
        musclepic.add("@drawable/mukti_gautam_muscle_workout");
        musclepic.add("@drawable/mukti_gautam_arm_workout");
        musclepic.add("@drawable/madfit_muscle_workout");
        musclepic.add("@drawable/cultfit_muscle_workout");
        musclepic.add("@drawable/thebodyproject_muscle_workout");
        musclepic.add("@drawable/healthyfime_muscles_workout");

        musclevideo=new ArrayList<>();
        musclevideo.add("https://www.youtube.com/embed/_yo1kHbCB0Q?si=cvdqx0fYyzeWFRUD");
        musclevideo.add("https://www.youtube.com/embed/ig1O65s5Yws?si=g1PguFqdO5sQnMr3");
        musclevideo.add("https://www.youtube.com/embed/crpjaOsjCbw?si=sEipZqBsOZNBdpvL");
        musclevideo.add("https://www.youtube.com/embed/tUUFn2oK_E0?si=jCjFTXLPw1Q1pjNJ");
        musclevideo.add("https://www.youtube.com/embed/v_0jdkgwPKE?si=AwCjHNC46K2PJEzC");
        musclevideo.add("https://www.youtube.com/embed/KY8jENkQpDQ?si=KrfJZ7q5geSvRin0");
        musclevideo.add("https://www.youtube.com/embed/dj03_VDetdw?si=kvqX5Ru587c84ZMs");

        muscletext1=new ArrayList<>();
        muscletext1.add("Muscle Maximization Marathon");
        muscletext1.add("Iron Grip Inferno");
        muscletext1.add("Mighty Muscle Mania");
        muscletext1.add("Dynamic Muscle Drive");
        muscletext1.add("Mega Muscle Medley");
        muscletext1.add("Herculean Hypertrophy");
        muscletext1.add("Anabolic Ascendancy");

        muscletext2=new ArrayList<>();
        muscletext2.add("Mind with Muscles");
        muscletext2.add("Mukti Gautam");
        muscletext2.add("Mukti Gautam");
        muscletext2.add("MadFit");
        muscletext2.add("CulFit");
        muscletext2.add("The body Project");
        muscletext2.add("Healthi Fyme");

        adaptor=new Adaptor(MainActivity3.this,musclepic,musclevideo,muscletext1,muscletext2);
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity3.this,1));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adaptor);
    }

    public void leg(){
        legpic=new ArrayList<>();
        legpic.add("@drawable/mind_with_muscle_leg_workout");
        legpic.add("@drawable/mukti_gautam_lower_body_workout");
        legpic.add("@drawable/madfit_leg_workout");
        legpic.add("@drawable/cultfit_legs_workout");
        legpic.add("@drawable/thebodyproject_leg_workout");
        legpic.add("@drawable/healthifyme_leg_workout");

        legvideo=new ArrayList<>();
        legvideo.add("https://www.youtube.com/embed/HMu6O4bfsqw?si=SQgirzjDHxM4mIAc");
        legvideo.add("https://www.youtube.com/embed/rHL0v2NBPSg?si=d_UV7oojPZclpTJl");
        legvideo.add("https://www.youtube.com/embed/FJA3R7n_594?si=esuKV_9posD6JEDn");
        legvideo.add("https://www.youtube.com/embed/hUwWYIz9f0Y?si=Or6bo925mWj3JLav");
        legvideo.add("https://www.youtube.com/embed/xsqh63FaYz4?si=PdoZv7J0Ai-XDNg3");
        legvideo.add("https://www.youtube.com/embed/Cv1SJ5qKhJE?si=1e3Ag6Oqm7G8bk36");

        legtext1=new ArrayList<>();
        legtext1.add("Legs of Steel Saga");
        legtext1.add("Thigh Thrust Throwdown");
        legtext1.add("Legs On Fire Frenzy");
        legtext1.add("Calf Carnage Crusade");
        legtext1.add("Quad Quake Quest");
        legtext1.add("Lower Limb Labyrinth");

        legtext2=new ArrayList<>();
        legtext2.add("Mind with Muscles");
        legtext2.add("Mukti Gautam");
        legtext2.add("MadFit");
        legtext2.add("CulFit");
        legtext2.add("The body Project");
        legtext2.add("Healthi Fyme");


        adaptor=new Adaptor(MainActivity3.this,legpic,legvideo,legtext1,legtext2);
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity3.this,1));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adaptor);
    }

    public void chest(){
        chestpic=new ArrayList<>();
        chestpic.add("@drawable/mind_with_muscle_chest_workout");
        chestpic.add("@drawable/madfit_chest_workout");
        chestpic.add("@drawable/cultfit_chest_workout");
        chestpic.add("@drawable/thebodyproject_chest_workout");
        chestpic.add("@drawable/healthyfime_chest_workout");


        chestvideo=new ArrayList<>();
        chestvideo.add("https://www.youtube.com/embed/l7ig59R_JU0?si=ghY6HQNsdMnwkNS5");
        chestvideo.add("https://www.youtube.com/embed/ESkI_WR1qqc?si=SzMeLzWGmXwiWJIv");
        chestvideo.add("https://www.youtube.com/embed/P0skdqdirWE?si=arAdl4eK73eJvqlc");
        chestvideo.add("https://www.youtube.com/embed/260e3JKxaNU?si=s0YXWRjITqqwe794");
        chestvideo.add("https://www.youtube.com/embed/YV-oiyU-kEo?si=ON6wRmWvEtUzE363");


        chesttext1=new ArrayList<>();
        chesttext1.add("Chest Conquest Chronicles");
        chesttext1.add("Upper Body Uprising");
        chesttext1.add("Pec Power Play");
        chesttext1.add("Fly & Flex Frenzy");
        chesttext1.add("Chest Comet Cascade");


        chesttext2=new ArrayList<>();
        chesttext2.add("Mind with Muscles");
        chesttext2.add("MadFit");
        chesttext2.add("CulFit");
        chesttext2.add("The body Project");
        chesttext2.add("Healthi Fyme");


        adaptor=new Adaptor(MainActivity3.this,chestpic,chestvideo,chesttext1,chesttext2);
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity3.this,1));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adaptor);
    }
}