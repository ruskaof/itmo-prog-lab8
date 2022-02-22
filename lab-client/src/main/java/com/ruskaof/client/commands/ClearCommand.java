package com.ruskaof.client.commands;


import com.ruskaof.client.data.StudyGroup;
import com.ruskaof.client.utility.CollectionManager;

public class ClearCommand extends Command {
    private final CollectionManager collectionManager;

    public ClearCommand(CollectionManager collectionManager) {
        super("clear");
        this.collectionManager = collectionManager;
    }

    @Override
    public CommandResult execute(String arg) {
        StudyGroup.usedIDs.clear();
        collectionManager.getMainData().clear();
        return new CommandResult(false, true, "The collection was cleared successfully.");
    }
}
