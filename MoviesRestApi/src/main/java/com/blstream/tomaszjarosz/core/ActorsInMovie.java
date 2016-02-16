package com.blstream.tomaszjarosz.core;


import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "movie_actors")
public class ActorsInMovie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne(optional = false, cascade = {CascadeType.ALL})
    @JoinColumns(value = {
            @JoinColumn(name = "id_movie", referencedColumnName = "id_movie")})
    private Movie movie;

    @Id
    @ManyToOne(optional = false, cascade = {CascadeType.ALL})
    @JoinColumns(value = {
            @JoinColumn(name = "id_actor", referencedColumnName = "id_actor")})
    private Actor actor;

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public ActorsInMovie(Actor actor, Movie movie) {
        this.actor = actor;
        this.movie = movie;
    }

    public ActorsInMovie() {
    }

}
