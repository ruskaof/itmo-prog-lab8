package com.ruskaof.common.commands;

import com.ruskaof.common.data.StudyGroup;
import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.CollectionManager;
import com.ruskaof.common.util.HistoryManager;

public class AddCommand extends Command {
    private final StudyGroup arg;

    public AddCommand(StudyGroup arg) {
        super("add");
        this.arg = arg;
    }

    @Override
    public CommandResultDto execute(
            CollectionManager collectionManager,
            HistoryManager historyManager,
            String username
    ) {
        historyManager.addNote(this.getName());
        StudyGroup studyGroup = arg;
        studyGroup.setId(-1);
        collectionManager.addStudyGroup(studyGroup);
        return new CommandResultDto("The element was added successfully");
    }
}
