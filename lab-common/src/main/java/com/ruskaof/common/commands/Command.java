package com.ruskaof.common.commands;

import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.DataManager;
import com.ruskaof.common.util.HistoryManager;

import java.io.Serializable;


public interface  Command extends Serializable {
    public abstract CommandResultDto execute(
            DataManager dataManager,
            HistoryManager historyManager,
            String username
    );
}
