package com.example.aplicatiemanagementfilme.database.service;

import android.content.Context;

import androidx.room.ColumnInfo;

import com.example.aplicatiemanagementfilme.asyncTask.AsyncTaskRunner;
import com.example.aplicatiemanagementfilme.asyncTask.Callback;
import com.example.aplicatiemanagementfilme.database.DatabaseManager;
import com.example.aplicatiemanagementfilme.database.dao.UserAccountDao;
import com.example.aplicatiemanagementfilme.database.model.UserAccount;

import java.util.concurrent.Callable;

public class UserAccountService {
    private UserAccountDao userAccountDao;
    private AsyncTaskRunner asyncTaskRunner;

    public UserAccountService(Context context) {
        userAccountDao = DatabaseManager.getInstance(context).getUserAccountDao();
        asyncTaskRunner = new AsyncTaskRunner();
    }

    // Metode
    public void insert(UserAccount userAccount, Callback<UserAccount> callback){
        Callable<UserAccount> callable = new Callable<UserAccount>() {
            @Override
            public UserAccount call() {
                if(userAccount == null){
                    return null;
                }
                long id = userAccountDao.insert(userAccount);
                if(id == -1){
                    return null;
                }

                userAccount.setId(id);
                return userAccount;
            }
        };

        asyncTaskRunner.executeAsync(callable, callback);
    }
}
