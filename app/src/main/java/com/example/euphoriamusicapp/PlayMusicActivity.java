package com.example.euphoriamusicapp;



import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.euphoriamusicapp.adapter.PodcastAdapter;
import com.example.euphoriamusicapp.adapter.RecentListenAdapter;
import com.example.euphoriamusicapp.data.MusicAndPodcast;
import com.example.euphoriamusicapp.fragment.HomeFragment;
import com.example.euphoriamusicapp.service.Myservice;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlayMusicActivity extends AppCompatActivity {
    public static final String CHANNEL_ID = "EUPHORIA_APP_MUSIC";
    private ImageButton ibBack;
    private CircleImageView imgSong;
    private TextView tvTatolTime,tvCurrentTime,tvPlayMusicSongName,tvPlayMusicArtistName;
    private SeekBar sbTimelineMusic;
    private ImageButton ibPlay;
    private ImageButton ibFavourite;

    private ImageButton ibPrevious;
    private ImageButton ibNext;
    private ImageButton ibRepeat;
    public static MediaPlayer mediaPlayer;
    public static MusicAndPodcast musicAndPodcast;
    private Handler handler = new Handler();
    private final int PREVIOUS = -1;
    private final int NEXT = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        ibBack = findViewById(R.id.btnBack);
        imgSong = findViewById(R.id.civDiscImage);
        tvTatolTime = findViewById(R.id.tvTatolTime);
        tvCurrentTime = findViewById(R.id.tvCurrentTime);
        tvPlayMusicSongName = findViewById(R.id.tvPlayMusicSongName);
        tvPlayMusicArtistName = findViewById(R.id.tvPlayMusicArtistName);
        sbTimelineMusic = findViewById(R.id.sbTimelineMusic);
        ibPlay = findViewById(R.id.ibPlay);
        ibPrevious = findViewById(R.id.ibPrevious);
        ibNext = findViewById(R.id.ibNext);
        ibFavourite = findViewById(R.id.ibFavourite);
        ibRepeat = findViewById(R.id.ibRepeat);
        sbTimelineMusic.setMax(100);
        mediaPlayer = new MediaPlayer();
        //receive data từ homfracment
        Bundle bundle = getIntent().getExtras();
        if(bundle.get("Song") != null){
            musicAndPodcast = (MusicAndPodcast) bundle.get("Song");
            prepareMediaPlayer(musicAndPodcast.getUrl());
            tvPlayMusicArtistName.setText(musicAndPodcast.getAuthorName());
            tvPlayMusicSongName.setText(musicAndPodcast.getSongName());
            Glide
                    .with(this)
                    .load(musicAndPodcast.getImage())
                    .into(imgSong);
        }else{

            tvCurrentTime.setText(milliSecordsToTimer(mediaPlayer.getCurrentPosition()));
        }

        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                miniLayoutmainapp();
            }
        });

        ibPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    handler.removeCallbacks(updater);
                    mediaPlayer.pause();
                    ibPlay.setImageResource(R.drawable.stop_icon_1);
                    stopAnimation();
                }else{
                    mediaPlayer.start();
                    ibPlay.setImageResource(R.drawable.stop_icon_2);
                    updateSeekbar();
                    startAnimation();
                }
            }
        });
        ibNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(musicAndPodcast.isLatest()){
                    ListSongorPodcast(NEXT);
                }else{
                    Toast.makeText(PlayMusicActivity.this, "Dánh sách phát đã hết!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ibPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListSongorPodcast(PREVIOUS);
            }
        });
        sbTimelineMusic.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                SeekBar seekBar = (SeekBar) v;
                int playPosition =(mediaPlayer.getDuration()/100) * seekBar.getProgress();
                mediaPlayer.seekTo(playPosition);
                tvCurrentTime.setText(milliSecordsToTimer(mediaPlayer.getCurrentPosition()));
                return false;
            }
        });
      //  CreateNotification();
        ibRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ibRepeat.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.repeat_icon_all).getConstantState()) ){
                    ibRepeat.setImageResource(R.drawable.repeat_icon_one);
                }else if(ibRepeat.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.repeat_icon_one).getConstantState()) ){
                    ibRepeat.setImageResource(R.drawable.repeat_icon_default);
                }else{
                    ibRepeat.setImageResource(R.drawable.repeat_icon_all);
                }
            }
        });
        Handler handler1 = new Handler();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if(ibRepeat.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.repeat_icon_all).getConstantState()) ){
                            if(musicAndPodcast.isLatest() == false){
                                if(musicAndPodcast.getType() == HomeFragment.SONG){
                                    repeatAll(RecentListenAdapter.basicMusicInformationList);
                                }else{
                                    repeatAll(PodcastAdapter.podcastList);
                                }
                            }else{
                                ListSongorPodcast(NEXT);
                            }
                        }else if(ibRepeat.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.repeat_icon_one).getConstantState()) ){
                            mediaPlayer.reset();
                            prepareMediaPlayer(musicAndPodcast.getUrl());
                        }else{
                            ListSongorPodcast(NEXT);
                        }
                    }
                },1000);
            }
        });




}

    private void repeatAll(List<MusicAndPodcast> listSong) {
        mediaPlayer.reset();
        tvPlayMusicArtistName.setText(listSong.get(0).getAuthorName());
        tvPlayMusicSongName.setText(listSong.get(0).getSongName());
        Glide
                .with(PlayMusicActivity.this)
                .load(listSong.get(0).getImage())
                .into(imgSong);
        musicAndPodcast = listSong.get(0);
        prepareMediaPlayer(listSong.get(0).getUrl());
    }

    private void CreateNotification() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel  channel =  new NotificationChannel(CHANNEL_ID,"PlayMusic", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager =getSystemService(NotificationManager.class);
            if(notificationManager != null){
                notificationManager.createNotificationChannel(channel);
            }
        }
    }




    private void miniLayoutmainapp() {
        Intent intent = new Intent(PlayMusicActivity.this, MainAppActivity.class);
        startActivity(intent);
    }

    private void ListSongorPodcast(int i) {
        if(musicAndPodcast.getType() == HomeFragment.SONG){
            if(i == NEXT){
                NextSong(RecentListenAdapter.basicMusicInformationList);
            }else{
                PreSong(RecentListenAdapter.basicMusicInformationList);
            }
        }else{
            if(i == NEXT){
                NextSong(PodcastAdapter.podcastList);
            }else{
                PreSong(PodcastAdapter.podcastList);
            }
        }
    }

    private void NextSong(List<MusicAndPodcast> listSong) {
        int pos =0;
        for (MusicAndPodcast bs: listSong) {
            if(bs.getUrl().equals(musicAndPodcast.getUrl())){
                break;
            }
            pos = pos+1;

        }
        if(listSong.size() > pos){
            if(listSong.get(pos).isLatest()){
                mediaPlayer.reset();
                tvPlayMusicArtistName.setText(listSong.get(pos + 1).getAuthorName());
                tvPlayMusicSongName.setText(listSong.get(pos + 1).getSongName());
                Glide
                        .with(PlayMusicActivity.this)
                        .load(listSong.get(pos + 1).getImage())
                        .into(imgSong);
                musicAndPodcast = listSong.get(pos+1);
                prepareMediaPlayer(listSong.get(pos + 1).getUrl());
            }else{
                handler.removeCallbacks(updater);
                mediaPlayer.stop();
                ibPlay.setImageResource(R.drawable.stop_icon_1);
                stopAnimation();
            }
        }
    }
    private void PreSong(List<MusicAndPodcast> listSong) {
        int pos =0;
        for (MusicAndPodcast bs: listSong) {
            if(bs.getUrl().equals(musicAndPodcast.getUrl())){
                break;
            }
            pos = pos+1;

        }
        if(listSong.get(pos).isFeatured()){
            mediaPlayer.reset();
            tvPlayMusicArtistName.setText(listSong.get(pos - 1).getAuthorName());
            tvPlayMusicSongName.setText(listSong.get(pos - 1).getSongName());
            Glide
                    .with(PlayMusicActivity.this)
                    .load(listSong.get(pos - 1).getImage())
                    .into(imgSong);
            musicAndPodcast = listSong.get(pos-1);
            prepareMediaPlayer(listSong.get(pos - 1).getUrl());
            }else{

            }

    }

    private void prepareMediaPlayer(String url){
        try{
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            tvTatolTime.setText(milliSecordsToTimer(mediaPlayer.getDuration()));
            mediaPlayer.start();
            startAnimation();
            updateSeekbar();
        }catch ( Exception e){
            Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
        }
    }
    private Runnable updater = new Runnable() {
        @Override
        public void run() {
            updateSeekbar();
            long currentDuration = mediaPlayer.getCurrentPosition();
            tvCurrentTime.setText(milliSecordsToTimer(currentDuration));
        }
    };
    private void updateSeekbar(){
        if(mediaPlayer.isPlaying()){
            sbTimelineMusic.setProgress((int)(((float) mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration()) * 100));
            handler.postDelayed(updater,1000);
        }
    }
    private String milliSecordsToTimer(long milliSeconds){
        String timerString ="";
        String SecondsString;

        int hours = (int) (milliSeconds / (1000 * 60 * 60));

        int minutes = (int) (milliSeconds % (1000 * 60 * 60)) / (1000 * 60);

        int secords = (int) ((milliSeconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);

        if(hours > 0){
            timerString = hours +" : ";
        }
        if(secords < 10){
            SecondsString = "0" + secords;
        }else{
            SecondsString = "" +secords;
        }
        timerString = timerString + minutes+ ":" + SecondsString;
        return timerString;
    }

    private void startAnimation(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                imgSong.animate().rotationBy(360).withEndAction(this).setDuration(10000)
                        .setInterpolator(new LinearInterpolator()).start();
            }
        };
        imgSong.animate().rotationBy(360).withEndAction(runnable).setDuration(10000)
                .setInterpolator(new LinearInterpolator()).start();
    }
    private void stopAnimation(){
        imgSong.animate().cancel();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        miniLayoutmainapp();
    }
}