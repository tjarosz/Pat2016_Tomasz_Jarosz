package com.blstream.tomaszjarosz.views;


import com.blstream.tomaszjarosz.core.Movie;
import io.dropwizard.views.View;

import java.util.List;

public class MoviesView extends View {
    private List<Movie> movies;

    public MoviesView(List<Movie> movies) {
        super("/views/movies.mustache");
        this.movies = movies;
    }

    public List<Movie> getMovies() {
        return movies;
    }
}
