package com.ruskaof.client.commands;

import com.ruskaof.client.data.StudyGroup;
import com.ruskaof.client.utility.CollectionManager;
import com.ruskaof.client.utility.OutputManager;
import com.ruskaof.client.utility.StudyGroupMaker;
import com.ruskaof.client.utility.UserInputManager;

public class UpdateCommand extends Command {
    public UpdateCommand(CollectionManager collectionManager, UserInputManager userInputManager, OutputManager outputManager) {
        super("update");
        this.userInputManager = userInputManager;
        this.collectionManager = collectionManager;
        this.outputManager = outputManager;
    }

    private final OutputManager outputManager;
    private final UserInputManager userInputManager;
    private final CollectionManager collectionManager;

    @Override
    public CommandResult execute(String arg) {
        int intArg;
        try {
            intArg = Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            return new CommandResult(false, "Your argument was incorrect. The command was not executed.");
        }

        if (collectionManager.getMainData().removeIf(x -> x.getId() == intArg)) {
            StudyGroup studyGroup = new StudyGroupMaker(userInputManager, outputManager, collectionManager).makeStudyGroup();
            studyGroup.setId(intArg);
            collectionManager.getMainData().add(studyGroup);
            return new CommandResult(false, "The element was updated successfully");
        } else {
            return new CommandResult(false, "Written id was not found. The command was not executed");
        }
    }
}
