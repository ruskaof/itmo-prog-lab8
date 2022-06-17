package com.ruskaof.common.commands;

import com.ruskaof.common.data.StudyGroup;
import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.DataManager;
import com.ruskaof.common.util.HistoryManager;

public class UpdateCommand extends Command implements PrivateAccessedStudyGroupCommand {
    private int idToUpdate;
    private final StudyGroup newStudyGroup;
    private String idToSet;

    public UpdateCommand(String username, String password, StudyGroup newStudyGroup, String idToSet) {
        super(username, password, "update");
        this.idToUpdate = newStudyGroup.getId();
        this.newStudyGroup = newStudyGroup;
    }


    @Override
    public CommandResultDto execute(
            DataManager dataManager,
            HistoryManager historyManager
    ) {
        if (idToSet == null) {
            dataManager.updateStudyGroupById(idToUpdate, newStudyGroup);
            return new CommandResultDto(true, "Updated study group successfully");
        } else {
            try {
                idToUpdate = Integer.parseInt(idToSet);
                dataManager.updateStudyGroupById(idToUpdate, newStudyGroup);
                return new CommandResultDto(true, "Updated study group successfully");
            } catch (Exception e) {
                return new CommandResultDto(false, "Invalid argument");
            }
        }
    }


    @Override
    public int getStudyGroupId() {
        return idToUpdate;
    }
}
