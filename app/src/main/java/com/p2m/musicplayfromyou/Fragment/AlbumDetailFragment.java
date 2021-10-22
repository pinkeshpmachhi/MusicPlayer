package com.p2m.musicplayfromyou.Fragment;

import static com.p2m.musicplayfromyou.MainActivity.albumArtIv;
import static com.p2m.musicplayfromyou.MainActivity.myService;
import static com.p2m.musicplayfromyou.MainActivity.playBtnFixIv;
import static com.p2m.musicplayfromyou.MainActivity.playIv;
import static com.p2m.musicplayfromyou.MyService.albumDetailsSongs;
import static com.p2m.musicplayfromyou.MyService.listSongs;
import static com.p2m.musicplayfromyou.MyService.mediaPlayer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.p2m.musicplayfromyou.Adapter.AlbumDetailAdapter;
import com.p2m.musicplayfromyou.Adapter.SongsAlbumAdapter;
import com.p2m.musicplayfromyou.MainActivity;
import com.p2m.musicplayfromyou.MusicAdapter;
import com.p2m.musicplayfromyou.MusicFiles;
import com.p2m.musicplayfromyou.MyService;
import com.p2m.musicplayfromyou.R;

import java.util.ArrayList;
import java.util.Objects;


public class AlbumDetailFragment extends Fragment implements AlbumDetailAdapter.onClickInterface{
    AlbumDetailAdapter adapter;
    RecyclerView recyclerView;
    ImageView imageView;

    public AlbumDetailFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album_detail, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewAlbumDetail);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        assert getArguments() != null;
        String albumName = getArguments().getString("albumName","Null");

        imageView = view.findViewById(R.id.albumImageDetail);

        albumDetailsSongs = new ArrayList<>();
        for (MusicFiles musicFiles : listSongs) {
            String albumNameCome = musicFiles.getAlbum();

            if (albumName.equals(albumNameCome)){
                albumDetailsSongs.add(musicFiles);
            }
        }

        Log.d("PINKESH", "onCreate: SIze ::::::       "+albumDetailsSongs.size());

        Bitmap bitmap = getAlbumArt(albumDetailsSongs.get(0).getPath());
        Glide.with(requireContext()).load(bitmap).placeholder(R.drawable.musicimage).into(imageView);

        adapter = new AlbumDetailAdapter(getContext(), albumDetailsSongs, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view, Integer position) {
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.putExtra("c","album");
        intent.putExtra("positionAlbum",position);
        requireContext().startActivity(intent);
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
}