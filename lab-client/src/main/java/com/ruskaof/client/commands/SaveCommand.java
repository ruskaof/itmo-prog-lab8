package com.ruskaof.client.commands;

import com.ruskaof.client.utility.CollectionManager;
import com.ruskaof.client.utility.FileManager;
import com.ruskaof.client.utility.Parser;

public class SaveCommand extends Command {
    private final FileManager fileManager;
    private final CollectionManager collectionManager;

    public SaveCommand(FileManager fileManager, CollectionManager collectionManager) {
        super("save");
        this.fileManager = fileManager;
        this.collectionManager = collectionManager;
    }

    @Override
    public CommandResult execute(String arg) {
        fileManager.save(new Parser().serialize(collectionManager.getMainData()));
        return new CommandResult(false, true, "The data was saved successfully");
    }
}
