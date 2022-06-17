package com.ruskaof.common.commands;


import com.ruskaof.common.data.Semester;
import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.DataManager;
import com.ruskaof.common.util.HistoryManager;

public class FilterLessThanSemesterEnumCommand extends Command {
    private final String theEnum;

    public FilterLessThanSemesterEnumCommand(String username, String password, String theEnum) {
        super(username, password, "filter_less_than_semester_enum");
        this.theEnum = theEnum;
    }


    @Override
    public CommandResultDto execute(DataManager dataManager, HistoryManager historyManager) {
        historyManager.addNote(getName());
        Semester inpEnum;
        try {
            if ("null".equals(theEnum)) {
                inpEnum = Semester.THIRD;
            } else {
                inpEnum = Semester.valueOf(theEnum);
            }
        } catch (IllegalArgumentException e) {
            return new CommandResultDto(false, "Your argument was incorrect");
        }
        return new CommandResultDto(true, dataManager.filterLessThanSemesterEnumToString(inpEnum));
    }
}
