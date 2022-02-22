package com.ruskaof.client.commands;


import com.ruskaof.client.data.Semester;
import com.ruskaof.client.data.StudyGroup;
import com.ruskaof.client.utility.CollectionManager;

import java.util.StringJoiner;

public class FilterLessThanSemesterEnumCommand extends Command {

    public FilterLessThanSemesterEnumCommand(CollectionManager collectionManager) {
        super("filter_less_than_semester_enum");
        this.collectionManager = collectionManager;
    }

    private final CollectionManager collectionManager;

    @Override
    public CommandResult execute(String arg) {
        StringJoiner output = new StringJoiner("\n\n");
        Semester inpEnum;
        try {
            inpEnum = Semester.valueOf(arg);
        } catch (Exception e) {
            return new CommandResult(false, true, "redo");
        }

        for (StudyGroup studyGroup : collectionManager.getMainData()) {
            if (studyGroup.getSemesterEnum().compareTo(inpEnum) > 0) {
                output.add(studyGroup.toString());
            }
        }

        return new CommandResult(false, true, output.toString());
    }
}
