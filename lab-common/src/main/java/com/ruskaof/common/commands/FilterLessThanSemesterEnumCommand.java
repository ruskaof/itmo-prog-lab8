package com.ruskaof.common.commands;


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
//        historyManager.addNote(this.getName());
//        StringJoiner output = new StringJoiner("\n\n");
//        Semester inpEnum;
//        try {
//            if (arg.equals("null")) {
//                inpEnum = Semester.THIRD;
//            } else {
//                inpEnum = Semester.valueOf((String) arg);
//            }
//        } catch (IllegalArgumentException e) {
//            return new CommandResultDto("Your argument was incorrect");
//        }
//        // STREAM API!!!
//        //collectionManager.getMainData().stream().filter(it -> it.getSemesterEnum().compareTo(inpEnum) < 0).forEach(it -> output.add(it.toString()));
//        for (StudyGroup studyGroup : collectionManager.getMainData()) {
//            Semester semesterEnum = studyGroup.getSemesterEnum();
//            if (semesterEnum == null) {
//                semesterEnum = Semester.THIRD;
//            }
//            if (semesterEnum.compareTo(inpEnum) < 0) {
//                output.add(studyGroup.toString());
//            }
//        }
//
//        return new CommandResultDto(output.toString());

        return new CommandResultDto(")");
    }
}
