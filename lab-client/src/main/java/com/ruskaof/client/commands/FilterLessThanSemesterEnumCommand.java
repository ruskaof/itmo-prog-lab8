package com.ruskaof.client.commands;


import com.ruskaof.client.data.Semester;
import com.ruskaof.client.data.StudyGroup;
import com.ruskaof.client.utility.CollectionManager;

import java.util.StringJoiner;

public class FilterLessThanSemesterEnumCommand extends Command {

    private final CollectionManager collectionManager;

    public FilterLessThanSemesterEnumCommand(CollectionManager collectionManager) {
        super("filter_less_than_semester_enum");
        this.collectionManager = collectionManager;
    }

    @Override
    public CommandResult execute(String arg) {
        StringJoiner output = new StringJoiner("\n\n");
        Semester inpEnum;
        try {
            inpEnum = Semester.valueOf(arg);
        } catch (IllegalArgumentException e) {
            return new CommandResult(false, "Your argument was incorrect");
        }

        for (StudyGroup studyGroup : collectionManager.getMainData()) {
            Semester semesterEnum = studyGroup.getSemesterEnum();
            if (semesterEnum == null) {
                semesterEnum = Semester.THIRD;
            }
            if (semesterEnum.compareTo(inpEnum) < 0) {
                output.add(studyGroup.toString());
            }
        }

        return new CommandResult(false, output.toString());
    }
}
