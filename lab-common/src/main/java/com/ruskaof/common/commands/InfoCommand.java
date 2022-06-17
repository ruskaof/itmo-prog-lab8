package com.ruskaof.common.commands;

import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.DataManager;
import com.ruskaof.common.util.HistoryManager;


public class InfoCommand extends Command {

    public InfoCommand(String username, String password) {
        super(username, password, "info");
    }

    @Override
    public CommandResultDto execute(DataManager dataManager, HistoryManager historyManager) {
        historyManager.addNote(this.getName());
        return new CommandResultDto(true, dataManager.getStringInfo());
    }

}
