package com.ruskaof.common.commands;

import com.ruskaof.common.data.StudyGroup;
import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.DataManager;
import com.ruskaof.common.util.HistoryManager;


public class AddIfMinCommand extends Command {
    private final StudyGroup studyGroupToAdd;

    public AddIfMinCommand(String username, String password, StudyGroup studyGroupToAdd) {
        super(username, password, "add_if_min");
        this.studyGroupToAdd = studyGroupToAdd;
    }


    @Override
    public CommandResultDto execute(
            DataManager dataManager,
            HistoryManager historyManager
    ) {
        historyManager.addNote(getName());
        if (dataManager.checkIfMin(studyGroupToAdd)) {
            dataManager.addStudyGroup(studyGroupToAdd);
            return new CommandResultDto(true, "Study group was added successfully");
        } else {
            return new CommandResultDto(false, "Study group was not added because it was not min");
        }
    }
}
