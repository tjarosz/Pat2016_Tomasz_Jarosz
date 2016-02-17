package com.blstream.tomaszjarosz.resources;

import com.blstream.tomaszjarosz.core.Actor;
import com.blstream.tomaszjarosz.db.ActorDAO;
import com.google.common.base.Optional;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/actors/{actorId}")
@Produces(MediaType.APPLICATION_JSON)
public class ActorResource {
    private final ActorDAO actorDAO;

    public ActorResource(ActorDAO actorDAO) {
        this.actorDAO = actorDAO;
    }

    @GET
    @UnitOfWork
    public Actor getActor(@PathParam("actorId") LongParam actorId) {
        return findSafely(actorId.get());
    }

    @DELETE
    @UnitOfWork
    public void deleteActor(@PathParam("actorId") LongParam actorId) {
        actorDAO.delete(findSafely(actorId.get()));
    }

    @PUT
    @UnitOfWork
    public Actor updateActor(@PathParam("actorId") Long actorId, @Valid Actor actor) {
        //Movie movieToUpdate = findSafely(movieId.get());
        actor.setId(actorId);
        actorDAO.update(actor);
        return actor;
    }

    private Actor findSafely(long actorId) {
        final Optional<Actor> actor = actorDAO.findById(actorId);
        if (!actor.isPresent()) {
            throw new NotFoundException("No such actor.");
        }
        return actor.get();
    }

}
