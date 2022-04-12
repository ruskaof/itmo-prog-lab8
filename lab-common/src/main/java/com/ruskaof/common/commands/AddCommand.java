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
        // stream api would not help
        historyManager.addNote(this.getName());
        StudyGroup studyGroup = (StudyGroup) arg;
        studyGroup.setId(collectionManager.getMaxId() + 1);
        collectionManager.getMainData().add(studyGroup);
        return new CommandResultDto("The element was added successfully");
    }
}
