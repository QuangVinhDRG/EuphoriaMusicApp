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
       context.sendBroadcast(new Intent("Music")
               .putExtra("actioname",intent.getAction()));

    }
}
