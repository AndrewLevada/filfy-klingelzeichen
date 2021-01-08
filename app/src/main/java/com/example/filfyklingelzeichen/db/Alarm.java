package com.example.filfyklingelzeichen.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

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
}
