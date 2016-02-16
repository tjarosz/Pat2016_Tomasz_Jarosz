package com.blstream.tomaszjarosz.resources;

import com.blstream.tomaszjarosz.core.Movie;
import com.blstream.tomaszjarosz.db.MovieDAO;
import com.google.common.base.Optional;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by Tomek on 2016-02-09.
 */
@Path("/movies/{movieId}")
@Produces(MediaType.APPLICATION_JSON)
public class MovieResource {
    private final MovieDAO moviesDAO;

    public MovieResource(MovieDAO moviesDAO) {
        this.moviesDAO = moviesDAO;
    }

    @GET
    @UnitOfWork
    public Movie getMovie(@PathParam("movieId") LongParam movieId) {
        return findSafely(movieId.get());
    }

    @DELETE
    @UnitOfWork
    public void deleteMovie(@PathParam("movieId") LongParam movieId) {
        moviesDAO.delete(findSafely(movieId.get()));
    }

    @PUT
    @UnitOfWork
    public Movie updateMovie(@PathParam("movieId") Long movieId, @Valid Movie movie) {
        //Movie movieToUpdate = findSafely(movieId.get());
        movie.setId(movieId);
        moviesDAO.update(movie);
        return movie;
    }

    private Movie findSafely(long movieId) {
        final Optional<Movie> movie = moviesDAO.findById(movieId);
        if (!movie.isPresent()) {
            throw new NotFoundException("No such movie.");
        }
        return movie.get();
    }

}
