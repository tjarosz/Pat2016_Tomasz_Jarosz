package com.blstream.tomaszjarosz.db;

import com.blstream.tomaszjarosz.core.Movie;
import com.google.common.base.Optional;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;

import java.util.List;


public class MovieDAO extends AbstractDAO<Movie> {
    public MovieDAO(SessionFactory factory) {
        super(factory);
    }

    public Optional<Movie> findById(Long id) {
        return Optional.fromNullable(get(id));
    }

    public Movie create(Movie movie) {
        return persist(movie);
    }

    public void delete(Movie movie) {
        currentSession().delete(movie);
    }

    public void update(Movie movie) {
        currentSession().saveOrUpdate(movie);
    }

    public List<Movie> findAll() {
        return list(namedQuery("com.blstream.tomaszjarosz.core.Movie.findAll").setResultTransformer(Criteria.ROOT_ENTITY));
    }
}
