package com.ruskaof.common.commands;

import com.ruskaof.common.data.StudyGroup;
import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.DataManager;
import com.ruskaof.common.util.HistoryManager;

/**
 * Небольшое уточнение: команды {@link ClearCommand}, {@link RemoveGreaterCommand} выполнятся всегда,
 * а вот команды {@link RemoveByIdCommand}, {@link UpdateCommand} не выполнятся, если
 * клиент пытается взаимодействовать с объектами, которые ему не принадлежат
 * именно поэтому clear, remove greater не имплементируют {@link PrivateAccessedStudyGroupCommand}
 */
public class RemoveGreaterCommand extends Command {
    private final StudyGroup arg;

    public RemoveGreaterCommand(StudyGroup arg) {
        super("remove_greater");
        this.arg = arg;
    }

    @Override
    public CommandResultDto execute(
            DataManager dataManager,
            HistoryManager historyManager,
            String username
    ) {
        historyManager.addNote(this.getName());

        dataManager.removeGreaterIfOwned(arg, username);

        return new CommandResultDto("Successfully removed greater elements", true);
    }
}
