package com.ruskaof.client.commands;


import com.ruskaof.client.data.StudyGroup;
import com.ruskaof.client.utility.CollectionManager;
import com.ruskaof.client.utility.OutputManager;
import com.ruskaof.client.utility.StudyGroupMaker;
import com.ruskaof.client.utility.UserInputManager;


public class AddCommand extends Command {
    private final UserInputManager userInputManager;
    private final OutputManager outputManager;
    private final CollectionManager collectionManager;

    public AddCommand(CollectionManager collectionManager, UserInputManager userInputManager, OutputManager outputManager) {
        super("add");
        this.collectionManager = collectionManager;
        this.userInputManager = userInputManager;
        this.outputManager = outputManager;
    }

    @Override
    public CommandResult execute(String arg) {
        StudyGroup studyGroup = new StudyGroupMaker(userInputManager, outputManager, collectionManager).makeStudyGroup();
        collectionManager.getMainData().add(studyGroup);
        return new CommandResult(false, true, "The element was added successfully");
    }
}
