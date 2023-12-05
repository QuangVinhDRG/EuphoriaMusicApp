package com.example.euphoriamusicapp.service;

import static com.example.euphoriamusicapp.PlayMusicActivity.CHANNEL_ID;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.RemoteViews;
import androidx.core.app.NotificationCompat;

import com.example.euphoriamusicapp.PlayMusicActivity;
import com.example.euphoriamusicapp.R;
import com.example.euphoriamusicapp.data.MusicAndPodcast;


public class Myservice extends Service {
    private static final int ACTION_PAUSE= 1;
    private static final int ACTION_RESUME = 2;
    private static final int ACTION_CLOSE= 3;
    MusicAndPodcast basicMusicInformation;


    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
         basicMusicInformation= (MusicAndPodcast) bundle.get("Song_notification");

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.imgsong);
            Intent intent1 =   new Intent(this, PlayMusicActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
            RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.custom_notification);
            remoteViews.setTextViewText(R.id.title_name_song,basicMusicInformation.getSongName());
            remoteViews.setTextViewText(R.id.title_single_song,basicMusicInformation.getAuthorName());
            remoteViews.setImageViewBitmap(R.id.imgsongg,bitmap);
            remoteViews.setImageViewResource(R.id.imPlay,R.drawable.pause_button_notification);
            remoteViews.setImageViewResource(R.id.imPlay,R.drawable.ic_play_arrow_24);
          //  remoteViews.setOnClickPendingIntent(R.id.imClose,getPendingIntent(this,ACTION_CLOSE));
            android.app.Notification notification =  new NotificationCompat.Builder(this,CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_small_icon_music)
                    .setContentIntent(pendingIntent)
                    .setCustomContentView(remoteViews)
                    .setSound(null)
                    .build();
            startForeground(1,notification);

        return START_NOT_STICKY;
    }







    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
