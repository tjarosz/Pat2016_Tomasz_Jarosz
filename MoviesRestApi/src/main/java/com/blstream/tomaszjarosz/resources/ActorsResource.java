package com.blstream.tomaszjarosz.resources;

import com.blstream.tomaszjarosz.api.ActorRepresentation;
import com.blstream.tomaszjarosz.core.Actor;
import com.blstream.tomaszjarosz.db.ActorDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Path("/actors")
@Produces(MediaType.APPLICATION_JSON)
public class ActorsResource {
    private final ActorDAO actorDAO;

    public ActorsResource(ActorDAO actorDAO) {
        this.actorDAO = actorDAO;
    }

    @POST
    @UnitOfWork
    public Actor createActor(ActorRepresentation actorRepresentation) {
        Actor actor = new Actor(actorRepresentation.getName(),
                actorRepresentation.getSurname(),
                actorRepresentation.getDateOfBirth());
        return actorDAO.create(actor);
    }

    @GET
    @UnitOfWork
    public List<Actor> listActors() {
        return actorDAO.findAll();
    }
}
