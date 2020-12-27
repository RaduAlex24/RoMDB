package com.example.aplicatiemanagementfilme.util;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Movie implements Serializable {
    private String title;
    private Date releaseDate;
    private List<String> genres;
    private String storyline;
    private List<String> actors;
    private float imdbRating;
    private String posterUrl;

    public Movie(String title, Date releaseDate, List<String> genres,
                 String storyline, List<String> actors, float imdbRating, String posterUrl) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.genres = genres;
        this.storyline = storyline;
        this.actors = actors;
        this.imdbRating = imdbRating;
        this.posterUrl = posterUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getStoryline() {
        return storyline;
    }

    public void setStoryline(String storyline) {
        this.storyline = storyline;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    public float getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(float imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", releaseDate=" + releaseDate +
                ", genres=" + genres +
                ", storyline='" + storyline + '\'' +
                ", actors=" + actors +
                ", imdbRating=" + imdbRating +
                ", posterUrl='" + posterUrl + '\'' +
                '}';
    }
}
