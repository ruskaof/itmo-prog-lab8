package com.ruskaof.common.commands;

import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.DataManager;
import com.ruskaof.common.util.HistoryManager;

public class ValidateCommand extends Command implements LoginNeedlessCommand {

    public ValidateCommand(String username, String password) {
        super(username, password, "validate");
    }

    @Override
    public CommandResultDto execute(DataManager dataManager, HistoryManager historyManager) {
        return new ValidateCommandResult(
                dataManager.validateUser(this.getUsername(), this.getPassword()),
                dataManager.getUserColor(this.getUsername()));
    }

    public static class ValidateCommandResult extends CommandResultDto {
        private final boolean loginAndPasswordCorrect;
        private final String color;

        public ValidateCommandResult(boolean loginAndPasswordCorrect, String color) {
            super(true, "");
            this.loginAndPasswordCorrect = loginAndPasswordCorrect;
            this.color = color;
        }

        public String getColor() {
            return color;
        }

        public boolean isLoginAndPasswordCorrect() {
            return loginAndPasswordCorrect;
        }
    }

}
