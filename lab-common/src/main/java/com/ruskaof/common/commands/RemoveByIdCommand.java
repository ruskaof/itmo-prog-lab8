package com.ruskaof.common.commands;

import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.DataManager;
import com.ruskaof.common.util.HistoryManager;

public class RemoveByIdCommand extends Command implements PrivateAccessedStudyGroupCommand {
    private int idToRemove;

    public RemoveByIdCommand(String username, String password, int idToRemove) {
        super(username, password, "remove_by_id");
        this.idToRemove = idToRemove;
    }

    public RemoveByIdCommand(String username, String password, String idToRemove) {
        super(username, password, "remove_by_id");
        try {
            this.idToRemove = Integer.parseInt(idToRemove);
        } catch (Exception e) {
            this.idToRemove = Integer.MIN_VALUE;
        }
    }


    @Override
    public CommandResultDto execute(
            DataManager dataManager,
            HistoryManager historyManager
    ) {
        if (idToRemove == Integer.MIN_VALUE) {
            return  new CommandResultDto(false, "Incorrect arg");
        }

        dataManager.removeStudyGroupById(idToRemove);

        return new CommandResultDto(true, "The study group was removed if it was present and yours");
    }

    @Override
    public int getStudyGroupId() {
        return idToRemove;
    }
}
