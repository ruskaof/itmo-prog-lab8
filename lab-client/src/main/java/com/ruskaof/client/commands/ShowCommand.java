package com.ruskaof.client.commands;

import com.ruskaof.client.data.StudyGroup;
import com.ruskaof.client.utility.CollectionManager;
import com.ruskaof.client.utility.FileManager;



public class ShowCommand extends Command {
    private final CollectionManager collectionManager;

    public ShowCommand(CollectionManager collectionManager) {
        super("show");
        this.collectionManager = collectionManager;
    }

    @Override
    public CommandResult execute(String arg) {
        StringBuilder output = new StringBuilder();
        if (collectionManager.getMainData().isEmpty()) return new CommandResult(false, true, "Collection is empty");
        for (StudyGroup studyGroup : collectionManager.getMainData()) {
            output.append(studyGroup.toString()).append("\n\n");
        }

        output.setLength(output.length() - 2);
        return new CommandResult(false, true, output.toString());
    }
}
