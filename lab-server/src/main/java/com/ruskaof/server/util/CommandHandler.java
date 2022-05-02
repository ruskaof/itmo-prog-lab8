package com.ruskaof.server.util;

import com.ruskaof.common.commands.RegisterCommand;
import com.ruskaof.common.dto.CommandFromClientDto;
import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.CollectionManager;
import com.ruskaof.common.util.HistoryManager;

public class CommandHandler {
    public CommandResultDto handleCommand(
            CommandFromClientDto commandFromClientDto,
            HistoryManager historyManager,
            CollectionManager collectionManager
    ) {
        if (collectionManager.validateUser(commandFromClientDto.getLogin(), commandFromClientDto.getPassword())) {
            return commandFromClientDto.getCommand().execute(collectionManager, historyManager);
        } else {
            return new CommandResultDto("Invalid login or password. Command was not executed");
        }
    }

    public CommandResultDto handleRegister(
            RegisterCommand registerCommand,
            HistoryManager historyManager,
            CollectionManager collectionManager
    ) {
        return registerCommand.execute(collectionManager, historyManager);
    }
}
