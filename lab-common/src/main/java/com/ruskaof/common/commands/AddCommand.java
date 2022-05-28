package com.ruskaof.common.commands;

import com.ruskaof.common.data.StudyGroup;
import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.DataManager;
import com.ruskaof.common.util.HistoryManager;

public class AddCommand extends Command {
    private final StudyGroup studyGroupToAdd;

    public AddCommand(String username, String password, StudyGroup studyGroupToAdd) {
        super(username, password);
        this.studyGroupToAdd = studyGroupToAdd;
    }


    @Override
    public CommandResultDto execute(
            DataManager dataManager,
            HistoryManager historyManager
    ) {

        dataManager.addStudyGroup(studyGroupToAdd);
        return new CommandResultDto(true);
    }
}
