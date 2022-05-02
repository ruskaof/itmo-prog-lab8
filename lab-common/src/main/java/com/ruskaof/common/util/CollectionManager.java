package com.ruskaof.common.util;

import com.ruskaof.common.data.StudyGroup;
import com.ruskaof.common.data.User;

import java.time.LocalDate;
import java.util.TreeSet;

public interface CollectionManager {
    void initialiseData(TreeSet<StudyGroup> studyGroups, TreeSet<User> users);

    LocalDate getCreationDate();

    void addUser(User user);

    void addStudyGroup(StudyGroup studyGroup);

    boolean validateUser(String username, String password);
}
