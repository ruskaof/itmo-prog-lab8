package com.ruskaof.common.commands;

import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.DataManager;
import com.ruskaof.common.util.HistoryManager;

public class ClearCommand extends Command {
    public ClearCommand(String username, String password) {
        super(username, password);
    }


    @Override
    public CommandResultDto execute(DataManager dataManager, HistoryManager historyManager) {
        dataManager.clearOwnedData(getUsername());
        return new CommandResultDto(true);
    }
}
