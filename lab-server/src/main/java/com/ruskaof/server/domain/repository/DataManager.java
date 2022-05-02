package com.ruskaof.server.domain.repository;

import com.ruskaof.common.data.StudyGroup;
import com.ruskaof.common.data.User;
import com.ruskaof.common.util.CollectionManager;
import com.ruskaof.server.data.remote.repository.posturesql.Database;
import com.ruskaof.server.util.Encryptor;
import org.slf4j.Logger;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.TreeSet;

public class DataManager implements CollectionManager {
    private final LocalDate creationDate = LocalDate.now();
    private final Database database;
    private TreeSet<StudyGroup> mainData = new TreeSet<>();
    private TreeSet<User> users = new TreeSet<>();

    public DataManager(Database database, Logger logger) {
        this.database = database;

        try {
            initialiseData(database.getStudyGroupTable().getCollection(), database.getUsersTable().getCollection());
        } catch (SQLException e) {
            System.out.println("wtf");
        }

        logger.info("Made a data manager with initialised collections:\n" +
                mainData.toString() + "\n" + users.toString());
    }

    private void initialiseData(TreeSet<StudyGroup> studyGroups, TreeSet<User> users) {
        this.mainData = studyGroups;
        this.users = users;
    }


    public LocalDate getCreationDate() {
        return creationDate;
    }

    @Override
    public void addUser(User user) {
        try {
            database.getUsersTable().add(user);
        } catch (SQLException e) {
            System.out.println("?FSFSDFS)");
            e.printStackTrace();
        }
        users.add(user);
    }

    @Override
    public void addStudyGroup(StudyGroup studyGroup) {
        try {
            database.getStudyGroupTable().add(studyGroup);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mainData.add(studyGroup);
    }

    @Override
    public boolean validateUser(String username, String password) {
        return users.stream().anyMatch(it -> it.getName().equals(username) && it.getPassword().equals(Encryptor.encryptThisString(password)));
    }

    public int getMaxId() {
        int maxId = 0;
        for (StudyGroup studyGroup : mainData) {
            if (studyGroup.getId() > maxId) {
                maxId = studyGroup.getId();
            }
        }
        return maxId;
    }
}
