package com.example.euphoriamusicapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;


import com.bumptech.glide.Glide;
import com.example.euphoriamusicapp.adapter.MainAppAdapter;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAppActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private BottomNavigationView bottomNavigationView;
    private LinearLayout layoutMiniPlayMusic;
    private ImageButton ibAccount;
    private ImageButton ibPlay;
    private TextView tvMiniPlaySongName, tvArtistName;
    private LinearLayout llMiniPlayMusic, llInfoMiniPlaySong;
    private CircleImageView civSongImage;
    private RelativeLayout rlMainApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);
        rlMainApp = findViewById(R.id.rlMainApp);
        viewPager2 = findViewById(R.id.viewPagerMain);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        layoutMiniPlayMusic = findViewById(R.id.miniPlayMusic);
        tvMiniPlaySongName = findViewById(R.id.tvMiniPlaySongName);
        tvArtistName = findViewById(R.id.tvArtistName);
        llMiniPlayMusic = findViewById(R.id.llMiniPlayMusic);
        llInfoMiniPlaySong = findViewById(R.id.llInfoMiniPlaySong);
        civSongImage = findViewById(R.id.civSongImage);
        ibPlay = findViewById(R.id.ibPlayminimusic);
        if(PlayMusicActivity.mediaPlayer != null && PlayMusicActivity.mediaPlayer.isPlaying() ){
            layoutMiniPlayMusic.setVisibility(View.VISIBLE);
            //KHOI TAO MINI LAYOUT
            tvArtistName.setText(PlayMusicActivity.musicAndPodcast.getAuthorName());
            tvMiniPlaySongName.setText(PlayMusicActivity.musicAndPodcast.getSongName());
            Glide
                    .with(this)
                    .load(PlayMusicActivity.musicAndPodcast.getImage())
                    .into(civSongImage);
        }
        else{

            layoutMiniPlayMusic.setVisibility(View.GONE);
            }


        ibPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PlayMusicActivity.mediaPlayer.isPlaying()){
                    PlayMusicActivity.mediaPlayer.pause();
                    ibPlay.setImageResource(R.drawable.play_icon);
                }else{
                    PlayMusicActivity.mediaPlayer.start();
                    ibPlay.setImageResource(R.drawable.pause_button);
                }
            }
        });
        MainAppAdapter mainAppAdapter = new MainAppAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager2.setAdapter(mainAppAdapter);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.menuPlaylist).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.menuSearch).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.menuHome).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.menuRank).setChecked(true);
                        break;
                    case 4:
                        bottomNavigationView.getMenu().findItem(R.id.menuAccount).setChecked(true);
                        break;
                }
            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menuPlaylist) {
                    viewPager2.setCurrentItem(0, false);
                } else if (item.getItemId() == R.id.menuSearch) {
                    viewPager2.setCurrentItem(1, false);
                } else if (item.getItemId() == R.id.menuHome) {
                    viewPager2.setCurrentItem(2, false);
                } else if (item.getItemId() == R.id.menuRank) {
                    viewPager2.setCurrentItem(3, false);
                } else if (item.getItemId() == R.id.menuAccount) {
                    viewPager2.setCurrentItem(4, false);
                }
                return true;
            }
        });
        viewPager2.setUserInputEnabled(false);
        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.menuHome);
        }

        try {
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            // TODO: handle exception
        }


//        layoutMiniPlayMusic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainAppActivity.this, PlayMusicActivity.class);
//                startActivity(intent);
//            }
//        });
        tvMiniPlaySongName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainAppActivity.this,PlayMusicActivity.class);
                startActivity(intent);
            }
        });
        tvArtistName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainAppActivity.this,PlayMusicActivity.class);
                startActivity(intent);
            }
        });
        llMiniPlayMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainAppActivity.this,PlayMusicActivity.class);

                startActivity(intent);
            }
        });
        llInfoMiniPlaySong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainAppActivity.this,PlayMusicActivity.class);
                startActivity(intent);
            }
        });
        civSongImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainAppActivity.this,PlayMusicActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}