package com.ruskaof.client.commands;

import com.ruskaof.client.data.StudyGroup;
import com.ruskaof.client.utility.*;

public class RemoveGreaterCommand extends Command {
    public RemoveGreaterCommand(CollectionManager collectionManager, UserInputManager userInputManager
            , OutputManager outputManager) {
        super("remove_greater");
        this.outputManager = outputManager;
        this.collectionManager = collectionManager;
        this.userInputManager = userInputManager;
    }

    private final OutputManager outputManager;
    private final CollectionManager collectionManager;
    private final UserInputManager userInputManager;

    @Override
    public CommandResult execute(String arg) {
        StudyGroup studyGroup = new StudyGroupMaker(userInputManager, outputManager, collectionManager).makeStudyGroup();
        return new CommandResult(false, true, "redo");
    }
}
