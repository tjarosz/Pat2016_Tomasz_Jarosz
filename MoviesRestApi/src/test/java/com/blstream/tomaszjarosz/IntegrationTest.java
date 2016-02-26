package com.blstream.tomaszjarosz;

import com.blstream.tomaszjarosz.api.ActorRepresentation;
import com.blstream.tomaszjarosz.api.MovieRepresentation;
import com.blstream.tomaszjarosz.core.Actor;
import com.blstream.tomaszjarosz.core.Movie;
import io.dropwizard.testing.ConfigOverride;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class IntegrationTest {

    private static final String TMP_FILE = createTempFile();
    private static final String CONFIG_PATH = ResourceHelpers.resourceFilePath("test-movies.yml");

    @ClassRule
    public static final DropwizardAppRule<MoviesConfiguration> RULE = new DropwizardAppRule<>(
            MoviesApplication.class, CONFIG_PATH,
            ConfigOverride.config("database.url", "jdbc:h2:" + TMP_FILE));

    private Client client;

    @BeforeClass
    public static void migrateDb() throws Exception {
        RULE.getApplication().run("db", "migrate", CONFIG_PATH);
    }

    private static String createTempFile() {
        try {
            return File.createTempFile("test-movies", null).getAbsolutePath();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Before
    public void setUp() throws Exception {
        client = ClientBuilder.newClient();
    }

    @After
    public void tearDown() throws Exception {
        client.close();
    }

    @Test
    public void testPostMovie() throws Exception {
        List<Actor> actors = new ArrayList<>();
        actors.add(new Actor("jon", "black", null));
        actors.add(new Actor("tom", "white", "21/11/1994"));
        final MovieRepresentation movie
                = new MovieRepresentation("tit1", "tit2", String.valueOf(actors));
        final Movie newMovie = client.target("http://localhost:" + RULE.getLocalPort() + "/movies")
                .request()
                .post(Entity.entity(movie, MediaType.APPLICATION_JSON_TYPE))
                .readEntity(Movie.class);
        assertThat(newMovie.getId()).isNotNull();
        assertThat(newMovie.getTitle()).isEqualTo(movie.getTitle());
        assertThat(newMovie.getDirector()).isEqualTo(movie.getDirector());
        assertThat(newMovie.getActors().get(0).getName()).isEqualTo(movie.getActorsList().get(0).getName());
        assertThat(newMovie.getActors().get(1).getName()).isEqualTo(movie.getActorsList().get(1).getName());
    }

    @Test
    public void testDeleteMovie() throws Exception {
        final MovieRepresentation movie
                = new MovieRepresentation("tit1", "tit2", null);
        final Movie newMovie = client.target("http://localhost:" + RULE.getLocalPort() + "/movies")
                .request()
                .post(Entity.entity(movie, MediaType.APPLICATION_JSON_TYPE))
                .readEntity(Movie.class);
        final Response response = client.target("http://localhost:" + RULE.getLocalPort() + "/movies/1")
                .request()
                .delete();
        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.NO_CONTENT);

    }

    @Test
    public void testUpdateMovie() throws Exception {
        final Movie movie = new Movie();
        movie.setTitle("tit1");
        movie.setDirector("dir2");
        final MovieRepresentation movieToUpdate
                = new MovieRepresentation("changedName", "changedSurname", null);
        final Movie updatedMovie = client.target("http://localhost:" + RULE.getLocalPort()
                + "/movies/" + Long.toString(movie.getId()))
                .request()
                .put(Entity.entity(movieToUpdate, MediaType.APPLICATION_JSON_TYPE))
                .readEntity(Movie.class);

        assertThat(updatedMovie.getId()).isNotNull();
        assertThat(updatedMovie.getTitle()).isEqualTo(movieToUpdate.getTitle());
        assertThat(updatedMovie.getDirector()).isEqualTo(movieToUpdate.getDirector());

    }

    @Test
    public void testPostActor() throws Exception {
        final ActorRepresentation actor
                = new ActorRepresentation("name1", "surname1", "21/11/1992");
        final Actor newActor = client.target("http://localhost:" + RULE.getLocalPort() + "/actors")
                .request()
                .post(Entity.entity(actor, MediaType.APPLICATION_JSON_TYPE))
                .readEntity(Actor.class);
        assertThat(newActor.getId()).isNotNull();
        assertThat(newActor.getName()).isEqualTo(actor.getName());
        assertThat(newActor.getSurname()).isEqualTo(actor.getSurname());
        assertThat(newActor.getDateOfBirth()).isEqualTo(actor.getDateOfBirth());
    }

    @Test
    public void testDeleteActor() throws Exception {
        final ActorRepresentation actor
                = new ActorRepresentation("name1", "surname1", null);
        final Actor newActor = client.target("http://localhost:" + RULE.getLocalPort() + "/actors")
                .request()
                .post(Entity.entity(actor, MediaType.APPLICATION_JSON_TYPE))
                .readEntity(Actor.class);
        final Response response = client.target("http://localhost:" + RULE.getLocalPort() + "/actors/1")
                .request()
                .delete();
        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.NO_CONTENT);

    }

    @Test
    public void testUpdateActor() throws Exception {
        final Actor actor = new Actor();
        actor.setName("name1");
        actor.setSurname("surname1");
        final ActorRepresentation actorToUpdate
                = new ActorRepresentation("changedName", "changedSurname", null);
        final Actor updatedActor = client.target("http://localhost:" + RULE.getLocalPort()
                + "/actors/" + Long.toString(actor.getId()))
                .request()
                .put(Entity.entity(actorToUpdate, MediaType.APPLICATION_JSON_TYPE))
                .readEntity(Actor.class);

        assertThat(updatedActor.getId()).isNotNull();
        assertThat(updatedActor.getName()).isEqualTo(actorToUpdate.getName());
        assertThat(updatedActor.getSurname()).isEqualTo(actorToUpdate.getSurname());

    }
}
