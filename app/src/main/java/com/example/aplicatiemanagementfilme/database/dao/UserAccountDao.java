package com.example.aplicatiemanagementfilme.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.aplicatiemanagementfilme.database.model.UserAccount;

import java.util.List;

@Dao
public interface UserAccountDao {

    // Operatii
    // Insert user
    @Insert
    long insert(UserAccount userAccount);

    // Get pentru tesatare username
    @Query("select * from userAccounts where username LIKE (:username)")
    List<UserAccount> getUsersByUsername(String username);

    // Get pentru tesatare email
    @Query("select * from userAccounts where email LIKE (:email)")
    List<UserAccount> getUsersByEmail(String email);
}
