package com.p2m.musicplayfromyou;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchMusicAdapter extends RecyclerView.Adapter<SearchMusicAdapter.MyViewHolder> {
    private static Context context;
    private ArrayList<MusicFiles> list;
    private ArrayList<Bitmap> artList = new ArrayList<>();
    public static onClickInterface onClickInterfaceOBJ;

    public SearchMusicAdapter(Context context, ArrayList<MusicFiles> list, onClickInterface onClickInterface1) {
        this.context = context;
        this.list = list;
        this.onClickInterfaceOBJ = onClickInterface1;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_song_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MusicFiles single = list.get(position);

        holder.title.setText(single.getTitle());
        holder.artist.setText(single.getTitle());

        try {

            Glide.with(context).asBitmap().load(R.drawable.musicimage).into(holder.imageView);

        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        CircleImageView imageView;
        TextView title, artist;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.cIv);
            title = itemView.findViewById(R.id.titleTv);
            artist = itemView.findViewById(R.id.artistTv);
            itemView.setOnClickListener(this);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickInterfaceOBJ.onClick(v,getAdapterPosition());
                }
            });

        }

        @Override
        public void onClick(View v) {
            onClickInterfaceOBJ.onClick(v, getAdapterPosition());
            Log.d("PINKESH", "onClick: Position:::::::        "+getAdapterPosition());
        }
    }

    private Bitmap getAlbumArt(String path) {

        try {
            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(path);
            byte[] data = mediaMetadataRetriever.getEmbeddedPicture();
            mediaMetadataRetriever.release();
            if (data != null) {
                return BitmapFactory.decodeByteArray(data, 0, data.length);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public interface onClickInterface {
        void onClick(View view, Integer position);
    }

    public void updateList(ArrayList<MusicFiles> newList){
        list = new ArrayList<>();
        list.addAll(newList);
        notifyDataSetChanged();
    }

}
