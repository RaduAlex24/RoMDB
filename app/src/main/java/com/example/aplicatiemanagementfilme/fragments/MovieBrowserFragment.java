package com.example.aplicatiemanagementfilme.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.aplicatiemanagementfilme.MovieDetailsActivity;
import com.example.aplicatiemanagementfilme.R;
import com.example.aplicatiemanagementfilme.util.Movie;
import com.example.aplicatiemanagementfilme.util.MovieListViewAdapter;

import java.io.Serializable;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieBrowserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieBrowserFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String MOVIE_DETAILS_KEY = "MOVIE_DETAILS_KEY";

    // TODO: Rename and change types of parameters
    private List<Movie> movieList;

    public MovieBrowserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovieBrowserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieBrowserFragment newInstance(List<Movie> movieList) {
        MovieBrowserFragment fragment = new MovieBrowserFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, (Serializable) movieList);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_browser, container, false);
        initComponents(view);
        return view;
    }


    private void initComponents(View view) {
        ListView lvMovies = view.findViewById(R.id.lv_movieBrowser);

        if (getArguments() != null) {
            movieList = (List<Movie>) getArguments().getSerializable(ARG_PARAM1);
        }

        if (getContext() != null) {
            // Creare adapter pentru ListView
            MovieListViewAdapter adapter = new MovieListViewAdapter(getContext().getApplicationContext(),
                    R.layout.lv_row_movie_browser, movieList, getLayoutInflater());
            lvMovies.setAdapter(adapter);

            // Adaugare eveniment click pe element din lista
            lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(view.getContext(), MovieDetailsActivity.class);
                    intent.putExtra(MOVIE_DETAILS_KEY, movieList.get(position));
                    startActivity(intent);
                }
            });
        }
    }
}