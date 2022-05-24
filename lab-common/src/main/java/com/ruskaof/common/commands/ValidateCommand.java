package com.ruskaof.common.commands;

import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.DataManager;
import com.ruskaof.common.util.HistoryManager;

public class ValidateCommand implements Command{

    @Override
    public CommandResultDto execute(DataManager dataManager, HistoryManager historyManager, String username) {
        return dataManager.validateUser();
    }
}

class ValidateCommandResult extends CommandResultDto{
    private final boolean loginAndPasswordCorrect;

    public boolean isLoginAndPasswordCorrect() {
        return loginAndPasswordCorrect;
    }

    public ValidateCommandResult(boolean loginAndPasswordCorrect) {
        super(true );
        this.loginAndPasswordCorrect = loginAndPasswordCorrect;
    }
}