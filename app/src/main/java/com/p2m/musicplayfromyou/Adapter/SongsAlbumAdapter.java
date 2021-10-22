package com.p2m.musicplayfromyou.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.p2m.musicplayfromyou.Models.albumModel;
import com.p2m.musicplayfromyou.MusicFiles;
import com.p2m.musicplayfromyou.R;

import java.util.ArrayList;

public class SongsAlbumAdapter extends RecyclerView.Adapter<SongsAlbumAdapter.MyViewHolder> {

    private ArrayList<MusicFiles> list;
    private Context context;
    private ArrayList<Bitmap> allBitmap = new ArrayList<>();
    public onClickListenerSong onClickListenerSongGG;

    public SongsAlbumAdapter(ArrayList<MusicFiles> list, Context context, onClickListenerSong onClickListenerSongFF) {
        this.list = list;
        this.context = context;
        onClickListenerSongGG = onClickListenerSongFF;
//        setAllBitmap();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.songs_album_sample_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MusicFiles single = list.get(position);
        if (single != null){
            holder.textView.setText(single.getTitle());

            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.musicimage);

            bitmap = getAlbumArt(list.get(position).getPath());
            Glide.with(context).asBitmap().load(R.drawable.musicimage).placeholder(R.drawable.musicimage).into(holder.imageView);


        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imageView;
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageAlbum);
            textView = itemView.findViewById(R.id.nameAlbumTv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListenerSongGG.onClick(v, getAdapterPosition());
        }
    }

    Bitmap getAlbumArt(String path) {

        try {
            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(path);
            byte[] data = mediaMetadataRetriever.getEmbeddedPicture();
            mediaMetadataRetriever.release();
            if (data != null) {
                return BitmapFactory.decodeByteArray(data, 0, data.length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void setAllBitmap(){
        for(MusicFiles musicFiles : list){
            Bitmap bitmap = getAlbumArt(musicFiles.getPath());
            allBitmap.add(bitmap);
        }
    }

    public interface onClickListenerSong{
        void onClick(View view, int position);
    }

}
