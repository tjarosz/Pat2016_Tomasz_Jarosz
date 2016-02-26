package com.blstream.tomaszjarosz.resources;

import com.blstream.tomaszjarosz.api.MovieRepresentation;
import com.blstream.tomaszjarosz.core.Movie;
import com.blstream.tomaszjarosz.db.MovieDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/movies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MoviesResource {
    private final MovieDAO moviesDAO;
    private Client client;

    public MoviesResource(MovieDAO moviesDAO) {
        this.moviesDAO = moviesDAO;
    }

    public MoviesResource(MovieDAO moviesDAO, Client client) {
        this.moviesDAO = moviesDAO;
        this.client = client;
    }

    @POST
    @UnitOfWork
    public Movie createMovie(MovieRepresentation movieRepresentation) {
        Movie movie = new Movie(movieRepresentation.getTitle(),
                movieRepresentation.getDirector(), movieRepresentation.getActorsList(),
                movieRepresentation.getYear(), movieRepresentation.getRated(), movieRepresentation.getReleased(),
                movieRepresentation.getRuntime(), movieRepresentation.getGenre(), movieRepresentation.getWriter(),
                movieRepresentation.getPlot(), movieRepresentation.getLanguage(), movieRepresentation.getCountry(),
                movieRepresentation.getAwards(), movieRepresentation.getPoster(), movieRepresentation.getMetascore(),
                movieRepresentation.getImdbRating(), movieRepresentation.getImdbVotes(), movieRepresentation.getImdbID(),
                movieRepresentation.getType(), movieRepresentation.getResponse());
        return moviesDAO.create(movie);
    }

    @POST
    @UnitOfWork
    @Path("/title/{title}")
    public Movie createMovieWithDetails(@PathParam("title") String title) {
        MovieRepresentation requestedMovie = getMovieDetails(title);
        Movie movie = new Movie();
        movie.getDetailsFromRepresentation(requestedMovie);
        return moviesDAO.create(movie);
    }

    @PUT
    @UnitOfWork
    @Path("/title/{title}/{movieId}")
    public Movie updateMovieWithDetails(@PathParam("title") String title, @PathParam("movieId") Long movieId) {
        Movie movie = new Movie();
        MovieRepresentation requestedMovie = getMovieDetails(title);
        movie.setId(movieId);
        movie.getDetailsFromRepresentation(requestedMovie);
        moviesDAO.update(movie);
        return movie;
    }

    public MovieRepresentation getMovieDetails(String title) {
        MovieRepresentation requestedMovie;
        requestedMovie = client
                .target("http://localhost:8080/movies/details/" + title)
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .buildGet()
                .invoke()
                .readEntity(MovieRepresentation.class);
        if (requestedMovie.getError() != null)
            throw new WebApplicationException(404);
        return requestedMovie;
    }

    @GET
    @UnitOfWork
    public List<Movie> listMovies() {
        return moviesDAO.findAll();
    }

    @GET
    @UnitOfWork
    @Path("/title/{title}")
    public List<Movie> getMovieByTitle(@PathParam("title") String title) {
        List<Movie> movies = moviesDAO.findByTitle(title);
        return movies;
    }
}
