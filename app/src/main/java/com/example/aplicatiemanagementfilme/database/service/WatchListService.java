package com.example.aplicatiemanagementfilme.database.service;

import android.content.Context;

import com.example.aplicatiemanagementfilme.asyncTask.AsyncTaskRunner;
import com.example.aplicatiemanagementfilme.asyncTask.Callback;
import com.example.aplicatiemanagementfilme.database.DatabaseManager;
import com.example.aplicatiemanagementfilme.database.dao.WatchListDao;
import com.example.aplicatiemanagementfilme.database.model.UserAccount;
import com.example.aplicatiemanagementfilme.database.model.WatchList;

import java.util.List;
import java.util.concurrent.Callable;

public class WatchListService {
    private WatchListDao watchListDao;
    private AsyncTaskRunner asyncTaskRunner;

    public WatchListService(Context context) {
        watchListDao = DatabaseManager.getInstance(context).getWatchListDao();
        asyncTaskRunner = new AsyncTaskRunner();
    }


    // Metode
    // Insert
    public void insert(WatchList watchList, Callback<WatchList> callback) {
        Callable<WatchList> callable = new Callable<WatchList>() {
            @Override
            public WatchList call() {
                if (watchList == null) {
                    return null;
                }

                long id = watchListDao.insert(watchList);

                if (id == -1) {
                    return null;
                }

                watchList.setId(id);
                return watchList;
            }
        };

        asyncTaskRunner.executeAsync(callable, callback);
    }

    // Get watchlists by user id
    public void getWatchListsByUserAccountId(long userAccountId, Callback<List<WatchList>> callback){
        Callable<List<WatchList>> callable = new Callable<List<WatchList>>() {
            @Override
            public List<WatchList> call() {
                return watchListDao.getWatchListsByUserAccountId(userAccountId);
            }
        };

        asyncTaskRunner.executeAsync(callable, callback);
    }
}
