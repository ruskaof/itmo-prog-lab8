package com.ruskaof.common.commands;

import com.ruskaof.common.data.StudyGroup;
import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.CollectionManager;
import com.ruskaof.common.util.HistoryManager;


public class MinByIDCommand extends Command {
    private final String arg;

    public MinByIDCommand(String arg) {
        super("min_by_id");
        this.arg = arg;
    }

    @Override
    public CommandResultDto execute(
            CollectionManager collectionManager,
            HistoryManager historyManager
    ) {
        historyManager.addNote(this.getName());

        final StudyGroup minStudyGroup = collectionManager.getMinByIdGroup();

        if (minStudyGroup == null) {
            return new CommandResultDto("Collection is empty :(");
        } else {
            return new CommandResultDto(minStudyGroup);
        }

    }
}
