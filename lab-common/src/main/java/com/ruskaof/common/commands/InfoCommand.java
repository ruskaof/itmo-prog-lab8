package com.ruskaof.common.commands;

import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.CollectionManager;
import com.ruskaof.common.util.HistoryManager;


public class InfoCommand extends Command {

    public InfoCommand() {
        super("", "info");
    }

    @Override
    public CommandResultDto execute(
            CollectionManager collectionManager,
            HistoryManager historyManager
    ) {
        // Stream api would not help
        if (!collectionManager.getMainData().isEmpty()) {
            return new CommandResultDto("Collection type: " + collectionManager.getMainData().getClass().toString() + "\n"
                    + "Number of elements: " + collectionManager.getMainData().size() + "\n"
                    + "Creation date: " + collectionManager.getCreationDate() + "\n"
                    + "The biggest element has studentsCount = " + collectionManager.getMainData().last().getStudentsCount());
        } else {
            return new CommandResultDto("Collection type: " + collectionManager.getMainData().getClass().toString() + "\n"
                    + "Number of elements: " + collectionManager.getMainData().size() + "\n"
                    + "Creation date: " + collectionManager.getCreationDate());
        }
    }
}
