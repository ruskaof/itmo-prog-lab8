package com.ruskaof.common.commands;

import com.ruskaof.common.data.StudyGroup;
import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.DataManager;
import com.ruskaof.common.util.HistoryManager;

public class UpdateCommand extends Command implements PrivateAccessedStudyGroupCommand {
    private final int idToUpdate;
    private final StudyGroup newStudyGroup;

    public UpdateCommand(String username, String password, StudyGroup newStudyGroup) {
        super(username, password);
        this.idToUpdate = newStudyGroup.getId();
        this.newStudyGroup = newStudyGroup;
    }


    @Override
    public CommandResultDto execute(
            DataManager dataManager,
            HistoryManager historyManager
    ) {
        dataManager.updateStudyGroupById(idToUpdate, newStudyGroup);

        return new CommandResultDto(true);
    }


    @Override
    public int getStudyGroupId() {
        return idToUpdate;
    }
}
