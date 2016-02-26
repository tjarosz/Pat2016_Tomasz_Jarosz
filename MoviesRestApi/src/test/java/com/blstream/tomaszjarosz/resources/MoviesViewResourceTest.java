package com.blstream.tomaszjarosz.resources;

import com.blstream.tomaszjarosz.core.Movie;
import com.blstream.tomaszjarosz.db.MovieDAO;
import com.blstream.tomaszjarosz.views.MoviesView;
import com.codahale.metrics.MetricRegistry;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import io.dropwizard.testing.junit.ResourceTestRule;
import io.dropwizard.views.ViewMessageBodyWriter;
import io.dropwizard.views.ViewRenderer;
import io.dropwizard.views.mustache.MustacheViewRenderer;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import javax.ws.rs.core.MediaType;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class MoviesViewResourceTest {
    private static final MovieDAO dao = mock(MovieDAO.class);
    private static final MoviesView moviesView = mock(MoviesView.class);
    private static List<ViewRenderer> renders = Lists.<ViewRenderer>newArrayList(new MustacheViewRenderer());
    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new MoviesViewResource(dao))
            .addProvider(new ViewMessageBodyWriter(new MetricRegistry(), renders))
            .build();
    ;
    @Captor
    private ArgumentCaptor<Movie> movieCaptor;
    private Movie movie;

    @Before
    public void setUp() {
        movie = new Movie();
        movie.setTitle("title");
        movie.setDirector("director");
    }

    @After
    public void tearDown() {
        reset(dao);
    }

    @Test
    public void listMovieView() throws Exception {
        final ImmutableList<Movie> movies = ImmutableList.of(movie);
        when(dao.findAll()).thenReturn(movies);
        final String response = resources.client()
                .target("/movies")
                .request()
                .accept(MediaType.TEXT_HTML)
                .buildGet()
                .invoke()
                .readEntity(String.class);

        verify(dao).findAll();
        assertThat(response).contains("<html>").contains("Title: title").contains("<b>Director:</b> director");
    }

    @Test
    public void getMovieView() throws Exception {
        when(dao.findById(1L)).thenReturn(Optional.of(movie));
        when(moviesView.getMovies()).thenReturn(ImmutableList.of(movie));
        String response = resources.client()
                .target("/movies/view/1")
                .request()
                .accept(MediaType.TEXT_HTML)
                .buildGet()
                .invoke()
                .readEntity(String.class);

        verify(dao).findById(1L);
        assertThat(response).contains("Title: title").contains("<b>Director:</b> director");
        assertThat(moviesView.getMovies()).isEqualTo(ImmutableList.of(movie));
    }

    @Test
    public void getMovieViewByTitle() throws Exception {
        when(dao.findByTitle("title")).thenReturn(ImmutableList.of(movie));
        String response = resources.client()
                .target("/movies/title/title")
                .request()
                .accept(MediaType.TEXT_HTML)
                .buildGet()
                .invoke()
                .readEntity(String.class);
        verify(dao).findByTitle("title");
        assertThat(response).contains("Title: title").contains("<b>Director:</b> director");
    }
}
