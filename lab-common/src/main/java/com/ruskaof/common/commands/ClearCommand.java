//package com.ruskaof.common.commands;
//
//import com.ruskaof.common.dto.CommandResultDto;
//import com.ruskaof.common.util.DataManager;
//import com.ruskaof.common.util.HistoryManager;
//
///**
// * Небольшое уточнение: команды {@link ClearCommand}, {@link RemoveGreaterCommand} выполнятся всегда,
// * а вот команды {@link RemoveByIdCommand}, {@link UpdateCommand} не выполнятся, если
// * клиент пытается взаимодействовать с объектами, которые ему не принадлежат
// * именно поэтому clear, remove greater не имплементируют {@link PrivateAccessedStudyGroupCommand}
// */
//public class ClearCommand extends Command {
//    @Override
//    public CommandResultDto execute(
//            DataManager dataManager,
//            HistoryManager historyManager,
//            String username
//    ) {
//        dataManager.clearOwnedData(username);
//        return new CommandResultDto(true);
//    }
//
//    @Override
//    public CommandResultDto execute(DataManager dataManager, HistoryManager historyManager) {
//        return null;
//    }
//}
