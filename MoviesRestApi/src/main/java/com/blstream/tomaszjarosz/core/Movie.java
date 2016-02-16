package com.blstream.tomaszjarosz.core;


import javax.persistence.*;
import java.io.Serializable;
import java.util.*;


@Entity
@Table(name = "movies")
@NamedQueries({
        @NamedQuery(
                name = "com.blstream.tomaszjarosz.core.Movie.findAll",
                query = "SELECT a FROM Movie a"
        )
})
public class Movie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movie")
    private long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "director", nullable = false)
    private String director;

    @ManyToMany(fetch=FetchType.EAGER, cascade = {CascadeType.ALL}, targetEntity=Actor.class)
    @JoinTable(name = "movie_actors",
            joinColumns = { @JoinColumn(name = "id_movie", referencedColumnName = "id_movie") },
            inverseJoinColumns = { @JoinColumn(name = "id_actor", referencedColumnName = "id_actor") })
    private List<Actor> actors = new ArrayList<Actor>();


    public Movie() {
    }

    public Movie(String title, String director, List<Actor> actors) {
        this.title = title;
        this.director = director;
        this.actors = actors;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
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
                Objects.equals(this.title, that.title) &&
                Objects.equals(this.director, that.director);
    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, title, director);
//    }

}
