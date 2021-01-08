package com.example.filfyklingelzeichen;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.filfyklingelzeichen.db.Alarm;
import com.example.filfyklingelzeichen.db.AlarmDao;
import com.example.filfyklingelzeichen.db.AppDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AlarmListActivity extends AppCompatActivity {
    AppDatabase db = Room.databaseBuilder(getApplicationContext(),
            AppDatabase.class, "database-name").build();

    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;

    private List<Alarm> alarms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);

        // Find views by ids
        fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recycler);

        setupRecyclerView();

        // Process fab onclick
        fab.setOnClickListener(v -> {
            // TODO: Open constructor
            alarms.add(new Alarm("Будильник " + (alarms.size() + 1), 13, 40, true));
            adapter.notifyDataSetChanged();
        });
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        AlarmDao alarmDao = db.alarmDao();
        alarms = alarmDao.getAll();

        adapter = new RecyclerAlarmsAdapter(recyclerView, alarms, index -> {
            // TODO: Open constructor
        });
        recyclerView.setAdapter(adapter);
    }
}