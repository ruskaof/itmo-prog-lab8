package com.ruskaof.common.util;

import com.ruskaof.common.data.StudyGroup;

import java.time.LocalDate;
import java.util.TreeSet;

public interface CollectionManager {
    void initialiseData(TreeSet<StudyGroup> treeSet);

    LocalDate getCreationDate();

    TreeSet<StudyGroup> getMainData();

    int getMaxId();
}
