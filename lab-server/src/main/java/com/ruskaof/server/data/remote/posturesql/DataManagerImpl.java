package com.ruskaof.server.data.remote.posturesql;

import com.ruskaof.common.data.Semester;
import com.ruskaof.common.data.StudyGroup;
import com.ruskaof.common.data.User;
import com.ruskaof.common.util.Encryptor;
import org.slf4j.Logger;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class DataManagerImpl implements com.ruskaof.common.util.DataManager {
    private final Database database;
    private TreeSet<StudyGroup> mainData = new TreeSet<>();
    private TreeSet<User> users = new TreeSet<>();
    private final Logger logger;
    private final ReadWriteLock lock = new ReentrantReadWriteLock(true);


    public DataManagerImpl(Database database, Logger logger) {
        this.database = database;
        this.logger = logger;


    }

    @Override
    public void addUser(User user) {
        Lock writeLock = lock.writeLock();
        try {
            writeLock.lock();
            final User encryptedUser = new User(user.getId(), Encryptor.encryptThisString(user.getPassword()), user.getName());

            final int generatedId;

            try {
                generatedId = database.getUsersTable().add(encryptedUser);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            encryptedUser.setId(generatedId);
            users.add(encryptedUser);

            logger.info("Successfully registered a new user: " + encryptedUser);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void addStudyGroup(StudyGroup studyGroup) {
        Lock writeLock = lock.writeLock();
        try {
            writeLock.lock();
            final int generatedId = database.getStudyGroupTable().add(studyGroup);
            studyGroup.setId(generatedId);
            mainData.add(studyGroup);
            logger.info("Successfully added a study group: " + studyGroup);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            writeLock.unlock();
        }

    }

    @Override
    public boolean validateUser(String username, String password) {
        Lock readLock = lock.readLock();
        try {
            readLock.lock();
            return users.stream().anyMatch(it -> it.getName().equals(username) && it.getPassword().equals(Encryptor.encryptThisString(password)));
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean checkIfUsernameUnique(String username) {
        Lock readLock = lock.readLock();
        try {
            readLock.lock();
            return users.stream().noneMatch(it -> it.getName().matches(username));
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean checkIfMin(StudyGroup studyGroup) {
        Lock readLock = lock.readLock();
        try {
            readLock.lock();
            return mainData.isEmpty() || studyGroup.compareTo(mainData.first()) < 0;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void clearOwnedData(String username) {
        Lock writeLock = lock.writeLock();
        try {
            writeLock.lock();
            database.getStudyGroupTable().clearOwnedData(username);
            mainData.removeIf(it -> it.getAuthorName().equals(username));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            writeLock.unlock();
        }

    }

    @Override
    public String filterLessThanSemesterEnumToString(Semester inpEnum) {
        Lock readLock = lock.readLock();
        try {
            readLock.lock();
            StringJoiner output = new StringJoiner("\n\n");
            mainData.stream().filter(it -> it.getSemesterEnum().compareTo(inpEnum) < 0).forEach(it -> output.add(it.toString()));
            return output.toString();
        } finally {
            readLock.unlock();
        }
    }



    @Override
    public StudyGroup getMinByIdGroup() {
        Lock readLock = lock.readLock();
        try {
            readLock.lock();
            return mainData.stream().min(Comparator.comparingInt(StudyGroup::getId)).orElse(null);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public String ascendingDataToString() {
        Lock readLock = lock.readLock();
        try {
            readLock.lock();
            return mainData.toString();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void removeStudyGroupById(int id) {
        Lock writeLock = lock.writeLock();
        try {
            writeLock.lock();
            database.getStudyGroupTable().removeById(id);
            mainData.removeIf(it -> it.getId() == id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public List<StudyGroup> showSortedByName() {
        Lock readLock = lock.readLock();
        try {
            readLock.lock();
            return mainData
                    .stream()
                    .sorted(Comparator.comparing(StudyGroup::getName))
                    .collect(Collectors.toList());
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void updateStudyGroupById(int id, StudyGroup studyGroup) {
        Lock writeLock = lock.writeLock();
        try {
            writeLock.lock();
            database.getStudyGroupTable().updateById(id, studyGroup);
            mainData.removeIf(it -> it.getId() == id);
            mainData.add(studyGroup);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void removeGreaterIfOwned(StudyGroup studyGroup, String username) {
        Lock writeLock = lock.writeLock();
        try {
            writeLock.lock();
            final Set<Integer> idsToRemove =
                    mainData
                            .tailSet(studyGroup)
                            .stream()
                            .filter(it -> it.getAuthorName().equals(username))
                            .map(StudyGroup::getId)
                            .collect(Collectors.toSet());
            for (int id : idsToRemove) {
                database.getStudyGroupTable().removeById(id);
                mainData.removeIf(it -> idsToRemove.contains(it.getId()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public boolean validateOwner(String username, int studyGroupId) {
        Lock readLock = lock.readLock();
        try {
            readLock.lock();
            return mainData.stream().anyMatch(it -> it.getId() == studyGroupId && it.getAuthorName().equals(username));
        } finally {
            readLock.unlock();
        }
    }


    @Override
    public void initialiseData() {
        try {
            this.mainData = database.getStudyGroupTable().getCollection();
            this.users = database.getUsersTable().getCollection();
            logger.info("Made a data manager with initialised collections:\n"
                    + mainData + "\n\n" + users);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
