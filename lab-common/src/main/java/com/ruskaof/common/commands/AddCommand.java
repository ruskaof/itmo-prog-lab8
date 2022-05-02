package com.ruskaof.common.commands;

import com.ruskaof.common.data.StudyGroup;
import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.CollectionManager;
import com.ruskaof.common.util.HistoryManager;

public class AddCommand extends Command {

    public AddCommand(StudyGroup arg) {
        super(arg, "add");
    }

    @Override
    public CommandResultDto execute(
            CollectionManager collectionManager,
            HistoryManager historyManager
    ) {
        historyManager.addNote(this.getName());
        StudyGroup studyGroup = (StudyGroup) arg;
        studyGroup.setId(-1);
        collectionManager.addStudyGroup(studyGroup);
        return new CommandResultDto("The element was added successfully");
    }
}
