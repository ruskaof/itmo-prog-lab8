package com.ruskaof.common.commands;

import com.ruskaof.common.data.StudyGroup;
import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.CollectionManager;
import com.ruskaof.common.util.HistoryManager;

public class RemoveGreaterCommand extends Command {

    public RemoveGreaterCommand(StudyGroup arg) {
        super(arg, "remove_greater");
    }

    @Override
    public CommandResultDto execute(
            CollectionManager collectionManager,
            HistoryManager historyManager
    ) {
//        historyManager.addNote(this.getName());
//        StudyGroup studyGroup = (StudyGroup) arg;
//        collectionManager.getMainData().removeIf(x -> x.compareTo(studyGroup) > 0);
//        return new CommandResultDto("Greater elements were removed successfully");

        return new CommandResultDto(")");
    }
}
