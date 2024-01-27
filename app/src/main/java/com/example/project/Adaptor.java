package com.example.project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Adaptor extends RecyclerView.Adapter<Adaptor.ViewHolder>{


    Context context;
    ArrayList<String> picture;
    ArrayList<String> video;
    ArrayList<String> text1;
    ArrayList<String> text2;

    public Adaptor(Context context, ArrayList<String> picture, ArrayList<String> video, ArrayList<String> text1, ArrayList<String> text2) {
        this.context = context;
        this.picture = picture;
        this.video = video;
        this.text1=text1;
        this.text2=text2;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new ViewHolder(view);
    }
    public int getImage(String imageName) {

        int drawableResourceId = this.context.getResources().getIdentifier(imageName, "drawable", this.context.getPackageName());

        return drawableResourceId;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //note
        Glide.with(context)
                .load(getImage(picture.get(position)))
                .into(holder.imageView);
        holder.txt1.setText(text1.get(position));
        holder.txt2.setText(text2.get(position));

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,MainActivity4.class);
                intent.putExtra("video",video.get(position));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return text1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;

        ImageView imageView;
        TextView txt1;
        TextView txt2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cv = (CardView)itemView.findViewById(R.id.cv);
            imageView=(ImageView) itemView.findViewById(R.id.image);
            txt1=(TextView) itemView.findViewById(R.id.t1);
            txt2=(TextView) itemView.findViewById(R.id.t2);
        }
    }
}
