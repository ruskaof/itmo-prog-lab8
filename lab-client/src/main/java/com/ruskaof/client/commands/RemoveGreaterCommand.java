package com.ruskaof.client.commands;

import com.ruskaof.client.data.StudyGroup;
import com.ruskaof.client.utility.FileManager;
import com.ruskaof.client.utility.StudyGroupMaker;
import com.ruskaof.client.utility.UserInputManager;

import java.util.Set;
import java.util.TreeSet;

public class RemoveGreaterCommand extends Command {
    public RemoveGreaterCommand(FileManager fileManager, UserInputManager userInputManager) {
        super("remove_greater");
        this.fileManager = fileManager;
        this.userInputManager = userInputManager;
    }

    private final FileManager fileManager;
    private final UserInputManager userInputManager;

    @Override
    public CommandResult execute(String arg) {
//        Integer count = 0;
//        StudyGroup studyGroup = new StudyGroupMaker(userInputManager).makeStudyGroup();
//        Set<StudyGroup> iterData = new TreeSet<>(fileManager.getMainData());
//        for (StudyGroup checkingStudyGroup : iterData) {
//            if (studyGroup.compareTo(checkingStudyGroup) < 0) {
//                fileManager.removeFromDataWithID(checkingStudyGroup);
//                count++;
//            }
//        }
        return new CommandResult(false, true, "redo");
    }
}
