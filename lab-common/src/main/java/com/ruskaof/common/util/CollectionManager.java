package com.ruskaof.common.util;

import com.ruskaof.common.data.StudyGroup;
import com.ruskaof.common.data.User;

import java.time.LocalDate;

public interface CollectionManager {

    LocalDate getCreationDate();

    void addUser(User user);

    void addStudyGroup(StudyGroup studyGroup);

    boolean validateUser(String username, String password);
}
