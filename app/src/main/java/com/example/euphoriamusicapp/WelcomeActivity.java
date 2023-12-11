package com.example.euphoriamusicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;

import com.example.euphoriamusicapp.Admin.UpLoadSong;
import com.example.euphoriamusicapp.Constant.Constant;
import com.example.euphoriamusicapp.fragment.PlaylistFragment;
import com.example.euphoriamusicapp.fragment.playlist.SongFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WelcomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mAuth = FirebaseAuth.getInstance();
    }
    @Override
    protected void onStart() {
        super.onStart();
//        Intent intent = new Intent(WelcomeActivity.this, UpLoadSong.class);
//           startActivity(intent);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi =  connectivityManager.getNetworkInfo(connectivityManager.TYPE_WIFI);
        NetworkInfo mobile_3g4g =  connectivityManager.getNetworkInfo(connectivityManager.TYPE_MOBILE);
        if(wifi.isConnected() || mobile_3g4g.isConnected()){
            FirebaseUser user = mAuth.getCurrentUser();
            if(user != null){
                Intent intent = new Intent(WelcomeActivity.this, MainAppActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }else{
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        }else{

            Intent intent = new Intent(this,MainAppActivity.class);
            intent.putExtra(Constant.Connection_key,Constant.Connection_value);
            startActivity(intent);

        }

    }
}