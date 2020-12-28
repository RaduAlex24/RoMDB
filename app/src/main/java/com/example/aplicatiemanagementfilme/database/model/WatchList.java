package com.example.aplicatiemanagementfilme.database.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "watchLists", foreignKeys = @ForeignKey(entity = UserAccount.class,
        parentColumns = "id", childColumns = "userAccountId"))
public class WatchList {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "wlName")
    private String wlName;
    @ColumnInfo(name = "userAccountId")
    private long userAccountId;

    public WatchList(long id, String wlName, long userAccountId) {
        this.id = id;
        this.wlName = wlName;
        this.userAccountId = userAccountId;
    }

    @Ignore
    public WatchList(String wlName, long userAccountId) {
        this.wlName = wlName;
        this.userAccountId = userAccountId;
    }

    public String getWlName() {
        return wlName;
    }

    public void setWlName(String wlName) {
        this.wlName = wlName;
    }

    public long getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(long userAccountId) {
        this.userAccountId = userAccountId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
