package com.example.finalproject.Room;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

// "location" is given a unique index so the location has to be unique.
@Entity(indices = {@Index(value = "location", unique = true)})
public class EntityLocation {
    @PrimaryKey(autoGenerate = true)
    int id;
    public String id_location;
    public String location;
    public String address;
}