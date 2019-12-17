package com.example.finalproject.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DaoEvents {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(EntityEvents listItem);

    @Query("SELECT * FROM events;")
    List<EntityEvents> getAllEvents();

    @Delete
    void delete(EntityEvents listItem);
}