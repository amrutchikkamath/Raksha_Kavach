package com.rakshakavach.data;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "users",
    indices = {@Index(value = "email", unique = true)}
)
public class UserEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String fullName;
    public String email;
    public String passwordHash;   // SHA-256 hex
    public String role;           // "worker" | "supervisor"
    public String company;
    public long createdAt;

    public UserEntity(String fullName, String email, String passwordHash,
                      String role, String company, long createdAt) {
        this.fullName     = fullName;
        this.email        = email;
        this.passwordHash = passwordHash;
        this.role         = role;
        this.company      = company;
        this.createdAt    = createdAt;
    }
}
