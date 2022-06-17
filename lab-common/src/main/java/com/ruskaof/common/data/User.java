package com.ruskaof.common.data;

import java.util.Objects;

public class User implements Comparable<User> {
    private long id;
    private final String password;
    private final String name;
    private final String color;

    public User(long id, String password, String name, String color) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.color = color;
    }

    public long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id && Objects.equals(password, user.password) && Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, password, name);
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int compareTo(User o) {
        return o.getId() - this.getId() > 0 ? 1 : -1;
    }

    @Override
    public String toString() {
        return "User{"
                + "id=" + id
                + ", password='" + password + '\''
                + ", name='" + name + '\''
                + '}';
    }

    public String getColor() {
        return color;
    }
}
