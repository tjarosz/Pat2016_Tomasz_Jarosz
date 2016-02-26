package com.blstream.tomaszjarosz.core;


import com.blstream.tomaszjarosz.api.MovieRepresentation;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "movies")
@NamedQueries({
        @NamedQuery(
                name = "com.blstream.tomaszjarosz.core.Movie.findAll",
                query = "SELECT a FROM Movie a"
        ),
        @NamedQuery(
                name = "com.blstream.tomaszjarosz.core.Movie.findByTitle",
                query = "SELECT a FROM Movie a WHERE a.Title = :title"
        )
})
public class Movie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movie")
    private long id;

    @Column(name = "title", nullable = false)
    private String Title;

    @Column(name = "director", nullable = false)
    private String Director;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, targetEntity = Actor.class)
    @JoinTable(name = "movie_actors",
            joinColumns = {@JoinColumn(name = "id_movie", referencedColumnName = "id_movie")},
            inverseJoinColumns = {@JoinColumn(name = "id_actor", referencedColumnName = "id_actor")})
    private List<Actor> Actors = new ArrayList<Actor>();

    @Column(name = "year")
    private int Year;

    @Column(name = "rated")
    private String Rated;

    @Column(name = "released")
    private String Released;

    @Column(name = "runtime")
    private String Runtime;

    @Column(name = "genre")
    private String Genre;

    @Column(name = "writer")
    private String Writer;

    @Column(name = "plot")
    private String Plot;

    @Column(name = "language")
    private String Language;

    @Column(name = "country")
    private String Country;

    @Column(name = "awards")
    private String Awards;

    @Column(name = "poster")
    private String Poster;

    @Column(name = "metascore")
    private String Metascore;

    @Column(name = "imdbRating")
    private float imdbRating;

    @Column(name = "imdbVotes")
    private String imdbVotes;

    @Column(name = "imdbID")
    private String imdbID;

    @Column(name = "type")
    private String Type;

    @Column(name = "response")
    private boolean Response;


    public Movie() {
    }

    public Movie(String Title, String Director, List<Actor> Actors, int Year, String Rated, String Released,
                 String Runtime, String Genre, String Writer, String Plot, String Language, String Country,
                 String Awards, String Poster, String Metascore, float imdbRating, String imdbVotes, String imdbID,
                 String Type, boolean Response) {
        this.Title = Title;
        this.Director = Director;
        this.Actors = Actors;
        this.Year = Year;
        this.Rated = Rated;
        this.Released = Released;
        this.Runtime = Runtime;
        this.Genre = Genre;
        this.Writer = Writer;
        this.Plot = Plot;
        this.Language = Language;
        this.Country = Country;
        this.Awards = Awards;
        this.Poster = Poster;
        this.Metascore = Metascore;
        this.imdbRating = imdbRating;
        this.imdbVotes = imdbVotes;
        this.imdbID = imdbID;
        this.Type = Type;
        this.Response = Response;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getDirector() {
        return Director;
    }

    public void setDirector(String director) {
        this.Director = director;
    }

    public List<Actor> getActors() {
        return Actors;
    }

    public void setActors(List<Actor> actors) {
        this.Actors = actors;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        this.Year = year;
    }

    public String getRated() {
        return Rated;
    }

    public void setRated(String rated) {
        this.Rated = rated;
    }

    public String getReleased() {
        return Released;
    }

    public void setReleased(String released) {
        this.Released = released;
    }

    public String getRuntime() {
        return Runtime;
    }

    public void setRuntime(String runtime) {
        this.Runtime = runtime;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        this.Genre = genre;
    }

    public String getWriter() {
        return Writer;
    }

    public void setWriter(String writer) {
        this.Writer = writer;
    }

    public String getPlot() {
        return Plot;
    }

    public void setPlot(String plot) {
        this.Plot = plot;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        this.Language = language;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        this.Country = country;
    }

    public String getAwards() {
        return Awards;
    }

    public void setAwards(String awards) {
        this.Awards = awards;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        this.Poster = poster;
    }

    public String getMetascore() {
        return Metascore;
    }

    public void setMetascore(String metascore) {
        this.Metascore = metascore;
    }

    public float getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(float imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getImdbVotes() {
        return imdbVotes;
    }

    public void setImdbVotes(String imdbVotes) {
        this.imdbVotes = imdbVotes;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        this.Type = type;
    }

    public boolean getResponse() {
        return Response;
    }

    public void setResponse(boolean response) {
        this.Response = response;
    }

    public void getDetailsFromRepresentation(MovieRepresentation movieRepresentation) {
        this.setTitle(movieRepresentation.getTitle());
        this.setYear(movieRepresentation.getYear());
        this.setDirector(movieRepresentation.getDirector());
        this.setActors(movieRepresentation.getActorsList());
        this.setRated(movieRepresentation.getRated());
        this.setReleased(movieRepresentation.getReleased());
        this.setRuntime(movieRepresentation.getRuntime());
        this.setGenre(movieRepresentation.getGenre());
        this.setWriter(movieRepresentation.getWriter());
        this.setPlot(movieRepresentation.getPlot());
        this.setCountry(movieRepresentation.getCountry());
        this.setLanguage(movieRepresentation.getLanguage());
        this.setAwards(movieRepresentation.getAwards());
        this.setPoster(movieRepresentation.getPoster());
        this.setMetascore(movieRepresentation.getMetascore());
        this.setImdbID(movieRepresentation.getImdbID());
        this.setImdbRating(movieRepresentation.getImdbRating());
        this.setImdbVotes(movieRepresentation.getImdbVotes());
        this.setType(movieRepresentation.getType());
        this.setResponse(movieRepresentation.getResponse());
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Movie)) {
            return false;
        }

        final Movie that = (Movie) o;

        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.Title, that.Title) &&
                Objects.equals(this.Director, that.Director);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31).
                append(id).
                append(Title).
                append(Director).
                toHashCode();
    }
}
