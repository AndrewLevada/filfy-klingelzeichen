package com.example.filfyklingelzeichen;

import android.os.Bundle;

import com.example.filfyklingelzeichen.db.Alarm;
import com.example.filfyklingelzeichen.db.AlarmDao;
import com.example.filfyklingelzeichen.db.AppDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class AlarmConstructorActivity extends AppCompatActivity {
    public static final String ALARM_INDEX = "ALARM_INDEX";

    private final EditText nameEt = findViewById(R.id.activity_constructor_name);
    private final TimePicker timePicker = findViewById(R.id.activity_constructor_time_picker);;
    private final Button saveButton = findViewById(R.id.activity_constructor_save);

    AppDatabase db = Room.databaseBuilder(getApplicationContext(),
            AppDatabase.class, "database-name").build();
    AlarmDao alarmDao = db.alarmDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_constructor);

        int id = getIntent().getIntExtra(ALARM_INDEX, -1);

        Calendar now = Calendar.getInstance();
        if (id == -1) {
            timePicker.setHour(now.get(Calendar.HOUR_OF_DAY));
            timePicker.setMinute(now.get(Calendar.MINUTE));
        } else {
            Alarm current = alarmDao.findById(id);
            timePicker.setHour(current.hour);
            timePicker.setMinute(current.minute);
        }

        saveButton.setOnClickListener(v -> {
            String name = nameEt.getText().toString();
            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();

            Alarm alarm = new Alarm(name, hour, minute);
            alarmDao.insert(alarm);
            finish();
        });
    }
}
