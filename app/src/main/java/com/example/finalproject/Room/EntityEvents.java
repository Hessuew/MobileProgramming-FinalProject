package com.example.finalproject.Room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;


@Entity(tableName = "events")
public class EntityEvents {
    @PrimaryKey(autoGenerate = true)
    int id_login_log;
    public String name;
    public String date;
    public String is_free;
    public String price;
    public String pricedescription;
    public String shortdescription;
}