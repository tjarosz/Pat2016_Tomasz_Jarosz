package com.blstream.tomaszjarosz.resources;

import com.blstream.tomaszjarosz.core.Actor;
import com.blstream.tomaszjarosz.core.Movie;
import com.blstream.tomaszjarosz.db.MovieDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Set;

/**
 * Created by Tomek on 2016-02-09.
 */
@Path("/movies")
@Produces(MediaType.APPLICATION_JSON)
public class MoviesResource {
    private final MovieDAO moviesDAO;

    public MoviesResource(MovieDAO moviesDAO) {
        this.moviesDAO = moviesDAO;
    }

    @POST
    @UnitOfWork
    public Movie createMovie(Movie movie) {
        return moviesDAO.create(movie);
    }

    @GET
    @UnitOfWork
    public List<Movie> listMovies() {
        return moviesDAO.findAll();
    }
}
