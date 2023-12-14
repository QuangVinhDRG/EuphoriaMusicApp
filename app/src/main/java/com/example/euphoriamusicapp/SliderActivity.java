package com.example.euphoriamusicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.euphoriamusicapp.adapter.MainAppAdapter;
import com.example.euphoriamusicapp.adapter.SliderAdapter;
import com.example.euphoriamusicapp.data.ImageOfMusic;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class SliderActivity extends AppCompatActivity {
    private TextView tvSkip;
    private ViewPager2 viewPager2Slider;
    private CircleIndicator3 circleIndicator3Slider;
    private List<ImageOfMusic> sliderList;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (viewPager2Slider.getCurrentItem() == sliderList.size() - 1) {
                viewPager2Slider.setCurrentItem(0);
            } else {
                viewPager2Slider.setCurrentItem(viewPager2Slider.getCurrentItem() + 1);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);
        tvSkip = findViewById(R.id.tvSkip);
        viewPager2Slider = findViewById(R.id.vp2Slider);
        circleIndicator3Slider = findViewById(R.id.ci3Slider);
        sliderList = getListSlider();
        SliderAdapter sliderAdapter = new SliderAdapter(sliderList);
        viewPager2Slider.setAdapter(sliderAdapter);
        circleIndicator3Slider.setViewPager(viewPager2Slider);
        viewPager2Slider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 2000);
            }
        });
        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SliderActivity.this, MainAppActivity.class);
                startActivity(intent);
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
    private List<ImageOfMusic> getListSlider() {
        List<ImageOfMusic> list = new ArrayList<>();
        list.add(new ImageOfMusic(R.drawable.slider_1));
        list.add(new ImageOfMusic(R.drawable.slider_2));
        list.add(new ImageOfMusic(R.drawable.slider_3));
        list.add(new ImageOfMusic(R.drawable.slider_4));
        list.add(new ImageOfMusic(R.drawable.slider_5));
        return list;
    }
}