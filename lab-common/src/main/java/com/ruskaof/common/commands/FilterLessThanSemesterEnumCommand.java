package com.ruskaof.common.commands;


import com.ruskaof.common.data.Semester;
import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.CollectionManager;
import com.ruskaof.common.util.HistoryManager;

public class FilterLessThanSemesterEnumCommand extends Command {

    public FilterLessThanSemesterEnumCommand(String arg) {
        super(arg, "filter_less_than_semester_enum");
    }

    @Override
    public CommandResultDto execute(
            CollectionManager collectionManager,
            HistoryManager historyManager
    ) {
        historyManager.addNote(this.getName());
        Semester inpEnum;
        try {
            if (arg.equals("null")) {
                inpEnum = Semester.THIRD;
            } else {
                inpEnum = Semester.valueOf((String) arg);
            }
        } catch (IllegalArgumentException e) {
            return new CommandResultDto("Your argument was incorrect");
        }
        return new CommandResultDto(collectionManager.filterLessThanSemesterEnumToString(inpEnum));

    }
}
