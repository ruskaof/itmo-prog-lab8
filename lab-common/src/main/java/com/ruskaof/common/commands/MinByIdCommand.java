package com.ruskaof.common.commands;

import com.ruskaof.common.data.StudyGroup;
import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.DataManager;
import com.ruskaof.common.util.HistoryManager;


public class MinByIdCommand extends Command {
    private final String arg;

    public MinByIdCommand(String username, String password, String arg) {
        super(username,password, "min_by_id");
        this.arg = arg;
    }


    @Override
    public CommandResultDto execute(DataManager dataManager, HistoryManager historyManager) {
        historyManager.addNote(this.getName());

        final StudyGroup minStudyGroup = dataManager.getMinByIdGroup();

        if (minStudyGroup == null) {
            return new CommandResultDto(true, "Collection is empty :(");
        } else {
            return new CommandResultDto(true, minStudyGroup.toString());
        }
    }
}
