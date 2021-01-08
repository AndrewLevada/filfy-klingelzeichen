package com.example.filfyklingelzeichen.db;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Alarm {
    @PrimaryKey
    public int id;

    public String name;
    public int hour;
    public int minute;

    public boolean isActive;

    public Alarm(String name, int hour, int minute, boolean isActive) {
        this.name = name;
        this.hour = hour;
        this.minute = minute;
        this.isActive = isActive;
    }
}
