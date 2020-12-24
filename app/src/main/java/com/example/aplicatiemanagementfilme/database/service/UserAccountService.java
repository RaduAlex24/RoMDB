package com.example.aplicatiemanagementfilme.database.service;

import android.content.Context;

import androidx.room.ColumnInfo;

import com.example.aplicatiemanagementfilme.asyncTask.AsyncTaskRunner;
import com.example.aplicatiemanagementfilme.asyncTask.Callback;
import com.example.aplicatiemanagementfilme.database.DatabaseManager;
import com.example.aplicatiemanagementfilme.database.dao.UserAccountDao;
import com.example.aplicatiemanagementfilme.database.model.UserAccount;

import java.util.List;
import java.util.concurrent.Callable;

public class UserAccountService {
    private UserAccountDao userAccountDao;
    private AsyncTaskRunner asyncTaskRunner;

    public UserAccountService(Context context) {
        userAccountDao = DatabaseManager.getInstance(context).getUserAccountDao();
        asyncTaskRunner = new AsyncTaskRunner();
    }

    // Metode
    // Insert
    public void insert(UserAccount userAccount, Callback<UserAccount> callback) {
        Callable<UserAccount> callable = new Callable<UserAccount>() {
            @Override
            public UserAccount call() {
                if (userAccount == null) {
                    return null;
                }
                long id = userAccountDao.insert(userAccount);
                if (id == -1) {
                    return null;
                }

                userAccount.setId(id);
                return userAccount;
            }
        };

        asyncTaskRunner.executeAsync(callable, callback);
    }

    // Get pt username si email
    public void getUsersByUsernameOrEmail(String username, String email,
                                          Callback<List<UserAccount>> callback) {
        Callable<List<UserAccount>> callable = new Callable<List<UserAccount>>() {
            @Override
            public List<UserAccount> call() {
                return userAccountDao.getUsersByUsernameOrEmail(username, email);
            }
        };

        asyncTaskRunner.executeAsync(callable, callback);
    }


    // Get pt username
    public void getUsersByUsername(String username, Callback<List<UserAccount>> callback) {
        Callable<List<UserAccount>> callable = new Callable<List<UserAccount>>() {
            @Override
            public List<UserAccount> call() {
                return userAccountDao.getUsersByUsername(username);
            }
        };

        asyncTaskRunner.executeAsync(callable, callback);
    }
}
