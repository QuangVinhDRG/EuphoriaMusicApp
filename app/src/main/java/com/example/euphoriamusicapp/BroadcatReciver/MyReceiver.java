package com.example.euphoriamusicapp.BroadcatReciver;

import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.example.euphoriamusicapp.service.Myservice;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int action = intent.getIntExtra("audio_music",0);
        Intent intent1 =  new Intent(context, Myservice.class);
        intent1.putExtra("audio_music_service",action);
        context.startService(intent1);

    }
}
