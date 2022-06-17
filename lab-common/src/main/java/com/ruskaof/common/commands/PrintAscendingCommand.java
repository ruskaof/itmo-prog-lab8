package com.ruskaof.common.commands;

import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.DataManager;
import com.ruskaof.common.util.HistoryManager;


public class PrintAscendingCommand extends Command {

    public PrintAscendingCommand(String username, String password) {
        super(username, password, "print_ascending");
    }

    @Override
    public CommandResultDto execute(DataManager dataManager, HistoryManager historyManager) {
        historyManager.addNote(this.getName());

        return new CommandResultDto(true, dataManager.ascendingDataToString());
    }
}
