package com.ruskaof.common.commands;

import com.ruskaof.common.data.StudyGroup;
import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.DataManager;
import com.ruskaof.common.util.HistoryManager;

public class AddCommand implements Command {
    private final StudyGroup arg;

    public AddCommand(StudyGroup arg) {
        this.arg = arg;
    }

    @Override
    public CommandResultDto execute(
            DataManager dataManager,
            HistoryManager historyManager,
            String username
    ) {
        StudyGroup studyGroup = arg;
        studyGroup.setId(-1);
        dataManager.addStudyGroup(studyGroup);
        return new CommandResultDto(true);
    }
}
