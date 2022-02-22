package com.ruskaof.client.data;

import java.util.Objects;

public class Person {
    public Person(String name, Integer height, Country nationality, Location location) {
        this.name = name;
        this.height = height;
        this.nationality = nationality;
        this.location = location;
    }

    private String name; //not null, not empty
    private Integer height; //not null, >0
    private Country nationality; //not null
    private Location location; //not null

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", height=" + height +
                ", nationality=" + nationality +
                ", location=" + location +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name) && Objects.equals(height, person.height) && nationality == person.nationality && Objects.equals(location, person.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, height, nationality, location);
    }
}
