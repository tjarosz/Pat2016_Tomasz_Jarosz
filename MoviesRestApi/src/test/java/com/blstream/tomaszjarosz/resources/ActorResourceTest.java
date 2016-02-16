package com.blstream.tomaszjarosz.resources;

import com.blstream.tomaszjarosz.core.Actor;

import com.blstream.tomaszjarosz.db.ActorDAO;
import com.google.common.base.Optional;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ActorResourceTest {
    private static final ActorDAO DAO = mock(ActorDAO.class);
    @ClassRule
    public static final ResourceTestRule RULE = ResourceTestRule.builder()
            .addResource(new ActorResource(DAO))
            //.setTestContainerFactory(new GrizzlyWebTestContainerFactory())
            .build();
    private Actor actor;

    @Before
    public void setup() {
        actor = new Actor();
        actor.setId(1L);
    }

    @After
    public void tearDown() {
        reset(DAO);
    }

    @Test
    public void getActorSuccess() {
        when(DAO.findById(1L)).thenReturn(Optional.of(actor));

        Actor found = RULE.getJerseyTest().target("/actors/1").request().get(Actor.class);

        assertThat(found.getId()).isEqualTo(actor.getId());
        verify(DAO).findById(1L);

    }

    @Test
    public void getActorNotFound() {
        when(DAO.findById(2L)).thenReturn(Optional.<Actor>absent());
        final Response response = RULE.getJerseyTest().target("/actors/2").request().get();

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
        verify(DAO).findById(2L);
    }

    @Test
    public void deleteActor(){
        when(DAO.findById(1L)).thenReturn(Optional.of(actor));
        final Response response = RULE.client().target("/actors/1")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .delete();
        verify(DAO).findById(1L);
        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.NO_CONTENT.getStatusCode());
        verify(DAO).delete(any(Actor.class));
    }

    @Test
    public void deleteMovieWhenThereIsNoMovie(){
        when(DAO.findById(1L)).thenReturn(Optional.<Actor>absent());
        final Response response = RULE.client().target("/actors/1")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .delete();
        verify(DAO).findById(1L);
        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
        verify(DAO, never()).delete(any(Actor.class));
    }

    @Test
    public void updateActor(){
        final Response response = RULE.client().target("/actors/1")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .put(Entity.entity(actor, MediaType.APPLICATION_JSON_TYPE));

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.OK.getStatusCode());
        verify(DAO).update(any(Actor.class));
    }

}
