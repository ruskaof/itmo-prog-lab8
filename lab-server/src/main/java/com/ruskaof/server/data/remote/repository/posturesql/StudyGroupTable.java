package com.ruskaof.server.data.remote.repository.posturesql;

import com.ruskaof.common.data.Coordinates;
import com.ruskaof.common.data.Country;
import com.ruskaof.common.data.FormOfEducation;
import com.ruskaof.common.data.Location;
import com.ruskaof.common.data.Person;
import com.ruskaof.common.data.Semester;
import com.ruskaof.common.data.StudyGroup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
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
                    + "name varchar(100) NOT NULL,"
                    + "coordinates_x bigint NOT NULL,"
                    + "coordinates_y double precision NOT NULL,"
                    + "creation_date TIMESTAMP NOT NULL,"
                    + "students_count integer,"
                    + "form_of_education varchar(100) NOT NULL,"
                    + "semester varchar(100),"
                    + "admin_name varchar(100) NOT NULL,"
                    + "admin_height integer NOT NULL,"
                    + "admin_nationality varchar(100),"
                    + "admin_location_x real NOT NULL,"
                    + "admin_location_y bigint NOT NULL,"
                    + "admin_location_name varchar(100),"
                    + "author_username varchar (100) NOT NULL)");
        }
    }

    // рубрика "в котлине было бы в 100 раз короче"
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
                resultSet.getString("semester") != null ? Semester.valueOf(resultSet.getString("semester")) : null,
                new Person(
                        resultSet.getString("admin_name"),
                        resultSet.getInt("admin_height"),
                        resultSet.getString("admin_nationality") != null ? Country.valueOf(resultSet.getString("admin_nationality")) : null,
                        new Location(
                                resultSet.getFloat("admin_location_x"),
                                resultSet.getLong("admin_location_y"),
                                resultSet.getString("admin_location_name") != null ? resultSet.getString("admin_location_name") : null
                        )
                ),
                resultSet.getTimestamp("creation_date").toLocalDateTime().toLocalDate(),
                resultSet.getString("author_username")
        );

        studyGroup.setId(resultSet.getInt("id"));

        return studyGroup;
    }

    @Override
    public long add(StudyGroup element) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO study_groups VALUES (default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id"
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

    // А в котлине было бы красивее ()
    private void makePreparedStatement(PreparedStatement preparedStatement, StudyGroup studyGroup) throws SQLException {
        int currentParameterOffset = 0;
        preparedStatement.setString(++currentParameterOffset, studyGroup.getName());
        preparedStatement.setLong(++currentParameterOffset, studyGroup.getCoordinates().getX());
        preparedStatement.setDouble(++currentParameterOffset, studyGroup.getCoordinates().getY());
        preparedStatement.setTimestamp(++currentParameterOffset, Timestamp.valueOf(studyGroup.getCreationDate().atStartOfDay()));
        if (studyGroup.getStudentsCount() != null) {
            preparedStatement.setInt(++currentParameterOffset, studyGroup.getStudentsCount());
        } else {
            preparedStatement.setNull(++currentParameterOffset, Types.INTEGER);
        }
        preparedStatement.setString(++currentParameterOffset, studyGroup.getFormOfEducation().toString());
        if (studyGroup.getSemesterEnum() != null) {
            preparedStatement.setString(++currentParameterOffset, studyGroup.getSemesterEnum().toString());
        } else {
            preparedStatement.setNull(++currentParameterOffset, Types.VARCHAR);
        }
        preparedStatement.setString(++currentParameterOffset, studyGroup.getGroupAdmin().getName());
        preparedStatement.setInt(++currentParameterOffset, studyGroup.getGroupAdmin().getHeight());
        if (studyGroup.getGroupAdmin().getNationality() != null) {
            preparedStatement.setString(++currentParameterOffset, studyGroup.getGroupAdmin().getNationality().toString());
        } else {
            preparedStatement.setNull(++currentParameterOffset, Types.VARCHAR);
        }
        preparedStatement.setFloat(++currentParameterOffset, studyGroup.getGroupAdmin().getLocation().getX());
        preparedStatement.setLong(++currentParameterOffset, studyGroup.getGroupAdmin().getLocation().getY());
        if (studyGroup.getGroupAdmin().getLocation().getName() != null) {
            preparedStatement.setString(++currentParameterOffset, studyGroup.getGroupAdmin().getLocation().getName());
        } else {
            preparedStatement.setNull(++currentParameterOffset, Types.VARCHAR);
        }
        preparedStatement.setString(++currentParameterOffset, studyGroup.getAuthorName());
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


                while (resultSet.next()) {
                    StudyGroup studyGroup = mapRowToObject(resultSet);
                    newCollection.add(studyGroup);
                }
            }
        }

        return newCollection;
    }

    public void clearOwnedData(String username) throws SQLException {

        String query = "DELETE FROM study_groups WHERE author_username=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.execute();
        }

    }

    public void removeById(int id) throws SQLException {
        try (
                Statement statement = connection.createStatement()
        ) {
            statement.execute("DELETE FROM study_groups WHERE id=" + id);

        }
    }

    public String showSortedByName() throws SQLException {
        final List<StudyGroup> sortedCollection = new ArrayList<>();

        try (
                Statement statement = connection.createStatement()
        ) {
            try (
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM study_groups ORDER BY name")
            ) {


                while (resultSet.next()) {
                    StudyGroup studyGroup = mapRowToObject(resultSet);
                    sortedCollection.add(studyGroup);
                }
            }
        }

        return sortedCollection.toString();
    }

    public void updateById(int id, StudyGroup studyGroup) throws SQLException {

        String query = "UPDATE study_groups SET "
                + "name=?"
                + ",coordinates_x=?"
                + ",coordinates_y=?"
                + ",creation_date=?"
                + ",students_count=?"
                + ",form_of_education=?"
                + ",semester=?"
                + ",admin_name=?"
                + ",admin_height=?"
                + ",admin_nationality=?"
                + ",admin_location_x=?"
                + ",admin_location_y=?"
                + ",admin_location_name=?"
                + ",author_username=? "
                + "WHERE id =" + id;

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            makePreparedStatement(preparedStatement, studyGroup);
            preparedStatement.execute();
        }


    }

    public void removeGreaterIfOwned(StudyGroup studyGroup, String username) throws SQLException {
        try (
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM study_groups WHERE students_count>" + studyGroup.getStudentsCount() + "AND author_username=?")
        ) {
            preparedStatement.setString(1, username);
            preparedStatement.execute();
        }
    }
}
