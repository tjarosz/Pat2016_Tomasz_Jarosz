package com.blstream.tomaszjarosz;

import com.blstream.tomaszjarosz.resources.MovieDetailsResource;
import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import javax.ws.rs.client.Client;


public abstract class MoviesBundle<T extends Configuration & JerseyConfiguration>
        implements ConfiguredBundle<T> {

    public void run(T config, Environment environment) {
        final Client client = new JerseyClientBuilder(environment).using(config.getJerseyClientConfiguration())
                .build("MoviesClient");
        environment.jersey().register(new MovieDetailsResource(client));
    }

    public void initialize(Bootstrap<?> bootstrap) {
    }

}
