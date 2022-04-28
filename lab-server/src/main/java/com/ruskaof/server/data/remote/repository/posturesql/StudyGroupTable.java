package com.ruskaof.server.data.remote.repository.posturesql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class StudyGroupTable extends Table {
    private final Connection connection;

    public StudyGroupTable(Connection connection) {
        this.connection = connection;
    }

    public void init() throws SQLException {
        try (Statement statement = connection.createStatement();) {
            statement.execute("CREATE TABLE IF NOT EXISTS study_groups ("
                    + "id serial PRIMARY KEY,"
                    + "name varchar(50) NOT NULL ,"
                    + "coordinates_x bigint NOT NULL ,"
                    + "coordinates_y double precision NOT NULL,"
                    + "creation_date TIMESTAMP NOT NULL,"
                    + "students_count integer, "
                    + "form_of_education varchar(30) NOT NULL,"
                    + "semester varchar(20),"
                    + "admin_name varchar(50) NOT NULL,"
                    + "admin_height integer NOT NULL,"
                    + "admin_nationality varchar(20) NOT NULL,"
                    + "admin_location_x real NOT NULL,"
                    + "admin_location_y bigint NOT NULL,"
                    + "admin_location_name varchar(50))");
        }
    }
}
