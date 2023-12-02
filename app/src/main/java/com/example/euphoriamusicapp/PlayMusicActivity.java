package com.example.euphoriamusicapp;



import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.euphoriamusicapp.adapter.PodcastAdapter;
import com.example.euphoriamusicapp.adapter.RecentListenAdapter;
import com.example.euphoriamusicapp.data.BasicMusicInformation;
import com.example.euphoriamusicapp.data.Podcast;
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
    public static MediaPlayer mediaPlayer;
    public static  BasicMusicInformation basicMusicInformation;
    public static  Podcast podcast;
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
        sbTimelineMusic.setMax(100);
        mediaPlayer = new MediaPlayer();
        //receive data từ homfracment
        Bundle bundle = getIntent().getExtras();
        basicMusicInformation = (BasicMusicInformation) bundle.get("Song");
        podcast = (Podcast) bundle.get("podcast");
        if(basicMusicInformation == null && podcast != null){
            prepareMediaPlayer(podcast.getUrl());
            tvPlayMusicArtistName.setText(podcast.getAuthorname());
            tvPlayMusicSongName.setText(podcast.getPodcastName());
            Glide
                    .with(this)
                    .load(podcast.getImage())
                    .into(imgSong);
        }else if(basicMusicInformation != null){
            prepareMediaPlayer(basicMusicInformation.getUrl());
            tvPlayMusicArtistName.setText(basicMusicInformation.getAuthorName());
            tvPlayMusicSongName.setText(basicMusicInformation.getSongName());
            Glide
                    .with(this)
                    .load(basicMusicInformation.getImage())
                    .into(imgSong);

        }else{
            basicMusicInformation = (BasicMusicInformation) bundle.get("mainAppSong");
            if(basicMusicInformation == null){
                podcast = (Podcast) bundle.get("mainAppPodcast");
                tvPlayMusicArtistName.setText(podcast.getAuthorname());
                tvPlayMusicSongName.setText(podcast.getPodcastName());
                Glide
                        .with(this)
                        .load(podcast.getImage())
                        .into(imgSong);
                try {
                    mediaPlayer.setDataSource(podcast.getUrl());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                tvTatolTime.setText(milliSecordsToTimer(mediaPlayer.getDuration()));
                updateSeekbar();
            }else{

                tvPlayMusicArtistName.setText(basicMusicInformation.getAuthorName());
                tvPlayMusicSongName.setText(basicMusicInformation.getSongName());
                Glide
                        .with(this)
                        .load(basicMusicInformation.getImage())
                        .into(imgSong);
                try {
                    mediaPlayer.setDataSource(basicMusicInformation.getUrl());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                tvTatolTime.setText(milliSecordsToTimer(mediaPlayer.getDuration()));
                updateSeekbar();
            }


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
                ListSongorPodcast(NEXT);
            }
        });
        ibPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListSongorPodcast(PREVIOUS);
            }
        });
        CreateNotification();
        SenNotification();


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

    private void SenNotification() {

        Intent intent =  new Intent(this, Myservice.class);
        Bundle bundle = new Bundle();
        if(basicMusicInformation!= null){
            bundle.putSerializable("Song_notification",basicMusicInformation);
        }else{
            bundle.putSerializable("Podcast_notification",podcast);
        }
        intent.putExtras(bundle);
        startService(intent);
    }


    private void miniLayoutmainapp() {
        Intent intent = new Intent(PlayMusicActivity.this, MainAppActivity.class);
        startActivity(intent);
    }

    private void ListSongorPodcast(int i) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        if(basicMusicInformation != null){
            if(i == NEXT){
                NextSong(RecentListenAdapter.basicMusicInformationList);
            }else{
                PreSong(RecentListenAdapter.basicMusicInformationList);
            }
        }else if(podcast != null){
            if(i == NEXT){
                NextPobcast(PodcastAdapter.podcastList);
            }else{
                PrePobcast(PodcastAdapter.podcastList);
            }
        }
    }

    private void NextSong(List<BasicMusicInformation> listSong) {
        int pos =0;
        for (BasicMusicInformation bs: listSong) {
            if(bs.getUrl().equals(basicMusicInformation.getUrl())){
                break;
            }
            pos = pos+1;

        }
        if(listSong.size() > pos ){
            if(listSong.get(pos).isLatest()){
                mediaPlayer.reset();
                tvPlayMusicArtistName.setText(listSong.get(pos + 1).getAuthorName());
                tvPlayMusicSongName.setText(listSong.get(pos + 1).getSongName());
                Glide
                        .with(PlayMusicActivity.this)
                        .load(listSong.get(pos + 1).getImage())
                        .into(imgSong);
                basicMusicInformation = listSong.get(pos+1);
                prepareMediaPlayer(listSong.get(pos + 1).getUrl());
            }else{

            }
        }
    }
    private void NextPobcast(List<Podcast> listPodcast) {
        int pos =0;
        for (Podcast bs: listPodcast) {
            if(bs.getUrl().equals(podcast.getUrl())){
                break;
            }
            pos = pos+1;

        }
        if(listPodcast.size() > pos ){
            if(listPodcast.get(pos).isLatest()){
                mediaPlayer.reset();
                tvPlayMusicArtistName.setText(listPodcast.get(pos + 1).getAuthorname());
                tvPlayMusicSongName.setText(listPodcast.get(pos + 1).getPodcastName());
                Glide
                        .with(PlayMusicActivity.this)
                        .load(listPodcast.get(pos + 1).getImage())
                        .into(imgSong);
                podcast = listPodcast.get(pos+1);
                prepareMediaPlayer(listPodcast.get(pos + 1).getUrl());
            }else{

            }
        }
    }
    private void PreSong(List<BasicMusicInformation> listSong) {
        int pos =0;
        for (BasicMusicInformation bs: listSong) {
            if(bs.getUrl().equals(basicMusicInformation.getUrl())){
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
            basicMusicInformation = listSong.get(pos-1);
            prepareMediaPlayer(listSong.get(pos - 1).getUrl());
            }else{

            }

    }
    private void PrePobcast(List<Podcast> listPodcast) {
        int pos =0;
        for (Podcast pc: listPodcast) {
            if(pc.getUrl().equals(podcast.getUrl())){
                break;
            }
            pos = pos+1;

        }
        if(listPodcast.get(pos).isFeatured()){
            mediaPlayer.reset();
            tvPlayMusicArtistName.setText(listPodcast.get(pos - 1).getAuthorname());
            tvPlayMusicSongName.setText(listPodcast.get(pos - 1).getPodcastName());
            Glide
                    .with(PlayMusicActivity.this)
                    .load(listPodcast.get(pos - 1).getImage())
                    .into(imgSong);
            podcast = listPodcast.get(pos-1);
            prepareMediaPlayer(listPodcast.get(pos - 1).getUrl());
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