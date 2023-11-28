package com.example.euphoriamusicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.euphoriamusicapp.data.BasicMusicInformation;
import com.example.euphoriamusicapp.data.Podcast;

public class PlayMusicActivity extends AppCompatActivity {
    private ImageButton ibBack;
    private ImageView imgSong;
    private TextView tvTatolTime,tvCurrentTime,tvPlayMusicSongName,tvPlayMusicArtistName;
    private SeekBar sbTimelineMusic;
    private ImageButton ibPlay;
    private ImageButton ibFavourite;

    private ImageButton ibPrevious;
    private ImageButton ibNext;
    public static MediaPlayer mediaPlayer;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        ibBack = findViewById(R.id.btnBack);
        imgSong = findViewById(R.id.imgSong);
        tvTatolTime = findViewById(R.id.tvTatolTime);
        tvCurrentTime = findViewById(R.id.tvCurrentTime);
        tvPlayMusicSongName = findViewById(R.id.tvPlayMusicSongName);
        tvPlayMusicArtistName = findViewById(R.id.tvPlayMusicArtistName);
        sbTimelineMusic = findViewById(R.id.sbTimelineMusic);
        ibPlay = findViewById(R.id.ibPlay);
        ibPrevious = findViewById(R.id.ibPrevious);
        ibNext = findViewById(R.id.ibNext);
        ibFavourite = findViewById(R.id.ibFavourite);
        sbTimelineMusic.setMax(100);
        mediaPlayer = new MediaPlayer();

        //receive data từ homfracment
        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return;
        }
        BasicMusicInformation basicMusicInformation = (BasicMusicInformation) bundle.get("Song");
        if(basicMusicInformation == null){
            Podcast podcast = (Podcast) bundle.get("podcast");
            prepareMediaPlayer(podcast.getUrl());
            tvPlayMusicArtistName.setText(podcast.getAuthorname());
            tvPlayMusicSongName.setText(podcast.getPodcastName());
            Glide
                    .with(this)
                    .load(podcast.getImage())
                    .into(imgSong);
        }else{
            prepareMediaPlayer(basicMusicInformation.getUrl());
            tvPlayMusicArtistName.setText(basicMusicInformation.getAuthorName());
            tvPlayMusicSongName.setText(basicMusicInformation.getSongName());
            Glide
                    .with(this)
                    .load(basicMusicInformation.getImage())
                    .into(imgSong);
        }


        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlayMusicActivity.this, MainAppActivity.class);
                startActivity(intent);
            }
        });

        ibPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    handler.removeCallbacks(updater);
                    mediaPlayer.pause();
                    ibPlay.setImageResource(R.drawable.stop_icon_1);
                }else{
                    mediaPlayer.start();
                    ibPlay.setImageResource(R.drawable.stop_icon_2);
                    updateSeekbar();
                }
            }
        });

    }
    private void prepareMediaPlayer(String url){
        try{
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            tvTatolTime.setText(milliSecordsToTimer(mediaPlayer.getDuration()));
            mediaPlayer.start();
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


}