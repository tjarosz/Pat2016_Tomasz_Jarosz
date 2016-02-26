package com.blstream.tomaszjarosz.resources;

import com.blstream.tomaszjarosz.api.MovieRepresentation;
import com.blstream.tomaszjarosz.core.Movie;
import com.blstream.tomaszjarosz.db.MovieDAO;
import com.google.common.base.Optional;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/movies/{movieId}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
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
    public Movie updateMovie(@PathParam("movieId") Long movieId,
                             @Valid MovieRepresentation movieRepresentation) {
        Movie movie = new Movie(movieRepresentation.getTitle(),
                movieRepresentation.getDirector(), movieRepresentation.getActorsList(),
                movieRepresentation.getYear(), movieRepresentation.getRated(), movieRepresentation.getReleased(),
                movieRepresentation.getRuntime(), movieRepresentation.getGenre(), movieRepresentation.getWriter(),
                movieRepresentation.getPlot(), movieRepresentation.getLanguage(), movieRepresentation.getCountry(),
                movieRepresentation.getAwards(), movieRepresentation.getPoster(), movieRepresentation.getMetascore(),
                movieRepresentation.getImdbRating(), movieRepresentation.getImdbVotes(), movieRepresentation.getImdbID(),
                movieRepresentation.getType(), movieRepresentation.getResponse());
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
