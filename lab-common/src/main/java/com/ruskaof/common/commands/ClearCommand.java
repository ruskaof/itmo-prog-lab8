package com.ruskaof.common.commands;

import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.DataManager;
import com.ruskaof.common.util.HistoryManager;

/**
 * Небольшое уточнение: команды {@link ClearCommand}, {@link RemoveGreaterCommand} выполнятся всегда,
 * а вот команды {@link RemoveByIdCommand}, {@link UpdateCommand} не выполнятся, если
 * клиент пытается взаимодействовать с объектами, которые ему не принадлежат
 * именно поэтому clear, remove greater не имплементируют {@link PrivateAccessedStudyGroupCommand}
 */
public class ClearCommand extends Command {
    public ClearCommand() {
        super("clear");
    }

    @Override
    public CommandResultDto execute(
            DataManager dataManager,
            HistoryManager historyManager,
            String username
    ) {
        historyManager.addNote(this.getName());
        dataManager.clearOwnedData(username);
        return new CommandResultDto("The data you owned was cleared successfully.", true);
    }
}
