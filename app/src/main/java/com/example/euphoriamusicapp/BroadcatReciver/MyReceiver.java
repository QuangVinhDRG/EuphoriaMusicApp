package com.example.euphoriamusicapp.BroadcatReciver;

import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.example.euphoriamusicapp.Constant.Constant;
import com.example.euphoriamusicapp.service.Myservice;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
      int actionmusic = intent.getIntExtra(Constant.ActionMusic_BroadcastReceiver_name,0);
      Intent intentService = new Intent(context, Myservice.class);
      intentService.putExtra(Constant.ActionMusic_BroadcastReceiver_int,actionmusic);
      context.startService(intentService);
    }
}
