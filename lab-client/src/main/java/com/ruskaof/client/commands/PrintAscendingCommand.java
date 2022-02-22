package com.ruskaof.client.commands;

import com.ruskaof.client.data.StudyGroup;
import com.ruskaof.client.utility.CollectionManager;
import com.ruskaof.client.utility.FileManager;


public class PrintAscendingCommand extends Command {
    public PrintAscendingCommand(CollectionManager collectionManager) {
        super("print_ascending");
        this.collectionManager = collectionManager;
    }

    private final CollectionManager collectionManager;

    @Override
    public CommandResult execute(String arg) {
        StringBuilder output = new StringBuilder();

        for (StudyGroup studyGroup : collectionManager.getMainData()) {
            output.append(studyGroup.toString()).append("\n\n");
        }

        output.setLength(output.length() - 2);
        return new CommandResult(false, true, output.toString());
    }
}
