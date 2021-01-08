package com.example.filfyklingelzeichen.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AlarmDao {
    @Query("SELECT * FROM alarm")
    List<Alarm> getAll();

    @Query("SELECT * FROM alarm WHERE id IN (:alarmIds)")
    List<Alarm> loadAllByIds(int[] alarmIds);

    @Query("SELECT * FROM alarm WHERE id LIKE :alarmId LIMIT 1")
    Alarm findById(int alarmId);

    @Insert
    void insertAll(Alarm... alarm);

    @Insert
    void insert(Alarm alarm);

    @Update
    void update(Alarm alarm);

    @Delete
    void delete(Alarm alarm);
}
