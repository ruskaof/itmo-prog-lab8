package com.ruskaof.client.commands;

import com.ruskaof.client.data.StudyGroup;
import com.ruskaof.client.utility.CollectionManager;
import com.ruskaof.client.utility.FileManager;


public class RemoveByIdCommand extends Command {
    private final CollectionManager collectionManager;

    public RemoveByIdCommand(CollectionManager collectionManager) {
        super("remove_by_id");
        this.collectionManager = collectionManager;
    }

    @Override
    public CommandResult execute(String arg) {
        int intArg;
        try {
            intArg = Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            return new CommandResult(false, true, "redo");
        }

        for (StudyGroup studyGroup : collectionManager.getMainData()) {
            if (studyGroup.getId() == intArg) {
                collectionManager.getMainData().remove(studyGroup);
                return new CommandResult(false, true, "redo");
            }
        }
        return new CommandResult(false, true, "redo");
    }
}
