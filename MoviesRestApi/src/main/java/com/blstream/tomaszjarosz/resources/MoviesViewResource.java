package com.blstream.tomaszjarosz.resources;

import com.blstream.tomaszjarosz.core.Movie;
import com.blstream.tomaszjarosz.db.MovieDAO;
import com.blstream.tomaszjarosz.views.MoviesView;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.List;

@Path("/movies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MoviesViewResource {
    private final MovieDAO moviesDAO;

    public MoviesViewResource(MovieDAO moviesDAO) {
        this.moviesDAO = moviesDAO;
    }

    @GET
    @UnitOfWork
    @Path("/title/{title}")
    @Produces(MediaType.TEXT_HTML)
    public MoviesView getMovieViewByTitle(@PathParam("title") String title) {
        List<Movie> movies = moviesDAO.findByTitle(title);
        return new MoviesView(movies);
    }

    @GET
    @UnitOfWork
    @Produces(MediaType.TEXT_HTML)
    public MoviesView listMoviesView() {
        return new MoviesView(moviesDAO.findAll());
    }

    @GET
    @UnitOfWork
    @Path("/view/{movieId}")
    @Produces(MediaType.TEXT_HTML)
    public MoviesView getMovieView(@PathParam("movieId") LongParam movieId) {
        return new MoviesView(Collections.singletonList(moviesDAO.findById(movieId.get()).get()));
    }
}
