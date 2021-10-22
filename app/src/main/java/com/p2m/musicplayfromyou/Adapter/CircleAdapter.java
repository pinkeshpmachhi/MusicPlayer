package com.p2m.musicplayfromyou.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.p2m.musicplayfromyou.Models.albumModel;
import com.p2m.musicplayfromyou.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CircleAdapter extends RecyclerView.Adapter<CircleAdapter.MyViewHolderTwo> {

    private ArrayList<albumModel> list;
    private Context context;

    public CircleAdapter(ArrayList<albumModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CircleAdapter.MyViewHolderTwo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.circle_album_sample_item, parent, false);
        return new CircleAdapter.MyViewHolderTwo(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CircleAdapter.MyViewHolderTwo holder, int position) {
        albumModel single = list.get(position);
        if (single != null){
            holder.textView.setText(single.getName());
            Glide.with(context).load(single.getImage()).placeholder(R.drawable.musicimage).into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolderTwo extends RecyclerView.ViewHolder{

        CircleImageView imageView;
        TextView textView;

        public MyViewHolderTwo(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageAlbumCircle);
            textView = itemView.findViewById(R.id.nameAlbumTvCircle);
        }
    }

}

