package com.ruskaof.common.commands;

import com.ruskaof.common.data.StudyGroup;
import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.CollectionManager;
import com.ruskaof.common.util.HistoryManager;

public class RemoveGreaterCommand extends Command {
    private final StudyGroup arg;

    public RemoveGreaterCommand(StudyGroup arg) {
        super("remove_greater");
        this.arg = arg;
    }

    @Override
    public CommandResultDto execute(
            CollectionManager collectionManager,
            HistoryManager historyManager
    ) {
        historyManager.addNote(this.getName());
        StudyGroup studyGroup = (StudyGroup) arg;

        collectionManager.removeGreater(studyGroup);

        return new CommandResultDto("Successfully removed greater elements");
    }
}
