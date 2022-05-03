package com.ruskaof.server.executing;

import com.ruskaof.common.commands.PrivateAccessedStudyGroupCommand;
import com.ruskaof.common.commands.RegisterCommand;
import com.ruskaof.common.dto.CommandFromClientDto;
import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.DataManager;
import com.ruskaof.common.util.HistoryManager;

public class CommandHandler {
    public CommandResultDto handleCommand(
            CommandFromClientDto commandFromClientDto,
            HistoryManager historyManager,
            DataManager dataManager
    ) {
        if (dataManager.validateUser(commandFromClientDto.getLogin(), commandFromClientDto.getPassword())) {
            if (commandFromClientDto.getCommand() instanceof PrivateAccessedStudyGroupCommand) {
                final int id = ((PrivateAccessedStudyGroupCommand) commandFromClientDto.getCommand()).getStudyGroupId();
                if (dataManager.validateOwner(commandFromClientDto.getLogin(), id)) {
                    return commandFromClientDto.getCommand().execute(dataManager, historyManager, commandFromClientDto.getLogin());
                } else {
                    return new CommandResultDto("You are not the owner of the object so you can't do anything with it");
                }
            } else if (commandFromClientDto.getCommand() instanceof RegisterCommand) {
                return commandFromClientDto.getCommand().execute(dataManager, historyManager, "zatichka");
            }
            return commandFromClientDto.getCommand().execute(dataManager, historyManager, commandFromClientDto.getLogin());
        } else {
            return new CommandResultDto("Invalid login or password. Command was not executed");
        }
    }

    public CommandResultDto handleRegister(
            RegisterCommand registerCommand,
            HistoryManager historyManager,
            DataManager dataManager
    ) {
        return registerCommand.execute(dataManager, historyManager, "zatichka");
    }
}
