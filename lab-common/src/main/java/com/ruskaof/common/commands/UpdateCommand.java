package com.ruskaof.common.commands;

import com.ruskaof.common.data.StudyGroup;
import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.CollectionManager;
import com.ruskaof.common.util.HistoryManager;

public class UpdateCommand extends Command {
    private final String idArg;

    public UpdateCommand(StudyGroup studyGroup, String id) {
        super(studyGroup, "update");
        this.idArg = id;
    }

    @Override
    public CommandResultDto execute(
            CollectionManager collectionManager,
            HistoryManager historyManager
    ) {
        historyManager.addNote(this.getName());
        int intArg;
        try {
            intArg = Integer.parseInt(idArg);
        } catch (NumberFormatException e) {
            return new CommandResultDto("Your argument was incorrect. The command was not executed.");
        }
        StudyGroup studyGroup = (StudyGroup) arg;

        collectionManager.updateStudyGroupById(intArg, studyGroup);

        return new CommandResultDto("Element was updated if it was in the table");
    }
}
