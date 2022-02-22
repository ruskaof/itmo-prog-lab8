package com.ruskaof.client.commands;

import com.ruskaof.client.data.StudyGroup;
import com.ruskaof.client.exceptions.IdAlreadyPresentException;
import com.ruskaof.client.utility.FileManager;
import com.ruskaof.client.utility.StudyGroupMaker;
import com.ruskaof.client.utility.UserInputManager;

public class UpdateCommand extends Command {
    public UpdateCommand(FileManager fileManager, UserInputManager userInputManager) {
        super("update");
        this.fileManager = fileManager;
        this.userInputManager = userInputManager;
    }

    private final FileManager fileManager;
    private final UserInputManager userInputManager;

    @Override
    public CommandResult execute(String arg) {
//        int intArg;
//        try {
//            intArg = Integer.parseInt(arg);
//        } catch (NumberFormatException e) {
//            return "Wrong argument. The command was not executed. Please enter integer id";
//        }
//
//        for (StudyGroup studyGroup : fileManager.getMainData()) {
//            if (studyGroup.getId() == intArg) {
//                try {
//                    fileManager.removeFromDataWithID(studyGroup);
//                    fileManager.addToDataWithID(new StudyGroupMaker(userInputManager).makeStudyGroupWithGivenId(intArg));
//                    return "The element was updated successfully";
//                } catch (IdAlreadyPresentException e) {
//                    return "Written id was already in the data. The command was not executed";
//                }
//            }
//        }
        return new CommandResult(false, true, "redo");
    }
}
