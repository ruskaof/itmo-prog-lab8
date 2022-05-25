package com.ruskaof.common.commands;

import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.DataManager;
import com.ruskaof.common.util.HistoryManager;

public class RemoveByIdCommand extends Command implements PrivateAccessedStudyGroupCommand {
    private final int idToRemove;

    public RemoveByIdCommand(String username, String password, int idToRemove) {
        super(username, password);
        this.idToRemove = idToRemove;
    }


    @Override
    public CommandResultDto execute(
            DataManager dataManager,
            HistoryManager historyManager
    ) {

        dataManager.removeStudyGroupById(idToRemove);

        return new CommandResultDto(true);
    }

    @Override
    public int getStudyGroupId() {
        return idToRemove;
    }
}
