package com.example.euphoriamusicapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.euphoriamusicapp.BroadcatReciver.MyReceiver;
import com.example.euphoriamusicapp.data.MusicAndPodcast;

public class CreateNotification {
    public static final String CHANNEL_ID = "channel1";
    public static final String ACTION_PREVIOUS = "actionprevious";
    public static final String ACTION_NEXT = "actionnext";
    public static final String ACTION_PLAY = "actionplay";
    public static Notification notification;

    public static void createNotification(Context context, MusicAndPodcast musicAndPodcast, int playbutton, int pos, int size) {
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(context, "tag");
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.a_loi_image);

        PendingIntent pendingIntentpre ;
        int drb_pre ;
        if(pos == 0){
            drb_pre = 0;
            pendingIntentpre = null;
        }
        else{
            Intent intentPre = new Intent(context, MyReceiver.class)
                    .setAction(ACTION_PREVIOUS);
            pendingIntentpre = PendingIntent.getBroadcast(context,0,intentPre,PendingIntent.FLAG_UPDATE_CURRENT);
            drb_pre = R.drawable.ic_skip_previous_24;
        }
        int drb_next ;
        PendingIntent pendingIntentNext ;
        if(pos == 0){
            drb_next = 0;
            pendingIntentNext = null;
        }
        else{
            Intent intentnext = new Intent(context, MyReceiver.class)
                    .setAction(ACTION_NEXT);
            pendingIntentNext = PendingIntent.getBroadcast(context,0,intentnext,PendingIntent.FLAG_UPDATE_CURRENT);
            drb_next = R.drawable.ic_skip_next_24;
        }
        Intent intentPlay = new Intent(context, MyReceiver.class)
                .setAction(ACTION_PLAY);
        PendingIntent pendingIntentplay = PendingIntent.getBroadcast(context,0,intentPlay,PendingIntent.FLAG_UPDATE_CURRENT);

        //create notification
        notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_small_icon_music)
                .setContentTitle(musicAndPodcast.getSongName())
                .setContentText(musicAndPodcast.getAuthorName())
                .setLargeIcon(bitmap)
                .setOnlyAlertOnce(true)
                .setShowWhen(false)
                .addAction(drb_pre,"Previous",pendingIntentpre)
                .addAction(playbutton,"Play",pendingIntentplay)
                .addAction(drb_pre,"Next",pendingIntentpre)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0,1,2)
                        .setMediaSession(mediaSessionCompat.getSessionToken() ))

                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();
        notificationManagerCompat.notify(1, notification);
    }

}
