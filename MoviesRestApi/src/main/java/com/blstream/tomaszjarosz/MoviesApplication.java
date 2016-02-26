package com.blstream.tomaszjarosz;

import com.blstream.tomaszjarosz.core.Actor;
import com.blstream.tomaszjarosz.core.Movie;
import com.blstream.tomaszjarosz.db.ActorDAO;
import com.blstream.tomaszjarosz.db.MovieDAO;
import com.blstream.tomaszjarosz.resources.*;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

import javax.ws.rs.client.Client;


public class MoviesApplication extends Application<MoviesConfiguration> {
    private final HibernateBundle<MoviesConfiguration> hibernate = new HibernateBundle<MoviesConfiguration>(Movie.class, Actor.class) {

        public PooledDataSourceFactory getDataSourceFactory(MoviesConfiguration moviesConfiguration) {
            return moviesConfiguration.getDataSourceFactory();
        }
    };

    private final MoviesBundle<MoviesConfiguration> moviesBundle
            = new MoviesBundle<MoviesConfiguration>() {
    };

    public static void main(String[] args) throws Exception {
        new MoviesApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<MoviesConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(
                        bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)
                )
        );

        bootstrap.addBundle(new AssetsBundle());
        bootstrap.addBundle(new MigrationsBundle<MoviesConfiguration>() {

            public DataSourceFactory getDataSourceFactory(MoviesConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });
        bootstrap.addBundle(hibernate);
        bootstrap.addBundle(moviesBundle);
        bootstrap.addBundle(new ViewBundle());
    }

    @Override
    public void run(MoviesConfiguration moviesConfiguration, Environment environment) throws Exception {
        final Client client = new JerseyClientBuilder(environment)
                .using(moviesConfiguration.getJerseyClientConfiguration())
                .build(getName());
        final MovieDAO movieDAO = new MovieDAO(hibernate.getSessionFactory());
        final ActorDAO actorDAO = new ActorDAO(hibernate.getSessionFactory());
        environment.jersey().register(new MoviesResource(movieDAO, client));
        environment.jersey().register(new MovieResource(movieDAO));
        environment.jersey().register(new ActorsResource(actorDAO));
        environment.jersey().register(new ActorResource(actorDAO));
        environment.jersey().register(new MoviesViewResource(movieDAO));
    }
}
