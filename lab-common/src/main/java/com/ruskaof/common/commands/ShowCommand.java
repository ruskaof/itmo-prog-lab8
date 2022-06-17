package com.ruskaof.common.commands;

import com.ruskaof.common.data.StudyGroup;
import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.DataManager;
import com.ruskaof.common.util.HistoryManager;

import java.util.List;


public class ShowCommand extends Command {

    public ShowCommand(String username, String password) {
        super(username, password, "show");
    }

    @Override
    public CommandResultDto execute(
            DataManager dataManager,
            HistoryManager historyManager
    ) {
        historyManager.addNote(getName());
        return new ShowCommandResult(true, dataManager.showSortedByName());
    }

    public static class ShowCommandResult extends CommandResultDto {
        private final List<StudyGroup> data;

        public ShowCommandResult(boolean wasExecutedCorrectly, List<StudyGroup> data) {
            super(wasExecutedCorrectly, data.toString());
            this.data = data;
        }

        public List<StudyGroup> getData() {
            return data;
        }
    }
}
