package com.ruskaof.common.commands;

import com.ruskaof.common.data.StudyGroup;
import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.CollectionManager;
import com.ruskaof.common.util.HistoryManager;


public class AddIfMinCommand extends Command {
    private final StudyGroup arg;

    public AddIfMinCommand(StudyGroup arg) {
        super("add_if_min");
        this.arg = arg;
    }

    @Override
    public CommandResultDto execute(
            CollectionManager collectionManager,
            HistoryManager historyManager
    ) {
        historyManager.addNote(this.getName());

        StudyGroup studyGroup = arg;

        // stream api would be worse in this case (I would lose TreeSet optimisation)
        if (collectionManager.checkIfMin(studyGroup)) {
            collectionManager.addStudyGroup(studyGroup);
            return new CommandResultDto("The element was added successfully");
        } else {
            return new CommandResultDto("The element was not min, so it was not added");
        }
    }
}
