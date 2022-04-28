package com.ruskaof.server.util;

import com.ruskaof.common.dto.CommandFromClientDto;
import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.CollectionManager;
import com.ruskaof.common.util.HistoryManager;
import com.ruskaof.server.data.remote.repository.posturesql.Database;

public class CommandHandler {
    public CommandResultDto handle(
            CommandFromClientDto commandFromClientDto,
            HistoryManager historyManager,
            CollectionManager collectionManager,
            Database database
    ) {
        if (database.getUsersTable().validate(commandFromClientDto.getLogin(), commandFromClientDto.getPassword())) {
            return commandFromClientDto.getCommand().execute(collectionManager, historyManager);
        } else {
            return new CommandResultDto("Invalid login or password. Command was not executed");
        }
    }
}
