package com.p2m.musicplayfromyou;

import static com.p2m.musicplayfromyou.MainActivity.searchPositionBoolean;
import static com.p2m.musicplayfromyou.MainActivity.tempo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadata;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyService extends Service{
    public static final String TAG="PINKESH";
    public static int position = 0;
    public  static ArrayList<MusicFiles> listSongs = new ArrayList<>();
    public static MediaPlayer mediaPlayer = new MediaPlayer();
    public static MutableLiveData<Integer> li = new MutableLiveData<>();
    public static MutableLiveData<Integer> nA = new MutableLiveData<>();
    public static boolean ccc = false;
    public static MediaSession mediaSession;
    public static Notification.MediaStyle mediaStyle;
    public static Notification notification;
    public static NotificationManager notificationManager;
    public static final String CHANNEL_ONE = "channel one only for testing purpose";
    public static final int NOTIFICATION_MEDIA_PLAYING_SONG_ID = 1000;

    public static final int PREV_CODE = 101;
    public static final int PLAY_CODE = 102;
    public static final int NEXT_CODE = 103;
    public static final int STOP_CODE = 104;
    public static MutableLiveData<Integer> nextLive = new MutableLiveData<>();
    public static MutableLiveData<Integer> prevLive = new MutableLiveData<>();
    int akm = 0;
    IBinder mBinder = new MyBinder();
    public static MutableLiveData<Integer> xH= new MutableLiveData<>();
    int jk = 0;
    public MainActivity mainActivity = new MainActivity();
    public static SharedPreferences sharedPreferences;
    public static ArrayList<MusicFiles> albumDetailsSongs = new ArrayList<>();

    public class MyBinder extends Binder {
        public MyService getService() {
            return MyService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        mediaPlayer = new MediaPlayer();
        mainActivity = new MainActivity();
        mediaPlayer.setOnCompletionListener(mainActivity);
        sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        return mBinder;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        int a = intent.getIntExtra("P",0);

        mainActivity = new MainActivity();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Context context = this.getApplicationContext();
            notificationActions(a, context);
        }
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = getSharedPreferences("MySharedPref",Context.MODE_PRIVATE);

    }

    public int getPosition(Context context) {
        return position;
    }

    public void setPosition(int positionn) {
        position = positionn;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void notificationActions(int a, Context context){
        mainActivity = new MainActivity();
        if ( a == PREV_CODE ) {
            if (getPosition(context) > 0) {
                if (isCcc()){
                    setPosition(getPosition(context) - 1);
                    Context context1 = this.getApplicationContext();
                    simpleMeth(context1);
                }else {
                    try {
                        prevLive.setValue(1);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }

            } else {
                Toast.makeText(context, "No more songs!", Toast.LENGTH_SHORT).show();
            }
        }

        if (a == PLAY_CODE) {
            try {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    Resources resources = context.getResources();
                    MainActivity.playIv.setImageDrawable(ResourcesCompat.getDrawable(resources,R.drawable.ic_baseline_play_circle_filled_24,null));
                    MainActivity.playBtnFixIv.setImageDrawable(ResourcesCompat.getDrawable(resources,R.drawable.ic_baseline_play_circle_filled_24,null));

                } else {
                    mediaPlayer.start();
                    Resources resources = context.getResources();
                    MainActivity.playIv.setImageDrawable(ResourcesCompat.getDrawable(resources,R.drawable.ic_baseline_pause_circle_filled_24,null));
                    MainActivity.playBtnFixIv.setImageDrawable(ResourcesCompat.getDrawable(resources,R.drawable.ic_baseline_pause_circle_filled_24,null));

                }
                initForNotification(context);
            }catch (Exception e){
                Log.d("ffff", "onClick: "+e.getLocalizedMessage()+"  :::::::::::        "+e.getMessage());
            }
        }

        if (a == NEXT_CODE) {
            if ((getPosition(context) - 2) <listSongs.size()) {


                if (isCcc()){
                    setPosition(getPosition(context) + 1);
                    Log.d(TAG, "notificationActions: "+isCcc());
                    Context context1 = this.getApplicationContext();
                    simpleMeth(context1);
                }else {
                    try {
                        nextLive.setValue(2);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }


            } else {
                Toast.makeText(context, "No more songs!", Toast.LENGTH_SHORT).show();
            }
        }

        if (a == STOP_CODE) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            mainActivity = new MainActivity();
            mainActivity.finishMeth();
            MainActivity mainActivity = new MainActivity();
            mainActivity.finish();
            mainActivity.setBound(false);
            stopSelf();
            notificationManager.cancel(NOTIFICATION_MEDIA_PLAYING_SONG_ID);

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void initForNotification(Context context) {

        if (searchPositionBoolean){
            mediaSession= new MediaSession(context, "testing");
            mediaStyle = new Notification.MediaStyle().setMediaSession(mediaSession.getSessionToken());

            NotificationChannel channel = new NotificationChannel(CHANNEL_ONE, "Testing channel", NotificationManager.IMPORTANCE_MIN);
            notificationManager = (NotificationManager) context.getSystemService(MainActivity.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);

            Bitmap smallIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.musicimage);

            try {
                MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                mediaMetadataRetriever.setDataSource(tempo.get(getPosition(context)).getPath());
                byte[] arr = mediaMetadataRetriever.getEmbeddedPicture();
                smallIcon = BitmapFactory.decodeByteArray(arr, 0, arr.length);

            }catch (Exception e){
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            String contentTitle = tempo.get(getPosition(context)).getTitle();
            String contentText = tempo.get(getPosition(context)).getArtist();

            Intent intentPrev = new Intent(context, MyService.class);
            intentPrev.putExtra("P",PREV_CODE);
            PendingIntent pendingIntentPrev = PendingIntent.getService(context, PREV_CODE, intentPrev, 0);
            Notification.Action prev = new Notification.Action.Builder(R.drawable.ic_baseline_skip_previous_24,"Previous", pendingIntentPrev).build();


            Intent intentPlay = new Intent(context, MyService.class);
            intentPlay.putExtra("P",PLAY_CODE);
            PendingIntent pendingIntentPlay = PendingIntent.getService(context, PLAY_CODE, intentPlay, 0);
            Notification.Action play;
            if (mediaPlayer.isPlaying()){
                play = new Notification.Action.Builder(R.drawable.ic_baseline_pause_circle_filled_24,"Play",pendingIntentPlay).build();
            }else {
                play = new Notification.Action.Builder(R.drawable.ic_baseline_play_circle_filled_24,"Play",pendingIntentPlay).build();
            }

            Intent intentNext = new Intent(context, MyService.class);
            intentNext.putExtra("P",NEXT_CODE);
            PendingIntent pendingIntentNext = PendingIntent.getService(context, NEXT_CODE, intentNext, 0);
            Notification.Action next = new Notification.Action.Builder(R.drawable.ic_baseline_skip_next_24, "Next", pendingIntentNext).build();

            Intent intentStop = new Intent(context, MyService.class);
            intentStop.putExtra("P",STOP_CODE);
            PendingIntent pendingIntentStop = PendingIntent.getService(context, STOP_CODE, intentStop, 0);
            Notification.Action stop = new Notification.Action.Builder(R.drawable.ic_baseline_stop_24, "Stop", pendingIntentStop).build();

            notification = new Notification.Builder(context, CHANNEL_ONE)
                    .setSmallIcon(R.drawable.musicimage)
                    .setContentTitle(contentTitle)
                    .setContentText(contentText)
                    .setLargeIcon(smallIcon)
                    .setActions(prev, play, next, stop)
                    .setStyle(mediaStyle)
                    .setOngoing(true)
                    .setAutoCancel(true)
                    .build();

            mediaSession.setMetadata(
                    new MediaMetadata.Builder()
                            .putString(MediaMetadata.METADATA_KEY_TITLE, tempo.get(getPosition(context)).getTitle())
                            .putString(MediaMetadata.METADATA_KEY_ARTIST, tempo.get(getPosition(context)).getArtist())
                            .putString(MediaMetadata.METADATA_KEY_ALBUM_ART_URI, tempo.get(getPosition(context)).getPath())
                            .putLong(MediaMetadata.METADATA_KEY_DURATION, mediaPlayer.getDuration()) // 4
                            .build()
            );

            mediaSession.setPlaybackState(
                    new PlaybackState.Builder().setState(PlaybackState.STATE_PLAYING, mediaPlayer.getCurrentPosition(), 1.0f).build()
            );

            notificationManager.notify(NOTIFICATION_MEDIA_PLAYING_SONG_ID, notification);
        }else {
            mediaSession= new MediaSession(context, "testing");
            mediaStyle = new Notification.MediaStyle().setMediaSession(mediaSession.getSessionToken());

            NotificationChannel channel = new NotificationChannel(CHANNEL_ONE, "Testing channel", NotificationManager.IMPORTANCE_MIN);
            notificationManager = (NotificationManager) context.getSystemService(MainActivity.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);

            Bitmap smallIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.musicimage);

            try {
                MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                mediaMetadataRetriever.setDataSource(listSongs.get(getPosition(context)).getPath());
                byte[] arr = mediaMetadataRetriever.getEmbeddedPicture();
                smallIcon = BitmapFactory.decodeByteArray(arr, 0, arr.length);

            }catch (Exception e){
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            String contentTitle = listSongs.get(getPosition(context)).getTitle();
            String contentText = listSongs.get(getPosition(context)).getArtist();

            Intent intentPrev = new Intent(context, MyService.class);
            intentPrev.putExtra("P",PREV_CODE);
            PendingIntent pendingIntentPrev = PendingIntent.getService(context, PREV_CODE, intentPrev, 0);
            Notification.Action prev = new Notification.Action.Builder(R.drawable.ic_baseline_skip_previous_24,"Previous", pendingIntentPrev).build();


            Intent intentPlay = new Intent(context, MyService.class);
            intentPlay.putExtra("P",PLAY_CODE);
            PendingIntent pendingIntentPlay = PendingIntent.getService(context, PLAY_CODE, intentPlay, 0);
            Notification.Action play;
            if (mediaPlayer.isPlaying()){
                play = new Notification.Action.Builder(R.drawable.ic_baseline_pause_circle_filled_24,"Play",pendingIntentPlay).build();
            }else {
                play = new Notification.Action.Builder(R.drawable.ic_baseline_play_circle_filled_24,"Play",pendingIntentPlay).build();
            }

            Intent intentNext = new Intent(context, MyService.class);
            intentNext.putExtra("P",NEXT_CODE);
            PendingIntent pendingIntentNext = PendingIntent.getService(context, NEXT_CODE, intentNext, 0);
            Notification.Action next = new Notification.Action.Builder(R.drawable.ic_baseline_skip_next_24, "Next", pendingIntentNext).build();

            Intent intentStop = new Intent(context, MyService.class);
            intentStop.putExtra("P",STOP_CODE);
            PendingIntent pendingIntentStop = PendingIntent.getService(context, STOP_CODE, intentStop, 0);
            Notification.Action stop = new Notification.Action.Builder(R.drawable.ic_baseline_stop_24, "Stop", pendingIntentStop).build();

            notification = new Notification.Builder(context, CHANNEL_ONE)
                    .setSmallIcon(R.drawable.musicimage)
                    .setContentTitle(contentTitle)
                    .setContentText(contentText)
                    .setLargeIcon(smallIcon)
                    .setActions(prev, play, next, stop)
                    .setStyle(mediaStyle)
                    .setOngoing(true)
                    .setAutoCancel(true)
                    .build();

            mediaSession.setMetadata(
                    new MediaMetadata.Builder()
                            .putString(MediaMetadata.METADATA_KEY_TITLE, listSongs.get(getPosition(context)).getTitle())
                            .putString(MediaMetadata.METADATA_KEY_ARTIST, listSongs.get(getPosition(context)).getArtist())
                            .putString(MediaMetadata.METADATA_KEY_ALBUM_ART_URI, listSongs.get(getPosition(context)).getPath())
                            .putLong(MediaMetadata.METADATA_KEY_DURATION, mediaPlayer.getDuration()) // 4
                            .build()
            );

            mediaSession.setPlaybackState(
                    new PlaybackState.Builder().setState(PlaybackState.STATE_PLAYING, mediaPlayer.getCurrentPosition(), 1.0f).build()
            );

            notificationManager.notify(NOTIFICATION_MEDIA_PLAYING_SONG_ID, notification);
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void simpleMeth(Context context){
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
        }

        mediaPlayer = MediaPlayer.create(context, Uri.parse(listSongs.get(getPosition(context)).getPath()));
        mediaPlayer.start();
        initForNotification(context);
        Random random = new Random();
        int a = random.nextInt();

    }

    public static boolean isCcc() {
        return ccc;
    }

    public static void setCcc(boolean ccc) {
        MyService.ccc = ccc;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void  nnn(Context context){
        int i = getPosition(context)+1;
        if (listSongs.size()>i){
            MainActivity mainActivity = new MainActivity();
            setPosition(i);
            Log.d(TAG, "nnn: POSITION::::::::      "+getPosition(context));
            mainActivity.someClickMethod(context);

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

    public int getRandom(){
        Random random = new Random();
        return random.nextInt();
    }
}