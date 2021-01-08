package com.example.filfyklingelzeichen;

import android.os.Bundle;

import com.example.filfyklingelzeichen.db.Alarm;
import com.example.filfyklingelzeichen.db.AlarmDao;
import com.example.filfyklingelzeichen.db.AppDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class AlarmConstructorActivity extends AppCompatActivity {
    public static final String ALARM_INDEX = "ALARM_INDEX";

    private EditText nameEt;
    private TimePicker timePicker;
    private Button saveButton;

    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_constructor);

        nameEt = findViewById(R.id.activity_constructor_name);
        timePicker = findViewById(R.id.activity_constructor_time_picker);;
        saveButton = findViewById(R.id.activity_constructor_save);
        timePicker.setIs24HourView(true);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();
        AlarmDao alarmDao = db.alarmDao();

        int id = getIntent().getIntExtra(ALARM_INDEX, -1);

        Calendar now = Calendar.getInstance();
        if (id == -1) {
            timePicker.setHour(now.get(Calendar.HOUR_OF_DAY));
            timePicker.setMinute(now.get(Calendar.MINUTE));
        } else {
            new Thread(() -> {
                Alarm current = alarmDao.findById(id);
                timePicker.setHour(current.hour);
                timePicker.setMinute(current.minute);
            }).start();
        }

        saveButton.setOnClickListener(v -> {
            String name = nameEt.getText().toString();
            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();

            new Thread(() -> {
                Alarm alarm = new Alarm(name, hour, minute);
                alarmDao.insert(alarm);
                alarm.schedule(this);
                finish();
            }).start();
        });
    }
}
