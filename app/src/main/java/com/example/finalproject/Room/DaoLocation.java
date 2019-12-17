package com.example.finalproject.Room;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DaoLocation {
    // IF user tries to insert duplicate. It is just IGNORED!
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(EntityLocation location);

    @Query("SELECT * FROM EntityLocation WHERE id_location = :id_location LIMIT 1; ")
    List<EntityLocation> getLocation(String id_location);

    @Query("SELECT * FROM EntityLocation")
    List<EntityLocation> getAllLocations();
}
