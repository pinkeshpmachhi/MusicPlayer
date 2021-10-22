package com.p2m.musicplayfromyou;

import static com.google.android.material.internal.ContextUtils.getActivity;
import static com.p2m.musicplayfromyou.MyService.albumDetailsSongs;
import static com.p2m.musicplayfromyou.MyService.listSongs;
import static com.p2m.musicplayfromyou.MyService.mediaPlayer;
import static com.p2m.musicplayfromyou.MyService.nextLive;
import static com.p2m.musicplayfromyou.MyService.prevLive;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.media2.exoplayer.external.C;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadata;
import android.media.MediaMetadataRetriever;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;
import com.p2m.musicplayfromyou.Fragment.MusicFragment;
import com.p2m.musicplayfromyou.Fragment.ProfleFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import static com.p2m.musicplayfromyou.MyService.mediaPlayer;
import static com.p2m.musicplayfromyou.MyService.nA;
import static com.p2m.musicplayfromyou.MyService.setCcc;
import static com.p2m.musicplayfromyou.MyService.xH;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity implements ServiceConnection, MusicAdapter.onClickInterface,SearchMusicAdapter.onClickInterface, MediaPlayer.OnCompletionListener, SearchView.OnQueryTextListener {
    public static final int MANAGE_EXTERNAL_STORAGE_REQUEST_CODE = 1011;
    public static ArrayList<MusicFiles> songs = new ArrayList<>();
    private RecyclerView recyclerView;
    public static ArrayList<MusicFiles> songsList = new ArrayList<>();
    private MusicAdapter adapter;
    public static final String TAG = "PINKESH";
    public static MutableLiveData<List<MusicFiles>> lives = new MutableLiveData<>();
    Context context;
    Resources resources;
    public ProfleFragment profleFragment;
    public MusicFragment musicFragment;
    public BottomNavigationView bottomNavigationView;
    public View fragment;
    public static ImageView albumArtIv, shuffleIv, prevTenSecIv,
            prevSkipIv, playIv, nextSkipIv, fastForTenIv, repeatIv;
    public SeekBar seekBar;
    public TextView songNameTv, artistNameTv, timeElapsedTv, totalTimeTv;
    public Uri uri;
    public Handler handler = new Handler();
    public static MyService myService;
    public static boolean xn = false;
    Noticlass noticlass;
    public boolean isBound = false;
    public static MutableLiveData<Integer> positionLiveData = new MutableLiveData<>();
    public ImageView albumArtFiXIv;
    public static FloatingActionButton playBtnFixIv;
    public TextView nameFixTv, artistFixTv;
    public static ArrayList<MusicFiles> tempo = new ArrayList<>();
    public static boolean searchPositionBoolean = false;
    public AlbumFragment albumFragment;
    public ArtistFragment artistFragment;

    @SuppressLint("RestrictedApi")
    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setElevation(0);

        Window window = getActivity(this).getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(getActivity(this),R.color.main));


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.MANAGE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.MANAGE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MANAGE_EXTERNAL_STORAGE_REQUEST_CODE);
        } else {
        }

        setContentView(R.layout.activity_main);

        setCcc(false);

        SharedPreferences sh = MainActivity.this.getApplicationContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        int a = Integer.parseInt(sh.getString("position", "0"));

        myService = new MyService();
        myService.setPosition(a);

        mediaPlayer.setOnCompletionListener(this);

        profleFragment = new ProfleFragment();
        musicFragment = new MusicFragment();
        bottomNavigationView = findViewById(R.id.bottomNavView);
        fragment = findViewById(R.id.fragContainer);

        context = getBaseContext();
        resources = getResources();

        recyclerView = findViewById(R.id.recyclerViewSongsFrag);

        songs = getAllAudio(this);
        lives.setValue(songs);
        songsList.addAll(songs);
        tempo.addAll(songs);


        recyclerView.setHasFixedSize(true);
        if (songsList.size() > 0) {
            adapter = new MusicAdapter(this, songsList, this);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.musicMenu) {
                    recyclerView.setVisibility(View.GONE);
                    fragment.setVisibility(View.VISIBLE);
                    setFragment(musicFragment);
                } else if (id == R.id.offlineMenu) {
                    recyclerView.setVisibility(View.VISIBLE);
                    fragment.setVisibility(View.GONE);
                } else if (id == R.id.profileMenu) {
                    recyclerView.setVisibility(View.GONE);
                    fragment.setVisibility(View.VISIBLE);
                    setFragment(profleFragment);
                }
                return true;
            }
        });

        positionLiveData.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer != null) {
                    myService.setPosition(integer);
                    Context context = MainActivity.this.getApplicationContext();
                    someClickMethod(context);
                }
            }
        });

        context = MainActivity.this.getApplicationContext();
        resources = getResources();
        noticlass = new Noticlass();
        setCcc(true);


        if (listSongs.size() <= songs.size())
            listSongs.addAll(songs);
        myService = new MyService();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 3000);

        initViews();
//        int positionOk = getIntent().getIntExtra("position", -1);
        if (listSongs.size() == songsList.size()) {
            getIntentMehtod(myService.getPosition(context));
        }

        nextLive.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Context context = MainActivity.this.getApplicationContext();
                nextThreadMethod(context);
                Log.d("PINKESH", "notificationActions: Next:::::::     Touched");
            }
        });

        prevLive.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Context context = MainActivity.this.getApplicationContext();
                prevThreadMethod(context);
                Log.d("PINKESH", "notificationActions: Prev:::::::     Touched");
            }
        });

        xH.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                nextNoti();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (mediaPlayer != null) {
                    seekBar.setProgress(progress);
                    timeElapsedTv.setText(getTime(progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mediaPlayer != null) {
                    int time = seekBar.getProgress();
                    mediaPlayer.seekTo(time);

                    timeElapsedTv.setText(getTime(time));
                    myService.initForNotification(context);
                }
            }
        });


        String code = getIntent().getStringExtra("c");

        if (code != null){

            if (code.equals("album")){
                int position = getIntent().getIntExtra("positionAlbum",0);
                forAlbumFragment(position);
            }
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void forAlbumFragment(int position) {
        Log.d(TAG, "forAlbumFragment: Touched::");
        MyService myService = new MyService();

        Context context = MainActivity.this.getApplicationContext();

        myService.setPosition(position);

        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
        }

        mediaPlayer = MediaPlayer.create(context, Uri.parse(albumDetailsSongs.get(myService.getPosition(context)).getPath()));
        mediaPlayer.start();
        playIv.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
        playBtnFixIv.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);

        MusicFiles single = albumDetailsSongs.get(myService.getPosition(context));
        String name = single.getTitle();
        String artist = single.getArtist();

        Bitmap bitmap = getAlbumArt(single.getPath());
        myService.initForNotification(context);

        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    songNameTv.setText(name);
                    nameFixTv.setText(name);
                    artistNameTv.setText(artist);
                    artistFixTv.setText(artist);
                    Glide.with(context).asBitmap().load(bitmap).placeholder(R.drawable.musicimage).into(albumArtIv);
                    Glide.with(context).asBitmap().load(bitmap).placeholder(R.drawable.musicimage).into(albumArtFiXIv);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        seekMethod(mediaPlayer, context);
        myService.initForNotification(context);
        mediaPlayer.setOnCompletionListener(this);
        searchPositionBoolean = false;
        next();
        play();
        prev();

        Log.d("PINKESH", "onClick: Position At fragment:::::     " );
    }

    public void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragContainer, fragment).addToBackStack(null).commit();
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getIntentMehtod(Integer pos) {

        myService.setPosition(pos);
        Context context = MainActivity.this.getApplicationContext();

        if (listSongs != null) {
            playIv.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
            playBtnFixIv.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
            try {
                uri = Uri.parse(listSongs.get(myService.getPosition(context)).getTitle());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        myService = new MyService();

        try {
            mediaPlayer.pause();
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
        } catch (Exception e) {
            e.printStackTrace();
        }


        Context context1 = MainActivity.this.getApplicationContext();
        try {
            assert listSongs != null;
            mediaPlayer = MediaPlayer.create(MainActivity.this, Uri.parse(listSongs.get(myService.getPosition(context1)).getPath()));
            mediaPlayer.start();
            metaData(mediaPlayer, context);
            seekMethod(mediaPlayer, context);
            mediaPlayer.pause();
            play();
            next();
            prev();
            xn = true;
            context = this.getApplicationContext();
            myService.initForNotification(context);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void next() {
        nextSkipIv.setOnClickListener(v -> {
            Context context = MainActivity.this.getApplicationContext();
            nextThreadMethod(context);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void prev() {
        prevSkipIv.setOnClickListener(v -> {
            Context context = MainActivity.this.getApplicationContext();
            prevThreadMethod(context);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void play() {
        playIv.setOnClickListener(v -> {
            playPauseThreadMethod();
        });
        playBtnFixIv.setOnClickListener(v -> {
            playPauseThreadMethod();
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextThreadMethod(Context context) {

        int i = myService.getPosition(context) + 1;
        if (listSongs.size() > i) {
            myService = new MyService();

            myService.setPosition(i);
            someClickMethod(context);

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void prevThreadMethod(Context context) {

        Context context1 = MainActivity.this.getApplicationContext();
        int i = myService.getPosition(context1) - 1;
        if (i > 0) {
            myService = new MyService();

            myService.setPosition(i);
            someClickMethod(context);

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void playPauseThreadMethod() {
        if (mediaPlayer.isPlaying()) {
            myService = new MyService();
            mediaPlayer.pause();
            playIv.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
            playBtnFixIv.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
        } else {
            myService = new MyService();
            mediaPlayer.start();
            playIv.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
            playBtnFixIv.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
        }
        Context context = this.getApplicationContext();
        myService.initForNotification(context);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void someClickMethod(Context context) {

        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
        }

        mediaPlayer = MediaPlayer.create(context, Uri.parse(listSongs.get(myService.getPosition(context)).getPath()));
        mediaPlayer.start();
        playIv.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
        playBtnFixIv.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
        metaData(mediaPlayer, context);
        seekMethod(mediaPlayer, context);
//        Context context = this.getApplicationContext();
        myService.initForNotification(context);
        mediaPlayer.setOnCompletionListener(this);
    }


    private void initViews() {

        albumArtIv = findViewById(R.id.albumArtIv);
        shuffleIv = findViewById(R.id.shuffleIv);
        prevTenSecIv = findViewById(R.id.fastRevForTenSec);
        prevSkipIv = findViewById(R.id.skipPrevIv);
        playIv = findViewById(R.id.playPause);
        nextSkipIv = findViewById(R.id.skipNextIv);
        fastForTenIv = findViewById(R.id.fastForTenSec);
        repeatIv = findViewById(R.id.repeatIv);
        seekBar = findViewById(R.id.seekBarPlayerFra);
        songNameTv = findViewById(R.id.songNameTvPlayer);
        artistNameTv = findViewById(R.id.artistNameTvPlayer);
        timeElapsedTv = findViewById(R.id.startTimeTvv);
        totalTimeTv = findViewById(R.id.totalTimeTv);
        albumArtFiXIv = findViewById(R.id.imageFixed);
        playBtnFixIv = findViewById(R.id.playBtnFixed);
        nameFixTv = findViewById(R.id.titleFixed);
        artistFixTv = findViewById(R.id.artistFixed);
    }

    public String getTime(long time) {
        final long minutes = (time / 1000) / 60;
        final int seconds = (int) ((time / 1000) % 60);
        return minutes + ":" + seconds;
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


    public String formattedTime(long millis) {
        String totalout = "";
        String totalNew = "";

        String seconds = String.valueOf(millis % 60);
        String minutes = String.valueOf(millis / 60);

        totalout = minutes + ":" + seconds;
        totalNew = minutes + ":" + "0" + seconds;

        if (seconds.length() == 1) {
            return totalNew;
        } else
            return totalout;

    }


    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        MyService.MyBinder myBinder = (MyService.MyBinder) service;
        myService = myBinder.getService();
        mediaPlayer.setOnCompletionListener(this);

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        myService = null;
    }

    @Override
    protected void onStart() {
        super.onStart();
        setCcc(false);
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
        bindService(intent, this, BIND_AUTO_CREATE);
        try {
            mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
            myService = new MyService();
            Toast.makeText(this, "Service connected", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.d(TAG, "onStart: ERROR      :: " + e.getMessage());
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void seekMethod(MediaPlayer mediaPlayer, Context context) {

        int totalT = mediaPlayer.getDuration();

        try {
            seekBar = findViewById(R.id.seekBarPlayerFra);
            seekBar.setMax(totalT);
            seekBar.setProgress(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    totalTimeTv.setText(getTime(totalT));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        MainActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                updateMethod(mediaPlayer);
                handler.postDelayed(this, 1000);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateMethod(MediaPlayer mx) {

        try {
            if (mx != null && mx.isPlaying()) {
                seekBar.setProgress(mx.getCurrentPosition());
                timeElapsedTv.setText(getTime(mx.getCurrentPosition()));
            }
        } catch (Exception e) {
            Log.d("FFF", "updateMethod: " + e.getMessage());
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void metaData(MediaPlayer mediaPlayer, Context context) {
        MusicFiles single = listSongs.get(myService.getPosition(context));
        String name = single.getTitle();
        String artist = single.getArtist();

        Bitmap bitmap = getAlbumArt(single.getPath());
        myService.initForNotification(context);

        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    songNameTv.setText(name);
                    nameFixTv.setText(name);
                    artistNameTv.setText(artist);
                    artistFixTv.setText(artist);
                    Glide.with(MainActivity.this).asBitmap().load(bitmap).placeholder(R.drawable.musicimage).into(albumArtIv);
                    Glide.with(MainActivity.this).asBitmap().load(bitmap).placeholder(R.drawable.musicimage).into(albumArtFiXIv);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextNoti() {
        Resources resources = context.getResources();
        MainActivity.playIv.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_pause_circle_filled_24, null));
        playBtnFixIv.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_pause_circle_filled_24, null));
        Context context = MainActivity.this.getApplicationContext();
        nextThreadMethod(context);

        MusicFiles single = listSongs.get(myService.getPosition(context));
        String name = single.getTitle();
        String artist = single.getArtist();

        myService.initForNotification(context);
        songNameTv.setText(name);
        nameFixTv.setText(name);
        artistNameTv.setText(artist);
        artistFixTv.setText(artist);
        int totalT = mediaPlayer.getDuration();

        try {
            seekBar = findViewById(R.id.seekBarPlayerFra);
            seekBar.setMax(totalT);
            seekBar.setProgress(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        MutableLiveData<Integer> kk = new MutableLiveData<>();

        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                kk.setValue(mediaPlayer.getCurrentPosition());
                new Handler().postDelayed(this, 1000);
            }
        });


        kk.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer aLong) {
                Log.d("TAGG", "onChanged: :::::: " + aLong);
                seekBar.setProgress(aLong);
                totalTimeTv.setText(getTime(totalT));
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)

    public void finishMeth() {
        this.finish();
    }

    public void setBound(boolean bound) {
        isBound = bound;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view, Integer position) {

        if (searchPositionBoolean){
           Context context = MainActivity.this.getApplicationContext();
            myService.setPosition(position);

            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.release();
            }

            mediaPlayer = MediaPlayer.create(context, Uri.parse(tempo.get(myService.getPosition(context)).getPath()));
            mediaPlayer.start();
            playIv.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
            playBtnFixIv.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);

            MusicFiles single = tempo.get(myService.getPosition(context));
            String name = single.getTitle();
            String artist = single.getArtist();

            Bitmap bitmap = getAlbumArt(single.getPath());
            myService.initForNotification(context);

            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        songNameTv.setText(name);
                        nameFixTv.setText(name);
                        artistNameTv.setText(artist);
                        artistFixTv.setText(artist);
                        Glide.with(MainActivity.this).asBitmap().load(bitmap).placeholder(R.drawable.musicimage).into(albumArtIv);
                        Glide.with(MainActivity.this).asBitmap().load(bitmap).placeholder(R.drawable.musicimage).into(albumArtFiXIv);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });

            seekMethod(mediaPlayer, context);
            myService.initForNotification(context);
            mediaPlayer.setOnCompletionListener(this);
            searchPositionBoolean = false;

        }else {
            Log.d(TAG, "onClick: Position" + position);
            Context context = MainActivity.this.getApplicationContext();
            myService.setPosition(position);
            someClickMethod(context);
        }




    }

    @Override
    protected void onStop() {
        super.onStop();
        setCcc(true);
    }

    @Override
    protected void onDestroy() {
        setCcc(true);
        super.onDestroy();
        Toast.makeText(this, "Main Destroyed", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onDestroy: Main Destroyed");

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("position", String.valueOf(myService.getPosition(MainActivity.this.getApplicationContext())));
        myEdit.apply();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.d(TAG, "onCompletion: Touch::::::");

        Context context = MainActivity.this.getApplicationContext();

        nextThreadMethod(context);

//        Random random = new Random();
//        int i = random.nextInt(listSongs.size()-1);
//        if (listSongs.size()>i) {
////            setPosition(i);
//
////            Context context = MainActivity.this.getApplicationContext();
//            if (isCcc()){
//                xH.setValue(1);
//                myService.nnn(context);
//            }else {
//                myService.setPosition(i);
//                Log.d(TAG, "onCompletion: Touch::::::");
//
//
//                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
//                    mediaPlayer.stop();
//                    mediaPlayer.reset();
//                    mediaPlayer.release();
//                }
//
//                mediaPlayer = MediaPlayer.create(context, Uri.parse(listSongs.get(myService.getPosition(context)).getPath()));
//                mediaPlayer.start();
//                myService.initForNotification(context);
//            }
//
//
//        }else {
//            Toast.makeText(this, "No more songs!", Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.serchMenu);
        SearchView searchView1 = (SearchView) menuItem.getActionView();
        searchView1.setOnQueryTextListener(this);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.serchMenu){
            searchMethod();
        }else if (id == R.id.dateMenu){
            dateMenuMethod();
        }else  if (id == R.id.nameMenuCHoose){
            nameMenuMethod();
        }else if (id == R.id.sizeMenu){
            sizeMenuMethod();
        }else if (id == R.id.albumMenu){
            albumMenuMethod();
        }else if (id == R.id.artistMenu){
            artistMenuMethod();
        }

        return super.onOptionsItemSelected(item);
    }

    public void searchMethod(){
    }

    public void dateMenuMethod(){
        sortByAllMedia("date");
    }

    public void nameMenuMethod() {
        sortByAllMedia("name");
    }

    public void sizeMenuMethod(){
        sortByAllMedia("size");
    }

    public void albumMenuMethod(){
        sortByAllMedia("album");
    }

    public void artistMenuMethod(){
        sortByAllMedia("artist");
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String userInput = newText.toLowerCase();
        tempo = new ArrayList<>();
        for (MusicFiles single : listSongs){
            if (single.getTitle().toLowerCase().contains(userInput)){
                tempo.add(single);
            }
        }
        searchPositionBoolean = true;
        adapter.updateList(tempo);

        return true;
    }

    public void sortByAllMedia(String name){

        String sortBy = "";
        switch (name){
            case "album":
                albumFragment = new AlbumFragment();
                setFragment(albumFragment);
                break;

            case "date":
                sortBy = MediaStore.Audio.Media.DATE_ADDED+" DESC";
                break;

            case "size":
                sortBy = MediaStore.Audio.Media.SIZE+" DESC";
                break;

            case "artist":
                artistFragment = new ArtistFragment();
                setFragment(artistFragment);
                break;

            case "name":
                sortBy = MediaStore.Audio.Media.TITLE;
                break;
        }
        ArrayList<MusicFiles> tempFiles = new ArrayList<>();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        String[] projection = new String[]{
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA, //for path
                MediaStore.Audio.Media.ARTIST
        };

        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, sortBy);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String album = cursor.getString(0);
                String title = cursor.getString(1);
                String duration = cursor.getString(2);
                String data = cursor.getString(3);
                String artist = cursor.getString(4);

                MusicFiles musicFiles = new MusicFiles(data, title, artist, album, duration);
                tempFiles.add(musicFiles);
                Log.d(TAG, "sortByAllMedia: songs::  "+title);
            }
            cursor.close();

            listSongs.clear();
            listSongs.addAll(tempFiles);
            adapter = new MusicAdapter(this,tempFiles,this);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

    }





























}




















