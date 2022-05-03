package com.ruskaof.common.commands;

import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.CollectionManager;
import com.ruskaof.common.util.HistoryManager;


public class ShowCommand extends Command {


    public ShowCommand() {
        super("show");
    }

    @Override
    public CommandResultDto execute(
            CollectionManager collectionManager,
            HistoryManager historyManager,
            String username
    ) {
        historyManager.addNote(this.getName());

        return new CommandResultDto(collectionManager.showSortedByName());
    }
}
