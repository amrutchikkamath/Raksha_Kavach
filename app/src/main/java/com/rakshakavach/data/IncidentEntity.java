package com.rakshakavach.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "incidents")
public class IncidentEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public String description;
    public String location;
    public String severity; // LOW, MEDIUM, HIGH
    public long timestamp;
    public boolean isPrevented;

    public IncidentEntity(String title, String description, String location,
                          String severity, long timestamp, boolean isPrevented) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.severity = severity;
        this.timestamp = timestamp;
        this.isPrevented = isPrevented;
    }
}
