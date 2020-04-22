package com.hilmi.githubusersearch.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user_table")
    List<User> getAll();

    @Query("SELECT * from user_table ORDER BY name ASC")
    LiveData<List<User>> getAlphabetizedUser();

    @Query("SELECT * FROM user_table WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user_table WHERE username LIKE :username LIMIT 1")
    User findByUsername(String username);

    @Query("DELETE FROM user_table WHERE username LIKE :username")
    void deleteByUsername(String username);

    @Insert
    void insertAll(User... users);

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);
}

