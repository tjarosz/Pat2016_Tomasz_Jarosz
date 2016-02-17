package com.blstream.tomaszjarosz.resources;

import com.blstream.tomaszjarosz.core.Actor;
import com.blstream.tomaszjarosz.core.Movie;
import com.blstream.tomaszjarosz.db.MovieDAO;
import com.google.common.base.Optional;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


public class MovieResourceTest {
    private static final MovieDAO DAO = mock(MovieDAO.class);
    @ClassRule
    public static final ResourceTestRule RULE = ResourceTestRule.builder()
            .addResource(new MovieResource(DAO))
            .setTestContainerFactory(new GrizzlyWebTestContainerFactory())
            .build();

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new MovieResource(DAO))
            .build();

    private Movie movie;
    private Movie movieWithActors;

    @Before
    public void setup() {
        movie = new Movie();
        movie.setId(1L);

        movieWithActors = new Movie();
        movie.setTitle("The Lord of the Rings");
        movie.setDirector("Peter Jackson");
        List<Actor> actors = new ArrayList<>();
        actors.add(new Actor("Orlando", "Bloom", "13/01/1977"));
        actors.add(new Actor("Liv", "Taylor", "01/07/1977"));
        movie.setActors(actors);
    }

    @After
    public void tearDown() {
        reset(DAO);
    }

    @Test
    public void getmovieSuccess() {
        when(DAO.findById(1L)).thenReturn(Optional.of(movie));

        Movie found = RULE.getJerseyTest().target("/movies/1").request().get(Movie.class);

        assertThat(found.getId()).isEqualTo(movie.getId());
        verify(DAO).findById(1L);
    }

    @Test
    public void getmovieNotFound() {
        when(DAO.findById(2L)).thenReturn(Optional.<Movie>absent());
        final Response response = RULE.getJerseyTest().target("/movies/2").request().get();

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
        verify(DAO).findById(2L);
    }

    @Test
    public void deleteMovie() {
        when(DAO.findById(1L)).thenReturn(Optional.of(movie));
        final Response response = resources.client().target("/movies/1")
                .request()
                .delete();

        verify(DAO).findById(1L);
        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.NO_CONTENT.getStatusCode());
        verify(DAO).delete(any(Movie.class));
    }

    @Test
    public void deleteMovieWhenThereIsNoMovie() {
        when(DAO.findById(1L)).thenReturn(Optional.<Movie>absent());
        final Response response = resources.client().target("/movies/1")
                .request()
                .delete();

        verify(DAO).findById(1L);
        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
        verify(DAO, never()).delete(any(Movie.class));
    }

    @Test
    public void updateMovie() {
        final Response response = resources.client().target("/movies/1")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .put(Entity.entity(movieWithActors, MediaType.APPLICATION_JSON_TYPE));

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.OK.getStatusCode());
        verify(DAO).update(any(Movie.class));
    }
}
