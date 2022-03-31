package com.ruskaof.common.commands;

import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.CollectionManager;
import com.ruskaof.common.util.HistoryManager;

public class HistoryCommand extends Command {
    public HistoryCommand() {
        super("", "history");
    }

    @Override
    public CommandResultDto execute(
            CollectionManager collectionManager,
            HistoryManager historyManager
    ) {
        // stream api would not help
        return new CommandResultDto(historyManager.niceToString());
    }
}
