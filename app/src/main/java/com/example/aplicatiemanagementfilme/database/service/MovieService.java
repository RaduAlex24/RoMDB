package com.example.aplicatiemanagementfilme.database.service;

import android.content.Context;

import com.example.aplicatiemanagementfilme.asyncTask.AsyncTaskRunner;
import com.example.aplicatiemanagementfilme.asyncTask.Callback;
import com.example.aplicatiemanagementfilme.database.DatabaseManager;
import com.example.aplicatiemanagementfilme.database.dao.MovieDao;
import com.example.aplicatiemanagementfilme.database.dao.UserAccountDao;
import com.example.aplicatiemanagementfilme.database.dao.WatchListDao;
import com.example.aplicatiemanagementfilme.database.model.Movie;
import com.example.aplicatiemanagementfilme.database.model.WatchList;

import java.util.concurrent.Callable;

public class MovieService {
    private MovieDao movieDao;
    private WatchListDao watchListDao;
    private AsyncTaskRunner asyncTaskRunner;

    public MovieService(Context context) {
        movieDao = DatabaseManager.getInstance(context).getMovieDao();
        watchListDao = DatabaseManager.getInstance(context).getWatchListDao();
        asyncTaskRunner = new AsyncTaskRunner();
    }

    // Metode
    // Insert
    public void insert(Movie movie, Callback<Movie> callback){
        Callable<Movie> callable = new Callable<Movie>() {
            @Override
            public Movie call() {
                if (movie == null) {
                    return null;
                }
                long id = movieDao.insert(movie);
                if (id == -1) {
                    return null;
                }

                watchListDao.update(movie.getWatchListId());
                movie.setId(id);
                return movie;
            }
        };

        asyncTaskRunner.executeAsync(callable, callback);
    }
}
