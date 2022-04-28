package com.ruskaof.server.util;

import com.ruskaof.common.commands.Command;
import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.CollectionManager;
import com.ruskaof.common.util.HistoryManager;

public class CommandHandler {
    public CommandResultDto handle(Command command, HistoryManager historyManager, CollectionManager collectionManager) {
        return command.execute(collectionManager, historyManager);
    }
}
