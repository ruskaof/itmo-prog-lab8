package com.ruskaof.server.connection.tcp;

import com.ruskaof.common.commands.Command;
import com.ruskaof.common.commands.LoginNeedlessCommand;
import com.ruskaof.common.commands.PrivateAccessedStudyGroupCommand;
import com.ruskaof.common.dto.CommandFromClientDto;
import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.DataManager;
import com.ruskaof.common.util.HistoryManager;

public class CommandHandler {
    private final DataManager dataManager;
    private final HistoryManager historyManager;

    public CommandHandler(DataManager dataManager, HistoryManager historyManager) {
        this.dataManager = dataManager;
        this.historyManager = historyManager;
    }

    public CommandResultDto handle(CommandFromClientDto commandFromClientDto) {
        final Command command = commandFromClientDto.getCommand();

        if (dataManager.validateUser(command.getUsername(), command.getPassword())) {
            if (commandFromClientDto.getCommand() instanceof PrivateAccessedStudyGroupCommand) {
                final int id = ((PrivateAccessedStudyGroupCommand) command).getStudyGroupId();
                if (dataManager.validateOwner(command.getUsername(), id)) {
                    return command.execute(dataManager, historyManager);
                } else {
                    return new CommandResultDto(false);
                }
            } else {
                return command.execute(dataManager, historyManager);
            }
        } else if (command instanceof LoginNeedlessCommand) {
            return command.execute(dataManager, historyManager);
        } else {
            return new CommandResultDto(false);
        }
    }
}
