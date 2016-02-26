package com.blstream.tomaszjarosz;


import io.dropwizard.client.JerseyClientConfiguration;

public interface JerseyConfiguration {
    JerseyClientConfiguration getJerseyClientConfiguration();
}
