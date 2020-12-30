package com.example.aplicatiemanagementfilme.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.aplicatiemanagementfilme.R;
import com.example.aplicatiemanagementfilme.database.model.Movie;
import com.example.aplicatiemanagementfilme.database.model.WatchList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class WatchListViewAdapter extends ArrayAdapter<WatchList> {

    private Context context;
    private List<WatchList> watchListArray;
    private LayoutInflater inflater;
    private int resource;

    public WatchListViewAdapter(@NonNull Context context, int resource,
                                @NonNull List<WatchList> objects, LayoutInflater inflater) {
        super(context, resource, objects);

        this.context = context;
        this.watchListArray = objects;
        this.inflater = inflater;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(resource, parent, false);
        WatchList watchList = watchListArray.get(position);
        if (watchList != null) {
            // Imagine
            ImageView ivPoster = view.findViewById(R.id.iv_poster_watchListRow);

            // Nume watch list
            TextView tvName = view.findViewById(R.id.tv_name_watchListRow);
            tvName.setText(watchList.getWlName());

            // Numar filme in watch list
            TextView tvMovieCount = view.findViewById(R.id.tv_movieCount_watchListRow);
            tvMovieCount.setText(String.valueOf(watchList.getMovieCount()));

            // Buton remove watch list
            FloatingActionButton fabRemoveWL = view.findViewById(R.id.fab_remove_watchListRow);
            fabRemoveWL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(),"Ceva",Toast.LENGTH_SHORT).show();
                }
            });
            fabRemoveWL.setFocusable(false);
        }
        return view;
    }
}
