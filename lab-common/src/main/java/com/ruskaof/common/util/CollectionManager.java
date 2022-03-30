package com.ruskaof.common.util;

import com.ruskaof.common.data.StudyGroup;

import java.time.LocalDate;
import java.util.TreeSet;

public interface CollectionManager {
    public void initialiseData(TreeSet<StudyGroup> treeSet);

    public LocalDate getCreationDate();

    public TreeSet<StudyGroup> getMainData();

    public int getMaxId();
}
