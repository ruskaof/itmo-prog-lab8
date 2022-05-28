package com.ruskaof.common.commands;

import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.DataManager;
import com.ruskaof.common.util.HistoryManager;

public class ValidateCommand extends Command implements LoginNeedlessCommand {

    public ValidateCommand(String username, String password) {
        super(username, password);
    }

    @Override
    public CommandResultDto execute(DataManager dataManager, HistoryManager historyManager) {
        return new ValidateCommandResult(
                dataManager.validateUser(this.getUsername(), this.getPassword())
        );
    }

    public static class ValidateCommandResult extends CommandResultDto {
        private final boolean loginAndPasswordCorrect;

        public ValidateCommandResult(boolean loginAndPasswordCorrect) {
            super(true);
            this.loginAndPasswordCorrect = loginAndPasswordCorrect;
        }

        public boolean isLoginAndPasswordCorrect() {
            return loginAndPasswordCorrect;
        }
    }

}
