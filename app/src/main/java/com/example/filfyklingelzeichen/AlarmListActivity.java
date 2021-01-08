package com.example.filfyklingelzeichen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.filfyklingelzeichen.db.Alarm;
import com.example.filfyklingelzeichen.db.AlarmDao;
import com.example.filfyklingelzeichen.db.AppDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AlarmListActivity extends AppCompatActivity {
    AppDatabase db;

    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;

    private List<Alarm> alarms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();
        alarms = new ArrayList<>();

        // Find views by ids
        fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recycler);

        setupRecyclerView();

        // Process fab onclick
        fab.setOnClickListener(v -> {
            startActivity(new Intent(this, AlarmConstructorActivity.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Handler uiHandler = new Handler();
        new Thread(() -> {
            AlarmDao alarmDao = db.alarmDao();
            alarms.clear();
            alarms.addAll(alarmDao.getAll());

            if (adapter != null) uiHandler.post(() -> adapter.notifyDataSetChanged());
        }).start();
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerAlarmsAdapter(recyclerView, alarms, id -> {
            Intent intent = new Intent(this, AlarmConstructorActivity.class);
            intent.putExtra(AlarmConstructorActivity.ALARM_INDEX, id);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
    }
}