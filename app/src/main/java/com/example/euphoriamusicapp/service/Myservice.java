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

        return START_NOT_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
