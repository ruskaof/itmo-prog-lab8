package com.ruskaof.common.commands;

import com.ruskaof.common.data.User;
import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.DataManager;
import com.ruskaof.common.util.HistoryManager;

import java.util.Objects;

/**
 * This command doesn't even need a user to use correct login+password because it doesn't check on server side
 */
public class RegisterCommand implements Command {
    private final String login;
    private final String password;

    public RegisterCommand(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public CommandResultDto execute(
            DataManager dataManager,
            HistoryManager historyManager,
            String username
    ) {
        if (dataManager.checkIfUsernameUnique(login)) {
            dataManager.addUser(new User(-1, login, password));
            return new RegisterCommand.RegisterCommandResult(true);
        } else {
            return new RegisterCommand.RegisterCommandResult(false);
        }
    }

    public static class RegisterCommandResult extends CommandResultDto {
        private final boolean wasRegistered;

        public RegisterCommandResult(boolean wasRegistered) {
            super(wasRegistered);
            this.wasRegistered = wasRegistered;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            RegisterCommandResult that = (RegisterCommandResult) o;
            return wasRegistered == that.wasRegistered;
        }

        @Override
        public int hashCode() {
            return Objects.hash(wasRegistered);
        }

        public boolean isWasRegistered() {
            return wasRegistered;
        }

        @Override
        public String toString() {
            return "RegisterCommandResult{"
                    + "wasRegistered=" + wasRegistered
                    + '}';
        }
    }
}
