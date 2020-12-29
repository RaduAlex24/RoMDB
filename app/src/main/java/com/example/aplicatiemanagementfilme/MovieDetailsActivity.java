package com.example.aplicatiemanagementfilme;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.aplicatiemanagementfilme.asyncTask.Callback;
import com.example.aplicatiemanagementfilme.database.model.WatchList;
import com.example.aplicatiemanagementfilme.database.service.MovieService;
import com.example.aplicatiemanagementfilme.database.service.WatchListService;
import com.example.aplicatiemanagementfilme.fragments.MovieBrowserFragment;
import com.example.aplicatiemanagementfilme.fragments.WatchListFragment;
import com.example.aplicatiemanagementfilme.util.DateConverter;
import com.example.aplicatiemanagementfilme.database.model.Movie;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailsActivity extends AppCompatActivity {

    private ImageView ivPoster;
    private TextView tvTitle;
    private TextView tvImedbRating;
    private TextView tvReleaseDate;
    private TextView tvGenres;
    private TextView tvPlot;
    private TextView tvActors;
    private FloatingActionButton fab_addMovieToWL;
    private FloatingActionButton fab_deleteMovieFromWL;

    private DateConverter dateConverter = new DateConverter();

    private Intent intent;
    private Movie movieFromIntent;
    private boolean buttonVisibility;
    private int moviePositionInList;

    private MovieService movieService;
    private WatchListService watchListService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        // Initializare componente
        initComponents();

        // Incarcare film in componenetele paginii
        loadPageWithMovie(movieFromIntent);

        // Adaugare on click pe add movie to wl
        fab_addMovieToWL.setOnClickListener(onClickAddMovieToWLListener());

        // Adaugare on click pe stergere movie from wl
        fab_deleteMovieFromWL.setOnClickListener(onClickDeleteMovieListener());
    }


    // Functii
    // Initializare Componente
    private void initComponents() {
        // Componente
        ivPoster = findViewById(R.id.iv_poster_movie_details_act);
        tvTitle = findViewById(R.id.tv_title_movie_details_act);
        tvImedbRating = findViewById(R.id.tv_imdbRating_movie_details_act);
        tvReleaseDate = findViewById(R.id.tv_releaseDate_movie_details_act);
        tvGenres = findViewById(R.id.tv_genres_movie_details_act);
        tvPlot = findViewById(R.id.tv_plot_movie_details_act);
        tvActors = findViewById(R.id.tv_actors_movie_details_act);
        fab_addMovieToWL = findViewById(R.id.fab_add_movieToWL_movie_details_act);
        fab_deleteMovieFromWL = findViewById(R.id.fab_delete_movieFromWL_movie_details_act);

        // Intent
        intent = getIntent();

        // Vizibilitate buton add
        buttonVisibility = (boolean) intent
                .getSerializableExtra(MovieBrowserFragment.ADD_MOVIE_VISIBILITY_KEY);
        if (buttonVisibility) {
            fab_deleteMovieFromWL.setVisibility(View.GONE);
        } else {
            fab_addMovieToWL.setVisibility(View.GONE);
        }

        // Pozitia elementului in lista
        moviePositionInList = (int) intent.getSerializableExtra(MovieBrowserFragment.MOVIE_POSITION_KEY);

        // Film primit
        movieFromIntent = (Movie) intent.getSerializableExtra(MovieBrowserFragment.MOVIE_DETAILS_KEY);

        // Movie service
        movieService = new MovieService(getApplicationContext());

        // Watch list service
        watchListService = new WatchListService(getApplicationContext());
    }


    // Incarcare film in componenetele paginii
    private void loadPageWithMovie(Movie movie) {
        // Poster
        Glide.with(getApplicationContext()).load(movie.getPosterUrl()).into(ivPoster);
        // Titlu
        tvTitle.setText(movie.getTitle());
        // Imdb Rating
        tvImedbRating.setText(String.valueOf(movie.getImdbRating()));
        // Data lansare
        tvReleaseDate.setText(dateConverter.toString(movie.getReleaseDate()));
        // Gen-uri
        String genresString = "";
        for (int i = 0; i < movie.getGenres().size(); i++) {
            if (i != movie.getGenres().size() - 1) {
                genresString += movie.getGenres().get(i) + ", ";
            } else {
                genresString += movie.getGenres().get(i);
            }
        }
        tvGenres.setText(genresString);
        // Plot
        tvPlot.setText("  Plot: " + movie.getStoryline());
        // Actori
        String actorsString = "";
        for (int i = 0; i < movie.getActors().size(); i++) {
            if (i != movie.getActors().size() - 1) {
                actorsString += movie.getActors().get(i) + ", ";
            } else {
                actorsString += movie.getActors().get(i);
            }
        }
        tvActors.setText("  Starring: " + actorsString);
    }


    // Functie on click pe add movie to wl
    private View.OnClickListener onClickAddMovieToWLListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogSelectWL(v);
            }
        };
    }

    // Alert dialog pt select watchlist
    private void alertDialogSelectWL(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Add to watch list:");

        final Spinner spinner = new Spinner(v.getContext());
        List<String> titlesWLfromParser = titleWatchListParser(WatchListFragment.watchListArray);
        ArrayAdapter adapter = new ArrayAdapter(v.getContext(),
                android.R.layout.simple_dropdown_item_1line, titlesWLfromParser);
        spinner.setAdapter(adapter);
        builder.setView(spinner);

        builder.setPositiveButton("OK", onClickAddMovieToWLandDb(spinner));

        builder.setNegativeButton("Cancel", onClickCancelAddMovieToWLandDb());

        builder.show();
    }


    // functie click cancel adaugare film in DB
    private DialogInterface.OnClickListener onClickCancelAddMovieToWLandDb() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        };
    }

    // functie click adaugare film in DB
    private DialogInterface.OnClickListener onClickAddMovieToWLandDb(Spinner spinner) {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                int position = spinner.getSelectedItemPosition();
                WatchList selectedWatchList = WatchListFragment.watchListArray.get(position);
                movieFromIntent.setWatchListId(selectedWatchList.getId());
                movieService.insert(movieFromIntent, callbackAddMovieToWLandDb(selectedWatchList));

            }
        };
    }

    // Callback adaugare film in wl si db
    private Callback<Movie> callbackAddMovieToWLandDb(WatchList selectedWatchList) {
        return new Callback<Movie>() {
            @Override
            public void runResultOnUiThread(Movie result) {
                Toast.makeText(getApplicationContext(),
                        getString(R.string.toast_addMovieInWL_param,
                                selectedWatchList.getWlName()), Toast.LENGTH_LONG).show();

                watchListService.getWatchListsByUserAccountId(MainActivity.currentUserAccount.getId(), callbackNotifyAdapter());
            }
        };
    }

    // Callback notify adapter
    private Callback<List<WatchList>> callbackNotifyAdapter() {
        return new Callback<List<WatchList>>() {
            @Override
            public void runResultOnUiThread(List<WatchList> result) {
                WatchListFragment.watchListArray.clear();
                WatchListFragment.watchListArray.addAll(result);
                WatchListFragment.notifyInternalAdapter();
            }
        };
    }


    // Parsare Watch list array in lista de stringuri
    private List<String> titleWatchListParser(List<WatchList> watchListArray) {
        List<String> titleList = new ArrayList<>();

        for (int i = 0; i < watchListArray.size(); i++) {
            titleList.add(watchListArray.get(i).getWlName());
        }

        return titleList;
    }


    // Functie on click pentru stergere film din watch list
    private View.OnClickListener onClickDeleteMovieListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieService.delete(movieFromIntent, new Callback<Integer>() {
                    @Override
                    public void runResultOnUiThread(Integer result) {
                        intent.putExtra(MovieBrowserFragment.MOVIE_POSITION_KEY, moviePositionInList);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
            }
        };
    }
}