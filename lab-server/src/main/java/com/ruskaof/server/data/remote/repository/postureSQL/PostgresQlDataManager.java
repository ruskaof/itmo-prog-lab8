package com.ruskaof.server.data.remote.repository.postureSQL;

import com.ruskaof.common.data.StudyGroup;
import com.ruskaof.common.util.CollectionManager;

import java.time.LocalDate;
import java.util.TreeSet;

public class PostgresQlDataManager implements CollectionManager {
    private final LocalDate creationDate = LocalDate.now();
    private TreeSet<StudyGroup> mainData = new TreeSet<>();

    @Override
    public void initialiseData(TreeSet<StudyGroup> treeSet) {
        this.mainData = treeSet;
    }

    @Override
    public LocalDate getCreationDate() {
        return creationDate;
    }

    @Override
    public TreeSet<StudyGroup> getMainData() {
        return mainData;
    }

    @Override
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
