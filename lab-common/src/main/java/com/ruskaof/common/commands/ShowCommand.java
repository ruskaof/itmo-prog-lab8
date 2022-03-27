package com.ruskaof.common.commands;

import com.ruskaof.common.data.StudyGroup;
import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.CollectionManager;
import com.ruskaof.common.util.HistoryManager;

import java.util.StringJoiner;


public class ShowCommand extends Command {


    public ShowCommand() {
        super("", "show");
    }

    @Override
    public CommandResultDto execute(
            CollectionManager collectionManager,
            HistoryManager historyManager
    ) {
        // Stream api would not help
        StringJoiner output = new StringJoiner("\n\n");
        if (collectionManager.getMainData().isEmpty()) {
            return new CommandResultDto("Collection is empty");
        }
        for (StudyGroup studyGroup : collectionManager.getMainData()) {
            output.add(studyGroup.toString());
        }

        return new CommandResultDto(output.toString());
    }
}
