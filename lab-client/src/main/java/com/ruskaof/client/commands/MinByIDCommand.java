package com.ruskaof.client.commands;

import com.ruskaof.client.data.StudyGroup;
import com.ruskaof.client.utility.CollectionManager;


public class MinByIDCommand extends Command {
    public MinByIDCommand(CollectionManager collectionManager) {
        super("min_by_id");
        this.collectionManager = collectionManager;
    }

    private final CollectionManager collectionManager;

    @Override
    public CommandResult execute(String arg) {
        int minID = Integer.MAX_VALUE;
        StudyGroup minIdStudyGroup = null;

        for (StudyGroup studyGroup : collectionManager.getMainData()) {
            if (minID > studyGroup.getId()) {
                minID = studyGroup.getId();
                minIdStudyGroup = studyGroup;
            }
        }

        assert minIdStudyGroup != null;
        return new CommandResult(false, true, minIdStudyGroup.toString());
    }
}
