package com.blstream.tomaszjarosz.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class ActorRepresentation {

    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;
    private String dateOfBirth;

    public ActorRepresentation() {
    }

    public ActorRepresentation(String name, String surname,
                               String dateOfBirth) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    @JsonProperty
    public String getSurname() {
        return surname;
    }

    @JsonProperty
    public String getDateOfBirth() {
        return dateOfBirth;
    }
}
