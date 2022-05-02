package com.ruskaof.common.commands;

import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.CollectionManager;
import com.ruskaof.common.util.HistoryManager;


public class ShowCommand extends Command {


    public ShowCommand() {
        super("", "show");
    }

    @Override
    public CommandResultDto execute(
            CollectionManager collectionManager,
            HistoryManager historyManager
    ) {
//        historyManager.addNote(this.getName());
//        // Сортировка по имени как в тз
//        return new CommandResultDto((Serializable) collectionManager.getMainData().stream().sorted(Comparator.comparing(StudyGroup::getName)).collect(Collectors.toList()));

        return new CommandResultDto(")");
    }
}
