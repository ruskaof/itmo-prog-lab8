package com.ruskaof.common.commands;


import com.ruskaof.common.data.Semester;
import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.DataManager;
import com.ruskaof.common.util.HistoryManager;

public class FilterLessThanSemesterEnumCommand extends Command {
    private final String arg;

    public FilterLessThanSemesterEnumCommand(String arg) {
        super("filter_less_than_semester_enum");
        this.arg = arg;
    }

    @Override
    public CommandResultDto execute(
            DataManager dataManager,
            HistoryManager historyManager,
            String username
    ) {
        historyManager.addNote(this.getName());
        Semester inpEnum;
        try {
            if ("null".equals(arg)) {
                inpEnum = Semester.THIRD;
            } else {
                inpEnum = Semester.valueOf(arg);
            }
        } catch (IllegalArgumentException e) {
            return new CommandResultDto("Your argument was incorrect");
        }
        return new CommandResultDto(dataManager.filterLessThanSemesterEnumToString(inpEnum));

    }
}
