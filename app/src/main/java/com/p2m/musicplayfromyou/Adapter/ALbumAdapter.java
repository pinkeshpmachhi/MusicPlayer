package com.p2m.musicplayfromyou.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.p2m.musicplayfromyou.Models.albumModel;
import com.p2m.musicplayfromyou.R;

import java.util.ArrayList;

public class ALbumAdapter extends RecyclerView.Adapter<ALbumAdapter.MyViewHolder> {

    private ArrayList<albumModel> list;
    private Context context;

    public ALbumAdapter(ArrayList<albumModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.album_sample_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
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

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageAlbum);
            textView = itemView.findViewById(R.id.nameAlbumTv);
        }
    }

}
