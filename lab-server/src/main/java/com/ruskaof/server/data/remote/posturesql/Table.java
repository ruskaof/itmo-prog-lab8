package com.ruskaof.server.data.remote.posturesql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeSet;

public abstract class Table<T> {
    public abstract void init() throws SQLException;

    public abstract TreeSet<T> getCollection() throws SQLException;

    public abstract T mapRowToObject(ResultSet resultSet) throws SQLException;

    public abstract long add(T element) throws SQLException;
}
