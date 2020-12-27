package com.example.aplicatiemanagementfilme.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
        //preiau lista de jucatori
        if (getArguments() != null) {
            movieList = (List<Movie>) getArguments().getSerializable(ARG_PARAM1);
        }
        //creare adapter pentru ListView
        if (getContext() != null) {
            //vezi seminar 3 pentru creare si adaugare adapter ListView
            MovieListViewAdapter adapter = new MovieListViewAdapter(getContext().getApplicationContext(),
                    R.layout.lv_row_movie_browser, movieList, getLayoutInflater());
            lvMovies.setAdapter(adapter);
        }
    }
}