package com.example.euphoriamusicapp;

import static com.example.euphoriamusicapp.PlayMusicActivity.mediaPlayer;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.euphoriamusicapp.Constant.Constant;
import com.example.euphoriamusicapp.adapter.FavouriteSongAdapter;
import com.example.euphoriamusicapp.data.MusicAndPodcast;
import com.example.euphoriamusicapp.fragment.playlist.SongFragment;
import com.example.euphoriamusicapp.service.Myservice;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlayMusicOfflineActivity extends AppCompatActivity {
    public static MediaPlayer mediaPlayeroffline ;
    private ImageButton ibBack;
    private CircleImageView imgSong;
    private TextView tvTatolTime, tvCurrentTime, tvPlayMusicSongName, tvPlayMusicArtistName;
    private SeekBar sbTimelineMusic;
    private ImageButton ibPlay;
    private ImageButton ibFavourite;

    private ImageButton ibPrevious;
    private ImageButton ibNext;
    private ImageButton ibRepeat;
    public static MusicAndPodcast musicAndPodcast;
    private Handler handler = new Handler();
    private final int PREVIOUS = -1;
    private final int NEXT = 1;
    public static boolean isPlaying;
    private void handleActionMusic(int action) {
        switch (action){
            case Constant.ACTION_PAUSE:
                handler.removeCallbacks(updater);
                mediaPlayeroffline.pause();
                ibPlay.setImageResource(R.drawable.stop_icon_1);
                stopAnimation();

                break;
            case Constant.ACTION_RESUME:
                mediaPlayeroffline.start();
                ibPlay.setImageResource(R.drawable.stop_icon_2);
                updateSeekbar();
                startAnimation();
                break;
            case Constant.ACTION_PRE:
                ListSongorPodcast(PREVIOUS);
                startServiceMusic();
                break;
            case Constant.ACTION_NEXT:
                ListSongorPodcast(NEXT);
                startServiceMusic();
                break;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music_offline);
        ibBack = findViewById(R.id.btnBack);
        imgSong = findViewById(R.id.civDiscImage);
        tvTatolTime = findViewById(R.id.tvTatolTime);
        tvCurrentTime = findViewById(R.id.tvCurrentTime);
        tvPlayMusicSongName = findViewById(R.id.tvPlayMusicSongName);
        tvPlayMusicArtistName = findViewById(R.id.tvPlayMusicArtistName);
        sbTimelineMusic = findViewById(R.id.sbTimelineMusic);
        ibPlay = findViewById(R.id.ibPlay0ff);
        ibPrevious = findViewById(R.id.ibPrevious);
        ibNext = findViewById(R.id.ibNext);
        ibFavourite = findViewById(R.id.ibFavourite);
        ibRepeat = findViewById(R.id.ibRepeat);
        sbTimelineMusic.setMax(100);
        mediaPlayeroffline = new MediaPlayer();
        //receive data từ homfracment
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            musicAndPodcast = (MusicAndPodcast) bundle.get(Constant.StartMusic);
            prepareMediaPlayer(musicAndPodcast.getUrl());
            tvPlayMusicArtistName.setText(musicAndPodcast.getAuthorName());
            tvPlayMusicSongName.setText(musicAndPodcast.getSongName());
            imgSong.setImageBitmap(FavouriteSongAdapter.stringToBitmap(musicAndPodcast.getImage()));
        }else {
            //mediaPlayer = new MediaPlayer();
            tvTatolTime.setText(milliSecordsToTimer(mediaPlayer.getDuration()));
            tvPlayMusicArtistName.setText(musicAndPodcast.getAuthorName());
            tvPlayMusicSongName.setText(musicAndPodcast.getSongName());
            imgSong.setImageBitmap(FavouriteSongAdapter.stringToBitmap(musicAndPodcast.getImage()));
            ibPlay.setImageResource(R.drawable.stop_icon_2);
            tvCurrentTime.setText(milliSecordsToTimer(mediaPlayer.getCurrentPosition()));
            updateSeekbar();
            startAnimation();
        }

        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.RLPlaymuisc, new SongFragment(), "songFragment");
                fragmentTransaction.commit();
            }
        });
        ibFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ibPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayeroffline.isPlaying()) {
                    handler.removeCallbacks(updater);
                    mediaPlayeroffline.pause();
                    ibPlay.setImageResource(R.drawable.stop_icon_1);
                    startServiceMusic();
                    stopAnimation();
                } else {
                    mediaPlayeroffline.start();
                    ibPlay.setImageResource(R.drawable.stop_icon_2);
                    updateSeekbar();
                    startServiceMusic();
                    startAnimation();
                }
            }
        });
        ibNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (musicAndPodcast.isLatest()) {
                    ListSongorPodcast(NEXT);
                    startServiceMusic();
                } else {
                    Toast.makeText(PlayMusicOfflineActivity.this, "Dánh sách phát đã hết!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ibPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListSongorPodcast(PREVIOUS);
                startServiceMusic();
            }
        });
        sbTimelineMusic.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                SeekBar seekBar = (SeekBar) v;
                int playPosition = (mediaPlayeroffline.getDuration() / 100) * seekBar.getProgress();
                mediaPlayeroffline.seekTo(playPosition);
                tvCurrentTime.setText(milliSecordsToTimer(mediaPlayeroffline.getCurrentPosition()));
                return false;
            }
        });
        ibRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ibRepeat.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.repeat_icon_all).getConstantState())) {
                    ibRepeat.setImageResource(R.drawable.repeat_icon_one);
                } else if (ibRepeat.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.repeat_icon_one).getConstantState())) {
                    ibRepeat.setImageResource(R.drawable.repeat_icon_default);
                } else {
                    ibRepeat.setImageResource(R.drawable.repeat_icon_all);
                }
            }
        });

        Handler handler1 = new Handler();
        mediaPlayeroffline.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (ibRepeat.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.repeat_icon_all).getConstantState())) {
                            if (musicAndPodcast.isLatest() == false) {
                                repeatAll(FavouriteSongAdapter.list);
                            } else {
                                ListSongorPodcast(NEXT);
                            }
                        } else if (ibRepeat.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.repeat_icon_one).getConstantState())) {
                            mediaPlayeroffline.reset();
                            prepareMediaPlayer(musicAndPodcast.getUrl());
                        } else {
                            if(bundle!=null ){
                                ListSongorPodcast(NEXT);
                            }

                        }
                    }
                }, 1000);
            }
        });
        startServiceMusic();
        createChanelNotification();
        LocalBroadcastManager.getInstance(PlayMusicOfflineActivity.this).registerReceiver(broadcastReceiver1,new IntentFilter(Constant.Send_Data_To_PlayMusic));
    }

    private void createChanelNotification() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(Constant.CHANNEL_ID,
                    "Service Music",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setSound(null,null);
            NotificationManager manager =  getSystemService(NotificationManager.class);
            if(manager!= null){
                manager.createNotificationChannel(channel);
            }

        }
    }
    public void startServiceMusic() {
        Intent intent = new Intent(this, Myservice.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("audio",musicAndPodcast);
        bundle.putString(Constant.State,Constant.offline);
        intent.putExtras(bundle);
        startService(intent);
    }

    private void repeatAll(List<MusicAndPodcast> listSong) {
        mediaPlayeroffline.reset();
        tvPlayMusicArtistName.setText(listSong.get(0).getAuthorName());
        tvPlayMusicSongName.setText(listSong.get(0).getSongName());
        imgSong.setImageBitmap(FavouriteSongAdapter.stringToBitmap(musicAndPodcast.getImage()));
        musicAndPodcast = listSong.get(0);
        prepareMediaPlayer(listSong.get(0).getUrl());
    }





    private void ListSongorPodcast(int i) {
        if(i == NEXT){
            NextSong(FavouriteSongAdapter.list);
        }else{
            PreSong(FavouriteSongAdapter.list);
        }

    }
    private int getPosition(List<MusicAndPodcast> listSong){
        int pos =0;
        for (MusicAndPodcast bs: listSong) {
            if(bs.getUrl().equals(musicAndPodcast.getUrl())){
                break;
            }
            pos = pos+1;
        }
        return pos;

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
                mediaPlayeroffline.reset();
                tvPlayMusicArtistName.setText(listSong.get(pos + 1).getAuthorName());
                tvPlayMusicSongName.setText(listSong.get(pos + 1).getSongName());
                imgSong.setImageBitmap(FavouriteSongAdapter.stringToBitmap(listSong.get(pos + 1).getImage()));
                musicAndPodcast = listSong.get(pos+1);
                prepareMediaPlayer(listSong.get(pos + 1).getUrl());
            }else{
                handler.removeCallbacks(updater);
                mediaPlayeroffline.stop();
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
            mediaPlayeroffline.reset();
            tvPlayMusicArtistName.setText(listSong.get(pos - 1).getAuthorName());
            tvPlayMusicSongName.setText(listSong.get(pos - 1).getSongName());
            imgSong.setImageBitmap(FavouriteSongAdapter.stringToBitmap(listSong.get(pos - 1).getImage()));
            musicAndPodcast = listSong.get(pos-1);
            prepareMediaPlayer(listSong.get(pos - 1).getUrl());
        }

    }

    private void prepareMediaPlayer(String url){
        try{
            mediaPlayeroffline.setDataSource(url);
            mediaPlayeroffline.prepare();
            tvTatolTime.setText(milliSecordsToTimer(mediaPlayeroffline.getDuration()));
            mediaPlayeroffline.start();
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
            long currentDuration = mediaPlayeroffline.getCurrentPosition();
            tvCurrentTime.setText(milliSecordsToTimer(currentDuration));
        }
    };
    private void updateSeekbar(){
        if(mediaPlayeroffline!= null && mediaPlayeroffline.isPlaying()){
            sbTimelineMusic.setProgress((int)(((float) mediaPlayeroffline.getCurrentPosition() / mediaPlayeroffline.getDuration()) * 100));
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
    public BroadcastReceiver broadcastReceiver1 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if(bundle ==null){
                return;
            }
            isPlaying = bundle.getBoolean(Constant.Action_play_music_service_toPlayMusic_Boolean);
            int action = bundle.getInt(Constant.Action_play_music_service_toPlayMusic_int);
            handleActionMusic(action);
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}