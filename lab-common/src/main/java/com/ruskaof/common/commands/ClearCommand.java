package com.ruskaof.common.commands;

import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.CollectionManager;
import com.ruskaof.common.util.HistoryManager;

public class ClearCommand extends Command {
    public ClearCommand() {
        super("", "clear");
    }

    @Override
    public CommandResultDto execute(
            CollectionManager collectionManager,
            HistoryManager historyManager
    ) {

        // stream api would not help
        collectionManager.getMainData().clear();
        return new CommandResultDto("The collection was cleared successfully.");
    }
}
