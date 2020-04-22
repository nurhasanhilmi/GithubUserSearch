package com.hilmi.githubusersearch.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.hilmi.githubusersearch.db.User;
import com.hilmi.githubusersearch.db.UserRepository;

import java.util.List;

public class UserFavoriteViewModel extends AndroidViewModel {

    private UserRepository mRepository;

    private LiveData<List<User>> mAllUser;

    public UserFavoriteViewModel (Application application) {
        super(application);
        mRepository = new UserRepository(application);
        mAllUser = mRepository.getAllUser();
    }

    public LiveData<List<User>> getAllUser() { return mAllUser; }

    public void insert(User user) { mRepository.insert(user); }

    public void delete(String username) {
        mRepository.delete(username);
    }

    public User getUserByUsername(String username) {
        return mRepository.getUserByUsername(username);
    }
}
