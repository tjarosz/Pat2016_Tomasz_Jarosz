package com.blstream.tomaszjarosz.api;

import com.blstream.tomaszjarosz.core.Actor;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MovieRepresentation {

    private String title;
    private String director;
    private List<Actor> actorsList = new ArrayList<>();
    private String actors;
    private int year;
    private String rated;
    private String released;
    private String runtime;
    private String genre;
    private String writer;
    private String plot;
    private String language;
    private String country;
    private String awards;
    private String poster;
    private String metascore;
    private float imdbRating;
    private String imdbVotes;
    private String imdbID;
    private String type;
    private boolean response;
    private String error;

    public MovieRepresentation() {
    }

    public MovieRepresentation(String title, String director, String actors) {
        this.title = title;
        this.director = director;
        this.actors = actors;
    }

    public MovieRepresentation(String title, String director, String actors, int year, String rated, String released,
                               String runtime, String genre, String writer, String plot, String language, String country,
                               String awards, String poster, String metascore, float imdbRating, String imdbVotes, String imdbID,
                               String type, boolean response) {
        this.title = title;
        this.director = director;
        this.actors = actors;
        this.year = year;
        this.rated = rated;
        this.released = released;
        this.runtime = runtime;
        this.genre = genre;
        this.writer = writer;
        this.plot = plot;
        this.language = language;
        this.country = country;
        this.awards = awards;
        this.poster = poster;
        this.metascore = metascore;
        this.imdbRating = imdbRating;
        this.imdbVotes = imdbVotes;
        this.imdbID = imdbID;
        this.type = type;
        this.response = response;
    }

    @JsonProperty("Title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("Director")
    public String getDirector() {
        return director;
    }

    @JsonProperty("Actors")
    public String getActors() {
        return actors;
    }

    public List<Actor> getActorsList() {
        if (actors != null) {
            List<Actor> actorsList = new ArrayList<>();
            List<String> actorsItems = Arrays.asList(actors.split("\\s*,\\s*"));
            for (String a : actorsItems) {
                Actor actor = new Actor();
                String name = a.split(" ")[0];
                String surname = a.split(" ")[1];
                actor.setName(name);
                actor.setSurname(surname);
                actorsList.add(actor);
            }
            return actorsList;
        } else
            return null;
    }

    @JsonProperty("Year")
    public int getYear() {
        return year;
    }

    @JsonProperty("Rated")
    public String getRated() {
        return rated;
    }

    @JsonProperty("Released")
    public String getReleased() {
        return released;
    }

    @JsonProperty("Runtime")
    public String getRuntime() {
        return runtime;
    }

    @JsonProperty("Genre")
    public String getGenre() {
        return genre;
    }

    @JsonProperty("Writer")
    public String getWriter() {
        return writer;
    }

    @JsonProperty("Plot")
    public String getPlot() {
        return plot;
    }

    @JsonProperty("Language")
    public String getLanguage() {
        return language;
    }

    @JsonProperty("Country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("Awards")
    public String getAwards() {
        return awards;
    }

    @JsonProperty("Poster")
    public String getPoster() {
        return poster;
    }

    @JsonProperty("Metascore")
    public String getMetascore() {
        return metascore;
    }

    @JsonProperty
    public float getImdbRating() {
        return imdbRating;
    }

    @JsonProperty
    public String getImdbVotes() {
        return imdbVotes;
    }

    @JsonProperty
    public String getImdbID() {
        return imdbID;
    }

    @JsonProperty("Type")
    public String getType() {
        return type;
    }

    @JsonProperty("Response")
    public boolean getResponse() {
        return response;
    }

    @JsonProperty("Error")
    public String getError() {
        return error;
    }

}
