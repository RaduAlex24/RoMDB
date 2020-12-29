package com.example.aplicatiemanagementfilme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.aplicatiemanagementfilme.database.model.WatchList;
import com.example.aplicatiemanagementfilme.fragments.WatchListFragment;

public class WatchListDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_list_details);

        Intent intent = getIntent();
        WatchList watchList = (WatchList) intent.getSerializableExtra(WatchListFragment.WATCH_LIST_INFORMATION_KEY);
    }
}