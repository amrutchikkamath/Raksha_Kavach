package com.rakshakavach.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    long insertUser(UserEntity user);

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    UserEntity findByEmail(String email);

    @Query("SELECT * FROM users WHERE email = :email AND passwordHash = :hash LIMIT 1")
    UserEntity login(String email, String hash);

    @Query("SELECT COUNT(*) FROM users WHERE email = :email")
    int emailExists(String email);

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    UserEntity findById(int id);
}
