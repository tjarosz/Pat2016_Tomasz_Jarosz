package com.blstream.tomaszjarosz.api;

import com.blstream.tomaszjarosz.core.Actor;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

public class MovieRepresentation {

    @NotEmpty
    private String title;
    @NotEmpty
    private String director;
    private List<Actor> actors = new ArrayList<>();

    public MovieRepresentation() {
    }

    public MovieRepresentation(String title, String director, List<Actor> actors) {
        this.title = title;
        this.director = director;
        this.actors = actors;
    }

    @JsonProperty
    public String getTitle() {
        return title;
    }

    @JsonProperty
    public String getDirector() {
        return director;
    }

    @JsonProperty
    public List<Actor> getActors() {
        return actors;
    }
}
