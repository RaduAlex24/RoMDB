package com.example.aplicatiemanagementfilme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.aplicatiemanagementfilme.asyncTask.AsyncTaskRunner;
import com.example.aplicatiemanagementfilme.asyncTask.Callback;
import com.example.aplicatiemanagementfilme.fragments.ViewPagerAdapter;
import com.example.aplicatiemanagementfilme.network.HttpManager;
import com.example.aplicatiemanagementfilme.database.model.Movie;
import com.example.aplicatiemanagementfilme.util.MovieJsonParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private FragmentManager fragmentManager;

    //private final String URL_MOVIES = "https://jsonkeeper.com/b/B2YA";
    private final String URL_MOVIES = "https://jsonkeeper.com/b/A4PU";
    private List<Movie> movieList = new ArrayList<>();
    private AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializare componente
        initComponents();
        initViewPagerAdapter();

        // Preluare din HTTP
        getMoviesFromHttp(URL_MOVIES);
    }


    // Functii
    // Initializare componente
    private void initComponents() {
        viewPager = findViewById(R.id.viewPager_main);
        fragmentManager = getSupportFragmentManager();
    }

    // Setare adapter
    private void initViewPagerAdapter() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(fragmentManager, movieList);
        viewPager.setAdapter(adapter);
    }

    // Preluare filme din HTTP
    private void getMoviesFromHttp(String url){
        Callable<String> callable = new HttpManager(url);

        asyncTaskRunner.executeAsync(callable, new Callback<String>() {
            @Override
            public void runResultOnUiThread(String result) {
                movieList.addAll(MovieJsonParser.fromJson(result));
                Collections.shuffle(movieList);

                // Initializare swipe view
                initComponents();
                initViewPagerAdapter();
            }
        });
    }

}