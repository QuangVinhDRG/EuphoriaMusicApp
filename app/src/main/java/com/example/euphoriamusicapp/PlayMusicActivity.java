    package com.example.euphoriamusicapp;
    
    
    
    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.localbroadcastmanager.content.LocalBroadcastManager;
    
    import android.Manifest;
    import android.app.DownloadManager;
    import android.app.NotificationChannel;
    import android.app.NotificationManager;
    import android.content.BroadcastReceiver;
    import android.content.Context;
    import android.content.Intent;
    import android.content.IntentFilter;
    import android.content.pm.PackageManager;
    import android.media.MediaPlayer;
    import android.net.Uri;
    import android.os.Build;
    import android.os.Bundle;
    import android.os.Environment;
    import android.os.Handler;
    import android.util.Log;
    import android.view.MotionEvent;
    import android.view.View;
    import android.view.animation.LinearInterpolator;
    import android.widget.ImageButton;
    import android.widget.SeekBar;
    import android.widget.TextView;
    import android.widget.Toast;
    
    import com.bumptech.glide.Glide;
    import com.example.euphoriamusicapp.Constant.Constant;
    import com.example.euphoriamusicapp.adapter.ExploreAdapter;
    import com.example.euphoriamusicapp.adapter.ForYouAdapter;
    import com.example.euphoriamusicapp.adapter.NewReleaseAdapter;
    import com.example.euphoriamusicapp.adapter.PodcastAdapter;
    import com.example.euphoriamusicapp.adapter.RecentListenAdapter;
    import com.example.euphoriamusicapp.data.MusicAndPodcast;
    import com.example.euphoriamusicapp.fragment.HomeFragment;
    import com.example.euphoriamusicapp.service.Myservice;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    
    import java.io.File;
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    
    import de.hdodenhof.circleimageview.CircleImageView;
    
    public class PlayMusicActivity extends AppCompatActivity{
    
        private ImageButton ibBack;
        private CircleImageView imgSong;
        private TextView tvTatolTime, tvCurrentTime, tvPlayMusicSongName, tvPlayMusicArtistName;
        private SeekBar sbTimelineMusic;
        private ImageButton ibPlay;
        private ImageButton ibFavourite;
    
        private ImageButton ibPrevious;
        private ImageButton ibNext;
        private ImageButton ibRepeat;
        public static MediaPlayer mediaPlayer;
        public static MusicAndPodcast musicAndPodcast;
        private final Handler handler = new Handler();
        private final int PREVIOUS = -1;
        private final int NEXT = 1;
        public static boolean isPlaying;
        public static int checkList;
        NotificationManager notificationManager;
    
        private void handleActionMusic(int action) {
            switch (action){
                case Constant.ACTION_PAUSE:
                    handler.removeCallbacks(updater);
                    mediaPlayer.pause();
                    ibPlay.setImageResource(R.drawable.stop_icon_1);
                    stopAnimation();
                    break;
                case Constant.ACTION_RESUME:
                    mediaPlayer.start();
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
            //receive data từ homfracment
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                mediaPlayer = new MediaPlayer();
                checkList =(int) bundle.getInt(Constant.ListSong,0);
                musicAndPodcast = (MusicAndPodcast) bundle.get(Constant.StartMusic);
                prepareMediaPlayer(musicAndPodcast.getUrl());
                tvPlayMusicArtistName.setText(musicAndPodcast.getAuthorName());
                tvPlayMusicSongName.setText(musicAndPodcast.getSongName());
                Glide
                        .with(this)
                        .load(musicAndPodcast.getImage())
                        .into(imgSong);
            } else {
                //mediaPlayer = new MediaPlayer();
                checkList = MainAppActivity.currentList;
                tvTatolTime.setText(milliSecordsToTimer(mediaPlayer.getDuration()));
                tvPlayMusicArtistName.setText(musicAndPodcast.getAuthorName());
                tvPlayMusicSongName.setText(musicAndPodcast.getSongName());
                Glide
                        .with(this)
                        .load(musicAndPodcast.getImage())
                        .into(imgSong);
                ibPlay.setImageResource(R.drawable.stop_icon_2);
                tvCurrentTime.setText(milliSecordsToTimer(mediaPlayer.getCurrentPosition()));
                updateSeekbar();
                startAnimation();
            }
    
            ibBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    miniLayoutmainapp();
                   // unregisterReceiver(broadcastReceiver);
                }
            });
            ibFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkPermission();
                }
            });
            ibPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mediaPlayer.isPlaying()) {
                        handler.removeCallbacks(updater);
                        mediaPlayer.pause();
                        ibPlay.setImageResource(R.drawable.stop_icon_1);
                        startServiceMusic();
                        stopAnimation();
                    } else {
                        mediaPlayer.start();
                        ibPlay.setImageResource(R.drawable.stop_icon_2);
                        updateSeekbar();
                        startAnimation();
                        startServiceMusic();
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
                        Toast.makeText(PlayMusicActivity.this, "Dánh sách phát đã hết!!!", Toast.LENGTH_SHORT).show();
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
                    int playPosition = (mediaPlayer.getDuration() / 100) * seekBar.getProgress();
                    mediaPlayer.seekTo(playPosition);
                    tvCurrentTime.setText(milliSecordsToTimer(mediaPlayer.getCurrentPosition()));
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
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
    
                            if (ibRepeat.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.repeat_icon_all).getConstantState())) {
                                if (!musicAndPodcast.isLatest()) {
                                        List<MusicAndPodcast> musicAndPodcastList = new ArrayList<>();
                                        musicAndPodcastList = checlistEx(checkList);
                                        repeatAll(musicAndPodcastList);
                                } else {
                                    ListSongorPodcast(NEXT);
                                }
                            } else if (ibRepeat.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.repeat_icon_one).getConstantState())) {
                                mediaPlayer.reset();
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
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,new IntentFilter(Constant.Send_Data_To_PlayMusic));
        }
    
        private void checkPermission() {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED
                        || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    String[] pemisson = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
                    requestPermissions(pemisson, Constant.REQUEST_PERMISSON_CODE);
                }else {
                    startDownLoadFile();
                }
            }else{
                startDownLoadFile();
            }
        }
    
        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            if(Constant.REQUEST_PERMISSON_CODE == requestCode){
                if(grantResults.length > 0 && grantResults[0] ==PackageManager.PERMISSION_GRANTED){
                    startDownLoadFile();
                }else{
                    Toast.makeText(this, "Pemission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    
        private void startDownLoadFile() {
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(musicAndPodcast.getUrl()));
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
            request.setTitle("Download " + musicAndPodcast.getSongName());
            request.setDescription("Download file ...");
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_MUSIC , musicAndPodcast.getSongName()+"+"+musicAndPodcast.getAuthorName()+".mp3");
            DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            if(downloadManager!= null){
              //   DatabaseReference databaseReference =
    
                File musicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
                Boolean checkfileMusic = false;
                // Kiểm tra xem thư mục có tồn tại không
                if (musicDirectory.exists() && musicDirectory.isDirectory()) {
                    for (File file:musicDirectory.listFiles()
                         ) {
                        if (file.isFile() && file.getName().toLowerCase().endsWith(".mp3")) {
                            if(file.getName().equalsIgnoreCase((musicAndPodcast.getSongName()+"+"+musicAndPodcast.getAuthorName()+".mp3"))){
                                Toast.makeText(this, "File đã được tải xuống", Toast.LENGTH_SHORT).show();
                                checkfileMusic = true;
                            }
                        }
                    }
                    if(!checkfileMusic){
                        downloadManager.enqueue(request);
                    }
                }
            }
        }
    
        private void createChanelNotification() {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                NotificationChannel channel = new NotificationChannel(Constant.CHANNEL_ID,
                        "Service Music",
                        NotificationManager.IMPORTANCE_DEFAULT);
                channel.setSound(null,null);
                NotificationManager manager =   getSystemService(NotificationManager.class);
                if(manager!= null){
                    manager.createNotificationChannel(channel);
                }
    
            }
        }
    
        public void startServiceMusic() {
            Intent intent = new Intent(this, Myservice.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("audio",musicAndPodcast);
            bundle.putString(Constant.State,Constant.online);
            intent.putExtras(bundle);
            startService(intent);
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
    
    
    
    
        private void miniLayoutmainapp() {
            Intent intent = new Intent(PlayMusicActivity.this, MainAppActivity.class);
            intent.putExtra(Constant.ListSong,checkList);
            startActivity(intent);
        }
    
        private void ListSongorPodcast(int i) {
            if(musicAndPodcast.getType() == HomeFragment.SONG){
                List<MusicAndPodcast> musicAndPodcastList = new ArrayList<>();
                musicAndPodcastList.addAll(checlistEx(checkList)) ;
                if(i == NEXT){
                    NextSong(musicAndPodcastList);
                }else{
                    PreSong(musicAndPodcastList);
                }
            }else{
                if(i == NEXT){
                    NextSong(PodcastAdapter.podcastList);
                }else{
                    PreSong(PodcastAdapter.podcastList);
                }
            }
        }
    
        private List<MusicAndPodcast> checlistEx(int checkList) {
            ArrayList<MusicAndPodcast> list = new ArrayList<>();
            Log.d("checlistEx", "checlistEx: "+checkList);
            switch (checkList){
                case Constant.recent:
                    list.addAll(RecentListenAdapter.basicMusicInformationList);
                    break;
                case Constant.foryou:
                    list.addAll(ForYouAdapter.forYouImageList);
                    break;
                case Constant.podcast:
                    list.addAll(PodcastAdapter.podcastList);
                    break;
                case Constant.newrelease:
                    list.addAll(NewReleaseAdapter.newReleaseMusicList);
                    break;
                case Constant.explore:
                    list.addAll(ExploreAdapter.exploreImageList);
                    break;
            }
            return  list;
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
        private final Runnable updater = new Runnable() {
            @Override
            public void run() {
                updateSeekbar();
                long currentDuration = mediaPlayer.getCurrentPosition();
                tvCurrentTime.setText(milliSecordsToTimer(currentDuration));
            }
        };
        private void updateSeekbar(){
            if(mediaPlayer!= null && mediaPlayer.isPlaying()){
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
        public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
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
            miniLayoutmainapp();
    
        }
    
    
    }