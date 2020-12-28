package com.example.aplicatiemanagementfilme.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.aplicatiemanagementfilme.database.dao.UserAccountDao;
import com.example.aplicatiemanagementfilme.database.model.Movie;
import com.example.aplicatiemanagementfilme.database.model.UserAccount;
import com.example.aplicatiemanagementfilme.database.model.WatchList;
import com.example.aplicatiemanagementfilme.util.DateConverter;
import com.example.aplicatiemanagementfilme.util.StringListConverter;

@Database(entities = {UserAccount.class, WatchList.class, Movie.class},
        exportSchema = false, version = 3)
@TypeConverters({DateConverter.class, StringListConverter.class})
public abstract class DatabaseManager extends RoomDatabase {

    public static final String APES_TOGETHER_DB = "Apes_Together_DB";
    private static DatabaseManager databaseManager;

    public static DatabaseManager getInstance(Context context) {
        if (databaseManager == null) {
            synchronized (DatabaseManager.class) {
                if (databaseManager == null) {
                    databaseManager = Room.databaseBuilder(context,
                            DatabaseManager.class,
                            APES_TOGETHER_DB)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }

        return databaseManager;
    }

    // DAO
    public abstract UserAccountDao getUserAccountDao();
}
