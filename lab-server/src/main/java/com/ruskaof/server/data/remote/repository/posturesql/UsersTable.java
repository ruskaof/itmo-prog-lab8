package com.ruskaof.server.data.remote.repository.posturesql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class UsersTable extends Table {
    private final Connection connection;

    public UsersTable(Connection connection) {
        this.connection = connection;
    }

    public Boolean validate(String username, String password) {
        return true; // TODO
    }

    @Override
    public void init() throws SQLException {
        Statement statement = connection.createStatement();

        statement.execute("CREATE TABLE IF NOT EXISTS users ("
                + "    id serial PRIMARY KEY,"
                + "    login varchar(50) NOT NULL UNIQUE,"
                + "    password varchar(30) NOT NULL)");
    }

}
