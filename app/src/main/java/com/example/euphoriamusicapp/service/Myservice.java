package com.example.euphoriamusicapp.service;

import static com.example.euphoriamusicapp.PlayMusicActivity.CHANNEL_ID;
import static com.example.euphoriamusicapp.PlayMusicActivity.mediaPlayer;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;
import androidx.core.app.NotificationCompat;

import com.example.euphoriamusicapp.BroadcatReciver.MyReceiver;
import com.example.euphoriamusicapp.PlayMusicActivity;
import com.example.euphoriamusicapp.R;
import com.example.euphoriamusicapp.data.BasicMusicInformation;
import com.example.euphoriamusicapp.data.Podcast;



public class Myservice extends Service {
    private static final int ACTION_PAUSE= 1;
    private static final int ACTION_RESUME = 2;
    private static final int ACTION_CLOSE= 3;


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
        BasicMusicInformation basicMusicInformation= (BasicMusicInformation) bundle.get("Song_notification");
        if(basicMusicInformation == null ){
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.imgsong);
            Podcast podcast = (Podcast) bundle.get("Podcast_notification");
            Intent intent1 =   new Intent(this, PlayMusicActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
            RemoteViews  remoteViews = new RemoteViews(getPackageName(),R.layout.custom_notification);
            remoteViews.setTextViewText(R.id.title_name_song,podcast.getPodcastName());
            remoteViews.setTextViewText(R.id.title_single_song,podcast.getAuthorname());
            remoteViews.setImageViewBitmap(R.id.imgsongg,bitmap);
            remoteViews.setImageViewResource(R.id.imPlay,R.drawable.pause_button_notification);
            if(mediaPlayer.isPlaying()){
                remoteViews.setOnClickPendingIntent(R.id.imPlay,getPendingIntent(this,ACTION_PAUSE));
                remoteViews.setImageViewResource(R.id.imPlay,R.drawable.ic_play_arrow_24);
            }else{
                remoteViews.setOnClickPendingIntent(R.id.imPlay,getPendingIntent(this,ACTION_RESUME));
                remoteViews.setImageViewResource(R.id.imPlay,R.drawable.pause_button_notification);
            }
            remoteViews.setOnClickPendingIntent(R.id.imClose,getPendingIntent(this,ACTION_CLOSE));

            android.app.Notification notification =  new NotificationCompat.Builder(this,CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_small_icon_music)
                    .setContentIntent(pendingIntent)
                    .setCustomContentView(remoteViews)
                    .setSound(null)
                    .build();
            startForeground(1,notification);
        }else if(basicMusicInformation != null){
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.imgsong);
            Intent intent1 =   new Intent(this, PlayMusicActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
            RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.custom_notification);
            remoteViews.setTextViewText(R.id.title_name_song,basicMusicInformation.getSongName());
            remoteViews.setTextViewText(R.id.title_single_song,basicMusicInformation.getAuthorName());
            remoteViews.setImageViewBitmap(R.id.imgsongg,bitmap);
            remoteViews.setImageViewResource(R.id.imPlay,R.drawable.pause_button_notification);
            if(mediaPlayer.isPlaying()){
                remoteViews.setOnClickPendingIntent(R.id.imPlay,getPendingIntent(this,ACTION_PAUSE));
                remoteViews.setImageViewResource(R.id.imPlay,R.drawable.ic_play_arrow_24);
            }else{
                remoteViews.setOnClickPendingIntent(R.id.imPlay,getPendingIntent(this,ACTION_RESUME));
                remoteViews.setImageViewResource(R.id.imPlay,R.drawable.pause_button_notification);
            }
            remoteViews.setOnClickPendingIntent(R.id.imClose,getPendingIntent(this,ACTION_CLOSE));
            android.app.Notification notification =  new NotificationCompat.Builder(this,CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_small_icon_music)
                    .setContentIntent(pendingIntent)
                    .setCustomContentView(remoteViews)
                    .setSound(null)
                    .build();
            startForeground(1,notification);
        }
        int actionmusic = intent.getIntExtra("audio_music_service",0);
        Log.d("kakak", "onStartCommand: "+actionmusic);
        handleActionMusic(actionmusic);
        return START_NOT_STICKY;
    }

    private PendingIntent getPendingIntent(Context context, int actionPause) {
        Intent intent = new Intent(this, MyReceiver.class);
        intent.putExtra("audio_music",actionPause);
        Log.d("dddddd", "getPendingIntent: "+actionPause);
        return PendingIntent.getBroadcast(context.getApplicationContext(),actionPause,intent,PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void handleActionMusic(int action){
        switch(action){
            case ACTION_PAUSE:
                pauseMusic();
                break;
            case ACTION_RESUME:
                 ResumeMusic();
                break;
            case ACTION_CLOSE:
                stopSelf();
                break;
        }
    }

    private void pauseMusic() {
        if(mediaPlayer.isPlaying() && mediaPlayer!= null){
            mediaPlayer.pause();
        }

    }

    private void ResumeMusic() {
        if(mediaPlayer.isPlaying() && mediaPlayer!= null){
            mediaPlayer.pause();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
