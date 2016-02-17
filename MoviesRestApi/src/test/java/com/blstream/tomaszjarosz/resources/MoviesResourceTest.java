package com.blstream.tomaszjarosz.resources;


import com.blstream.tomaszjarosz.core.Actor;
import com.blstream.tomaszjarosz.core.Movie;
import com.blstream.tomaszjarosz.db.MovieDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableList;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MoviesResourceTest {
    private static final MovieDAO dao = mock(MovieDAO.class);
    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new MoviesResource(dao))
            .build();
    @Captor
    private ArgumentCaptor<Movie> movieCaptor;
    private Movie movie;
    private Movie movieWithActors;

    @Before
    public void setUp() {
        movie = new Movie();
        movie.setTitle("title");
        movie.setDirector("director");

        movieWithActors = new Movie();
        movieWithActors.setTitle("The Lord of the Rings");
        movieWithActors.setDirector("Peter Jackson");
        List<Actor> actors = new ArrayList<>();
        actors.add(new Actor("Orlando", "Bloom", "13/01/1977"));
        actors.add(new Actor("Liv", "Taylor", "01/07/1977"));
        movieWithActors.setActors(actors);
    }

    @After
    public void tearDown() {
        reset(dao);
    }

    @Test
    public void createMovie() throws JsonProcessingException {
        when(dao.create(any(Movie.class))).thenReturn(movie);
        final Response response = resources.client().target("/movies")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(movie, MediaType.APPLICATION_JSON_TYPE));

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        verify(dao).create(movieCaptor.capture());
        assertThat(movieCaptor.getValue()).isEqualTo(movie);
    }

    @Test
    public void createMovieWithActors() throws JsonProcessingException {
        when(dao.create(any(Movie.class))).thenReturn(movieWithActors);
        final Response response = resources.client().target("/movies")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(movieWithActors, MediaType.APPLICATION_JSON_TYPE));

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        verify(dao).create(movieCaptor.capture());
        assertThat(movieCaptor.getValue()).isEqualTo(movieWithActors);
    }

    @Test
    public void listMovies() throws Exception {
        final ImmutableList<Movie> movies = ImmutableList.of(movie);
        when(dao.findAll()).thenReturn(movies);

        final List<Movie> response = resources.client().target("/movies")
                .request().get(new GenericType<List<Movie>>() {
                });

        verify(dao).findAll();
        assertThat(response).containsAll(movies);
        assertThat(1);
    }
}
