package com.ruskaof.server.data.remote.repository.posturesql;

import com.ruskaof.common.data.*;

import java.sql.*;
import java.util.TreeSet;

public class StudyGroupTable extends Table<StudyGroup> {
    private final Connection connection;

    public StudyGroupTable(Connection connection) {
        this.connection = connection;
    }

    public void init() throws SQLException {
        try (
                Statement statement = connection.createStatement()
        ) {
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

    @Override
    public StudyGroup mapRowToObject(ResultSet resultSet) throws SQLException {
        final StudyGroup studyGroup = new StudyGroup(
                resultSet.getString("name"),
                new Coordinates(
                        resultSet.getLong("coordinates_x"),
                        resultSet.getDouble("coordinates_y")
                ),
                resultSet.getInt("students_count"),
                FormOfEducation.valueOf(resultSet.getString("form_of_education")),
                Semester.valueOf(resultSet.getString("semester")),
                new Person(
                        resultSet.getString("admin_name"),
                        resultSet.getInt("admin_height"),
                        Country.valueOf(resultSet.getString("admin_nationality")),
                        new Location(
                                resultSet.getFloat("admin_location_x"),
                                resultSet.getLong("admin_location_y"),
                                resultSet.getString("admin_location_name")
                        )
                ),
                resultSet.getTimestamp("creation_date").toLocalDateTime().toLocalDate()
        );

        studyGroup.setId(resultSet.getInt("id"));

        return studyGroup;
    }

    @Override
    public long add(StudyGroup element) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO study_groups VALUES (default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id"
        )) {
            makePreparedStatement(preparedStatement, element);
            try (
                    ResultSet resultSet = preparedStatement.executeQuery()
            ) {
                resultSet.next();
                return resultSet.getLong("id");
            }
        }
    }

    private void makePreparedStatement(PreparedStatement preparedStatement, StudyGroup studyGroup) throws SQLException {
        int currentParameterOffset = 0;
        preparedStatement.setString(++currentParameterOffset, studyGroup.getName());
        preparedStatement.setLong(++currentParameterOffset, studyGroup.getCoordinates().getX());
        preparedStatement.setDouble(++currentParameterOffset, studyGroup.getCoordinates().getY());
        preparedStatement.setTimestamp(++currentParameterOffset, Timestamp.valueOf(studyGroup.getCreationDate().atStartOfDay()));
        preparedStatement.setInt(++currentParameterOffset, studyGroup.getStudentsCount());
        preparedStatement.setString(++currentParameterOffset, studyGroup.getFormOfEducation().toString());
        preparedStatement.setString(++currentParameterOffset, studyGroup.getSemesterEnum().toString());
        preparedStatement.setString(++currentParameterOffset, studyGroup.getGroupAdmin().getName());
        preparedStatement.setInt(++currentParameterOffset, studyGroup.getGroupAdmin().getHeight());
        preparedStatement.setString(++currentParameterOffset, studyGroup.getGroupAdmin().getNationality().toString());
        preparedStatement.setFloat(++currentParameterOffset, studyGroup.getGroupAdmin().getLocation().getX());
        preparedStatement.setLong(++currentParameterOffset, studyGroup.getGroupAdmin().getLocation().getY());
        preparedStatement.setString(++currentParameterOffset, studyGroup.getGroupAdmin().getLocation().getName());
    }

    @Override
    public TreeSet<StudyGroup> getCollection() throws SQLException {
        final TreeSet<StudyGroup> newCollection = new TreeSet<>();

        try (
                Statement statement = connection.createStatement()
        ) {
            try (
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM study_groups")
            ) {
                int i = 1;

                while (resultSet.next()) {
                    StudyGroup studyGroup = resultSet.getObject(i, StudyGroup.class);
                    newCollection.add(studyGroup);
                }
            }
        }

        return newCollection;
    }
}
