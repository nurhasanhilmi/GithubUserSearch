package com.hilmi.githubusersearch.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class User {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "avatar_url")
    public String avatarUrl;

    public User(String name, String username, String avatarUrl) {
        this.name = name;
        this.username = username;
        this.avatarUrl = avatarUrl;
    }
}

