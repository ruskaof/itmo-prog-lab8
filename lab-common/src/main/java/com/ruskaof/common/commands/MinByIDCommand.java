package com.ruskaof.common.commands;

import com.ruskaof.common.data.StudyGroup;
import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.CollectionManager;
import com.ruskaof.common.util.HistoryManager;

import java.util.Comparator;


public class MinByIDCommand extends Command {

    public MinByIDCommand(String arg) {
        super(arg, "min_by_id");
    }

    @Override
    public CommandResultDto execute(
            CollectionManager collectionManager,
            HistoryManager historyManager
    ) {
        historyManager.addNote(this.getName());
        // STREAM API!!
        StudyGroup minIdStudyGroup = collectionManager.getMainData().stream().min(Comparator.comparingInt(StudyGroup::getId)).orElse(null);

//        for (StudyGroup studyGroup : collectionManager.getMainData()) {
//            if (minID > studyGroup.getId()) {
//                minID = studyGroup.getId();
//                minIdStudyGroup = studyGroup;
//            }
//        }

        if (minIdStudyGroup == null) {
            return new CommandResultDto("Collection is empty");
        } else {
            return new CommandResultDto(minIdStudyGroup);
        }
    }
}
