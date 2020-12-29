package com.example.aplicatiemanagementfilme.database.dao;

import android.widget.ListView;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.aplicatiemanagementfilme.database.model.WatchList;

import java.util.List;

@Dao
public interface WatchListDao {

    // Operatii
    // Insert Watch list
    @Insert
    long insert(WatchList watchList);

    // Select all pentru un user, dupa user id
    @Query("SELECT * from watchLists WHERE userAccountId = (:userAccountId)")
    List<WatchList> getWatchListsByUserAccountId(long userAccountId);

    // Update pentru incrementarea numarului de filme dintr un watch list
    @Query("UPDATE watchLists SET movieCount = movieCount + 1 WHERE id=(:watchListId)")
    int updateMovieCountPlus(long watchListId);

    // Update pentru decrementarea numarului de filme dintr un watch list
    @Query("UPDATE watchLists SET movieCount = movieCount - 1 WHERE id=(:watchListId)")
    int updateMovieCountMinus(long watchListId);
}
