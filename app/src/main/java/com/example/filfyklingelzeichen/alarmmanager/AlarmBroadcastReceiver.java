package com.example.filfyklingelzeichen.alarmmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;


public class AlarmBroadcastReceiver extends BroadcastReceiver {
    public static final String TITLE = "TITLE";

    @Override
    public void onReceive(Context context, Intent intent) {
        startAlarmService(context, intent);
    }

    private void startAlarmService(Context context, Intent intent) {
        Intent intentService = new Intent(context, AlarmService.class);
        intentService.putExtra(TITLE, intent.getStringExtra(TITLE));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService);
        } else {
            context.startService(intentService);
        }
    }
}
