package com.hilmi.githubusersearch.model;

import android.os.Parcel;
import android.os.Parcelable;

public class UserFavorite implements Parcelable {
    private int id;
    private String name;
    private String username;
    private String avatar;

    public UserFavorite(int id, String name, String username, String avatar) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.avatar = avatar;
    }

    public UserFavorite(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    private UserFavorite(Parcel in) {
        id = in.readInt();
        name = in.readString();
        username = in.readString();
        avatar = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(username);
        dest.writeString(avatar);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserFavorite> CREATOR = new Creator<UserFavorite>() {
        @Override
        public UserFavorite createFromParcel(Parcel in) {
            return new UserFavorite(in);
        }

        @Override
        public UserFavorite[] newArray(int size) {
            return new UserFavorite[size];
        }
    };
}
