package com.hilmi.githubusersearch.db;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class UserRepository {

    private UserDao mUserDao;
    private LiveData<List<User>> mAllUser;

    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mUserDao = db.userDao();
        mAllUser = mUserDao.getAlphabetizedUser();
    }

    public LiveData<List<User>> getAllUser() {
        return mAllUser;
    }

    public void insert(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mUserDao.insert(user);
        });
    }

    public void delete(String username) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mUserDao.deleteByUsername(username);
        });
    }

    public User getUserByUsername(String username) {
        final User[] user = new User[1];
        AppDatabase.databaseWriteExecutor.execute(() -> {
            user[0] = mUserDao.findByUsername(username);
        });

        return user[0];
    }
}
