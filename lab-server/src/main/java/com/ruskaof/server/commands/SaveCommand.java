package com.ruskaof.server.commands;

import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.CollectionManager;
import com.ruskaof.common.util.JsonParser;
import com.ruskaof.server.util.FileManager;

import java.io.FileNotFoundException;

public class SaveCommand {
    public CommandResultDto execute(FileManager fileManager, CollectionManager collectionManager) {
        try {
            fileManager.save(new JsonParser().serialize(collectionManager.getMainData()));
        } catch (FileNotFoundException e) {
            return new CommandResultDto("There was a problem saving a file. Please restart the program with another one");
        }
        return new CommandResultDto("The data was saved successfully");
    }
}
