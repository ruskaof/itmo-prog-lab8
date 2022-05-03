package com.ruskaof.server.domain.repository;

import com.ruskaof.common.commands.InfoCommand;
import com.ruskaof.common.data.Semester;
import com.ruskaof.common.data.StudyGroup;
import com.ruskaof.common.data.User;
import com.ruskaof.common.util.CollectionManager;
import com.ruskaof.server.data.remote.repository.posturesql.Database;
import com.ruskaof.server.util.Encryptor;
import org.slf4j.Logger;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.StringJoiner;
import java.util.TreeSet;

public class DataManager implements CollectionManager {
    private final LocalDate creationDate = LocalDate.now();
    private final Database database;
    private TreeSet<StudyGroup> mainData = new TreeSet<>();
    private TreeSet<User> users = new TreeSet<>();
    private final Logger logger;

    public DataManager(Database database, Logger logger) {
        this.database = database;
        this.logger = logger;

        try {
            initialiseData(
                    database.getStudyGroupTable().getCollection(),
                    database.getUsersTable().getCollection()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        logger.info("Made a data manager with initialised collections:\n"
                + mainData + "\n\n" + users);
    }


    public LocalDate getCreationDate() {
        return creationDate;
    }

    @Override
    public void addUser(User user) {
        final User encryptedUser = new User(user.getId(), Encryptor.encryptThisString(user.getPassword()), user.getName());

        try {
            database.getUsersTable().add(user);
        } catch (SQLException e) {
            System.out.println("?FSFSDFS)");
            e.printStackTrace();
        }
        users.add(user);

        logger.info("Successfully registered a new user: " + encryptedUser);
    }

    @Override
    public void addStudyGroup(StudyGroup studyGroup) {
        try {
            database.getStudyGroupTable().add(studyGroup);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mainData.add(studyGroup);

        logger.info("Successfully added a study group: " + studyGroup);
    }

    @Override
    public boolean validateUser(String username, String password) {
        return users.stream().anyMatch(it -> it.getName().equals(username) && it.getPassword().equals(Encryptor.encryptThisString(password)));
    }

    @Override
    public boolean checkIfUsernameUnique(String username) {
        return users.stream().noneMatch(it -> it.getName().matches(username));
    }

    @Override
    public boolean checkIfMin(StudyGroup studyGroup) {
        return mainData.isEmpty() || studyGroup.compareTo(mainData.first()) < 0;
    }

    @Override
    public void clearData() {
        try {
            database.getStudyGroupTable().clearData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        updateCollections();
    }

    @Override
    public String filterLessThanSemesterEnumToString(Semester inpEnum) {
        StringJoiner output = new StringJoiner("\n\n");
        mainData.stream().filter(it -> it.getSemesterEnum().compareTo(inpEnum) < 0).forEach(it -> output.add(it.toString()));
        return output.toString();
    }

    @Override
    public InfoCommand.InfoCommandResult getInfoAboutCollections() {
        if (mainData.isEmpty()) {
            return new InfoCommand.InfoCommandResult(
                    0,
                    creationDate,
                    0
            );
        }
        if (mainData.first().getStudentsCount() == null) {
            return new InfoCommand.InfoCommandResult(
                    mainData.size(),
                    creationDate,
                    0
            );
        }
        return new InfoCommand.InfoCommandResult(
                mainData.size(),
                creationDate,
                mainData.first().getStudentsCount()
        );
    }


    @Override
    public StudyGroup getMinByIdGroup() {
        return mainData.stream().min(Comparator.comparingInt(StudyGroup::getId)).orElse(null);
    }

    @Override
    public String ascendingDataToString() {
        return mainData.toString();
    }

    @Override
    public void removeStudyGroupById(int id) {
        try {
            database.getStudyGroupTable().removeById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        updateCollections();
    }

    @Override
    public String showSortedByName() {
        try {
            return database.getStudyGroupTable().showSortedByName();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateStudyGroupById(int id, StudyGroup studyGroup) {
        try {
            database.getStudyGroupTable().updateById(id, studyGroup);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateCollections() {
        try {
            initialiseData(database.getStudyGroupTable().getCollection(), database.getUsersTable().getCollection());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void initialiseData(TreeSet<StudyGroup> studyGroups, TreeSet<User> newUsers) {
        this.mainData = studyGroups;
        this.users = newUsers;
    }
}
