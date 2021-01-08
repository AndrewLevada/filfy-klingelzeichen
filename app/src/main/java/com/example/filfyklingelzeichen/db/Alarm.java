package com.example.filfyklingelzeichen.db;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.filfyklingelzeichen.alarmmanager.AlarmBroadcastReceiver;

import java.util.Calendar;

import static com.example.filfyklingelzeichen.alarmmanager.AlarmBroadcastReceiver.TITLE;

@Entity
public class Alarm {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public int hour;
    public int minute;
    public boolean isActive;

    public Alarm(String name, int hour, int minute) {
        this.name = name;
        this.hour = hour;
        this.minute = minute;
        this.isActive = true;
    }

    public void schedule(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);

        intent.putExtra(TITLE, name);

        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, id, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // if alarm time has already passed, increment day by 1
        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        }


        final long RUN_DAILY = 24 * 60 * 60 * 1000;
        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                RUN_DAILY,
                alarmPendingIntent
        );
    }
}
