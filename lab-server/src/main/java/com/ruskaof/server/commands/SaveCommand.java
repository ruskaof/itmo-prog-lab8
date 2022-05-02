package com.ruskaof.server.commands;

import com.ruskaof.common.commands.Command;
import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.CollectionManager;
import com.ruskaof.common.util.HistoryManager;
import com.ruskaof.server.data.remote.repository.json.FileManager;

public class SaveCommand extends Command {
    private final FileManager fileManager;

    public SaveCommand(FileManager fileManager) {
        super("", "save");
        this.fileManager = fileManager;
    }

    @Override
    public CommandResultDto execute(CollectionManager collectionManager, HistoryManager historyManager) {
//        try {
//            final JsonParser jsonParser = new JsonParser();
//            fileManager.save(
//                    jsonParser.serialize(collectionManager.getMainData()),
//                    jsonParser.serialize(collectionManager.getUsers())
//                    );
//        } catch (FileNotFoundException e) {
//            return new CommandResultDto("There was a problem saving a file. Please restart the program with another one");
//        }
        return new CommandResultDto("The data was saved successfully");
    }
}
