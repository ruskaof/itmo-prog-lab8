package com.ruskaof.server.util;

import com.ruskaof.common.commands.PrivateAccessedStudyGroupCommand;
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
            if (commandFromClientDto.getCommand() instanceof PrivateAccessedStudyGroupCommand) {
                final int id = ((PrivateAccessedStudyGroupCommand) commandFromClientDto.getCommand()).getStudyGroupId();
                if (collectionManager.validateOwner(commandFromClientDto.getLogin(), id)) {
                    return commandFromClientDto.getCommand().execute(collectionManager, historyManager, commandFromClientDto.getLogin());
                } else {
                    return new CommandResultDto("You are not the owner of the object so you can't do anything with it");
                }
            }
            return commandFromClientDto.getCommand().execute(collectionManager, historyManager, commandFromClientDto.getLogin());
        } else {
            return new CommandResultDto("Invalid login or password. Command was not executed");
        }
    }

    public CommandResultDto handleRegister(
            RegisterCommand registerCommand,
            HistoryManager historyManager,
            CollectionManager collectionManager
    ) {
        return registerCommand.execute(collectionManager, historyManager, "zatichka");
    }
}
