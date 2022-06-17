package com.ruskaof.server.connection.tcp;

import com.ruskaof.common.commands.Command;
import com.ruskaof.common.commands.LoginNeedlessCommand;
import com.ruskaof.common.commands.PrivateAccessedStudyGroupCommand;
import com.ruskaof.common.dto.CommandFromClientDto;
import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.DataManager;
import com.ruskaof.common.util.HistoryManager;
import com.ruskaof.server.util.Logger;

public class CommandHandler {
    private final DataManager dataManager;
    private final HistoryManager historyManager;

    public CommandHandler(DataManager dataManager, HistoryManager historyManager) {
        this.dataManager = dataManager;
        this.historyManager = historyManager;
    }

    public CommandResultDto handle(CommandFromClientDto commandFromClientDto) {
        final Command command = commandFromClientDto.getCommand();
        if (command instanceof LoginNeedlessCommand) {
            Logger.log("The command does need valid user");
            return command.execute(dataManager, historyManager);
        }
        if (dataManager.validateUser(command.getUsername(), command.getPassword())
                && command instanceof PrivateAccessedStudyGroupCommand
                && dataManager.validateOwner(command.getUsername(), ((PrivateAccessedStudyGroupCommand) command).getStudyGroupId()) || dataManager.validateUser(command.getUsername(), command.getPassword()) && !(command instanceof PrivateAccessedStudyGroupCommand)) {
            return command.execute(dataManager, historyManager);
        } else {
            Logger.log("The client was not the owner of a study group they wanted to update or was not validated");
            return new CommandResultDto(false, "You are not the owner so command was not executed");
        }
//        if (dataManager.validateUser(command.getUsername(), command.getPassword())) {
//            if (commandFromClientDto.getCommand() instanceof PrivateAccessedStudyGroupCommand) {
//                final int id = ((PrivateAccessedStudyGroupCommand) command).getStudyGroupId();
//                if (dataManager.validateOwner(command.getUsername(), id)) {
//                    return command.execute(dataManager, historyManager);
//                } else {
//                    Logger.log("The client was not the owner of a study group they wanted to update");
//                    return new CommandResultDto(false);
//                }
//            } else {
//                return command.execute(dataManager, historyManager);
//            }
//        } else {
//            Logger.log("The command from client was not with valid login or id");
//            return new CommandResultDto(false);
//        }
    }
}
