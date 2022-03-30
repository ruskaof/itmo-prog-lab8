package com.ruskaof.common.commands;

import com.ruskaof.common.data.StudyGroup;
import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.CollectionManager;
import com.ruskaof.common.util.HistoryManager;

import java.util.StringJoiner;


public class PrintAscendingCommand extends Command {

    public PrintAscendingCommand() {
        super("", "print_ascending");
    }

    @Override
    public CommandResultDto execute(
            CollectionManager collectionManager,
            HistoryManager historyManager
    ) {
        // Stream api would not help
        return new CommandResultDto(collectionManager.getMainData());
    }
}
