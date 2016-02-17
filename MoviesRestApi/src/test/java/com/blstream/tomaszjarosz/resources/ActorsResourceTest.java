package com.blstream.tomaszjarosz.resources;

import com.blstream.tomaszjarosz.core.Actor;
import com.blstream.tomaszjarosz.db.ActorDAO;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class ActorsResourceTest {
    private static final ActorDAO dao = mock(ActorDAO.class);
    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new ActorsResource(dao))
            .build();
    @Captor
    private ArgumentCaptor<Actor> actorCaptor;
    private Actor actor;


    @Before
    public void setUp() {
        actor = new Actor();
        actor.setName("name");
        actor.setSurname("surname");
        actor.setDateOfBirth("12/11/1990");
    }

    @After
    public void tearDown() {
        reset(dao);
    }

    @Test
    public void createActor() throws JsonProcessingException {
        when(dao.create(any(Actor.class))).thenReturn(actor);
        final Response response = resources.client().target("/actors")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(actor, MediaType.APPLICATION_JSON_TYPE));

        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        verify(dao).create(actorCaptor.capture());
        assertThat(actorCaptor.getValue()).isEqualTo(actor);
    }

    @Test
    public void listActors() throws Exception {
        final ImmutableList<Actor> actors = ImmutableList.of(actor);
        when(dao.findAll()).thenReturn(actors);

        final List<Actor> response = resources.client().target("/actors")
                .request().get(new GenericType<List<Actor>>() {
                });

        verify(dao).findAll();
        assertThat(response).containsAll(actors);
        assertThat(1);
    }
}
