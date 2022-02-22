package com.ruskaof.client.commands;

import com.ruskaof.client.utility.CollectionManager;


public class InfoCommand extends Command {
    public InfoCommand(CollectionManager collectionManager) {
        super("info");
        this.collectionManager = collectionManager;
    }

    private final CollectionManager collectionManager;

    @Override
    public CommandResult execute(String arg) {
        if (!collectionManager.getMainData().isEmpty()) {
            return new CommandResult(false, "Collection type: " + collectionManager.getMainData().getClass().toString() + "\n" +
                    "Number of elements: " + collectionManager.getMainData().size() + "\n" +
                    "Creation date: " + collectionManager.getCreationDate() + "\n" +
                    "The biggest element has studentsCount = " + collectionManager.getMainData().last().getStudentsCount());
        } else {
            return new CommandResult(false, "Collection type: " + collectionManager.getMainData().getClass().toString() + "\n" +
                    "Number of elements: " + collectionManager.getMainData().size() + "\n" +
                    "Creation date: " + collectionManager.getCreationDate());
        }
    }
}
