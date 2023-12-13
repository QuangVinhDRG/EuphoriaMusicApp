package com.example.euphoriamusicapp.service;


import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Base64;
import android.util.Log;
import android.widget.RemoteViews;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.example.euphoriamusicapp.BroadcatReciver.MyReceiver;
import com.example.euphoriamusicapp.Constant.Constant;
import com.example.euphoriamusicapp.PlayMusicActivity;
import com.example.euphoriamusicapp.PlayMusicOfflineActivity;
import com.example.euphoriamusicapp.R;
import com.example.euphoriamusicapp.data.MusicAndPodcast;

import org.checkerframework.checker.units.qual.C;

import java.io.InputStream;
import java.net.URL;


public class Myservice extends Service {

    Bitmap bitmap_imSong = null;
    private MusicAndPodcast musicAndPodcastService;
    Boolean isplaying =true;
    String state ="";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        if(bundle!= null){
            MusicAndPodcast musicAndPodcast = (MusicAndPodcast) bundle.get("audio");
            String s = (String) bundle.get(Constant.State);
            if(musicAndPodcast!=null){
                musicAndPodcastService = musicAndPodcast;
                state = s;
                sendNotification(musicAndPodcast);
            }

        }
        int actionmusic = intent.getIntExtra(Constant.ActionMusic_BroadcastReceiver_int,0);
        handleActionMusic(actionmusic);
        return START_NOT_STICKY;
    }
    private class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
        Bitmap bitmap = null;
        @Override
        protected Bitmap doInBackground(String... strings) {


            try {
                URL url = new URL(strings[0]);
                InputStream inputStream = url.openConnection().getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                bitmap_imSong = result;
            }
        }
    }
    private void sendNotification(MusicAndPodcast musicAndPodcast) {
        Intent intent;
        if(state.equals(Constant.online)){
             intent = new Intent(this, PlayMusicActivity.class);
            Log.d("qqqqqqqqqqqqqqqqqqqq", "sendNotification: ");
        }else{
             intent = new Intent(this, PlayMusicOfflineActivity.class);
            Log.d("ffff", "sendNotification: ");
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.custom_notification);
        remoteViews.setTextViewText(R.id.title_name_song,musicAndPodcast.getSongName());
        remoteViews.setTextViewText(R.id.title_single_song,musicAndPodcast.getAuthorName());
        if(state.equals(Constant.online)){
            new LoadImageTask().execute(musicAndPodcast.getImage());
            Bitmap bitmap = bitmap_imSong;
            remoteViews.setImageViewBitmap(R.id.imgsongg,bitmap);
        }else{
            Bitmap bitmap = stringToBitmap(musicAndPodcast.getImage());
            remoteViews.setImageViewBitmap(R.id.imgsongg,bitmap);
        }

        remoteViews.setOnClickPendingIntent(R.id.imPre,getPendingIntent(this,Constant.ACTION_PRE));
        if((PlayMusicActivity.mediaPlayer!= null && PlayMusicActivity.mediaPlayer.isPlaying()  ) || ( PlayMusicOfflineActivity.mediaPlayeroffline!= null && PlayMusicOfflineActivity.mediaPlayeroffline.isPlaying())){
            remoteViews.setOnClickPendingIntent(R.id.imPlay,getPendingIntent(this,Constant.ACTION_PAUSE));
            remoteViews.setImageViewResource(R.id.imPlay,R.drawable.ic_pause_24);
        }else{
            remoteViews.setOnClickPendingIntent(R.id.imPlay,getPendingIntent(this,Constant.ACTION_RESUME));
            remoteViews.setImageViewResource(R.id.imPlay,R.drawable.ic_play_arrow_24);
        }
        remoteViews.setOnClickPendingIntent(R.id.imNext,getPendingIntent(this,Constant.ACTION_NEXT));

        remoteViews.setOnClickPendingIntent(R.id.imClose,getPendingIntent(this,Constant.ACTION_CLEAR));

        Notification notification = new NotificationCompat.Builder(this,Constant.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_small_icon_music)
                .setContentIntent(pendingIntent)
                .setCustomContentView(remoteViews)
                .setSound(null)
                .build();
        startForeground(1,notification);

    }


    private PendingIntent getPendingIntent(Context context,int action) {
        Intent intent =  new Intent(context, MyReceiver.class);
        intent.putExtra(Constant.ActionMusic_BroadcastReceiver_name,action);
        return PendingIntent.getBroadcast(context.getApplicationContext(),action,intent,PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void handleActionMusic(int action){
        switch (action){
            case Constant.ACTION_PAUSE:
                pausemusic();
                break;
            case Constant.ACTION_RESUME:
                resumemusic();
                break;
            case Constant.ACTION_CLEAR:
                stopSelf();
                break;
            case Constant.ACTION_PRE:
                premusic();
                break;
            case Constant.ACTION_NEXT:
                nextmusic();
                break;
        }
    }

    private void nextmusic() {
        sendActiontoPlayMusicActivity(Constant.ACTION_NEXT);
    }

    private void premusic() {
        sendActiontoPlayMusicActivity(Constant.ACTION_PRE);
    }


    private void resumemusic() {
        if (PlayMusicActivity.mediaPlayer != null && !PlayMusicActivity.mediaPlayer.isPlaying())
        {
            isplaying = false;
            PlayMusicActivity.mediaPlayer.start();
            sendNotification(musicAndPodcastService);
            sendActiontoPlayMusicActivity(Constant.ACTION_RESUME);
        } else if (PlayMusicOfflineActivity.mediaPlayeroffline != null && !PlayMusicOfflineActivity.mediaPlayeroffline.isPlaying()) {
            isplaying = false;
            PlayMusicOfflineActivity.mediaPlayeroffline.start();
            sendNotification(musicAndPodcastService);
            sendActiontoPlayMusicActivity(Constant.ACTION_RESUME);
        }
    }

    private void pausemusic() {

        if(PlayMusicActivity.mediaPlayer!= null && PlayMusicActivity.mediaPlayer.isPlaying())
        {
            isplaying = true;
            PlayMusicActivity.mediaPlayer.pause();
            sendNotification(musicAndPodcastService);
            sendActiontoPlayMusicActivity(Constant.ACTION_PAUSE);
        } else if (PlayMusicOfflineActivity.mediaPlayeroffline != null && PlayMusicOfflineActivity.mediaPlayeroffline.isPlaying()) {
            isplaying = true;
            PlayMusicOfflineActivity.mediaPlayeroffline.pause();
            sendNotification(musicAndPodcastService);
            sendActiontoPlayMusicActivity(Constant.ACTION_PAUSE);
        }

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    private void sendActiontoPlayMusicActivity(int action){
        Intent intent =  new Intent(Constant.Send_Data_To_PlayMusic);
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constant.Action_play_music_service_toPlayMusic_Boolean,isplaying);
        bundle.putInt(Constant.Action_play_music_service_toPlayMusic_int,action);
        bundle.putSerializable(Constant.ActionMusic_BroadcastReceiver_Object,musicAndPodcastService);
        intent.putExtras(bundle);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
    public static Bitmap stringToBitmap(String encodedString) {
        byte[] decodedByteArray = Base64.decode(encodedString, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }
}
