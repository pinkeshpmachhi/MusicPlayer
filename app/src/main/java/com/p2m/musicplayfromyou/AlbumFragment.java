package com.p2m.musicplayfromyou;

import static com.p2m.musicplayfromyou.MainActivity.songsList;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.p2m.musicplayfromyou.Adapter.ALbumAdapter;
import com.p2m.musicplayfromyou.Adapter.SongsAlbumAdapter;
import com.p2m.musicplayfromyou.Fragment.AlbumDetailFragment;

import java.util.ArrayList;
import java.util.Objects;

public class AlbumFragment extends Fragment implements SongsAlbumAdapter.onClickListenerSong {
    public SongsAlbumAdapter adapter;
    public RecyclerView recyclerView;
    public static ArrayList<MusicFiles> albums = new ArrayList<>();
    public ArrayList <MusicFiles> songsAl  = new ArrayList<>();


    public AlbumFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album,container, false);

        recyclerView = view.findViewById(R.id.recyclerViewForAlbumSong);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        albums.clear();
        for (MusicFiles single : songsList){
//            String album = single.getAlbum();

            if (!albums.contains(single)){
                albums.add(single);
            }

        }

        adapter = new SongsAlbumAdapter(albums, getContext(), this);
        recyclerView.setAdapter(adapter);
    }

    public static ArrayList<MusicFiles> getAllAudio(Context context) {
        ArrayList<MusicFiles> tempFiles = new ArrayList<>();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = new String[]{
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA, //for path
                MediaStore.Audio.Media.ARTIST
        };

        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String album = cursor.getString(0);
                String title = cursor.getString(1);
                String duration = cursor.getString(2);
                String data = cursor.getString(3);
                String artist = cursor.getString(4);

                MusicFiles musicFiles = new MusicFiles(data, title, artist, album, duration);
                tempFiles.add(musicFiles);
            }
            cursor.close();
        }

        return tempFiles;
    }

    @Override
    public void onClick(View view, int position) {
        AlbumDetailFragment albumDetailFragment = new AlbumDetailFragment();

        Bundle bundle = new Bundle();
        bundle.putString("albumName",albums.get(position).getAlbum());
        albumDetailFragment.setArguments(bundle);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragContainer, albumDetailFragment);;
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    /*
    Fragment fragment = new tasks();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
     */
}