package com.blstream.tomaszjarosz.core;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "actors")
@NamedQueries({
        @NamedQuery(
                name = "com.blstream.tomaszjarosz.core.Actor.findAll",
                query = "SELECT m FROM Actor m"
        )
})
public class Actor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_actor")
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    public Actor() {
    }

    public Actor(String name, String surname, String dateOfBirth) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Actor)) {
            return false;
        }

        final Actor that = (Actor) o;

        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.name, that.name) &&
                Objects.equals(this.surname, that.surname) &&
                Objects.equals(this.dateOfBirth, that.dateOfBirth);
    }
}
