package com.ruskaof.common.commands;

import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.DataManager;
import com.ruskaof.common.util.HistoryManager;

public class RemoveByIdCommand extends Command implements PrivateAccessedStudyGroupCommand {
    private final String arg;

    public RemoveByIdCommand(String arg) {
        super("remove_by_id");
        this.arg = arg;
    }

    @Override
    public CommandResultDto execute(
            DataManager dataManager,
            HistoryManager historyManager,
            String username
    ) {

        historyManager.addNote(this.getName());

        int intArg;
        try {
            intArg = Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            return new CommandResultDto("Your argument was incorrect. The command was not executed.");
        }

        dataManager.removeStudyGroupById(intArg);

        return new CommandResultDto("The element was removed if it was in the data");
    }

    @Override
    public int getStudyGroupId() {
        try {
            return Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
