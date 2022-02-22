package com.ruskaof.client.commands;

import com.ruskaof.client.data.StudyGroup;
import com.ruskaof.client.utility.CollectionManager;
import com.ruskaof.client.utility.FileManager;

import java.util.StringJoiner;


public class ShowCommand extends Command {
    private final CollectionManager collectionManager;

    public ShowCommand(CollectionManager collectionManager) {
        super("show");
        this.collectionManager = collectionManager;
    }

    @Override
    public CommandResult execute(String arg) {
        StringJoiner output = new StringJoiner("\n\n");
        if (collectionManager.getMainData().isEmpty()) return new CommandResult(false, true, "Collection is empty");
        for (StudyGroup studyGroup : collectionManager.getMainData()) {
            output.add(studyGroup.toString());
        }

        return new CommandResult(false, true, output.toString());
    }
}
