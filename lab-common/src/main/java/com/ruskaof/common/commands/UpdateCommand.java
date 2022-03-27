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
        // Stream api would not help
        int intArg;
        try {
            intArg = Integer.parseInt(idArg);
        } catch (NumberFormatException e) {
            return new CommandResultDto("Your argument was incorrect. The command was not executed.");
        }

        if (collectionManager.getMainData().removeIf(x -> x.getId() == intArg)) {
            StudyGroup studyGroup = (StudyGroup) arg;
            studyGroup.setId(intArg);
            collectionManager.getMainData().add(studyGroup);
            return new CommandResultDto("The element was updated successfully");
        } else {
            return new CommandResultDto("Written id was not found. The command was not executed");
        }
    }
}
