package com.rakshakavach.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface IncidentDao {

    @Insert
    void insert(IncidentEntity incident);

    @Delete
    void delete(IncidentEntity incident);

    @Query("SELECT * FROM incidents ORDER BY timestamp DESC")
    LiveData<List<IncidentEntity>> getAllIncidents();

    @Query("SELECT COUNT(*) FROM incidents")
    int getTotalCount();

    @Query("SELECT COUNT(*) FROM incidents WHERE timestamp > :weekStart")
    int getCountSince(long weekStart);

    @Query("SELECT COUNT(*) FROM incidents WHERE isPrevented = 1")
    int getPreventedCount();

    @Query("DELETE FROM incidents")
    void deleteAll();
}
