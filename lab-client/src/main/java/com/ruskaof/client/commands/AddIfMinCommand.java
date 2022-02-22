package com.ruskaof.client.commands;

import com.ruskaof.client.data.StudyGroup;
import com.ruskaof.client.utility.CollectionManager;
import com.ruskaof.client.utility.OutputManager;
import com.ruskaof.client.utility.StudyGroupMaker;
import com.ruskaof.client.utility.UserInputManager;


public class AddIfMinCommand extends Command {
    final private OutputManager outputManager;
    final private CollectionManager collectionManager;
    final private UserInputManager userInputManager;

    public AddIfMinCommand(CollectionManager collectionManager, UserInputManager userInputManager, OutputManager outputManager) {
        super("add_if_min");
        this.collectionManager = collectionManager;
        this.userInputManager = userInputManager;
        this.outputManager = outputManager;
    }

    @Override
    public CommandResult execute(String arg) {
        StudyGroup studyGroup = new StudyGroupMaker(userInputManager, outputManager, collectionManager).makeStudyGroup();

        if (collectionManager.getMainData().isEmpty() || studyGroup.compareTo(collectionManager.getMainData().first()) < 0) {
            collectionManager.getMainData().add(studyGroup);
            return new CommandResult(false, true, "The element was added successfully");
        } else {
            return new CommandResult(false, true, "The element was not min, so it was not added");
        }
    }
}
