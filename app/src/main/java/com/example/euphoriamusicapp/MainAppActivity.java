package com.example.euphoriamusicapp;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager2.widget.ViewPager2;


import com.bumptech.glide.Glide;
import com.example.euphoriamusicapp.Constant.Constant;
import com.example.euphoriamusicapp.adapter.MainAppAdapter;

import com.example.euphoriamusicapp.service.Myservice;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAppActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private BottomNavigationView bottomNavigationView;
    private LinearLayout layoutMiniPlayMusic;
    private ImageButton ibPlay;
    private TextView tvMiniPlaySongName, tvArtistName;
    private CircleImageView civSongImage;
    private RelativeLayout rlMainApp;
    String s;
    public  static int currentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);
        rlMainApp = findViewById(R.id.rlMainApp);
        viewPager2 = findViewById(R.id.viewPagerMain);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        layoutMiniPlayMusic = findViewById(R.id.llMiniPlayMusic);
        tvMiniPlaySongName = findViewById(R.id.tvMiniPlaySongName);
        tvArtistName = findViewById(R.id.tvArtistName);
        civSongImage = findViewById(R.id.civSongImage);
        ibPlay = findViewById(R.id.ibPlayminimusic);

        checkPermission();
        Intent intent = getIntent();
        s = intent.getStringExtra(Constant.Connection_key);

        if((PlayMusicOfflineActivity.mediaPlayeroffline != null && PlayMusicOfflineActivity.mediaPlayeroffline.isPlaying() )|| PlayMusicActivity.isPlaying||(PlayMusicActivity.mediaPlayer != null && PlayMusicActivity.mediaPlayer.isPlaying() )|| PlayMusicActivity.isPlaying){
            layoutMiniPlayMusic.setVisibility(View.VISIBLE);
            //KHOI TAO MINI LAYOUT
            currentList = PlayMusicActivity.checkList;
            tvArtistName.setText(PlayMusicActivity.musicAndPodcast.getAuthorName());
            tvMiniPlaySongName.setText(PlayMusicActivity.musicAndPodcast.getSongName());
            Glide
                    .with(this)
                    .load(PlayMusicActivity.musicAndPodcast.getImage())
                    .into(civSongImage);
            LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,new IntentFilter(Constant.Send_Data_To_PlayMusic));
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
                    Intent intent = new Intent(MainAppActivity.this, Myservice.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("audio",PlayMusicActivity.musicAndPodcast);
                    intent.putExtras(bundle);
                    startService(intent);
                }else{
                    PlayMusicActivity.mediaPlayer.start();
                    ibPlay.setImageResource(R.drawable.pause_button);
                    Intent intent = new Intent(MainAppActivity.this, Myservice.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("audio",PlayMusicActivity.musicAndPodcast);
                    intent.putExtras(bundle);
                    startService(intent);
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
                        checkPermission();
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
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (s != null && s.equals(Constant.Connection_value)) {
                    if (item.getItemId() == R.id.menuPlaylist) {
                        checkPermission();
                        viewPager2.setCurrentItem(0, false);
                    }else {
                        Toast.makeText(MainAppActivity.this, "Vui lòng kết nối mạng", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    if (item.getItemId() == R.id.menuPlaylist) {
                        checkPermission();
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
                }

                return true;
            }
        });
        viewPager2.setUserInputEnabled(false);


        if (s != null && s.equals(Constant.Connection_value)) {
            // Set BottomNavigationView to "menuPlaylist" only if the condition is true
            if (savedInstanceState == null) {
                bottomNavigationView.setSelectedItemId(R.id.menuPlaylist);
            }
        } else {
            // Set BottomNavigationView to "menuHome" if the condition is not true
            if (savedInstanceState == null) {
                bottomNavigationView.setSelectedItemId(R.id.menuHome);
            }
        }


        try {
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            // TODO: handle exception
        }


        layoutMiniPlayMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainAppActivity.this, PlayMusicActivity.class);
                startActivity(intent);
            }
        });
        tvMiniPlaySongName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainAppActivity.this, PlayMusicActivity.class);
                startActivity(intent);
            }
        });
        tvArtistName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainAppActivity.this, PlayMusicActivity.class);
                startActivity(intent);
            }
        });
        civSongImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainAppActivity.this, PlayMusicActivity.class);
                startActivity(intent);
            }
        });

    }
    public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if(bundle ==null){
                return;
            }
            PlayMusicActivity.isPlaying = bundle.getBoolean(Constant.Action_play_music_service_toPlayMusic_Boolean);
            int action = bundle.getInt(Constant.Action_play_music_service_toPlayMusic_int);
            handleActionMusic(action);
        }
    };

    private void handleActionMusic(int action) {

            switch (action){
                case Constant.ACTION_PAUSE:
                    ibPlay.setImageResource(R.drawable.play_icon);
                    break;
                case Constant.ACTION_RESUME:
                    ibPlay.setImageResource(R.drawable.pause_button);
                    break;
                case Constant.ACTION_PRE:
                    tvArtistName.setText(PlayMusicActivity.musicAndPodcast.getAuthorName());
                    tvMiniPlaySongName.setText(PlayMusicActivity.musicAndPodcast.getSongName());
                    Glide
                            .with(this)
                            .load(PlayMusicActivity.musicAndPodcast.getImage())
                            .into(civSongImage);
                    break;
                case  Constant.ACTION_NEXT:
                    ShowInfor();
                    break;

            }
    }
    private void checkPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED
                    || checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                String[] pemisson = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
                requestPermissions(pemisson, Constant.REQUEST_PERMISSON_CODE);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(Constant.REQUEST_PERMISSON_CODE == requestCode){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Pemission allow", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Pemission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void ShowInfor(){
        tvArtistName.setText(PlayMusicActivity.musicAndPodcast.getAuthorName());
        tvMiniPlaySongName.setText(PlayMusicActivity.musicAndPodcast.getSongName());
        Glide
                .with(this)
                .load(PlayMusicActivity.musicAndPodcast.getImage())
                .into(civSongImage);
    }
    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Bạn có chắc chắn muốn thoát?")
                .setCancelable(false)
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainAppActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("Không", null)
                .show();
    }

}