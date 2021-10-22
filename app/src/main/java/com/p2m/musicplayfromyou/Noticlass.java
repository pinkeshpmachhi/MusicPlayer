package com.p2m.musicplayfromyou;

import static com.p2m.musicplayfromyou.ApplicationClass.NEXT_CODE;
import static com.p2m.musicplayfromyou.ApplicationClass.NOTIFICATION_MEDIA_PLAYING_SONG_ID;
import static com.p2m.musicplayfromyou.ApplicationClass.PLAY_CODE;
import static com.p2m.musicplayfromyou.ApplicationClass.PREV_CODE;
import static com.p2m.musicplayfromyou.ApplicationClass.STOP_CODE;
import static com.p2m.musicplayfromyou.ApplicationClass.mediaSession;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadata;
import android.media.MediaMetadataRetriever;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

public class Noticlass {
    public static MediaSession mediaSession;
    public static Notification.MediaStyle mediaStyle;
    public static Notification notification;
    public static NotificationManager notificationManager;
    public static final String CHANNEL_ONE = "channel one only for testing purpose";
    public static final int NOTIFICATION_MEDIA_PLAYING_SONG_ID = 1000;


    public Noticlass() {
    }

//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public void notificationActions(int a, Context context){
//        if ( a == PREV_CODE ) {
//            if (getPosition() > 0) {
//                setPosition(getPosition() - 1);
//
//                try {
//                    playerActivity.playIv.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_pause_circle_filled_24));
//                    playerActivity.someClickMethod();
//                }catch (Exception e){
//                    Log.d("TAG", "onClick: "+e.getMessage());
//                }
//
//            } else {
//                Toast.makeText(context, "No more songs!", Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        if (a == PLAY_CODE) {
//            try {
//                if (mediaPlayer.isPlaying()) {
//                    mediaPlayer.pause();
//                    playerActivity.playIv.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_play_circle_filled_24));
//
//                } else {
//                    mediaPlayer.start();
//                    playerActivity.playIv.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_pause_circle_filled_24));
//
//                }
//                initForNotification(context);
//            }catch (Exception e){
//                Log.d("ffff", "onClick: "+e.getLocalizedMessage()+"  :::::::::::        "+e.getMessage());
//            }
//        }
//
//        if (a == NEXT_CODE) {
//            if ((getPosition() - 2) <listSongs.size()) {
//                setPosition(getPosition() + 1);
//
////                    Glide.with(PlayerActivity.this).load(singleList.get(getCurrentSong()).getImage()).placeholder(getResources().getDrawable(R.drawable.musicimage)).into(albumArtIv);
//
////                    startActivity(new Intent(PlayerActivity.this, PlayerActivity.class).putExtra("position",getCurrentSong()));
//
//                try {
//                    playerActivity.playIv.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_pause_circle_filled_24));
//                    playerActivity.someClickMethod();
//                }catch (Exception e){
//                    Log.d("TAG", "onClick: "+e.getMessage());
//                }
//
////                    playPauseIv.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_pause_circle_filled_24));
////                    finish();
//            } else {
//                Toast.makeText(context, "No more songs!", Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        if (a == STOP_CODE) {
//
//            playerActivity.isBound = false;
//            notificationManager.cancel(NOTIFICATION_MEDIA_PLAYING_SONG_ID);
//            playerActivity.finish();
//
//            Intent intent = new Intent(context, MyService.class);
//            context.stopService(intent);
//        }
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public void initForNotification(Context context) {
//        mediaSession= new MediaSession(context, "testing");
//        mediaStyle = new Notification.MediaStyle().setMediaSession(mediaSession.getSessionToken());
//
//        NotificationChannel channel = new NotificationChannel(CHANNEL_ONE, "Testing channel", NotificationManager.IMPORTANCE_MIN);
//        notificationManager = (NotificationManager) context.getSystemService(MainActivity.NOTIFICATION_SERVICE);
//        notificationManager.createNotificationChannel(channel);
//
//        Bitmap smallIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.musicimage);
//
//        try {
//            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
//            mediaMetadataRetriever.setDataSource(listSongs.get(getPosition()).getPath());
//            byte[] arr = mediaMetadataRetriever.getEmbeddedPicture();
//            smallIcon = BitmapFactory.decodeByteArray(arr, 0, arr.length);
//
//        }catch (Exception e){
//            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//
//        String contentTitle = listSongs.get(getPosition()).getTitle();
//        String contentText = listSongs.get(getPosition()).getArtist();
//
//        Intent intentPrev = new Intent(context, MyService.class);
//        intentPrev.putExtra("P",PREV_CODE);
//        PendingIntent pendingIntentPrev = PendingIntent.getService(context, PREV_CODE, intentPrev, 0);
//        Notification.Action prev = new Notification.Action.Builder(R.drawable.ic_baseline_skip_previous_24,"Previous", pendingIntentPrev).build();
//
//
//        Intent intentPlay = new Intent(context, MyService.class);
//        intentPlay.putExtra("P",PLAY_CODE);
//        PendingIntent pendingIntentPlay = PendingIntent.getService(context, PLAY_CODE, intentPlay, 0);
//        Notification.Action play;
//        if (mediaPlayer.isPlaying()){
//            play = new Notification.Action.Builder(R.drawable.ic_baseline_pause_circle_filled_24,"Play",pendingIntentPlay).build();
//        }else {
//            play = new Notification.Action.Builder(R.drawable.ic_baseline_play_circle_filled_24,"Play",pendingIntentPlay).build();
//        }
//
//        Intent intentNext = new Intent(context, MyService.class);
//        intentNext.putExtra("P",NEXT_CODE);
//        PendingIntent pendingIntentNext = PendingIntent.getService(context, NEXT_CODE, intentNext, 0);
//        Notification.Action next = new Notification.Action.Builder(R.drawable.ic_baseline_skip_next_24, "Next", pendingIntentNext).build();
//
//        Intent intentStop = new Intent(context, MyService.class);
//        intentStop.putExtra("P",STOP_CODE);
//        PendingIntent pendingIntentStop = PendingIntent.getService(context, STOP_CODE, intentStop, 0);
//        Notification.Action stop = new Notification.Action.Builder(R.drawable.ic_baseline_stop_24, "Stop", pendingIntentStop).build();
//
//        notification = new Notification.Builder(context, CHANNEL_ONE)
//                .setSmallIcon(R.drawable.musicimage)
//                .setContentTitle(contentTitle)
//                .setContentText(contentText)
//                .setLargeIcon(smallIcon)
//                .setActions(prev, play, next, stop)
//                .setStyle(mediaStyle)
//                .setOngoing(true)
//                .setAutoCancel(true)
//                .build();
//
//        mediaSession.setMetadata(
//                new MediaMetadata.Builder()
//                        .putString(MediaMetadata.METADATA_KEY_TITLE, listSongs.get(getPosition()).getTitle())
//                        .putString(MediaMetadata.METADATA_KEY_ARTIST, listSongs.get(getPosition()).getArtist())
//                        .putString(MediaMetadata.METADATA_KEY_ALBUM_ART_URI, listSongs.get(getPosition()).getPath())
//                        .putLong(MediaMetadata.METADATA_KEY_DURATION, mediaPlayer.getDuration()) // 4
//                        .build()
//        );
//
//        mediaSession.setPlaybackState(
//                new PlaybackState.Builder().setState(PlaybackState.STATE_PLAYING, mediaPlayer.getCurrentPosition(), 1.0f).build()
//        );
//
//        notificationManager.notify(NOTIFICATION_MEDIA_PLAYING_SONG_ID, notification);
//
//    }
}
