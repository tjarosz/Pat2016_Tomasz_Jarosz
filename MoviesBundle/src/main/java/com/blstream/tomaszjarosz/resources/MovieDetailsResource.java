package com.blstream.tomaszjarosz.resources;

import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;


@Path("/movies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MovieDetailsResource {
    private Client client;

    public MovieDetailsResource(Client client) {
        this.client = client;
    }

    @GET
    @UnitOfWork
    @Path("/details/{title}")
    public String getMovieDetails(@PathParam("title") String title) {
        String response = client
                .target("http://www.omdbapi.com/?t=" + title)
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .buildGet()
                .invoke()
                .readEntity(String.class);
        return response;
    }

}
