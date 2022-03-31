package com.ruskaof.common.commands;

import com.ruskaof.common.data.StudyGroup;
import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.CollectionManager;
import com.ruskaof.common.util.HistoryManager;

import java.io.Serializable;
import java.util.Comparator;
import java.util.stream.Collectors;


public class ShowCommand extends Command {


    public ShowCommand() {
        super("", "show");
    }

    @Override
    public CommandResultDto execute(
            CollectionManager collectionManager,
            HistoryManager historyManager
    ) {
        // Сортировка по имени как в тз
        return new CommandResultDto((Serializable) collectionManager.getMainData().stream().sorted(Comparator.comparing(StudyGroup::getName)).collect(Collectors.toList()));
    }
}
