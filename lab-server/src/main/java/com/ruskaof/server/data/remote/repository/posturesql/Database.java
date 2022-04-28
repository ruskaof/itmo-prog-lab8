package com.ruskaof.server.data.remote.repository.posturesql;

import java.sql.Connection;
import java.sql.SQLException;

public class Database {
    public final UsersTable usersTable;
    private final StudyGroupTable studyGroupTable;

    public Database(Connection connection) {
        this.studyGroupTable = new StudyGroupTable(connection);
        this.usersTable = new UsersTable(connection);

        try {
            initTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initTables() throws SQLException {
        studyGroupTable.init();
        usersTable.init();
    }

    public StudyGroupTable getStudyGroupTable() {
        return studyGroupTable;
    }

    public UsersTable getUsersTable() {
        return usersTable;
    }
}
