package com.example.aplicatiemanagementfilme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.aplicatiemanagementfilme.fragments.MovieBrowserFragment;
import com.example.aplicatiemanagementfilme.util.DateConverter;
import com.example.aplicatiemanagementfilme.database.model.Movie;
import com.example.aplicatiemanagementfilme.util.StringListConverter;

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

    private DateConverter dateConverter = new DateConverter();

    private Intent intent;
    private Movie movieFromIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        // Initializare componente
        initComponents();

        // Incarcare film in componenetele paginii
        loadPageWithMovie(movieFromIntent);
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

        // Intent
        intent = getIntent();

        // Film primit
        movieFromIntent = (Movie) intent.getSerializableExtra(MovieBrowserFragment.MOVIE_DETAILS_KEY);

        // Test
        List<String> stringList = new ArrayList<>();
        stringList.add("Salut");
        stringList.add("Buna");
        stringList.add("Kys");

        String rez1 = StringListConverter.toString(stringList);
        List<String> rez2 = StringListConverter.toList(rez1);
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
            if(i != movie.getGenres().size() - 1) {
                genresString += movie.getGenres().get(i) + ", ";
            }
            else{
                genresString += movie.getGenres().get(i);
            }
        }
        tvGenres.setText(genresString);
        // Plot
        tvPlot.setText("  Plot: "+movie.getStoryline());
        // Actori
        String actorsString = "";
        for (int i = 0; i < movie.getActors().size(); i++) {
            if(i != movie.getActors().size() - 1) {
                actorsString += movie.getActors().get(i) + ", ";
            }
            else{
                actorsString += movie.getActors().get(i);
            }
        }
        tvActors.setText("  Starring: " + actorsString);
    }
}