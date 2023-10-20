package com.example.euphoriamusicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.euphoriamusicapp.adapter.MainAppAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainAppActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private BottomNavigationView bottomNavigationView;
//    private LinearLayout layoutMiniPlayMusic;
    TextView tvMiniPlaySongName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);
        viewPager2 = findViewById(R.id.viewPagerMain);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
//        layoutMiniPlayMusic = findViewById(R.id.layoutMiniPlayMusic);
        tvMiniPlaySongName = findViewById(R.id.tvMiniPlaySongName);
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
                Intent intent = new Intent(MainAppActivity.this, PlayMusicActivity.class);
                startActivity(intent);
            }
        });
    }
}