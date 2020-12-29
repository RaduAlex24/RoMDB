package com.example.aplicatiemanagementfilme.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.aplicatiemanagementfilme.database.model.Movie;
import com.example.aplicatiemanagementfilme.database.model.UserAccount;

@Dao
public interface MovieDao {

    // Operatii
    // Insert movie
    @Insert
    long insert(Movie movie);
}
