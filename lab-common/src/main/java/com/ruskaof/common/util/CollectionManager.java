package com.ruskaof.common.util;

import com.ruskaof.common.commands.InfoCommand;
import com.ruskaof.common.data.Semester;
import com.ruskaof.common.data.StudyGroup;
import com.ruskaof.common.data.User;

/**
 * This interface does everything but giving the collection itself, so no SOLID principles should be
 * violated :)
 */
public interface CollectionManager {

    void addUser(User user);

    void addStudyGroup(StudyGroup studyGroup);

    boolean validateUser(String username, String password);

    boolean checkIfUsernameUnique(String username);

    boolean checkIfMin(StudyGroup studyGroup);

    void clearOwnedData(String username);

    String filterLessThanSemesterEnumToString(Semester inpEnum);

    InfoCommand.InfoCommandResult getInfoAboutCollections();

    /**
     *
     * @return null if collection is empty and StudyGroup instance if it is not
     */
    StudyGroup getMinByIdGroup();

    String ascendingDataToString();

    void removeStudyGroupById(int id);

    String showSortedByName();

    void updateStudyGroupById(int id, StudyGroup studyGroup);

    void removeGreaterIfOwned(StudyGroup studyGroup, String username);

    boolean validateOwner(String username, int studyGroupId);
}
