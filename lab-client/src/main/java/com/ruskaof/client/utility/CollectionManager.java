package com.ruskaof.client.utility;

import com.ruskaof.client.data.Location;
import com.ruskaof.client.data.StudyGroup;

import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

public class CollectionManager {
    private TreeSet<StudyGroup> mainData = new TreeSet<>();
    private final LocalDate creationDate = LocalDate.now();

    public void initialiseData(TreeSet<StudyGroup> treeSet){
        this.mainData = treeSet;
    }


    public LocalDate getCreationDate() {
        return creationDate;
    }

    public TreeSet<StudyGroup> getMainData() {
        return mainData;
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
