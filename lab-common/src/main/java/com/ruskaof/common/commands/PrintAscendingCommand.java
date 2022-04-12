package com.ruskaof.common.commands;

import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.CollectionManager;
import com.ruskaof.common.util.HistoryManager;


public class PrintAscendingCommand extends Command {

    public PrintAscendingCommand() {
        super("", "print_ascending");
    }

    @Override
    public CommandResultDto execute(
            CollectionManager collectionManager,
            HistoryManager historyManager
    ) {
        historyManager.addNote(this.getName());
        // Stream api would not help (Tree set is already sorted)
        return new CommandResultDto(collectionManager.getMainData());
    }
}
