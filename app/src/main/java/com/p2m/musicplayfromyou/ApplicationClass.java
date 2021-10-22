package com.p2m.musicplayfromyou;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.media.session.MediaSession;
import android.os.Build;

public class ApplicationClass extends Application {
    public static final String CHANNEL_ID_1 = "First channel";
    public static final String CHANNEL_ID_2 = "Second channel";
    public static final String ACTION_NEXT = "next";
    public static final String ACTION_PREVIOUS = "previous";
    public static final String ACTION_PLAY = "play";

    //for notification
    public static final int PREV_CODE = 101;
    public static final int PLAY_CODE = 102;
    public static final int NEXT_CODE = 103;
    public static final int STOP_CODE = 104;

    public static MediaSession mediaSession;
    public static Notification.MediaStyle mediaStyle;
    public static Notification notification;
    public static NotificationManager notificationManager;
    public static final int NOTIFICATION_MEDIA_PLAYING_SONG_ID = 1000;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channelOne = new NotificationChannel(CHANNEL_ID_1, "Channel One", NotificationManager.IMPORTANCE_LOW);
            channelOne.setDescription("This the first channel for notification...");

            NotificationChannel channelTwo = new NotificationChannel(CHANNEL_ID_2, "Channel Two", NotificationManager.IMPORTANCE_LOW);
            channelTwo.setDescription("This is second channel for notification.");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channelOne);
            notificationManager.createNotificationChannel(channelTwo);
        }
    }



}
