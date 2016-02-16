package com.blstream.tomaszjarosz.db;

import com.blstream.tomaszjarosz.core.Actor;
import com.google.common.base.Optional;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;


public class ActorDAO extends AbstractDAO<Actor> {
    public ActorDAO(SessionFactory factory) {
        super(factory);
    }

    public Optional<Actor> findById(Long id) {
        return Optional.fromNullable(get(id));
    }

    public Actor create(Actor actor) {
        return persist(actor);
    }
    public void delete(Actor actor) {
        currentSession().delete(actor);
    }
    public void update(Actor actor) {
        currentSession().saveOrUpdate(actor);
    }

    public List<Actor> findAll() {
        return list(namedQuery("com.blstream.tomaszjarosz.core.Actor.findAll"));
    }
}
